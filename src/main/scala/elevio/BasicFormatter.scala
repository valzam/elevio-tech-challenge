package elevio

import eleviopb.api.Article.AUTHOR
import eleviopb.api.{Article, ArticleList, SearchResults}

/**
 * Trait that handles all the pretty printing to stdout
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
    print(f"Last published: ${input.lastPublishedAt}")

    val author = input.author.getOrElse(AUTHOR())
    print(f"Author: ${author.name}")
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

  /**
   * The point of this method is to make it easy for other traits to re-use the formatting logic for specific
   * response objects but not use stdout as a target.
   * For example, another trait could implement a print method that saves the text to a file.
   * This is also useful for testing because a Mock just needs to override this method to capture the output of
   * the CommandDispatcher.
   * @param input string to be printed out
   */
  def print(input: String): Unit = {
    println(input)
  }
}
