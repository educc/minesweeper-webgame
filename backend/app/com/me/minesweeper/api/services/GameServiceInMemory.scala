package com.me.minesweeper.api.services

import com.me.minesweeper.game.{MineSweeper, MineSweeperProps}

import java.util.UUID
import javax.inject.Singleton

@Singleton
class GameServiceInMemory extends GameService {

  private var gameMap = Map.empty[String, MineSweeper]

  override def find(gameId: String): Option[MineSweeper] = gameMap.get(gameId)

  override def create(props: MineSweeperProps): String = {
    val uniqueId = UUID.randomUUID().toString
    val game = new MineSweeper(props)
    gameMap = gameMap + (uniqueId -> game)

    uniqueId
  }
}
