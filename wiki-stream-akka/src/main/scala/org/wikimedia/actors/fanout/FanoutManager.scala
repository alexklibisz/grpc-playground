package org.wikimedia.actors.fanout

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object FanoutManager {

  sealed trait Message[T]
  final case class AddListener[T](listener: ActorRef[T]) extends Message[T]
  final case class RemoveListener[T](listener: ActorRef[T]) extends Message[T]
  final case class Fanout[T](t: T) extends Message[T]

  def ready[T](listeners: Set[ActorRef[T]] = Set.empty[ActorRef[T]])
    : Behavior[Message[T]] =
    Behaviors.receiveMessage {
      case AddListener(listener)    => ready(listeners + listener)
      case RemoveListener(listener) => ready(listeners - listener)
      case Fanout(t) =>
        listeners.foreach(_ ! t)
        Behaviors.same
    }

}
