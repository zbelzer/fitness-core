package com.fivebyfive.fitness

import com.fivebyfive.fitness.model.Exercise
import org.joda.time.{Period, DateTime}

object Util {
  def dateToDouble(date: DateTime) = date.getMillis / 1000.0

  def toHalf(x: Double) = Math.round(x * 2.0) / 2.0
  def toWhole(x: Double) = x.toInt

  def roundNearestHundredth(x: Double) = {
    BigDecimal(x).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  def dateRange(start: DateTime, end: DateTime, step: Period): Iterator[DateTime] =
    Iterator.iterate(start)(_.plus(step)).takeWhile(!_.isAfter(end))
}
