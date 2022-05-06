package domain

object InviteStatus extends Enumeration {
  type InviteStatus = Value
  val Questionable, Accepted = Value

  def parse(string: String): Value = values.find(_.toString == string).get
}
