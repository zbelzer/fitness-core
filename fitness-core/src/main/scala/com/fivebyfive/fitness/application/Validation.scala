package com.fivebyfive.fitness.application

import com.fivebyfive.fitness.model.{Muscle, Exercise}
import com.fivebyfive.fitness.Util._
import enumeratum._

object Validation {
  def validate(target: Enum[Exercise]): Unit = {
    target.values.foreach { exercise =>
      val sum = roundNearestHundredth(exercise.muscleMap.values.sum)

      if (sum != 1.0) {
        throw new RuntimeException(s"$exercise muscle distribution $sum is not 1")
      }
    }

    val mappedMuscles =
      target.values.foldLeft(Set[Muscle]()) { case(set, exercise) =>
        set ++ exercise.muscleMap.keys.toSet
      }
    val unmappedMuscles = Muscle.values.toSet diff mappedMuscles

    if (unmappedMuscles.nonEmpty) {
      throw new RuntimeException(s"The following muscles are unmapped by exercises\n$unmappedMuscles")
    }

    println("All Exercises pass validation")
  }
}
