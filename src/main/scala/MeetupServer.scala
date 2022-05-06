import cats.effect.{Async, IO, Resource}
import cats.syntax.all._
import com.comcast.ip4s._
import fs2.Stream
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.middleware.Logger

object MeetupServer {

  def stream: Stream[IO, Nothing] = {
    for {
      _ <- Stream.resource(EmberClientBuilder.default[IO].build)

      httpApp = MeetupServiceRoutes.allRoutes.orNotFound

      finalHttpApp = Logger.httpApp(logHeaders = true, logBody = true)(httpApp)

      exitCode <- Stream.resource(
        EmberServerBuilder.default[IO]
          .withHost(ipv4"0.0.0.0")
          .withPort(port"8080")
          .withHttpApp(finalHttpApp)
          .build >>
          Resource.eval(Async[IO].never)
      )
    } yield exitCode
  }.drain
}
