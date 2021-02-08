package org.wikimedia.change

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.Source
import org.wikimedia.streams.Sources

class ChangeServiceImpl(implicit sys: ActorSystem) extends ChangeService {

  def streamChanges(
      r: StreamChangesRequest): Source[StreamChangesResponse, NotUsed] = {
    // TODO: implement pubsub so that there's a topic of changes and each streaming request subscribes to the topic.
    Sources
      .recentChange()
      .map(rc =>
        StreamChangesResponse(Some(Change(rc.id, rc.server_url, rc.title))))
  }
}
