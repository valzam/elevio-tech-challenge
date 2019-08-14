package elevio

import eleviopb.api.{Article, ArticleList, SearchResults}
import org.scalatest.FunSuite

case class MockApiClient(should_return_error: Boolean) extends ApiClient("", "") {
  override def get_all_articles(page: Int = 1): Either[HttpError, ArticleList] = {
    if (should_return_error) {
      Left(HttpError(error_code = 404, error_name = "Error"))
    } else {
      Right(ArticleList(articles=Array(Article(title = "get all was called"))))
    }
  }

  override def get_one_article(id: Int): Either[HttpError, Option[Article]]  = {
    if (should_return_error) {
      Left(HttpError(error_code = 404, error_name = "Error"))
    } else {
      Right(Option(Article(title = "get one was called")))
    }
  }

  override def search_articles(keyword: String, page: Int = 1): Either[HttpError, SearchResults]  = {
    if (should_return_error) {
      Left(HttpError(error_code = 404, error_name = "Error"))
    } else {
      Right(SearchResults(results = Seq(SearchResults.RESULTS(title = "search was called"))))
    }
  }
}

trait MockBasicFormatter extends BasicFormatter {
  var messages: Seq[String] = Seq()

  override def print(s: String): Unit = messages = messages :+ s
}

/**
 * Tests for the dispatcher logic, i.e. are the right methods called based on the command line inputs
 */
class CommandDispatcherTest extends FunSuite {
  test("Handle get all articles") {
    val client = MockApiClient(false)
    val conf = new CliConfiguration(List("get"))
    val dispatcher = new CommandDispatcher(client) with MockBasicFormatter
    dispatcher.dispatch(conf)

    assert(dispatcher.messages.mkString.contains("get all was called"))
  }

  test("Handle search articles") {
    val client = MockApiClient(false)
    val conf = new CliConfiguration(Array("search", "island"))
    val dispatcher = new CommandDispatcher(client) with MockBasicFormatter
    dispatcher.dispatch(conf)

    assert(dispatcher.messages.mkString.contains("search was called"))
  }

  test("Handle get one article") {
    val client = MockApiClient(false)
    val conf = new CliConfiguration(List("get", "1"))
    val dispatcher = new CommandDispatcher(client) with MockBasicFormatter
    dispatcher.dispatch(conf)

    assert(dispatcher.messages.mkString.contains("get one was called"))
  }

}
