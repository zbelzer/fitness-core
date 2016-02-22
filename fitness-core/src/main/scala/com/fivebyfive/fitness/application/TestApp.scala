package com.fivebyfive.fitness.application

import java.util.concurrent.TimeUnit

import com.fivebyfive.fitness.model._
import org.joda.time.DateTime

object TestApp extends App {
  println("Loading history from CSV")

  val path = getClass.getResource("/belzer.csv")
  val history = History.fromCSV(path)

  println(s"Found ${history.workouts.size} workouts")
  println

  val program = new Program()
  val today = DateTime.now
  val workout =
    program.nextWorkout(today, history).getOrElse {
      throw new Exception("A Workout could not be generated")
    }

  println(s"Your workout for ${today.toString("Y-MM-dd")} is:")
  workout.routines.foreach { routine =>
    println(routine)
  }
  println(s"Routines: ${workout.routines.size}")
  println(s"Estimated duration: ${workout.duration.toUnit(TimeUnit.MINUTES)} minutes")
  println("Scores:")
  workout.scores.foreach { score =>
    println(s"\t ${score}")
  }
}