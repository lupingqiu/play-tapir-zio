package controllers

import javax.inject.{Inject, Singleton}
import models.{Book, RepoError, ValidateError}
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import services.BookService
import zio.{IO, ZIO}

import scala.concurrent.ExecutionContext

/**
  * Created by luping.qiu in 11:06 AM 2021/8/5
  */
@Singleton
class BookController @Inject()(bookService: BookService, val controllerComponents: ControllerComponents)(implicit ex:ExecutionContext) extends BaseController {

  import ZIOAction._

  private def jsonValidation[A](jsValue: JsValue)(implicit reads: Reads[A]) =
    IO.fromEither(jsValue.validate[A].asEither).mapError(e => ValidateError(e.toString))


  def getById(id:Long)= Action.async{_=>
    bookService.getById(id).map{bs=>
      Ok(Json.toJson(bs))
    }
  }

  def all(): Action[AnyContent] = Action.zio{ _=>
    bookService.all()
      .map(books=>Ok(Json.toJson(books)))
      .recover {
        case e : RepoError => BadRequest(Json.obj("error" -> e.ex.getMessage))
      }
  }

  def create()= Action.zio(parse.json){r=>
    val b = for{
      toCreateBook <- jsonValidation[Book](r.body)
      book <- bookService.create(toCreateBook)
    }yield book
    b.fold(
      {
        case e : RepoError => BadRequest(Json.obj("error" -> e.ex.getMessage))
        case v : ValidateError => BadRequest(Json.obj("error" -> v.msg))
      }
      ,
      createdBook => Ok(Json.toJson(createdBook))
    )
  }

  def update(id:Long)= Action.zio(parse.json){r=>
    val b = for{
      toUpdatedBook <- jsonValidation[Book](r.body)
      _ <- ZIO.fromEither(validateId(id,toUpdatedBook))
      book <- bookService.update(toUpdatedBook.copy(id=id))
    }yield book
    b.fold(
      {
        case e : RepoError => BadRequest(Json.obj("error" -> e.ex.getMessage))
        case v : ValidateError => BadRequest(Json.obj("error" -> v.msg))
      }
      ,
      updatedBook => Ok(Json.toJson(updatedBook))
    )
  }

  def validateId(id:Long,book:Book): Either[ValidateError, Book] ={
    if(id!=book.id){
      Left(ValidateError("id is error"))
    }else Right(book)
  }

}
