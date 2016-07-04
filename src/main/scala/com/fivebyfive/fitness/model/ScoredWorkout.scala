package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.strategy.scoring.Score

case class ScoredWorkout(workout: Workout, scores: Seq[Score]) extends WorkoutLike {
  val totalScore = scores.foldLeft(1.0) { case(prod, score) => prod * score.value }

  def duration = workout.duration
  def routines = workout.routines
}
