
/**
 * TODO:
 *  - Show how akkaGrpcClient can depend on a library of protos.
 */

// Global settings (i.e. apply to all projects).
organization := "com.alexklibisz"

lazy val root: Project = project
  .in(file("."))
  .aggregate(akkaGrpcClient, akkaGrpcService, wikiStreamAkka, wikiApi)
  .settings(
    name := "grpc-playground",
    version := "0.1",
    scalaVersion := "2.12.8",
    publishArtifact := false
  )

lazy val akkaGrpcClient: Project = project
  .in(file("akka-grpc-client"))
   .enablePlugins(AkkaGrpcPlugin)
  .settings(
    name := "akka-grpc-client",
    description :=
      """
        |Akka-grpc client for the grpc service.
        |Published artifact includes the akka-grpc Scala client and protos for this service.
        |""".stripMargin,
    inConfig(Compile)(Seq(
      PB.protoSources += baseDirectory.value.getParentFile / "proto",
      akkaGrpcGeneratedSources := Seq(AkkaGrpc.Client)
    )),
    libraryDependencies ++= Dependencies.scalapbProtos
  )

lazy val akkaGrpcService = project
  .in(file("akka-grpc-service"))
  .dependsOn(akkaGrpcClient)
//  .enablePlugins(AkkaGrpcPlugin)
  .settings(
    name := "akka-grpc-service",
    description :=
      """
        |Akka-grpc service implementation for the grpc service.
        |Published as a Docker image, no JVM artifact.
        |Depends on the client for generated client-side service code.
        |""".stripMargin,
    publishArtifact := false,
    inConfig(Compile)(Seq(
      PB.protoSources += baseDirectory.value.getParentFile / "proto",
      akkaGrpcGeneratedSources := Seq(AkkaGrpc.Server)
    ))
  )

lazy val wikiApi = project
  .in(file("wiki-api"))
  .settings(
    name := "wiki-api",
    description := "Case classes for the Wikimedia API, based on https://github.com/wikimedia/mediawiki-event-schemas",
    libraryDependencies ++= Dependencies.wikiApi
  )


lazy val wikiStreamAkka = project
  .in(file("wiki-stream-akka"))
  .dependsOn(wikiApi)
  .settings(
    name := "wiki-stream-akka",
    description := "akka-stream API for consuming streaming data from wikipedia",
    libraryDependencies ++= Dependencies.wikiStreamAkka
  )