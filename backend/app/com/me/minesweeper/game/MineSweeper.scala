package com.me.minesweeper.game

import com.me.minesweeper.game.MineSweeperUtils.{ADJACENTS, random}
import com.me.minesweeper.game.MoveType.MoveType

class MineSweeper(props: MineSweeperProps) {

  val bombs = props.bombs
  val rows = props.rows
  val cols = props.cols

  // validations
  require(bombs < rows * cols, "Too many bombs")

  // internal classes
  case class Pos(row: Int, col: Int) {
    def isValid() = row >= 0 && col >= 0 && row < rows && col < cols
  }

  // public attributes

  def gameBoard = this.board

  def gameState = this.state

  // public methods

  def move(row: Int, col: Int, moveType: MoveType): Array[Array[GameCell]] = {
    val pos = Pos(row - 1, col - 1)
    require(pos.isValid())

    val cellState = board(pos.row)(pos.col)
    moveType match {
      case MoveType.MOVE =>
        if (cellState == AVAILABLE)
          makeMove(pos)
      case MoveType.FLAG =>
        board(pos.row)(pos.col) = cellState match {
          case AVAILABLE => FLAG
          case FLAG => AVAILABLE
          case _ => cellState
        }
    }
    board
  }


  // internal attributes

  private var movesLeft = cols * rows - bombs
  private var state: GameState = RUNNING
  private var board: Array[Array[GameCell]] = MineSweeperUtils.createBoard(rows, cols)
  val bombPositions: Set[Pos] = {
    var result = Set.empty[Pos]
    while (result.size < bombs) {
      result = result + makeRandomPos()
    }
    result
  }


  // internal methods

  private def makeMove(pos: Pos): Unit = {
    if (bombPositions.contains(pos)) {
      state = GAME_OVER
      revealBombs()
      board(pos.row)(pos.col) = LOSE
    } else {
      exploreBoard(pos)
      if (movesLeft == 0) {
        state = WINNER
      }
    }
  }

  private def exploreBoard(pos: Pos): Unit = if (board(pos.row)(pos.col) == AVAILABLE) {
    val bombsCount = count(pos)
    board(pos.row)(pos.col) = Played(bombsCount)
    movesLeft = movesLeft - 1
    if (bombsCount == 0) {
      adjacent(pos)
        .foreach(exploreBoard)
    }
  }

  private def count(pos: Pos): Int = adjacent(pos)
    .map(it => if (bombPositions.contains(it)) 1 else 0)
    .sum

  private def adjacent(pos: Pos): Seq[Pos] = ADJACENTS
    .map(it => Pos(pos.row + it._1, pos.col + it._2))
    .filter(_.isValid())

  private def revealBombs() = bombPositions
    .foreach(it => board(it.row)(it.col) = BOMB)


  private def makeRandomPos(): Pos = Pos(random.between(0, rows), random.between(0, cols))

}
