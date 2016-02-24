package com.fivebyfive.fitness

import org.joda.time.{DateTime, Period}

import scala.math._

object Util {
  def dateToDouble(date: DateTime) = date.getMillis / 1000.0

  def toHalf(x: Double) = Math.round(x * 2.0) / 2.0
  def toWhole(x: Double) = x.toInt

  def roundNearestHundredth(x: Double) = {
    BigDecimal(x).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  def dateRange(start: DateTime, end: DateTime, step: Period): Iterator[DateTime] =
    Iterator.iterate(start)(_.plus(step)).takeWhile(!_.isAfter(end))

  object Levenshtein {
    def minimum(i1: Int, i2: Int, i3: Int) = min(min(i1, i2), i3)

    def distance(s1: String, s2: String) = {
      val dist = Array.tabulate(s2.length + 1, s1.length + 1) { (j, i) => if (j == 0) i else if (i == 0) j else 0 }

      for (j <- 1 to s2.length; i <- 1 to s1.length)
        dist(j)(i) = if (s2(j - 1) == s1(i - 1)) dist(j - 1)(i - 1)
        else minimum(dist(j - 1)(i) + 1, dist(j)(i - 1) + 1, dist(j - 1)(i - 1) + 1)

      dist(s2.length)(s1.length)
    }
  }
}
