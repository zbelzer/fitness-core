package com.fivebyfive.fitness.model

import scala.collection.immutable.ListMap

object Routine {
  def apply(exercise: Exercise, sets: ListMap[Int, Int]): Routine = {
    new Routine(exercise, sets.map { case(r, w) => RepSet(r, Some(w)) }.toSeq )
  }

//  def apply(exercise: Exercise, sets: Seq[Int]): Routine = {
//    new Routine(exercise, sets.map(RepSet(_, None)))
//  }
}

case class Routine(exercise: Exercise, sets: Seq[RepSet]) extends RoutineLike {
  val TIME_PER_REP = 4
  val REST_INTERVAL = 60

  lazy val volume: Double = {
    sets.map { s => s.weight.map(_ * s.reps).getOrElse(s.reps.toDouble) }.sum
  }

  lazy val duration: Int = {
    sets.map(_.reps * TIME_PER_REP + REST_INTERVAL).sum
  }

  lazy val volumeByMuscle: Map[Muscle, Double] = {
    exercise.muscleMap.map { case (muscle, coef) =>
      muscle -> coef * volume
    }
  }

  lazy val weightedRoutine: Boolean = {
    sets.flatMap(_.weight).nonEmpty
  }

  override def toString: String = {
    s"""${exercise.entryName}
      |${sets.map(_.toString).mkString("\n")}
    """.stripMargin
  }
}
