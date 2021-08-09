package models

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import play.api.libs.json._

/**
  * Created by luping.qiu in 5:23 PM 2021/8/3
  */
case class Book(id:Long,title:String,published:LocalDateTime)

object Book {

  val df= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
  implicit object timestampFormat extends Format[LocalDateTime] {
    def reads(json: JsValue): JsResult[LocalDateTime] = {
      val l = json.as[String]
      val d = LocalDateTime.parse(l,df)
      JsSuccess(d)
    }
    def writes(ts: LocalDateTime) = JsString(ts.format(df))
  }
  implicit val format = Json.format[Book]
}