package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.strategy.scoring.{Score, ScoringAlgorithm}

object ScoredWorkout {
  def apply(workout: Workout, scores: Map[ScoringAlgorithm, Double]): ScoredWorkout = {
    new ScoredWorkout(workout, scores.map { case(a, v) => Score(a, v)}.toSet )
  }
}

case class ScoredWorkout(workout: Workout, scores: Set[Score]) extends WorkoutLike {
  val totalScore = scores.foldLeft(0.0) { case(prod, score) => prod + score.value }

  def duration = workout.duration
  def routines = workout.routines
}
