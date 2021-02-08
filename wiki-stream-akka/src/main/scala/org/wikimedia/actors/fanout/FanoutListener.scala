package org.wikimedia.actors.fanout

import akka.actor.ActorPath
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

object FanoutListener {

  def listen[T](): Behavior[T] =
    Behaviors.receive[T] { (ctx, t) =>
      ctx.log.info(t.toString)
      Behaviors.same
    }

}
