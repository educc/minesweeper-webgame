package com.me.minesweeper.game

case class MineSweeperProps(rows: Int, cols: Int, bombs: Int)


object MoveType extends Enumeration {
  type MoveType = Value
  val MOVE, FLAG = Value
}

sealed trait GameState
case object RUNNING extends GameState
case object GAME_OVER extends GameState
case object WINNER extends GameState

sealed trait GameCell
case object AVAILABLE extends GameCell {
  override def toString: String = "-"
}
case object FLAG extends GameCell {
  override def toString: String = "F"
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

