package endpoints

import java.time.LocalDateTime

import models.{Book, OutPutError}
import play.api.libs.json.Json
import sttp.tapir.generic.auto._
import sttp.tapir.json.play._
import sttp.tapir.{Endpoint, _}



/**
  * Created by luping.qiu in 10:38 AM 2021/8/6
  */
class PtzEndpoints {

  val basePoint: Endpoint[Unit, Unit, Unit, Any] = endpoint.tag("PTZ API").in("tapir")

  val getByIdPoint = basePoint.get
    .in("book")
    .in(
      query[Long]("id").description("book's id")
    )
    .errorOut(jsonBody[OutPutError])
    .out(jsonBody[Book])

  val createPoint = basePoint.post
    .in("book")
    .in(
      jsonBody[Book]
        .description("The book to add")
        .example(Book(-1L,"book name",LocalDateTime.now))
    )
    .errorOut(jsonBody[OutPutError])
    .out(jsonBody[Book])


  val updatePoint = basePoint.put
    .in("book")
    .in(path[Long]("id"))
    .in(
      jsonBody[Book]
        .description("The book to add")
        .example(Book(1L,"book name",LocalDateTime.now))
    )
    .errorOut(jsonBody[OutPutError])
    .out(jsonBody[Book])

}
