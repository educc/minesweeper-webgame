package com.me.minesweeper.api.dto

import com.me.minesweeper.api.dto.GameState.{GameState, ON_PLAY}
import com.me.minesweeper.game.{AVAILABLE, BOMB, GAME_OVER, GameCell, LOSE, MineSweeper, Played, RUNNING, WINNER}


case class GameCreated(gameId: String)
case class GameInfo(cols: Int, rows: Int, lastState: GameBoard)
object GameInfo {
  def from(it: MineSweeper): GameInfo = GameInfo(it.cols, it.rows, GameBoard.from(it))
}

case class GameBoard(state: GameState, cells: Seq[Cell])
object GameBoard {
  def from(it: MineSweeper): GameBoard = {
    val state = it.gameState match {
      case RUNNING =>  GameState.ON_PLAY
      case WINNER =>  GameState.WINNER
      case GAME_OVER =>  GameState.LOSER
    }

    val cells = for {
      i <- 0 until it.gameBoard.length
      j <- 0 until it.gameBoard(i).length
    } yield Cell.from(i, j, it.gameBoard(i)(j))

    GameBoard(state, cells)
  }
}

case class Cell (row: Int, col: Int, value: Char)
object Cell {
  def from(row: Int, col: Int, cell: GameCell): Cell = {
    val myChar = cell match {
      case AVAILABLE => 'A'
      case BOMB => 'B'
      case LOSE => 'L'
      case Played(number) => number.toString.charAt(0)
    }
    Cell(row, col, myChar)
  }
}

object GameState extends Enumeration {
  type GameState = Value
  val ON_PLAY, WINNER, LOSER = Value
}
