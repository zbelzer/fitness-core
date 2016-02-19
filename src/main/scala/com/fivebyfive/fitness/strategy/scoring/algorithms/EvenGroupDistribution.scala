package com.fivebyfive.fitness.strategy.scoring.algorithms

import com.fivebyfive.fitness.model.MuscleGroup._
import com.fivebyfive.fitness.model.{History, Workout}
import com.fivebyfive.fitness.strategy.scoring.{Score, ScoringAlgorithm}

object EvenGroupDistribution extends ScoringAlgorithm {
  def score(history: History, workout: Workout): Score = {
    val groups = workout.routines.map(_.exercise.muscleGroup).toSet
    if ((groups diff Set(Core, UpperBody, LowerBody)).isEmpty) {
      Score(this, 1.0)
    } else {
      Score(this, 0)
    }
  }
}
