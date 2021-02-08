package org.wikimedia

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import org.wikimedia.change.{ChangeServiceHandler, ChangeServiceImpl}

import scala.concurrent.duration._

object ServiceApp {

  def main(args: Array[String]): Unit = {

    implicit val sys: ActorSystem = ActorSystem("service-app")
    import sys.dispatcher

    val changeServiceHandler = ChangeServiceHandler(new ChangeServiceImpl)

    Http()
      .newServerAt("0.0.0.0", 50051)
      .bind(changeServiceHandler)
      .map(_.addToCoordinatedShutdown(30.seconds))
      .foreach(b => sys.log.info(s"Service bound: $b"))
  }

}
