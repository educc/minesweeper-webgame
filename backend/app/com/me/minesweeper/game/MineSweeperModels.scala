package com.me.minesweeper.game

case class MineSweeperProps(rows: Int, cols: Int, bombs: Int)

sealed trait GameState
case object RUNNING extends GameState
case object GAME_OVER extends GameState
case object WINNER extends GameState

sealed trait GameCell
case object AVAILABLE extends GameCell {
  override def toString: String = "-"
}

case object BOMB extends GameCell {
  override def toString: String = "*"
}
case object LOSE extends GameCell {
  override def toString: String = "X"
}

case class Played(number: Int) extends GameCell {
  override def toString: String = number.toString
}

