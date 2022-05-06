import cats.effect.{IO, Resource}
import cats.implicits.toSemigroupKOps
import domain.{InviteRequest, Meetup, User}
import doobie.ExecutionContexts
import doobie.hikari.HikariTransactor
import endpoint.InviteEndpoints.{acceptEndpoint, cancelEndpoint, inviteEndpoint}
import endpoint.MeetupEndpoints.{meetupCreateEndpoint, meetupGetEndpoint, meetupRemoveEndpoint, meetupUpdateEndpoint}
import endpoint.SwaggerEndpoints.swaggerEndpoints
import endpoint.UserEndpoints.{userCreateEndpoint, userGetEndpoint, userRemoveEndpoint, userUpdateEndpoint}
import org.http4s.HttpRoutes
import repository.impl.{DatabaseInviteRepo, DatabaseMeetupRepo, DatabaseUserRepo}
import repository.{InviteRepo, MeetupRepo, UserRepo}
import service.impl.{InviteServiceImpl, MeetupServiceImpl, UserServiceImpl}
import service.{InviteService, MeetupService, UserService}
import sttp.capabilities.fs2.Fs2Streams
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.http4s.Http4sServerInterpreter

object MeetupServiceRoutes {
  val hikariTransactor: Resource[IO, HikariTransactor[IO]] =
    for {
      ce <- ExecutionContexts.fixedThreadPool[IO](32) // our connect EC
      xa <- HikariTransactor.newHikariTransactor[IO](
        "org.postgresql.Driver",
        "jdbc:postgresql://postgres:5432/meetup-service",
        "postgres",
        "postgres",
        ce
      )
    } yield xa

  val userRepo: UserRepo = DatabaseUserRepo(hikariTransactor)
  val userService: UserService = UserServiceImpl(userRepo)

  def userRoutes: HttpRoutes[IO] = {
    def saveUser(user: User): IO[Either[Unit, Unit]] = IO.pure(Right[Unit, Unit](userService.save(user)))

    def updateUser(user: User): IO[Either[Unit, Unit]] = IO.pure(Right(userService.update(user)))

    def getUser(id: Long): IO[Either[String, User]] = IO.pure(
      userService.find(id) match {
        case Some(user) => Right(user)
        case _ => Left("Could not find user")
      }
    )

    def removeUser(id: Long): IO[Either[String, Unit]] = IO.pure(
      if (userService.remove(id)) Right() else Left("User does not exist")
    )


    toHttpRoutes(
      List(
        userCreateEndpoint.serverLogic(saveUser),
        userUpdateEndpoint.serverLogic(updateUser),
        userGetEndpoint.serverLogic(getUser),
        userRemoveEndpoint.serverLogic(removeUser)
      )
    )
  }

  val meetupRepo: MeetupRepo = DatabaseMeetupRepo(hikariTransactor)
  val meetupService: MeetupService = MeetupServiceImpl(meetupRepo)

  def meetupRoutes: HttpRoutes[IO] = {
    def saveMeetup(meetup: Meetup): IO[Either[Unit, Unit]] =
      IO.pure(Right[Unit, Unit](meetupService.save(meetup)))

    def updateMeetup(meetup: Meetup): IO[Either[Unit, Unit]] =
      IO.pure(Right(meetupService.update(meetup)))

    def getMeetup(id: Long): IO[Either[String, Meetup]] = IO.pure(
      meetupService.find(id) match {
        case Some(meetup) => Right(meetup)
        case _ => Left("Could not find user")
      }
    )

    def removeMeetup(id: Long): IO[Either[Unit, Unit]] =
      IO.pure(Right(meetupService.remove(id)))

    toHttpRoutes(
      List(
        meetupCreateEndpoint.serverLogic(saveMeetup),
        meetupUpdateEndpoint.serverLogic(updateMeetup),
        meetupGetEndpoint.serverLogic(getMeetup),
        meetupRemoveEndpoint.serverLogic(removeMeetup)
      )
    )
  }

  val inviteRepo: InviteRepo = DatabaseInviteRepo(hikariTransactor)
  val inviteService: InviteService = new InviteServiceImpl(inviteRepo)

  def inviteRoutes: HttpRoutes[IO] = {
    def invite(inviteRequest: InviteRequest): IO[Either[Unit, Unit]] =
      IO.pure(Right[Unit, Unit](inviteService.inviteUser(inviteRequest.userId, inviteRequest.meetupId)))

    def accept(inviteRequest: InviteRequest): IO[Either[Unit, Unit]] =
      IO.pure(Right(inviteService.accept(inviteRequest.userId, inviteRequest.meetupId)))

    def cancel(inviteRequest: InviteRequest): IO[Either[Unit, Unit]] =
      IO.pure(Right[Unit, Unit](inviteService.cancel(inviteRequest.userId, inviteRequest.meetupId)))

    toHttpRoutes(
      List(
        inviteEndpoint.serverLogic(invite),
        acceptEndpoint.serverLogic(accept),
        cancelEndpoint.serverLogic(cancel)
      )
    )
  }

  val swaggerRoutes: HttpRoutes[IO] = Http4sServerInterpreter[IO]().toRoutes(swaggerEndpoints)

  def toHttpRoutes(routesList: List[ServerEndpoint[Fs2Streams[IO], IO]]): HttpRoutes[IO] = Http4sServerInterpreter[IO]().toRoutes(routesList)

  def allRoutes: HttpRoutes[IO] = {
    userRoutes <+> meetupRoutes <+> inviteRoutes <+> swaggerRoutes
  }
}