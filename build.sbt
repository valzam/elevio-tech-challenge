lazy val root = (project in file(".")).
  settings(
    name := "elevio",
    version := "1.0",
    scalaVersion := "2.13.0",
  )

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)

// Protobuf
libraryDependencies += "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"
libraryDependencies += "com.thesamet.scalapb" %% "scalapb-json4s" % "0.9.3"

// Testing
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.8"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % "test"

// HTTP client
libraryDependencies += "com.lihaoyi" %% "requests" % "0.2.0"

// CLI Parser
libraryDependencies += "org.rogach" %% "scallop" % "3.3.1"
