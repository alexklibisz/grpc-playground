package org.wikimedia

import akka.actor.ActorSystem
import akka.grpc.GrpcClientSettings
import org.wikimedia.change._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object ClientApp {

  def main(args: Array[String]): Unit = {
    implicit val sys = ActorSystem()
    import sys.dispatcher

    val clientSettings =
      GrpcClientSettings.connectToServiceAt("localhost", 50051).withTls(false)
    val client = ChangeServiceClient(clientSettings)

    def streamRequest(i: Int) =
      client
        .streamChanges(StreamChangesRequest.defaultInstance)
        .map(c => s"$i: $c")
        .take(100)
        .runForeach(sys.log.info)

    val futures = Future.sequence((0 to 420).map(streamRequest))
    try Await.result(futures, Duration.Inf)
    finally sys.terminate()
  }

}
