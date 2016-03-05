package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.Util.Levenshtein
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

  def lookup(name: String): Exercise = {
    withNameInsensitiveOption(name).getOrElse {
      val guess = values.minBy { v => Levenshtein.distance(v.entryName, name) }
      if (Levenshtein.distance(guess.entryName, name) <=   3) {
        guess
      } else {
        throw new RuntimeException(s"Could not find a match for $name guessed $guess")
      }
    }
  }

  def randomPermutations(max: Int): Stream[Seq[Exercise]] = {
    Stream.continually {
      Random.shuffle(values)
    }.take(max)
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

  case object Crunches extends Exercise(
    Map(
      RectusAbdominis -> 0.90,
      Obliques -> 0.10
    )
  )

  case object FloorRearNeckBridge extends Exercise(
     Map(
      Splenius -> 0.74,
      TrapeziusUpper -> 0.06,
      LevatorScapulae -> 0.06,
      Sternocleidomastoid -> 0.06,
      ErectorSpinae -> 0.06,
      GluteusMaximus -> 0.06,
      Quadriceps -> 0.06
    )
  )

  case object WallRearNeckBridge extends Exercise(
    Map(
      Splenius -> 0.74,
      TrapeziusUpper -> 0.06,
      LevatorScapulae -> 0.06,
      Sternocleidomastoid -> 0.06,
      ErectorSpinae -> 0.06,
      GluteusMaximus -> 0.06,
      Quadriceps -> 0.06
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

  case object CableNeckExtensions extends Exercise(
    Map(
      Splenius -> 0.80,
      TrapeziusUpper -> 0.05,
      LevatorScapulae -> 0.05,
      ErectorSpinae -> 0.05,
      Sternocleidomastoid -> 0.05
    )
  )

  case object AssistedTricepsDips extends Exercise(
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

  case object Hyperextensions extends Exercise(
    Map(
      Hamstrings -> 0.70,
      ErectorSpinae -> 0.10,
      GluteusMaximus -> 0.10,
      Adductors -> 0.10
    )
  )

  case object ReverseLunges extends Exercise(
    Map(
      Quadriceps -> 0.70,
      GluteusMaximus -> 0.10,
      Adductors -> 0.10,
      Soleus -> 0.10
    )
  )

  case object CloseGripBenchPress extends Exercise(
    Map(
      TricepsBrachii -> 0.70,
      DeltoidAnterior -> 0.10,
      PectoralisMajor -> 0.20
    )
  )

  case object PushUps extends Exercise(
    Map(
      PectoralisMajor -> 0.80,
      DeltoidAnterior -> 0.10,
      TricepsBrachii -> 0.10
    )
  )

  case object BenchPress extends Exercise(
    Map(
      PectoralisMajor -> 0.80,
      DeltoidAnterior -> 0.10,
      TricepsBrachii -> 0.10
    )
  )

  case object ShoulderPress extends Exercise(
    Map(
      DeltoidAnterior -> 0.76,
      DeltoidLateral -> 0.04,
      Supraspinatus -> 0.04,
      TricepsBrachii -> 0.04,
      TrapeziusMiddle -> 0.04,
      TrapeziusLower -> 0.04,
      SerratusAnterior -> 0.04
    )
  )

  case object ArnoldPress extends Exercise(
    Map(
      DeltoidAnterior -> 0.76,
      DeltoidLateral -> 0.04,
      Supraspinatus -> 0.04,
      TricepsBrachii -> 0.04,
      TrapeziusMiddle -> 0.04,
      TrapeziusLower -> 0.04,
      SerratusAnterior -> 0.04
    )
  )

  case object TricepExtensions extends Exercise(
    Map(TricepsBrachii -> 1.0)
  )

  case object WristCurls extends Exercise(
    Map(WristFlexors -> 1.0)
  )

  case object ReverseWristCurls extends Exercise(
    Map(WristExensors -> 1.0)
  )

  case object Squats extends Exercise(
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

  case object StandingTwist extends Exercise(
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

  case object RussianTwist extends Exercise(
    Map(
      Obliques -> 0.60,
      HipExternalRotators -> 0.10,
      Iliopsoas -> 0.10,
      QuadratusLumborum -> 0.10,
      ErectorSpinae -> 0.10
    )
  )

  case object StandingCalfRaise extends Exercise(
    Map(
      Gastrocnemius -> 0.80,
      Soleus -> 0.20
    )
  )

  case object SeatedCalfPress extends Exercise(
    Map(
      Gastrocnemius -> 0.80,
      Soleus -> 0.20
    )
  )

  case object StandingMiliartyPress extends Exercise(
    Map(
      DeltoidAnterior -> 0.64,
      PectoralisMajor -> 0.06,
      TricepsBrachii -> 0.06,
      DeltoidLateral -> 0.06,
      TrapeziusMiddle -> 0.06,
      TrapeziusLower -> 0.06,
      SerratusAnterior -> 0.06
    )
  )

  case object Pulldown extends Exercise(
    Map(
      LatissimusDorsi -> 0.74,
      Brachialis -> 0.02,
      Brachioradialis -> 0.02,
      BicepsBrachii -> 0.02,
      TeresMajor -> 0.02,
      DeltoidPosterior -> 0.02,
      Infraspinatus -> 0.02,
      TeresMinor -> 0.02,
      Rhomboids -> 0.02,
      LevatorScapulae -> 0.02,
      TrapeziusLower -> 0.02,
      TrapeziusMiddle -> 0.02,
      PectoralisMinor -> 0.02
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
