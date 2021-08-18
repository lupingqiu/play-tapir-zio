package repositories

import java.time.LocalDateTime

import javax.inject.{Inject, Singleton}
import models.{Book, RepoError}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import zio.ZIO

import scala.concurrent.Future

/**
  * Created by luping.qiu in 10:36 AM 2021/8/4
  */
@Singleton
class BookRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[JdbcProfile] with JsonMapper{

  import profile.api._

  private class BookTable(tag:Tag) extends Table[Book](tag,"PTZ_BOOK"){
    def id = column[Long]("id",O.PrimaryKey,O.AutoInc)
    def title = column[String]("title")

    def published = column[LocalDateTime]("published")(timeMapper)
    override def * = (id,title,published) <> ((Book.apply _).tupled,Book.unapply)
  }

  private val books = TableQuery[BookTable]

  def getById(id:Long): Future[Seq[Book]] ={
    db.run(books.filter(_.id===id).result)
  }

  def getByIdZIO(id:Long): ZIO[Any, RepoError, Seq[Book]] ={

    for{
      ret<-ZIO.fromFuture(_ =>db.run(books.filter(_.id===id).result)).refineOrDie{ case e:Exception =>
        RepoError(e)
      }
    }yield ret
  }

  def all(): ZIO[Any, RepoError, Seq[Book]] = {
    ZIO.fromFuture{implicit ec=>
      db.run(books.result)
    }.catchAll{
      case e:Exception=>
        ZIO.fail(RepoError(e))
    }
//      .refineOrDie{
//      case e:Exception => RepoError(e)
//    }
  }

  def get(id:Long): ZIO[Any, RepoError, Option[Book]] = {
    ZIO.fromFuture(_=>db.run(books.filter(_.id===id).result.headOption)).refineOrDie{
      case e:Exception => RepoError(e)
    }
  }

  def create(book:Book): ZIO[Any, RepoError, Book] = {
    ZIO.fromFuture{implicit ec =>
      val sql = (books returning books.map(_.id)) += book
      db.run(sql).map{id => book.copy(id=id)}
    }.refineOrDie{
      case e:Exception => RepoError(e)
    }
  }

  def update(book:Book): ZIO[Any, RepoError, Int] ={
    ZIO.fromFuture{_=>
      val sql = books.insertOrUpdate(book)
      db.run(sql)
    }.refineOrDie{
      case e:Exception => RepoError(e)
    }
  }

  def delete(id:Long): ZIO[Any, RepoError, Int] ={
    ZIO.fromFuture(_=>db.run(books.filter(_.id===id).delete)).refineOrDie{
      case e:Exception => RepoError(e)
    }
  }

}
