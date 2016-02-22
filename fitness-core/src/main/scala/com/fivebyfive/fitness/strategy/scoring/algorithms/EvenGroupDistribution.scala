package com.fivebyfive.fitness.strategy.scoring.algorithms

import com.fivebyfive.fitness.model.{History, MuscleGroup, Workout}
import com.fivebyfive.fitness.strategy.scoring.{Score, ScoringAlgorithm}

object EvenGroupDistribution extends ScoringAlgorithm {
  def score(history: History, workout: Workout): Score = {
    val groups: Double = workout.routines.map(_.exercise.muscleGroup).size
    val allGroups: Double = MuscleGroup.values.size

    Score(EvenGroupDistribution, groups / allGroups)
  }
}
