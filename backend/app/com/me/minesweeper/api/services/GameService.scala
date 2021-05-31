package com.me.minesweeper.api.services

import com.me.minesweeper.game.{MineSweeper, MineSweeperProps}

trait GameService {

  def find(gameId: String): Option[MineSweeper]
  def remove(gameId: String): Boolean
  def create(props: MineSweeperProps): String
}
