package com.fivebyfive.fitness.model

case class RepSet(reps: Int, weight: Option[Double] = None) {
  override def toString: String = {
    weight.map { weight =>
      s"""Reps: $reps Weight: $weight"""
    }.getOrElse {
      s"""Reps: $reps"""
    }
  }
}