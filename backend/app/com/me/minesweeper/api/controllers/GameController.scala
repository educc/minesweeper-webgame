package com.me.minesweeper.api.controllers

import com.me.minesweeper.api.dto.GameDtoWriters._
import com.me.minesweeper.api.dto._
import play.api.libs.json.Json
import play.api.mvc._

import java.util.UUID
import javax.inject._

@Singleton
class GameController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {


  def createGame = Action {
    val gameId = UUID.randomUUID().toString
    val gameCreated = GameCreated(gameId)
    Ok(Json.toJson(gameCreated))
  }

  def getGameState(gameID: String) = Action {
    val obj = GameInfo(10, 10, GameBoard(GameState.ON_PLAY, Seq(Cell(1,1,'E'))))
    Ok(Json.toJson(obj))
  }

  def makeMove(gameID: String) = Action {
    val userMove = ""
    val gameBoard = GameBoard(GameState.ON_PLAY, Seq.empty)
    Ok(Json.toJson(gameBoard))
  }

}
