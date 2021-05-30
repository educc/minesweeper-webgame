package com.me.minesweeper.game

object MineSweeperConsoleClient {


  def printBoard(game: MineSweeper) = {
    // print headers
    println((0 to game.cols).mkString(" "))

    // print body
    for ((rowList, i) <- game.gameBoard.zipWithIndex) {
      print((i + 1) + " ")
      println(rowList.mkString(" "))
    }
  }

  def main(args: Array[String]): Unit = {

    val mineSweeper = new MineSweeper(5, 5, 24)

    while(mineSweeper.gameState == RUNNING) {
      printBoard(mineSweeper)

      print("row = ")
      val row = scala.io.StdIn.readInt()

      print("col = ")
      val col = scala.io.StdIn.readInt()

      mineSweeper.move(row, col)
    }
    println(s"game = ${mineSweeper.gameState}")
    println(printBoard(mineSweeper))
  }

}
