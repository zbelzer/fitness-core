package com.fivebyfive.fitness.model

import scala.concurrent.duration._

case class WorkoutSettings(
  targetDuration: Duration,
  iterations: Int
)

object WorkoutSettings {
  def default = {
    WorkoutSettings(
      targetDuration = 30.minutes,
      iterations = 1000
    )
  }
}

