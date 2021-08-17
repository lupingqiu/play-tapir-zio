package models

import play.api.libs.json.Json

/**
  * Created by luping.qiu in 4:19 PM 2021/8/16
  */
case class User(name:String,age:Int)

object User{
  implicit val format = Json.format[User]
}