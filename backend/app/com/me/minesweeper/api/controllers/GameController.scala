package com.me.minesweeper.api.controllers

import com.me.minesweeper.api.dto.GameDtoReaders._
import com.me.minesweeper.api.dto.GameDtoWriters._
import com.me.minesweeper.api.dto._
import com.me.minesweeper.api.services.GameService
import com.me.minesweeper.game.MoveType.MoveType
import com.me.minesweeper.game.{MineSweeper, MineSweeperProps, MoveType}
import play.api.Logger
import play.api.libs.json.{JsError, Json, Reads}
import play.api.mvc._

import javax.inject._
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success, Try}

@Singleton
class GameController @Inject()(cc: ControllerComponents, gameService: GameService)(implicit exec: ExecutionContext) extends AbstractController(cc) {

  val logger: Logger = Logger(this.getClass())

  def validateJson[A: Reads] = parse.json.validate(
    _.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e)))
  )

  def createGame = Action(validateJson[MineSweeperProps]) { req =>
    Try(gameService.create(req.body)) match {
      case Success(gameId) =>
        val gameCreated = GameCreated(gameId)
        Ok(Json.toJson(gameCreated))
      case Failure(ex: IllegalArgumentException) =>
        BadRequest(ex.getMessage)
      case ex: Throwable =>
        logger.error("at creating game", ex)
        InternalServerError("Some error")
    }
  }

  def getGameState(gameID: String) = Action {
    gameService.find(gameID) match {
      case Some(value) =>
        val gameBoard = GameBoard.from(value)
        val gameInfo = GameInfo(value.cols, value.rows, gameBoard)
        Ok(Json.toJson(gameInfo))
      case None => NotFound
    }
  }

  def makeMove(gameID: String) = Action(validateJson[GameMove]) { req =>

    val makeMoveObject = (game: MineSweeper) => {
      val move = req.body
      val moveType = move.moveType match {
        case com.me.minesweeper.api.dto.GameMoveType.MOVE => MoveType.MOVE
        case com.me.minesweeper.api.dto.GameMoveType.FLAG => MoveType.FLAG
      }
      (game, move, moveType)
    }

    gameService.find(gameID)
      .map(makeMoveObject)
      .map({
        case (game, move, moveType) =>
          Try(game.move(move.row, move.col, moveType)) match {
            case Success(_) =>
              val gameBoard = GameBoard.from(game)
              if (game.isGameFinished) {
                gameService.remove(gameID)
              }
              Ok(Json.toJson(gameBoard))
            case Failure(ex: IllegalArgumentException) =>
              BadRequest(ex.getMessage)
            case ex: Throwable =>
              logger.error("at creating game", ex)
              InternalServerError("Some error")
          }
      })
      .getOrElse(NotFound)
  }

}
