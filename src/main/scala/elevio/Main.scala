package elevio

object Main extends App {
  if (sys.env.get("ELEVIO_API_KEY").isEmpty | sys.env.get("ELEVIO_API_TOKEN").isEmpty) {
    println("Please set ELEVIO_API_KEY and ELEVIO_API_TOKEN")
  } else {
    val conf = new CliConfiguration(args)
    val api_client = ApiClient.fromEnv()
    val dispatcher = new CommandDispatcher(api_client)

    dispatcher.dispatch(conf)
  }

}
