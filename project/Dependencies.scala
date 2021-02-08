import sbt.librarymanagement.ModuleID
import sbt._

object Dependencies {

  // Try to make these agree with the versions expected by akka-grpc.
  object Versions {
    val akkaActorStream = "2.6.9"
    val akkaHttp = "10.2.3"
    val alpakkaSSE = "2.0.2"
    val circe = "0.13.0"
  }

  val wikiStreamAkka: Seq[ModuleID] = Seq(
    "com.typesafe.akka" %% "akka-actor-typed" % Versions.akkaActorStream,
    "com.typesafe.akka" %% "akka-stream" % Versions.akkaActorStream,
    "com.typesafe.akka" %% "akka-http" % Versions.akkaHttp,
    "com.lightbend.akka" %% "akka-stream-alpakka-sse" % Versions.alpakkaSSE,
    "com.typesafe.akka" %% "akka-slf4j" % Versions.akkaActorStream,
    "ch.qos.logback" % "logback-classic" % "1.2.3"
  )

  val wikiApi: Seq[ModuleID] = Seq(
    "io.circe" %% "circe-generic" % Versions.circe,
    "io.circe" %% "circe-parser" % Versions.circe
  )

  val scalapbProtos: Seq[ModuleID] = Seq(
    "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"
  )

}
