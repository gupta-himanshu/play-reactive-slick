package models

import slick.driver.PostgresDriver.api._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.db.DB
import play.api.Play.current

trait DAOComponent {

  def insert(employee: Employee): Future[Int]
  def update(id: Long, employee: Employee): Future[Int]
  def delete(id: Long): Future[Int]
  def list(page: Int = 0, pageSize: Int = 10, orderBy: Int = 1, filter: String = "%"): Future[Page[Employee]]
  def findById(id: Long): Future[Employee]
  def count: Future[Int]

}

object DAO extends DAOComponent {

  private val employees = TableQuery[Employees]

  private def db: Database = Database.forDataSource(DB.getDataSource())

  /**
   * Filter employee with id
   */
  private def filterQuery(id: Long): Query[Employees, Employee, Seq] =
    employees.filter(_.id === id)

  /**
   * Count employees with a filter
   */
  private def count(filter: String): Future[Int] =
    try db.run(employees.filter(_.name.toLowerCase like filter.toLowerCase()).length.result)
    finally db.close

  /**
   * Count total employees in database
   */
  override def count: Future[Int] =
    try db.run(employees.length.result)
    finally db.close

  /**
   * Find employee by id
   */
  override def findById(id: Long): Future[Employee] =
    try db.run(filterQuery(id).result.head)
    finally db.close

  /**
   * Create a new employee
   */
  override def insert(employee: Employee): Future[Int] =
    try db.run(employees += employee)
    finally db.close

  /**
   * Update employee with id
   */
  override def update(id: Long, employee: Employee): Future[Int] =
    try db.run(filterQuery(id).update(employee))
    finally db.close

  /**
   * Delete employee with id
   */
  override def delete(id: Long): Future[Int] =
    try db.run(filterQuery(id).delete)
    finally db.close

  /**
   * Return a page of employees
   */
  override def list(page: Int = 0, pageSize: Int = 10, orderBy: Int = 1, filter: String = "%"): Future[Page[Employee]] = {
    try {
      val offset = pageSize * page
      val query =
        (for {
          employee <- employees if employee.name.toLowerCase like filter.toLowerCase
        } yield (employee)).drop(offset).take(pageSize)
      val totalRows = count(filter)
      val result = db.run(query.result)
      result flatMap (employees => totalRows map (rows => Page(employees, page, offset, rows)))
    } finally { db.close() }
  }

}
