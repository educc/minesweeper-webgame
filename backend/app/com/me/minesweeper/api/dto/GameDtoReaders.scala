package com.me.minesweeper.api.dto

import play.api.libs.json.{Json, Reads}


object GameDtoReaders {

  implicit val gameMoveReader: Reads[GameMove] = Json.reads[GameMove]

}