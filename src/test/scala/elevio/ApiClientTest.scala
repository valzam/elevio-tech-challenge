package elevio

import org.scalatest.{FunSuite}

/**
 * Integration tests for the ApiClient
 */
class ApiClientTest extends FunSuite {
  val API_KEY = "da02bbca5ae02ff74c20b1b7fc6b48e1"
  val API_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2FwcC5lbGV2aW8tc3RhZ2luZy5jb20iLCJzdWIiOiI1ZDUxMzcwOTNkYjEyIiwiZXhwIjozMTQyNDA0NjY3LCJpYXQiOjE1NjU2MDQ2NjcsImp0aSI6InFkcjQ1NHFoaGFsOXJ2Ym02YmFlZ2dlZG1oOWpuOWIiLAogICJ1c2VyTmFtZSIgOiAidmFsZW50aW4uemFtYmVsbGlAZ21haWwuY29tIiwKICAidXNlcklkIiA6IDEzMDczLAogICJzY29wZSIgOiBbICJyZWFkOmFydGljbGUiIF0KfQ.6eYg-ILbiu8xQ2F0fuwk-Lo6Eru8FT7bEA9Mp0pkTv0"
  val client = ApiClient.fromEnv()
  test("Fetch one article") {
    val article = client.get_one_article(1)
    article match {
      case Left(e) => assert(e.message == HttpError(error_code = 1, error_name = "bar").message)
      case Right(Some(article)) => assert(article.id == 1)
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
