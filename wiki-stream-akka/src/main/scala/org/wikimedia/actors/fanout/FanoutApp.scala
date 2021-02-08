package org.wikimedia.actors.fanout

import akka.actor.ClassicActorSystemProvider
import akka.{Done, NotUsed}
import akka.actor.typed.{ActorRef, ActorSystem, Behavior, Props, SpawnProtocol}
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.Uri
import akka.stream.Materializer
import akka.stream.alpakka.sse.scaladsl.EventSource
import akka.stream.scaladsl.Source
import org.mediawiki.recentchange.MediawikiRecentchange
import org.wikimedia.actors.fanout.FanoutManager.AddListener
import io.circe.parser._

import scala.concurrent.duration._

object FanoutApp {

  def setup = Behaviors.setup[SpawnProtocol.Command] { ctx =>
    SpawnProtocol()
  }

  def recentChange(uri: Uri = Uri(org.mediawiki.urls.V2StreamRecentChange),
                   retryDelay: FiniteDuration = 1.second,
                   fanoutManager: ActorRef[FanoutManager.Fanout[MediawikiRecentchange]])(
      implicit sys: ClassicActorSystemProvider)
    : Source[Unit, NotUsed] = {
    sys.classicSystem.log.info(s"Opening a new event source stream from [$uri]")
    EventSource(
      uri = uri,
      send = Http().singleRequest(_),
      retryDelay = retryDelay
    )
      .map(_.data)
      .map(decode[MediawikiRecentchange])
      .map {
        case Left(err) =>
          sys.classicSystem.log.warning(err.getMessage, err.getCause)
        case Right(value) =>
          fanoutManager ! FanoutManager.Fanout(value)
      }
  }

  def main(args: Array[String]): Unit = {

    implicit val sys = ActorSystem(setup, "root")

    sys ! SpawnProtocol.Spawn(FanoutManager.ready[String], "fanout-manager", Props.empty, )

  }

}
