package controllers

import javax.inject.Inject
import models._
import play.api.Logging
import services.BookService
import zio.ZIO

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by luping.qiu in 10:51 AM 2021/8/6
  */
class PTZController @Inject()(bookService: BookService)(implicit ex:ExecutionContext) extends Logging{

  lazy val runtime = zio.Runtime.default

  /**
    * play zio tapir
    * @param id
    * @return
    */
  def getByIdPTZ(id:Long): Future[Either[OutPutError, Book]] = {
    runtime.unsafeRunToFuture {
      bookService.getByIdZIO(id).fold(
        fail => Left(OutPutError(fail.ex.getMessage)),
        data => if(data.nonEmpty)Right(data.head) else Left(OutPutError(s"$id not exist"))
      )
    }
  }

  def createPTZ(book:Book): Future[Either[OutPutError, Book]] = {
    runtime.unsafeRunToFuture {
      bookService.create(book).fold(
        {
          case r: RepoError => Left(OutPutError(r.ex.getMessage))
          case r: ValidateError => Left(OutPutError(r.msg))
        },
        data => Right(data)
      )
    }
  }

  def updatePTZ(id:Long,book:Book): Future[Either[OutPutError, Book]] ={
    runtime.unsafeRunToFuture {
      val ret = for{
        _ <- ZIO.fromEither(validateId(id,book))
        returnBook<-  bookService.update(book)
      }yield {
        returnBook
      }

      ret.fold(
        {
          case r: RepoError => Left(OutPutError(r.ex.getMessage))
          case r: ValidateError => Left(OutPutError(r.msg))
        },
        data => Right(data)
      )
    }
  }


  def validateId(id:Long,book:Book): Either[ValidateError, Book] ={
    if(id!=book.id){
      Left(ValidateError("id is validate error"))
    }else Right(book)
  }
}
