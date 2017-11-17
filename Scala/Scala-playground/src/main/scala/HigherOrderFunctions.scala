object HigherOrderFunctions {
  def main(args: Array[String]): Unit = {
    new EmailSystem().showEmails()

  }
}

case class Email(subject: String,
                 text: String,
                 sender: String,
                 recipient: String)


class EmailSystem {
  type EmailFilter = Email => Boolean
  val emails = Email("subjectOne", "textOne", "senderOne", "recipientOne") :: Email("subjectTwo", "textTwo", "senderTwo", "recipientTwo") :: Nil

  def showEmails() = {
    mailsForUser(emails, blacklist(Set("senderTwo"))).foreach(println)
  }

  def mailsForUser(mails: Seq[Email], f: EmailFilter) = mails.filter(f)

  val whitelist: Set[String] => EmailFilter = senders => email => senders.contains(email.sender)
  val blacklist: Set[String] => EmailFilter = senders => email => !senders.contains(email.sender)

  val minimumSize: Int => EmailFilter = n => email => email.text.length >= n
  val maximumSize: Int => EmailFilter = n => email => email.text.length <= n
}


