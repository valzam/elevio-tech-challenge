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

# Usage
```
# Set access credentials from elev.io as env variables
export ELEVIO_API_KEY=foo
export ELEVIO_API_TOKEN=bar
```

Run with sbt
```
sbt "run get" --> Fetch all articles for your account
sbt "run get $ID" --> Fetch a specific article with id $ID
sbt "run search $KEYWORD" --> Fetch all articles containing the word $KEYWORD
```

# Design

Main pieces
- ApiClient: Class that provides an easy way to access the elevio API
- CommandDispatcher: Takes a CliConfiguration object and decides how to process the commands
- BasicFormatter: Basic printing trait of results

CommandDispatcher uses the BasicFormatter by default but can be instantiated with a different one (for example print to file instead of stdout)
 