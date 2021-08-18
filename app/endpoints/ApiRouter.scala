package endpoints

import akka.actor.ActorSystem
import controllers.PTZController
import javax.inject.Inject
import play.api.mvc.{ActionBuilder, AnyContent, Request}
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import sttp.tapir.openapi.OpenAPI
import sttp.tapir.server.play.PlayServerInterpreter
import sttp.tapir.swagger.play.SwaggerPlay
import sttp.tapir.openapi.circe.yaml._

import scala.concurrent.Future

/**
  * Created by luping.qiu in 11:03 AM 2021/8/6
  */
class ApiRouter @Inject()(ptzEndpoints: PtzEndpoints,ptzController: PTZController,actorSystem: ActorSystem) extends SimpleRouter{

  implicit val system: ActorSystem = actorSystem
  implicit val ec = system.dispatcher

  lazy val playServerInterpreter = PlayServerInterpreter()



  val getAllUsers: Routes ={
    val zpt = ptzEndpoints.getAllUsers.serverLogic[Future](_=>ptzController.getAllUsers())
    playServerInterpreter.toRoutes(zpt)
  }

  private val openApiDocs: OpenAPI = OpenAPIDocsInterpreter().toOpenAPI(List(
    ptzEndpoints.getAllUsers
  ),"Tapir Play Sample", "1.0.0")

  val openApiYml: String = openApiDocs.toYaml

  import sttp.tapir.server.play.PlayServerOptions.default
  implicit val actionBuilder: ActionBuilder[Request, AnyContent] = default.defaultActionBuilder

  def openApiRoute() = new SwaggerPlay(openApiYml).routes

  override def routes: Routes = openApiRoute().orElse(getAllUsers)
}
