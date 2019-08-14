# Elevio technical challenge
This repository implements my solution to the following problem:

Build a piece of software that

- Retrieves all articles for a given account
- Allows searching for articles
- Display an individual articles details

I chose to implement a CLI tool to achieve this using the following technologies:

- [scalapb](https://scalapb.github.io/json.html) protobuf library to reflect the schema of JSON documents returned by the elevio API
- [requests-scala](https://github.com/lihaoyi/requests-scala) as an http client
- [scallop](https://github.com/scallop/scallop) to handle parsing of command line arguments

# Run
```
# Set access credentials from elev.io as env variables
export ELEVIO_API_KEY=foo
export ELEVIO_API_TOKEN=bar
```

Use pre-compiled version downloadable [here](https://elevio-tech-challlenge.s3-ap-southeast-2.amazonaws.com/eleviovz)

SHA256 d785cbb1cbed09440e7a1cc200efa526688d31749b9f59e13a09fde6782ad710
```
java -jar eleviovz --help

# Get all articles for your account
java -jar eleviovz get

# View page 2
java -jar eleviovz get --page 2
```

Run with sbt
```
git clone https://github.com/valzam/elevio-tech-challenge
cd elevio-tech-challenge
sbt "run get"
```

# Build

Build fat jar with sbt-assembly
```
sbt assembly
```

# Design

Main pieces
- ApiClient: Class that provides an easy way to access the elevio API
- CliConfiguration: Schema and data access object of command line arguments
- CommandDispatcher: Takes a CliConfiguration object and decides how to process the commands
- BasicFormatter: Basic printing trait of results

CommandDispatcher uses the BasicFormatter by default but could be instantiated with a different one (for example print to file instead of stdout)
 