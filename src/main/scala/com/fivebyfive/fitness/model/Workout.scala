package com.fivebyfive.fitness.model

import org.joda.time.DateTime

case class Workout(override val routines: Iterable[Routine], date: DateTime) extends WorkoutPlan(routines) {
  override def toString: String = {
    val dateString = date.toString("Y-MM-dd")

    s"""Workout on $dateString
      |
      |${routines.map(_.toString).mkString("\n")}
    """.stripMargin

  }
}
