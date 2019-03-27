package com.fivebyfive.fitness.model

import com.fivebyfive.fitness.BaseSpec
import com.fivebyfive.fitness.model.Exercise._
import com.fivebyfive.fitness.model.Muscle._
import org.joda.time.DateTime

class WorkoutSpec extends BaseSpec {
  describe("Workout") {
    describe("volumeByMuscle") {
      it("sums volumes by muscle from individual routines") {
        val routine1 = Routine(BicepCurls, Seq(RepSet(8, Some(15)), RepSet(10, Some(17.5))))
        val routine2 = Routine(DragCurls, Seq(RepSet(8, Some(15)), RepSet(10, Some(17.5))))

        val workout = Workout(Seq(routine1, routine2), new DateTime("2016-01-08"))

        val brachiiVol = (8 * 15 + 10 * 17.5) * 0.7 + (8 * 15 + 10 * 17.5 ) * 0.72
        val brachialisVol = (8 * 15 + 10 * 17.5) * 0.15 + (8 * 15 + 10 * 17.5 ) * 0.07
        val brachioradialisVol = (8 * 15 + 10 * 17.5) * 0.15 + (8 * 15 + 10 * 17.5 ) * 0.07
        val deltoidPosteriorVol = (8 * 15 + 10 * 17.5 ) * 0.07
        val deltoidAnteriorVol = (8 * 15 + 10 * 17.5 ) * 0.07

        val volumeByMuscle = workout.volumeByMuscle
        volumeByMuscle shouldBe Map(
          BicepsBrachii -> brachiiVol,
          Brachialis -> brachialisVol,
          Brachioradialis -> brachioradialisVol,
          DeltoidPosterior -> deltoidPosteriorVol,
          DeltoidAnterior -> deltoidAnteriorVol
        )
      }
    }

  }
}
