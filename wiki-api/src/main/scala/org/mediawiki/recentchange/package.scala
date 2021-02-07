package org.mediawiki

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

/**
  * Generated using https://cchandurkar.github.io/case-class-generator/ (replaced a few Any with Int.)
  * Based on JSONSchema: https://github.com/wikimedia/mediawiki-event-schemas/blob/master/jsonschema/mediawiki/recentchange/1.0.0.json
  */
package object recentchange {

  /**
    * Represents a MW RecentChange event. https://www.mediawiki.org/wiki/Manual:RCFeed
    *
    *
    * @param `$schema` A URI identifying the JSONSchema for this event. This should match an schema's $id in a schema repository. E.g. /schema_name/1.0.0
    * @param meta
    * @param id ID of the recentchange event (rcid).
    * @param `type` Type of recentchange event (rc_type). One of "edit", "new", "log", "categorize", or "external". (See Manual:Recentchanges table#rc_type)
    * @param title Full page name, from Title::getPrefixedText.
    * @param namespace ID of relevant namespace of affected page (rc_namespace, page_namespace). This is -1 ("Special") for log events.
    * @param comment (rc_comment)
    * @param parsedcomment The rc_comment parsed into simple HTML. Optional
    * @param timestamp Unix timestamp (derived from rc_timestamp).
    * @param user (rc_user_text)
    * @param bot (rc_bot)
    * @param server_url $wgCanonicalServer
    * @param server_name $wgServerName
    * @param server_script_path $wgScriptPath
    * @param wiki wfWikiID ($wgDBprefix, $wgDBname)
    * @param minor (rc_minor).
    * @param patrolled (rc_patrolled). This property only exists if patrolling is supported for this event (based on $wgUseRCPatrol, $wgUseNPPatrol).
    * @param length Length of old and new change
    * @param revision Old and new revision IDs
    * @param log_id (rc_log_id)
    * @param log_type (rc_log_type)
    * @param log_action (rc_log_action)
    * @param log_params Property only exists if event has rc_params.
    * @param log_action_comment
    */
  case class MediawikiRecentchange(
      `$schema`: String,
      meta: Meta,
      id: Option[Int],
      `type`: Option[String],
      title: Option[String],
      namespace: Option[Int],
      comment: Option[String],
      parsedcomment: Option[String],
      timestamp: Option[Int],
      user: Option[String],
      bot: Option[Boolean],
      server_url: Option[String],
      server_name: Option[String],
      server_script_path: Option[String],
      wiki: Option[String],
      minor: Option[Boolean],
      patrolled: Option[Boolean],
      length: Option[Length],
      revision: Option[Revision],
      log_id: Option[Int],
      log_type: Option[String],
      log_action: Option[String],
      log_action_comment: Option[String]
  )

  object MediawikiRecentchange {
    implicit def decoder: Decoder[MediawikiRecentchange] = deriveDecoder
    implicit def encoder: Encoder[MediawikiRecentchange] = deriveEncoder
  }

  /**
    * @param uri Unique URI identifying the event or entity
    * @param request_id Unique ID of the request that caused the event
    * @param id Unique ID of this event
    * @param dt Event datetime, in ISO-8601 format
    * @param domain Domain the event or entity pertains to
    * @param stream Name of the stream/queue/dataset that this event belongs in
    */
  case class Meta(
      uri: Option[String],
      request_id: Option[String],
      id: String,
      dt: String,
      domain: Option[String],
      stream: String
  )

  object Meta {
    implicit def decoder: Decoder[Meta] = deriveDecoder
    implicit def encoder: Encoder[Meta] = deriveEncoder
  }

  /**
    * Length of old and new change
    *
    * @param old (rc_old_len)
    * @param `new` (rc_new_len)
    */
  case class Length(
      old: Option[Int],
      `new`: Option[Int]
  )

  object Length {
    implicit def decoder: Decoder[Length] = deriveDecoder
    implicit def encoder: Encoder[Length] = deriveEncoder
  }

  /**
    * Old and new revision IDs
    *
    * @param `new` (rc_last_oldid)
    * @param old (rc_this_oldid)
    */
  case class Revision(
      `new`: Option[Int],
      old: Option[Int]
  )

  object Revision {
    implicit def decoder: Decoder[Revision] = deriveDecoder
    implicit def encoder: Encoder[Revision] = deriveEncoder
  }

}
