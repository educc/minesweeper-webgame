package com.me.minesweeper.api.dto

import com.me.minesweeper.api.dto.GameState.GameState

// output

case class GameCreated(gameId: String)
case class GameInfo(cols: Int, rows: Int, lastState: GameBoard)
case class GameBoard(state: GameState, cells: Seq[Cell])

case class Cell (row: Int, col: Int, value: Char)

object GameState extends Enumeration {
  type GameState = Value
  val ON_PLAY, WINNER, LOSER = Value
}

// input