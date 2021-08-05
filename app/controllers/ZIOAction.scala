package controllers
import play.api.mvc.{Action, ActionBuilder, BodyParser, Result}
import zio._
/**
  * Created by luping.qiu in 5:08 PM 2021/8/3
  */
object ZIOAction {

  val runtime = new DefaultRuntime (){}

  implicit class ActionBuilderOps[+R[_], B](actionBuilder: ActionBuilder[R, B]) {

    def zio[E](zioActionBody: R[B] => IO[E, Result]): Action[B] = actionBuilder.async { request =>
      runtime.unsafeRun {
        ioToTask(zioActionBody(request)).toFuture
      }
    }

    def zio[E, A](bp: BodyParser[A])(zioActionBody: R[A] => IO[E, Result]): Action[A] = actionBuilder(bp).async { request =>
      runtime.unsafeRun(
        ioToTask(zioActionBody(request)).toFuture
      )
    }

    private def ioToTask[E, A](io: IO[E, A]): ZIO[Any, Throwable, A] =
      io.mapError {
        case t: Throwable => t
        case s: String    => new Throwable(s)
        case e            => new Throwable("Error: " + e.toString)
      }
  }


  implicit class RecoverIO[E, A](io: IO[E, A]) {
    def recover(f: E => A): UIO[A] = io.fold(f, identity)
  }

}
