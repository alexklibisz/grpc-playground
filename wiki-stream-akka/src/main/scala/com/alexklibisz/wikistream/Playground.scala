package com.alexklibisz.wikistream

import akka.actor.ActorSystem

import scala.concurrent.Await
import scala.concurrent.duration._

object Playground extends App {
  implicit val sys = ActorSystem("playground")
  val future = Sources.recentChange().runForeach(println)
  Await.ready(future, Duration.Inf)
}
