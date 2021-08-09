package controllers

import controllers.ZIOAction.runtime
import javax.inject.Inject
import models.{Book, OutPutError, RepoError, ValidateError}
import services.BookService
import zio.{DefaultRuntime, ZIO}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by luping.qiu in 10:51 AM 2021/8/6
  */
class PTZController @Inject()(bookService: BookService)(implicit ex:ExecutionContext) {

  lazy val runtime = new DefaultRuntime (){}

  /**
    * play zio tapir
    * @param id
    * @return
    */
  def getByIdPTZ(id:Long): Future[Either[OutPutError, Book]] = {
    runtime.unsafeRun {
      bookService.getByIdZIO(id).fold(
        fail => Left(OutPutError(fail.ex.getMessage)),
        data => if(data.nonEmpty)Right(data.head) else Left(OutPutError(s"$id not exist"))
      ).toFuture
    }
  }

  def createPTZ(book:Book): Future[Either[OutPutError, Book]] = {
    runtime.unsafeRun {
      bookService.create(book).fold(
        {
          case r: RepoError => Left(OutPutError(r.ex.getMessage))
          case r: ValidateError => Left(OutPutError(r.msg))
        },
        data => Right(data)
      ).toFuture
    }
  }

  def updatePTZ(id:Long,book:Book): Future[Either[OutPutError, Book]] ={
    runtime.unsafeRun {
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
      ).toFuture
    }
  }


  def validateId(id:Long,book:Book): Either[ValidateError, Book] ={
    if(id!=book.id){
      Left(ValidateError("id is validate error"))
    }else Right(book)
  }
}
