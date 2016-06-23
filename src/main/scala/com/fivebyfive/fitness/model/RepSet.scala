package com.fivebyfive.fitness.model

case class RepSet(reps: Int, weight: Option[Double] = None) {
  lazy val inverted = weight.exists(_ < 0)

  override def toString: String = {
    weight.map { weight =>
      s"""Reps: $reps Weight: $weight"""
    }.getOrElse {
      s"""Reps: $reps"""
    }
  }
}