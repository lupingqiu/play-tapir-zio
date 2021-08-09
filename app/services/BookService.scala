package services

import java.time.LocalDateTime

import javax.inject.{Inject, Singleton}
import models.{Book, PtzError, RepoError, ValidateError}
import repositories.BookRepository
import zio.{IO, ZIO}

import scala.concurrent.Future


/**
  * Created by luping.qiu in 10:00 AM 2021/8/5
  */
@Singleton
class BookService @Inject()(bookRepo:BookRepository) {

  def getById(id:Long): Future[Seq[Book]] = bookRepo.getById(id)

  def getByIdZIO(id:Long): ZIO[Any, RepoError, Seq[Book]] = bookRepo.getByIdZIO(id)


  def all()= bookRepo.all()

  def create(book:Book): ZIO[Any, PtzError, Book] ={
    for{
      _ <- IO.fromEither(validatePublished(book))
      bookWithId <- bookRepo.create(book)
    }yield bookWithId
  }

  def update(book:Book)={
    for{
      maybeBook <- bookRepo.get(book.id)
      _ <- maybeBook.map(b=>IO.succeed(b)).getOrElse(IO.fail(ValidateError(s"${book.id} not exist")))
      _ <- bookRepo.update(book)
    }yield book
  }

  def validatePublished(book:Book): Either[ValidateError, Book] ={
    if(book.published.isAfter(LocalDateTime.now) ){
      Left(ValidateError("publish time is error"))
    }else Right(book)
  }

}
