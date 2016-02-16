package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.model.Muscle._
import com.fivebyfive.fitness.model.MuscleGroup.{Core, LowerBody, UpperBody}
import enumeratum._

import scala.util.Random

sealed abstract class Exercise(
  val muscleMap: Map[Muscle, Double]
) extends EnumEntry {
  val muscleGroup: MuscleGroup = {
    muscleMap.groupBy(_._1.group).map { case (group, thing) =>
      (group, thing.values.sum)
    }.toSeq.sortBy(_._2).last._1
  }
}

object Exercise extends Enum[Exercise] {
  val values = findValues

  def randomPermutations(max: Int): Iterator[Seq[Exercise]] = {
    Random.shuffle(values).permutations.take(max)
  }

  val lowerBody: Seq[Exercise] = {
    values.filter { e => e.muscleGroup == LowerBody}
  }

  val upperBody: Seq[Exercise] = {
    values.filter { e => e.muscleGroup == UpperBody}
  }

  val core: Seq[Exercise] = {
    values.filter { e => e.muscleGroup == Core}
  }

  case object BicepCurls extends Exercise(
    Map(
      BicepsBrachii -> 0.70,
      Brachialis -> 0.15,
      Brachioradialis -> 0.15
    )
  )

  case object DragCurls extends Exercise(
    Map(
      BicepsBrachii -> 0.72,
      Brachialis -> 0.07,
      Brachioradialis -> 0.07,
      DeltoidPosterior -> 0.07,
      DeltoidAnterior -> 0.07
    )
  )

  case object SitUps extends Exercise(
    Map(
      RectusAbdominis -> 0.70,
      Iliopsoas -> 0.06,
      TensorFasciaeLatae -> 0.06,
      RectusFemoris -> 0.06,
      Sartorius -> 0.06,
      Obliques -> 0.06
    )
  )

  case object LegExtensions extends Exercise(
    Map(Quadriceps -> 1.0)
  )

  case object PullUps extends Exercise(
    Map(
      LatissimusDorsi -> 0.64,
      BicepsBrachii -> 0.03,
      Brachialis -> 0.03,
      Brachioradialis -> 0.03,
      TeresMajor -> 0.03,
      DeltoidPosterior -> 0.03,
      Infraspinatus -> 0.03,
      TeresMinor -> 0.03,
      Rhomboids -> 0.03,
      LevatorScapulae -> 0.03,
      TrapeziusLower -> 0.03,
      TrapeziusMiddle -> 0.03,
      PectoralisMinor -> 0.03
    )
  )

  case object CableNeckExtension extends Exercise(
    Map(
      Splenius -> 0.80,
      TrapeziusUpper -> 0.05,
      LevatorScapulae -> 0.05,
      ErectorSpinae -> 0.05,
      Sternocleidomastoid -> 0.05
    )
  )

  case object AssistedTricepsDip extends Exercise(
    Map(
      TricepsBrachii -> 0.70,
      DeltoidAnterior -> 0.05,
      PectoralisMajor -> 0.05,
      PectoralisMinor -> 0.05,
      Rhomboids -> 0.05,
      LevatorScapulae -> 0.05,
      LatissimusDorsi -> 0.05
    )
  )

  case object CloseGripBenchPress extends Exercise(
    Map(
      TricepsBrachii -> 0.80,
      DeltoidAnterior -> 0.10,
      PectoralisMajor -> 0.10
    )
  )

  case object WristCurl extends Exercise(
    Map(WristFlexors -> 1.0)
  )

  case object ReverseWristCurl extends Exercise(
    Map(WristExensors -> 1.0)
  )

  case object Squat extends Exercise(
    Map(
      GluteusMaximus -> 0.70,
      Quadriceps -> 0.20,
      Adductors -> 0.05,
      Soleus -> 0.05
    )
  )

  case object Deadlift extends Exercise(
    Map(
      GluteusMaximus -> 0.60,
      Quadriceps -> 0.10,
      Adductors -> 0.10,
      Hamstrings -> 0.10,
      Soleus -> 0.10
    )
  )

  case object Twists extends Exercise(
    Map(
      Obliques -> 0.72,
      TensorFasciaeLatae -> 0.04,
      GluteusMedius -> 0.04,
      GluteusMinimus -> 0.04,
      Adductors -> 0.04,
      Iliopsoas -> 0.04,
      QuadratusLumborum -> 0.04,
      ErectorSpinae -> 0.04
    )
  )

  case object StandingCalfRaise extends Exercise(
    Map(
      Gastrocnemius -> 0.80,
      Soleus -> 0.20
    )
  )

  case object ReverseCalfRaise extends Exercise(
    Map(
      TibialisAnterior -> 1
    )
  )

  case object FrontLateralRaise extends Exercise(
    Map(
      Supraspinatus -> 0.75,
      DeltoidLateral -> 0.05,
      DeltoidAnterior -> 0.05,
      TrapeziusMiddle -> 0.05,
      TrapeziusLower -> 0.05,
      SerratusAnterior -> 0.05
    )
  )

  case object HipAdduction extends Exercise(
    Map(
      Adductors -> 0.80,
      Pectineous -> 0.10,
      Gracilis -> 0.10
    )
  )

  case object AbdominalVacuum extends Exercise(
    Map(
      TransverseAbdominus -> 1.0
    )
  )

  case object GluteHamRaise extends Exercise(
    Map(
      Hamstrings -> 0.64,
      GluteusMaximus -> 0.06,
      Adductors -> 0.06,
      Gastrocnemius -> 0.06,
      Sartorius -> 0.06,
      Gracilis -> 0.06,
      Popliteus -> 0.06
    )
  )

  case object ShoulderInternalRotation extends Exercise(
    Map(
      Subscapularis -> 0.80,
      PectoralisMajor -> 0.05,
      LatissimusDorsi -> 0.05,
      TeresMajor -> 0.05,
      DeltoidAnterior -> 0.05
    )
  )

  case object HipExternalRotation extends Exercise(
    Map(
      HipExternalRotators -> 1.0
    )
  )

  def minimalCoveringSet(muscles: Set[Muscle] = Muscle.values.toSet): Set[Exercise] = {
    val MAX_PERMUTATIONS = 1000

    val options =
      Exercise.values.permutations.take(MAX_PERMUTATIONS).map { randomExercises =>
        var currentExercises = Set[Exercise]()

        def missingMuscles(exercise: Exercise): Boolean = {
          val mappedMuscles = currentExercises.flatMap(_.muscleMap.keys)
          (Muscle.values.toSet diff mappedMuscles).nonEmpty
        }

        randomExercises.takeWhile(missingMuscles).foreach { exercise =>
          currentExercises += exercise
        }

        currentExercises
      }

    options.minBy(_.size)
  }
}
