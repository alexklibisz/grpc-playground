import sbt.librarymanagement.ModuleID
import sbt._

object Dependencies {

  // Try to make these agree with the versions expected by akka-grpc.
  object Versions {
    val akkaStream = "2.6.9"
    val akkaHttp = "10.2.3"
    val alpakkaSSE = "2.0.2"
    val circe = "0.13.0"
  }

  val wikiStreamAkka: Seq[ModuleID] = Seq(
    "com.typesafe.akka" %% "akka-stream" % Versions.akkaStream,
    "com.typesafe.akka" %% "akka-http" % Versions.akkaHttp,
    "com.lightbend.akka" %% "akka-stream-alpakka-sse" % Versions.alpakkaSSE
  )

  val wikiApi: Seq[ModuleID] = Seq(
    "io.circe" %% "circe-generic" % Versions.circe,
    "io.circe" %% "circe-parser" % Versions.circe
  )

}
