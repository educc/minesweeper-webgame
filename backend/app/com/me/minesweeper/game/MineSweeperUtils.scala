package com.me.minesweeper.game

import scala.util.Random

object MineSweeperUtils {


  val random = new Random()

  val ADJACENTS = Seq(
    (-1, -1), (-1, 0), (-1, 1),
    (0, -1), (0, 1),
    (1, -1), (1, 0), (1, 1))

  def createBoard(rows: Int, cols: Int): Array[Array[GameCell]] = (0 until rows)
    .map(_ => Array.fill[GameCell](cols)(AVAILABLE))
    .toArray
}
