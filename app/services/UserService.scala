package services

import javax.inject.Inject
import models.User
import repositories.UserRepository
import zio.ZIO

/**
  * Created by luping.qiu in 4:39 PM 2021/8/16
  */
class UserService @Inject()(userRepository: UserRepository) {

  def all(): ZIO[zio.ZEnv, Throwable, List[User]] = userRepository.all()
}
