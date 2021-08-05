package models

import java.sql.Timestamp

import play.api.libs.json._

/**
  * Created by luping.qiu in 5:23 PM 2021/8/3
  */
case class Book(id:Long,title:String,published:Timestamp)

object Book {
  implicit val format = Json.format[Book]

  implicit object timestampFormat extends Format[Timestamp] {
    def reads(json: JsValue): JsResult[Timestamp] = {
      val l = json.as[Long]
      JsSuccess(new Timestamp(l))
    }
    def writes(ts: Timestamp) = JsNumber(ts.getTime)
  }
}