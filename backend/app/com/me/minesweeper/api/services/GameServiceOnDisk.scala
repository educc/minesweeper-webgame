package com.me.minesweeper.api.services

import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.me.minesweeper.game.{MineSweeper, MineSweeperProps}

import java.nio.file.{Files, Paths}
import java.util.UUID
import javax.inject.Singleton


@Singleton
class GameServiceOnDisk extends GameService {

  val tmpPath = Paths.get("./tmp")
  val mapper = JsonMapper.builder()
    .addModule(DefaultScalaModule)
    .build()

  override def find(gameId: String): Option[MineSweeper] = {
    val pathFile = tmpPath.resolve(gameId)

    if (!Files.exists(pathFile)){
      Option.empty
    } else {
      val raw = Files.readAllBytes(pathFile)
      try {
        val obj = mapper.readValue(raw, new TypeReference[MineSweeper]{})
        Option(obj)
      }catch {
        case e: Throwable =>
          e.printStackTrace()
          Option.empty
      }
    }
  }


  override def create(props: MineSweeperProps): String = {
    val game = new MineSweeper(props)
    writeMinesweeper(game)
  }

  def writeMinesweeper(game: MineSweeper): String = {
    val gameId = UUID.randomUUID().toString
    val raw = mapper.writeValueAsString(game)
    Files.write(tmpPath.resolve(gameId), raw.getBytes)
    gameId
  }
}
