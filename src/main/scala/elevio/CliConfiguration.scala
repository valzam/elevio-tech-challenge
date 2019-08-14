package elevio

import org.rogach.scallop._

/**
 * Configuration of possible CLI parameters
 * @param arguments command line arguments passed during invocation, e.g. sbt "run get 1"
 */
class CliConfiguration(arguments: Seq[String]) extends ScallopConf(arguments) {

  banner("""Usage: elevio [get|search] [OPTION]...
           |Use this CLI to explore the elevio API
           |Options:
           |""".stripMargin)

  val get = new Subcommand("get") {
    val page = opt[Int](default = Some(1))
    val id = trailArg[Int](default = Some(0), required = false,
                          descr = "Id of article to retrieve. If omitted fetches all articles.")
  }
  val search = new Subcommand("search") {
    val page = opt[Int](default = Some(1))
    val keyword = trailArg[String](required = true, descr = "Keyword for search in articles.")
  }
  addSubcommand(get)
  addSubcommand(search)
  verify()
}
