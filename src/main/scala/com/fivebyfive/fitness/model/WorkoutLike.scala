package com.fivebyfive.fitness.model

trait WorkoutLike {
  def routines: Iterable[RoutineLike]
}
