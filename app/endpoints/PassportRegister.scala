package endpoints

import javax.inject.{Inject, Singleton}
import play.api.Logger
import play.api.routing.Router

/**
  * Created by luping.qiu in 6:31 PM 2021/8/20
  */
@Singleton
class PassportRegister @Inject()(router:Router) {

  val logger: Logger = Logger(this.getClass())

  val routers = router.documentation.map { s => RouterConf(s._1, s._2.replace("$", "%"), s._3) }.toList
  logger.info("开始注册client...")
  routers.foreach{r=>
    logger.info(r.toString)
  }
}
case class RouterConf(http:String,path:String,method:String)
