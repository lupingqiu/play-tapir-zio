package endpoints

import java.time.LocalDateTime

import models.{Book, OutPutError, User}
import sttp.tapir.generic.auto._
import sttp.tapir.json.play._
import sttp.tapir.{Endpoint, _}



/**
  * Created by luping.qiu in 10:38 AM 2021/8/6
  */
class PtzEndpoints {

  val basePoint: Endpoint[Unit, Unit, Unit, Any] = endpoint.tag("PTZ API").in("ptz")


  val getAllUsers = basePoint.get
    .in("user")
    .errorOut(jsonBody[OutPutError])
    .out(jsonBody[Vector[User]])

}
