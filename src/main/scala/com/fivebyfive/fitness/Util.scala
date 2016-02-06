package com.fivebyfive.fitness

import org.joda.time.DateTime

object Util {
  def dateToDouble(date: DateTime) = date.getMillis / 1000.0

  def toHalf(x: Double) = Math.round(x * 2.0) / 2.0
  def toWhole(x: Double) = x.toInt
}
