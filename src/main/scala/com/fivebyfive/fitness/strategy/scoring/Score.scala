package com.fivebyfive.fitness.strategy.scoring

case class Score(val algorithm: ScoringAlgorithm, val value: Double) {
  override def toString: String = {
    val name = this.algorithm.getClass.getSimpleName.dropRight(1)
    s"${name}: ${value}"
  }
}

