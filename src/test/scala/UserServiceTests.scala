import domain.{Email, Fullname, User}
import org.scalatest.flatspec.AnyFlatSpec
import repository.impl.SimpleUserRepo
import service.impl.UserServiceImpl

class UserServiceTests extends AnyFlatSpec {
  "User service" should "save user" in {
    val userService = UserServiceImpl(new SimpleUserRepo())
    val expected = User(1, Fullname("test"), Email("test-email"))
    userService.save(expected)
    val actual = userService.find(1).get
    assert(expected.id === actual.id)
    assert(expected.fullname.value === actual.fullname.value)
    assert(expected.email.value === actual.email.value)
  }

  "User service" should "update user" in {
    val userService = UserServiceImpl(new SimpleUserRepo())
    val user = User(1, Fullname("test"), Email("test-email"))
    userService.save(user)
    val updatedUser = User(1, Fullname("updated test"), Email("updated test-email"))
    userService.update(updatedUser)
    val actual = userService.find(1).get
    assert(updatedUser.id === actual.id)
    assert(updatedUser.fullname.value === actual.fullname.value)
    assert(updatedUser.email.value === actual.email.value)
  }

  "User service" should "delete user" in {
    val userService = UserServiceImpl(new SimpleUserRepo())
    val user = User(1, Fullname("test"), Email("test-email"))
    userService.save(user)
    userService.remove(user.id)
    val actual = userService.find(1)
    assert(actual.isEmpty)
  }
}
