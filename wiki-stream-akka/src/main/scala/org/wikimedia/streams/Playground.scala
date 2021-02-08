package org.wikimedia.streams

import akka.actor.ActorSystem

import scala.concurrent.Await
import scala.concurrent.duration._

object Playground extends App {
  implicit val sys = ActorSystem("playground")
  val future =
    Sources
      .recentChange()
      .map(ev => (ev.id, ev.server_url, ev.title))
      .runForeach(println)
  Await.ready(future, Duration.Inf)
}
