package com.fivebyfive.fitness.model

case class Routine(exercise: Exercise, sets: Seq[RepSet]) {
  def volume = {
    sets.map { s => s.weight.map(_ * s.reps).getOrElse(s.reps.toDouble) }.sum
  }

  def weightedRoutine: Boolean = {
    sets.flatMap(_.weight).nonEmpty
  }

  override def toString: String = {
    s"""${exercise.name}
      |${sets.map(_.toString).mkString("\n")}
    """.stripMargin
  }
}
