package models

/**
  * Created by luping.qiu in 4:50 PM 2021/8/4
  */
trait PtzError

case class RepoError(ex:Exception) extends PtzError

case class ValidateError(msg:String) extends PtzError