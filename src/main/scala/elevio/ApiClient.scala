package elevio

import eleviopb.api.{Article, ArticleList, ArticleSingle, SearchResults}
import scalapb.json4s.JsonFormat

case class HttpError(message: String = "Something went wrong, please try again!",
                     error_code: Int, error_name: String)

/**
 * ApiClient to access the elevio API
 * @param key api key created on https://app.elevio-staging.com/apikeys
 * @param token JWT token created on https://app.elevio-staging.com/apikeys
 * @param url base url for API requests
 */
class ApiClient(key: String,
                token: String,
                url: String = "https://api.elevio-staging.com/v1/") {
  private val headers = Map(
    "x-api-key" -> key,
    "Authorization" -> f"Bearer ${token}"
  )

  /**
   * Fetch all articles associated with this api key/token
   * @param page number of page to display
   * @return metadata about the results as well as a list of all articles of the current page
   */
  def get_all_articles(page: Int = 1): Either[HttpError, ArticleList] = {

    val r = requests.get(
      url + "articles",
      params = Map("page" -> page.toString, "status" -> "published"),
      headers = headers
    )
    if (r.statusCode == 200) {
      val articles: ArticleList = JsonFormat.fromJsonString[ArticleList](r.text)
      Right(articles)
    } else {
      Left(HttpError(error_code = r.statusCode, error_name = r.statusMessage))
    }

  }

  /**
   * Retrieve one article by id
   * @param id strictly positive unique integer
   * @return an Article instance if the id exists, otherwise None
   */
  def get_one_article(id: Int): Either[HttpError, Option[Article]]  = {

    val r = requests.get(
      url + "articles/" + id.toString,
      headers = headers
    )
    if (r.statusCode == 200) {
      val article: ArticleSingle = JsonFormat.fromJsonString[ArticleSingle](r.text)
      Right(article.article)
    } else {
      Left(HttpError(error_code = r.statusCode, error_name = r.statusMessage))
    }
  }

  /**
   * Search for article by keyword
   * @param keyword search term
   * @param page results page to display
   * @return a SearchResults instance with metadata about results as well as a list of
   *         abbreviated articles
   */
  def search_articles(keyword: String, page: Int = 1): Either[HttpError, SearchResults]  = {

    val r = requests.get(
      url + "search/en",
      params = Map("query" -> keyword, "page" -> page.toString),
      headers = headers
    )

    if (r.statusCode == 200) {
      val results: SearchResults = JsonFormat.fromJsonString[SearchResults](r.text)
      Right(results)
    } else {
      Left(HttpError(error_code = r.statusCode, error_name = r.statusMessage))
    }

  }
}

/**
 * Companion object for ApiClient, offers two ways of creating an ApiClient
 * 1) Implicitly, using environment variables for api key and token
 *  - ELEVIO_API_KEY
 *  - ELEVIO_API_TOKEN
 * 2) Explicitly, passing the key and token and arguments
 */
object ApiClient {

  def fromEnv(): ApiClient = {
    val key = sys.env("ELEVIO_API_KEY")
    val token = sys.env("ELEVIO_API_TOKEN")

    new ApiClient(key, token)
  }

  def fromParams(key: String, token: String): ApiClient = {
    new ApiClient(key, token)
  }
}
