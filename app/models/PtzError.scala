package models

import play.api.libs.json.{Format, Json}
/**
  * Created by luping.qiu in 4:50 PM 2021/8/4
  */
trait PtzError

case class RepoError(ex:Exception) extends PtzError

case class OutPutError(msg:String) extends PtzError
object OutPutError {
  implicit val format: Format[OutPutError] = Json.format[OutPutError]
}

case class ValidateError(msg:String) extends PtzError