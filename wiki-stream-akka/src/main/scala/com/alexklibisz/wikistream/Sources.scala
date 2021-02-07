package com.alexklibisz.wikistream

import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl._
import akka.http.scaladsl.model.Uri
import akka.stream.alpakka.sse.scaladsl.EventSource
import akka.stream.scaladsl.Source
import io.circe.parser.decode
import org.mediawiki.recentchange.MediawikiRecentchange

import scala.concurrent.duration._

object Sources {

  def recentChange(uri: Uri = Uri(org.mediawiki.urls.V2StreamRecentChange),
                   retryDelay: FiniteDuration = 1.second)(
      implicit sys: ActorSystem): Source[MediawikiRecentchange, NotUsed] =
    EventSource(
      uri = uri,
      send = Http().singleRequest(_),
      retryDelay = retryDelay
    ).map(_.data)
      .map(decode[MediawikiRecentchange])
      .flatMapConcat {
        case Left(err) =>
          sys.log.warning(err.getMessage, err.getCause)
          Source.empty[MediawikiRecentchange]
        case Right(value) => Source.single(value)
      }

}
