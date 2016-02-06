package com.fivebyfive.fitness.strategy.volume

import com.fivebyfive.fitness.Util
import com.fivebyfive.fitness.model.{Exercise, Goal, History}
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction
import org.apache.commons.math3.fitting.{PolynomialCurveFitter, WeightedObservedPoints}
import org.joda.time.DateTime

class VolumeGenerator(history: History, optGoal: Option[Goal] = None) {
  import Util._

  final val EXPONENT_VALUE = 1

  val fitter = PolynomialCurveFitter.create(EXPONENT_VALUE)

  def nextVolume(exercise: Exercise, date: DateTime): Double = {
    val routinesByDate = history.routineHistory(exercise)
    val observations = new WeightedObservedPoints

    routinesByDate.foreach { case (routineDate, routine) =>
      observations.add(dateToDouble(routineDate), routine.volume)
    }

    optGoal.foreach { goal =>
      goal.workout.routinesForExercise(exercise).foreach { routine =>
        observations.add(dateToDouble(goal.date), routine.volume)
      }
    }

    val fun = new PolynomialFunction(fitter.fit(observations.toList))
    toHalf(fun.value(dateToDouble(date)))
  }
}
