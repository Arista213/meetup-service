package endpoint

import cats.effect.IO
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.swagger.bundle.SwaggerInterpreter

object SwaggerEndpoints {
  val swaggerEndpoints: List[ServerEndpoint[Any, IO]] =
    SwaggerInterpreter()
      .fromEndpoints[IO](
        UserEndpoints.getAllEndpoints
        ++ MeetupEndpoints.getAllEndpoints
        ++ InviteEndpoints.getAllEndpoints,
        "meetup api", "0.1"
      )
}
