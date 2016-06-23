package com.fivebyfive.fitness.model

import java.util.concurrent.TimeUnit

import scala.concurrent.duration.Duration

trait WorkoutLike {
  def routines: Iterable[Routine]

  lazy val duration: Duration = {
    val sum = routines.map(_.duration).sum
    Duration(sum, TimeUnit.SECONDS)
  }
}
