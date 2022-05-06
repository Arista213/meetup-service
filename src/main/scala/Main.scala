import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    MeetupServer.stream.compile.drain.as(ExitCode.Success)
}
