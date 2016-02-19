package com.fivebyfive.fitness.strategy.scoring

import com.fivebyfive.fitness.model.{Workout, ScoredWorkout}

object Scoring {
  val algorithms = Seq(
    EvenGroupDistribution
  )

  def run(workout: Workout): ScoredWorkout = {
    val scores = algorithms.map { alg =>
      alg.score(workout)
    }

    ScoredWorkout(workout , scores)

  }
}