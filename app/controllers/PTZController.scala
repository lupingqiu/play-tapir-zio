package controllers

import javax.inject.Inject
import models._
import play.api.Logging
import services.UserService
import zio.CancelableFuture

import scala.concurrent.ExecutionContext

/**
  * Created by luping.qiu in 10:51 AM 2021/8/6
  */
class PTZController @Inject()(userService: UserService)(implicit ex:ExecutionContext) extends Logging{

  lazy val runtime = zio.Runtime.default

  def getAllUsers(): CancelableFuture[Either[OutPutError, Vector[User]]] = {
    runtime.unsafeRunToFuture {
      userService.all().fold(
        fail =>{
          logger.info(s"getAllUsers error:${fail.getMessage}",fail)
          Left(OutPutError(fail.getMessage))
        },
        data => Right(data.toVector)
      )
    }
  }

}
