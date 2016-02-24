package com.fivebyfive.fitness.strategy.scoring.algorithms

import com.fivebyfive.fitness.model.{History, Workout}
import com.fivebyfive.fitness.strategy.scoring.{Score, ScoringAlgorithm}

/**
  * Score a workout with Exercises over a given difficulty with 0 so they do not occur.
  */
class ExerciseDifficulty(maxDifficulty: Int) extends ScoringAlgorithm {
  def score(history: History, workout: Workout): Score = {
    Score(this, 0)
  }
}
