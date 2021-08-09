package controllers

import javax.inject._
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import services.BookService

import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(bookService: BookService, val controllerComponents: ControllerComponents)(implicit ex:ExecutionContext) extends BaseController {
  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def getById(id:Long)= Action.async{_=>
    bookService.getById(id).map{bs=>
      Ok(Json.toJson(bs))
    }
  }
}
