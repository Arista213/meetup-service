package service

import domain.Meetup

trait MeetupService {
  def save(meetup: Meetup): Unit

  def update(meetup: Meetup): Unit

  def remove(id: Long): Unit

  def find(id: Long): Option[Meetup]
}
