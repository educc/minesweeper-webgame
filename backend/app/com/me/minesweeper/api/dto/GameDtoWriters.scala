package com.me.minesweeper.api.dto

import com.me.minesweeper.api.dto.GameState.GameState
import play.api.libs.json.{JsString, JsValue, Json, OWrites, Writes}

object GameDtoWriters {

  implicit def gameStateWrites[E <: Enumeration]: Writes[GameState] = new Writes[GameState] {
    def writes(v: GameState): JsValue = JsString(v.toString)
  }
  implicit def charWrites: Writes[Char] = new Writes[Char] {
    def writes(v: Char): JsValue = JsString(v.toString)
  }

  implicit val gameCellWrites: OWrites[Cell] = Json.writes[Cell]
  implicit val gameCreatedWrites: OWrites[GameCreated] = Json.writes[GameCreated]
  implicit val gameBoardWrites: OWrites[GameBoard] = Json.writes[GameBoard]
  implicit val gameInfoWrites: OWrites[GameInfo] = Json.writes[GameInfo]

}
