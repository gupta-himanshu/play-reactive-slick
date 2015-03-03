import java.text.SimpleDateFormat
import play.api._
import models._
import scala.concurrent.ExecutionContext.Implicits.global

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    StartData.insert()
  }

}

/**
 *  Starting set of data to be inserted into the sample application.
 */
object StartData {

  val sdf = new SimpleDateFormat("MM/dd/yyyy")

  def insert(): Unit = {
    DAO.count map { size =>
      if (size == 0) {
        val employees = Seq(
          Employee(Option(1L), "John", "USA", None, new java.util.Date, Some("Trainee")),
          Employee(Option(2L), "Miles", "USA", None, new java.util.Date, Some("Assistant")),
          Employee(Option(3L), "Alexander", "USA", None, new java.util.Date, Some("Manager")),
          Employee(Option(4L), "Stefan", "USA", None, new java.util.Date, Some("Trainee")),
          Employee(Option(5L), "Robin", "USA", None, new java.util.Date, Some("Assistant")),
          Employee(Option(6L), "Pankaj", "India", None, new java.util.Date, Some("Manager")),
          Employee(Option(7L), "Mayank", "India", None, new java.util.Date, Some("Trainee")),
          Employee(Option(8L), "David", "UK", None, new java.util.Date, Some("Assistant")),
          Employee(Option(9L), "Philip", "Germany", None, new java.util.Date, Some("Manager")),
          Employee(Option(10L), "Fred", "USA", None, new java.util.Date, Some("Trainee")),
          Employee(Option(11L), "Louis", "Canada", None, new java.util.Date, Some("Assistant")))
        employees.map(DAO.insert)
      }
    }
  }
}
