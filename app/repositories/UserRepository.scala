package repositories

import io.getquill.context.ZioJdbc._
import javax.inject.Singleton
import models.User
import zio.ZIO

/**
  * Created by luping.qiu in 10:36 AM 2021/8/4
  */
@Singleton
class UserRepository{

  import QuillLayer._

  def all(): ZIO[zio.ZEnv, Throwable, List[User]] ={
    val users = quote{
      query[User]
    }
    QuillLayer.run(users).onDataSource.provideCustomLayer(zioDs)
  }
}
