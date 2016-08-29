package com.fivebyfive.fitness.application

import com.fivebyfive.fitness.model.{Exercise, Muscle}

object FirstWorkout extends App {
  val muscles = Muscle.values.toSet - Muscle.HipExternalRotators - Muscle.Splenius - Muscle.Sternocleidomastoid - Muscle.TransverseAbdominus
  val exercises = Exercise.minimalCoveringSet(muscles)

  exercises.foreach { exercise =>
    println(exercise)
  }
  println(s"Size: ${exercises.size}")
}
