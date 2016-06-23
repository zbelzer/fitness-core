package com.fivebyfive.fitness.strategy.scoring

import com.fivebyfive.fitness.model.{History, Workout}
import mojolly.inflector.Inflector

trait ScoringAlgorithm {
  def score(history: History, workout: Workout): Score
  val prettyName = Inflector.titleize(getClass.getSimpleName.replace("$", ""))
}
