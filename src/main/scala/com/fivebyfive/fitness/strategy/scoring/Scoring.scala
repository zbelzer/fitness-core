package com.fivebyfive.fitness.strategy.scoring

import com.fivebyfive.fitness.model.{History, Workout, ScoredWorkout}
import com.fivebyfive.fitness.strategy.scoring.algorithms.{RepeatedExercises, EvenGroupDistribution}

case class Scoring(algorithms: Seq[ScoringAlgorithm]) {

  def run(history: History, workout: Workout): ScoredWorkout = {
    val scores = algorithms.map { alg =>
      alg.score(history, workout)
    }

    ScoredWorkout(workout, scores.toSet)
  }
}