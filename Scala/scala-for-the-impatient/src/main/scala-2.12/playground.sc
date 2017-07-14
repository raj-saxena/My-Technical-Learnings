sealed trait User {
  def name: String
}

class FreeUser(val name: String) extends User
object FreeUserExtractor {
  def unapply(user: FreeUser): Option[Int] = Some(user.name.length)
}

class PremiumUser(val name: String) extends User
object PremiumUserExtractor {
  def unapply(user: PremiumUser): Option[Int] = Some(user.name.length)
}

val freeUser = new FreeUser("Free")
val premiumUser = new PremiumUser("Premium")

def doStuffWithUser(user: User) = user match {
  case FreeUserExtractor(x) => x
  case PremiumUserExtractor(x) => x
}


doStuffWithUser(freeUser)
doStuffWithUser(premiumUser)