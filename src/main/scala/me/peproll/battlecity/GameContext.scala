package me.peproll.battlecity

import me.peproll.battlecity.back.model._

final class GameContext(val userTank: PlayerTank) {

  def userMove(direction: Direction): GameContext = {
    val (x, y) = userTank.position.tuple
    val speed = userTank.rank.speed
    val newPosition = direction match {
      case Up    => Coordinates(x, (y - speed.value) max 0)
      case Down  => Coordinates(x, (y + speed.value) min Settings.gameHeight)
      case Right => Coordinates((x + speed.value) min Settings.gameWidth, y)
      case Left  => Coordinates((x - speed.value) max 0, y)
    }
    new GameContext(userTank.copy(position = newPosition, direction = direction, userTank.tankTrack.next))
  }

}

object GameContext {

  def initialState: GameContext = GameContext(
    PlayerTank(
      position = Coordinates(Settings.gameWidth / 2, Settings.gameHeight / 2),
      direction = Up,
      tankTrack = FirstPosition,
      shield = false,
      rank = Solder)
  )

  def apply(userTank: PlayerTank): GameContext = new GameContext(userTank)

}