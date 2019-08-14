package elevio

import org.scalatest.FunSuite

/**
 * Tests to assert that the configuration takes the expected arguments
 */
class CliConfigurationTest extends FunSuite {

  test("Test search") {
    val c = new CliConfiguration(Array("search", "island"))
    assert(c.subcommands.contains(c.search))
    assert(c.search.keyword() == "island")
  }

  test("Test get all") {
    val c = new CliConfiguration(Array("get"))
    assert(c.subcommands.contains(c.get))
  }

  test("Test get one") {
    val c = new CliConfiguration(Array("get", "2"))
    assert(c.subcommands.contains(c.get))
    assert(c.get.id() == 2)
  }

}
