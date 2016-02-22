package com.fivebyfive.fitness.strategy.scoring

import com.fivebyfive.fitness.model.{History, Workout}

trait ScoringAlgorithm {
  def score(history: History, workout: Workout): Score
}
