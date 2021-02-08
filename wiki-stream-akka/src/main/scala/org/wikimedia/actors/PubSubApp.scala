package org.wikimedia.actors

import akka.actor.typed.pubsub._
import akka.actor.typed.scaladsl.Behaviors

object PubSubApp {

  def setup = Behaviors.setup[Topic] { ctx =>
    val topic = ctx.spawn(Topic[Message]("my-topic"), "MyTopic")

  }

  def main(args: Array[String]): Unit = {}
}
