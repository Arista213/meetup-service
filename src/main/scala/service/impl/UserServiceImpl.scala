package service.impl

import cats.effect.unsafe.implicits.global
import domain.User
import repository.UserRepo
import service.UserService

case class UserServiceImpl(useRepo: UserRepo) extends UserService {
  override def save(user: User): Unit = useRepo.save(user).unsafeRunSync()

  override def find(id: Long): Option[User] = useRepo.find(id).unsafeRunSync()

  override def update(user: User): Unit = useRepo.update(user).unsafeRunSync()

  override def remove(id: Long): Boolean = useRepo.remove(id).unsafeRunSync()
}
