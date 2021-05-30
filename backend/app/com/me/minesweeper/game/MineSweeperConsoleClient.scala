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

    val mineSweeper = new MineSweeper(MineSweeperProps(5, 5, 4))

    while(mineSweeper.gameState == RUNNING) {
      printBoard(mineSweeper)

      print("row = ")
      val row = scala.io.StdIn.readInt()

      print("col = ")
      val col = scala.io.StdIn.readInt()
      print("type (Move = M, Flag = F) = ")
      val moveType = scala.io.StdIn.readChar() match {
        case 'F' => MoveType.FLAG
        case _ => MoveType.MOVE
      }

      mineSweeper.move(row, col, moveType)
    }
    println(s"game = ${mineSweeper.gameState}")
    println(printBoard(mineSweeper))
  }

}
