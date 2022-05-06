import com.github.nscala_time.time.Imports.{DateTime, DateTimeZone}
import domain.InviteStatus.{Accepted, Questionable}
import domain.{Email, Fullname, LocalDateTimeObject, Meetup, User}
import org.scalatest.flatspec.AnyFlatSpec
import repository.impl.{SimpleInviteRepo, SimpleMeetupRepo, SimpleUserRepo}
import service.InviteService
import service.impl.{InviteServiceImpl, MeetupServiceImpl, UserServiceImpl}

import java.time.LocalDateTime

class InviteServiceTests extends AnyFlatSpec {
  "Invite service" should "invite user" in {
    val userService = UserServiceImpl(new SimpleUserRepo)
    val user = User(1, Fullname("test-fullname"), Email("test-email"))
    userService.save(user)

    val meetupService = MeetupServiceImpl(new SimpleMeetupRepo)
    val meetup: Meetup = Meetup(1, "test",
      new DateTime(2022, 1, 1, 12, 0, 0, 0, DateTimeZone.getDefault()),
      new DateTime(2022, 1, 1, 13, 0, 0, 0, DateTimeZone.getDefault()))
    meetupService.save(meetup)

    val inviteService = new InviteServiceImpl(SimpleInviteRepo(MeetupServiceImpl(new SimpleMeetupRepo)))
    val users = inviteService.getAllUsers(meetup.id)
    inviteService.inviteUser(user, meetup.id)
    val status = inviteService.getStatus(meetup.id)
    assert(status === Questionable)
    assert(users === List(user))
  }

  "Invite service" should "change status after user acceptance" in {
    val userService = UserServiceImpl(new SimpleUserRepo)
    val user = User(1, Fullname("test-fullname"), Email("test-email"))
    userService.save(user)

    val start = LocalDateTimeObject.dateTimeToString(
      new LocalDateTime(2022, 1, 1, 12, 0, 0, 0)
    )

    val end = LocalDateTimeObject.dateTimeToString(
      new LocalDateTime(2022, 1, 1, 13, 0, 0, 0)
    )

    val meetupService = MeetupServiceImpl(new SimpleMeetupRepo)
    val meetup: Meetup = Meetup(1, "test", start, end)

    val inviteService: InviteService = new InviteServiceImpl(SimpleInviteRepo(meetupService))
    inviteService.inviteUser(user, meetup.id)
    assert(inviteService.getStatus(meetup.id) === Questionable)
    inviteService.accept(user, meetup.id)
    assert(inviteService.getStatus(meetup.id) === Accepted)
  }
}
