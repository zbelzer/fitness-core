package com.fivebyfive.fitness.model

trait RepSetLike {
  def reps: Int
  def weight: Option[Double]

  lazy val inverted = weight.exists(_ < 0)
}
