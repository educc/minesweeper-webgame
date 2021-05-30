package com.me.minesweeper.api.dto

import com.me.minesweeper.api.dto.GameMoveType.GameMoveType
import com.me.minesweeper.api.dto.GameState.GameState
import play.api.libs.json.{JsError, JsPath, JsResult, JsString, JsSuccess, JsValue, Json, JsonValidationError, Reads, Writes}


object GameDtoReaders {


  implicit def gameMoveTypeReader[E <: Enumeration]: Reads[GameMoveType] = new Reads[GameMoveType] {
    override def reads(json: JsValue): JsResult[GameMoveType] = json match {
      case JsString(str) if str == "MOVE" => JsSuccess(GameMoveType.MOVE)
      case JsString(str) if str == "FLAG" => JsSuccess(GameMoveType.FLAG)
      case _ => JsError("Invalid move")
    }
  }
  implicit val gameMoveReader: Reads[GameMove] = Json.reads[GameMove]
}