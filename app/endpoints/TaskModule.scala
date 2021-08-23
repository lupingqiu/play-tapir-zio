package endpoints

import com.google.inject.AbstractModule

/**
  * Created by luping.qiu in 9:33 PM 2021/8/20
  */
class TaskModule extends AbstractModule{

  override def configure(): Unit = {
    bind(classOf[PassportRegister]).asEagerSingleton()
  }
}
