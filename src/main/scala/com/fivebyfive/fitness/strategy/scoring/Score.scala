package com.fivebyfive.fitness.strategy.scoring

case class Score(algorithm: ScoringAlgorithm, value: Double) {
  override def toString: String = {
    val name = this.algorithm.getClass.getSimpleName.dropRight(1)
    s"${name}: ${value}"
  }
}

