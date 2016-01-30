package com.fivebyfive.fitness.model

case class Routine(exercise: Exercise, sets: Seq[RepSet]) {
  override def toString: String = {
    s"""${exercise.name}
      |${sets.map(_.toString).mkString("\n")}
    """.stripMargin
  }
}
