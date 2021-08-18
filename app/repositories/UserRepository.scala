package repositories

import io.getquill.{Literal, MysqlZioJdbcContext}
import javax.inject.{Inject, Singleton}
import models.User
import play.api.db.Database
import zio.{Task, ZIO, ZLayer, ZManaged}

/**
  * Created by luping.qiu in 10:36 AM 2021/8/4
  */
@Singleton
class UserRepository @Inject()(database:Database)  extends MysqlZioJdbcContext(Literal){

  lazy val zioCon = ZLayer.fromManaged(ZManaged.fromAutoCloseable(Task(database.getConnection)))

  def all(): ZIO[zio.ZEnv, Throwable, List[User]] ={
    val users = quote{
      query[User]
    }
    run(users).provideCustomLayer(zioCon)
  }
}
