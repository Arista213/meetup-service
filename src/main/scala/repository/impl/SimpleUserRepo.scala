package repository.impl

import cats.effect.IO
import domain.User
import repository.UserRepo

import scala.collection.immutable.HashMap

class SimpleUserRepo extends UserRepo {
  var users: Map[Long, User] = new HashMap[Long, User]()

  override def save(user: User): IO[Unit] = IO.pure(users += ((user.id, user)))

  override def find(id: Long): IO[Option[User]] = IO.pure(users.get(id))

  override def remove(id: Long): IO[Boolean] = IO.pure {
    if (users.contains(id)) {
      users -= id
      true
    } else false
  }

  override def update(user: User): IO[Unit] = IO.pure((users = users.updated(user.id, user)))
}
