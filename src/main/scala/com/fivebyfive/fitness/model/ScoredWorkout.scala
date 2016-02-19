package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.strategy.scoring.Score

case class ScoredWorkout(val workout: Workout, val scores: Seq[Score]) {
  val totalScore = scores.foldLeft(1.0) { case(prod, score) => prod * score.value }

  def routines = workout.routines
  def duration = workout.duration
}
