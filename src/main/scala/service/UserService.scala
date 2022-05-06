package service

import cats.effect.IO
import domain.User

trait UserService {
  def save(user: User): Unit

  def update(user: User): Unit

  def remove(id: Long): Boolean

  def find(id: Long): Option[User]
}
