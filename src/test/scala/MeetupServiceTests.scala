import com.github.nscala_time.time.Imports.{DateTime, DateTimeZone}
import domain.Meetup
import org.scalatest.flatspec.AnyFlatSpec
import repository.impl.SimpleMeetupRepo
import service.impl.MeetupServiceImpl

class MeetupServiceTests extends AnyFlatSpec {
  "Meetup service" should "create meetup" in {
    val meetupService = new MeetupServiceImpl(new SimpleMeetupRepo)
    val expected = Meetup(1, "test",
      new DateTime(2022, 1, 1, 12, 0, 0, 0, DateTimeZone.getDefault()),
      new DateTime(2022, 1, 1, 13, 0, 0, 0, DateTimeZone.getDefault())
    )

    meetupService.create("test",
      new DateTime(2022, 1, 1, 12, 0, 0, 0, DateTimeZone.getDefault()),
      new DateTime(2022, 1, 1, 13, 0, 0, 0, DateTimeZone.getDefault())
    )

    val actual = meetupService.find(1).get
    assert(expected.id === actual.id)
    assert(expected.start === actual.start)
    assert(expected.end === actual.end)
  }

  "Meetup service" should "update meetup" in {
    val meetupService = new MeetupServiceImpl(new SimpleMeetupRepo)
    val expected = Meetup(1, "test",
      new DateTime(2023, 1, 1, 12, 0, 0, 0, DateTimeZone.getDefault()),
      new DateTime(2023, 1, 1, 13, 0, 0, 0, DateTimeZone.getDefault())
    )

    meetupService.create("test",
      new DateTime(2022, 1, 1, 12, 0, 0, 0, DateTimeZone.getDefault()),
      new DateTime(2022, 1, 1, 13, 0, 0, 0, DateTimeZone.getDefault())
    )

    meetupService.update(expected)
    val actual = meetupService.find(1).get

    assert(expected.id === actual.id)
    assert(expected.start === actual.start)
    assert(expected.end === actual.end)
  }


  "Meetup service" should "remove meetup" in {
    val meetupService = new MeetupServiceImpl(new SimpleMeetupRepo)

    meetupService.create("test",
      new DateTime(2022, 1, 1, 12, 0, 0, 0, DateTimeZone.getDefault()),
      new DateTime(2022, 1, 1, 13, 0, 0, 0, DateTimeZone.getDefault())
    )

    meetupService.remove(1)

    val actual = meetupService.find(1)
    assert(actual.isEmpty)
  }
}
