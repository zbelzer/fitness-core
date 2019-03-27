package com.fivebyfive.fitness.model

trait RoutineLike {
  def exercise: Exercise
  def sets: Iterable[RepSetLike]

  lazy val inverted = sets.forall(_.inverted)

  def inversion = exercise.inversion
}
