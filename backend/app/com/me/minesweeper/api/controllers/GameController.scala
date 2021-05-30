package com.me.minesweeper.api.controllers

import com.me.minesweeper.api.dto.GameDtoReaders._
import com.me.minesweeper.api.dto.GameDtoWriters._
import com.me.minesweeper.api.dto._
import com.me.minesweeper.api.services.GameService
import com.me.minesweeper.game.MineSweeperProps
import play.api.libs.json.{JsError, Json, Reads}
import play.api.mvc._

import javax.inject._
import scala.concurrent.ExecutionContext

@Singleton
class GameController @Inject()(cc: ControllerComponents, gameService: GameService)(implicit exec: ExecutionContext) extends AbstractController(cc) {


  def validateJson[A: Reads] = parse.json.validate(
    _.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e)))
  )

  def createGame = Action {
    val gameId = gameService.create(new MineSweeperProps(8,8,10))
    val gameCreated = GameCreated(gameId)
    Ok(Json.toJson(gameCreated))
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
    gameService.find(gameID) match {
      case Some(game) =>
        val move = req.body
        game.move(move.row, move.col)
        val gameBoard = GameBoard.from(game)
        Ok(Json.toJson(gameBoard))
      case None => NotFound
    }
  }

}
