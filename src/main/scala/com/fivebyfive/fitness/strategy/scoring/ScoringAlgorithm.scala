package com.fivebyfive.fitness.strategy.scoring

import com.fivebyfive.fitness.model.Workout

trait ScoringAlgorithm {
  def score(workout: Workout): Score
}
