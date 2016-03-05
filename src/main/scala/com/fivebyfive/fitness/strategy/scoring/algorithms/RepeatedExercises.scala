package com.fivebyfive.fitness.strategy.scoring.algorithms

import java.util.concurrent.TimeUnit

import com.fivebyfive.fitness.model.{History, Workout}
import com.fivebyfive.fitness.strategy.scoring.{Score, ScoringAlgorithm}

import scala.concurrent.duration.Duration

object RepeatedExercises extends ScoringAlgorithm {
  import TimeUnit._

  val DEFAULT_SCORE = 3.0
  val NO_SCORE = 0.0

  val twoWeeks = Duration(14, DAYS)
  val oneWeek = Duration(7, DAYS)
  val threeDays = Duration(3, DAYS)

  def score(history: History, workout: Workout): Score = {

    val result =
      workout.exercises.map { exercise =>
        history.latestExercises.get(exercise).map { lastTime =>
          val millisSinceLast = workout.date.getMillis - lastTime.getMillis
          val span = Duration(millisSinceLast, TimeUnit.MILLISECONDS)

          if (span > twoWeeks) 3
          else if (span > oneWeek) 2
          else if (span > threeDays) 1
          else NO_SCORE
        }.getOrElse(DEFAULT_SCORE)
      }.sum


    val maxScore = workout.exercises.size * 3.0

    Score(this, result / maxScore)
  }
}
