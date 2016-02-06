package com.fivebyfive.fitness.model

import org.joda.time.DateTime

case class Workout(routines: Seq[Routine], date: DateTime) {
  def routinesForExercise(exercise: Exercise): Iterable[Routine] = {
    routines.filter(r => r.exercise == exercise)
  }

  override def toString: String = {
    val dateString = date.toString("Y-MM-dd")

    s"""Workout on $dateString
      |
      |${routines.map(_.toString).mkString("\n")}
    """.stripMargin
  }
}
