package com.me.minesweeper.api.dto

import com.me.minesweeper.api.dto.GameMoveType.GameMoveType


object GameMoveType extends Enumeration {
  type GameMoveType = Value
  val MOVE, FLAG = Value
}

case class GameMove(row: Int, col: Int, moveType: GameMoveType) {
  require(row >= 0, "row not valid")
  require(col >= 0, "col not valid")
}
