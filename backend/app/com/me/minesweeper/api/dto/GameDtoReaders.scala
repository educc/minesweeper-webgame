package com.me.minesweeper.api.dto

import com.me.minesweeper.api.dto.GameMoveType.GameMoveType
import com.me.minesweeper.game.MineSweeperProps
import play.api.libs.json._


object GameDtoReaders {


  implicit def gameMoveTypeReader[E <: Enumeration]: Reads[GameMoveType] = new Reads[GameMoveType] {
    override def reads(json: JsValue): JsResult[GameMoveType] = json match {
      case JsString(str) if str == "MOVE" => JsSuccess(GameMoveType.MOVE)
      case JsString(str) if str == "FLAG" => JsSuccess(GameMoveType.FLAG)
      case _ => JsError("Invalid move")
    }
  }
  implicit val gameMoveReader: Reads[GameMove] = Json.reads[GameMove]
  implicit val minesweeperPropsReader: Reads[MineSweeperProps] = Json.reads[MineSweeperProps]
}