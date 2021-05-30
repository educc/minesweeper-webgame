package com.me.minesweeper.api.dto

case class GameMove(row: Int, col: Int) {
  require(row >= 0, "row not valid")
  require(col >= 0, "col not valid")
}
