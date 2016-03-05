package com.fivebyfive.fitness.strategy.volume

import com.fivebyfive.fitness.Util
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction
import org.apache.commons.math3.fitting.{PolynomialCurveFitter, WeightedObservedPoints}
import org.joda.time.DateTime

object VolumePredictor {
  import Util._

  final val EXPONENT_VALUE = 1

  val fitter = PolynomialCurveFitter.create(EXPONENT_VALUE)

  def next(data: Iterable[(DateTime, Double)], date: DateTime): Double = {
    val observations = new WeightedObservedPoints

    data.foreach{ case (date, volume) =>
      observations.add(dateToDouble(date), volume)
    }

    val fun = new PolynomialFunction(fitter.fit(observations.toList))
    toHalf(fun.value(dateToDouble(date)))
  }
}
