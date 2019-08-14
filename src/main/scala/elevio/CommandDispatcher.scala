package elevio

import eleviopb.api.{Article, ArticleList, SearchResults}

/**
 * Dispatcher class containing handler functions for the various possible subcommands
 */
class CommandDispatcher(client: ApiClient) extends BasicFormatter {
  /**
   * Main entrypoint for this class. Takes a CliConfiguration object and decides what behaviour to invoke
   * based on the commands passed in the configuration.
   * @param conf command line args
   */
  def dispatch(conf: CliConfiguration): Unit = {
    conf.subcommand match {
      case Some(conf.get) => handle_get(conf)
      case Some(conf.search) => handle_search(conf)
      case _ => println("You need to provide a subcommand, please use --help for more information")
    }
  }
  /**
   * Handler for get requests, depending on whether id > 0 gets all articles or a specific one
   *
   * @param conf command line args
   */
  private def handle_get(conf: CliConfiguration): Unit = {
    val id = conf.get.id()
    id match {
      case 0 => handle_get_all(client.get_all_articles(page = conf.get.page()))
      case _ => handle_get_one(client.get_one_article(id))
    }
  }

  /**
   * Parse the response containing a list of articles and their metadata
   *
   * @param articles response object from api client containing a list of articles plus metadata
   */
  private def handle_get_all(articles: Either[HttpError, ArticleList]): Unit = {
    articles match {
      case Left(e) => print(e)
      case Right(articles) => print(articles)
    }
  }

  /**
   * Parse the response containing a single article
   *
   * @param article If exists article belonging to a specific id, otherwise None
   */
  private def handle_get_one(article: Either[HttpError, Option[Article]]): Unit = {
    article match {
      case Left(e) => print(e)
      case Right(None) => print("Cannot find article with this id")
      case Right(Some(article)) => print(article)
    }
  }

  /**
   * Handle a search request
   *
   * @param conf command line args
   */
  private def handle_search(conf: CliConfiguration): Unit = {
    val response: Either[HttpError, SearchResults] = client.search_articles(conf.search.keyword(),
                                                                            page = conf.search.page())
    response match {
      case Left(e) => print(e)
      case Right(response) => print(response)
    }
  }
}
