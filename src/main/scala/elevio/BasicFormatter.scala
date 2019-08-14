package elevio

import eleviopb.api.{Article, ArticleList, SearchResults}

/**
 * Object that handles all the pretty printing to stdout
 */
trait BasicFormatter {
  def print(input: ArticleList): Unit = {
    print(f"Available articles: ${input.totalEntries}, available pages: ${input.totalPages}")
    print(f"Current page: ${input.pageNumber}")
    for (article <- input.articles) {
      print(f"${article.id}: ${article.title}")
    }
    print("Use the ID to see read an article")
  }

  def print(input: Article): Unit = {
    print(f"${input.id}: ${input.title}")
    for (version <- input.translations)
      print(version.body)

  }

  def print(input: SearchResults): Unit = {
    print(f"Available articles: ${input.totalResults}, available pages: ${input.totalPages}")
    print(f"Current page: ${input.pageNumber}")
    for (result <- input.results) {
      print(f"${result.id}: ${result.title}")
    }
  }

  def print(input: HttpError): Unit = {
    print(f"ERROR: ${input.message}")
    print(f"Status code ${input.error_code} with name ${input.error_name}")

  }

  def print(input: String): Unit = {
    println(input)
  }
}
