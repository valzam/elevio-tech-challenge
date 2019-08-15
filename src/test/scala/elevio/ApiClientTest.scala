package elevio

import org.scalatest.{FunSuite}

/**
 * Integration tests for the ApiClient
 */
class ApiClientTest extends FunSuite {
  val client = ApiClient.fromEnv()
  test("Fetch one article") {
    val article = client.get_one_article(3)
    article match {
      case Left(e) => assert(e.message == HttpError(error_code = 1, error_name = "bar").message)
      case Right(Some(article)) => assert(article.id == 3)
      case Right(None) => assert(true)
    }
  }

  test("Fetch all articles") {
    val articles = client.get_all_articles()
    articles match {
      case Left(e) => assert(e.message == HttpError(error_code = 1, error_name = "bar").message)
      case Right(articles) => assert(articles.articles.length > 0)
    }
  }

  test("Search for articles by keyword") {
    val results = client.search_articles(keyword = "island")
    results match {
      case Left(e) => assert(e.message == HttpError(error_code = 1, error_name = "bar").message)
      case Right(results) => assert(results.results.length > 0)
    }
  }
}
