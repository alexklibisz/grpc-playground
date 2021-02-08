package org.wikimedia.change

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.Source
import org.wikimedia.streams.Sources

class ChangeServiceImpl(implicit sys: ActorSystem) extends ChangeService {

  def streamChanges(
      r: StreamChangesRequest): Source[StreamChangesResponse, NotUsed] = {
    // TODO: is there a way to avoid opening a new connection every time?
    //  i.e. broadcast changes from a single stream to a variable number of consumers?
    Sources
      .recentChange()
      .map(rc =>
        StreamChangesResponse(Some(Change(rc.id, rc.server_url, rc.title))))
  }
}
