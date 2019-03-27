package com.fivebyfive.fitness.integration

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.testkit.{ImplicitSender, TestKit}
import com.fivebyfive.fitness.actor.{GenerateWorkout, WorkoutGenerator}
import com.fivebyfive.fitness.model.{History, ScoredWorkout, Workout, WorkoutSettings}
import com.fivebyfive.fitness.strategy.routine.HypertrophicBuilder
import com.fivebyfive.fitness.strategy.scoring.Scoring
import com.fivebyfive.fitness.strategy.scoring.algorithms.{EvenGroupDistribution, IgnoreExercises, RepeatedExercises}
import com.fivebyfive.fitness.{StopSystemAfterAll, TestFixtures}
import org.joda.time.DateTime
import org.scalatest.FunSpecLike

class WorkoutScorererSpec extends TestKit(ActorSystem("testsystem"))
  with FunSpecLike
  with ImplicitSender
  with TestFixtures
  with StopSystemAfterAll {

  describe("WorkoutGeneration") {
    val settings = WorkoutSettings.default
    val scoring = Scoring(Seq(
      EvenGroupDistribution,
      RepeatedExercises,
      IgnoreExercises(Nil)
    ))

    val today = DateTime.now
    val seed = 12345L
    val history = History.fromResource("/scenario1.csv")

    val props = WorkoutGenerator.props(
      history = history,
      scoring = scoring,
      settings = settings,
      routineBuilder = new HypertrophicBuilder
    )

    val experctedWorkout = Workout(
      routines = Seq(

      )
    )

    val generator = system.actorOf(props, "generator")

    generator ! GenerateWorkout(date = today, seed = seed)
    expectMsg(ScoredWorkout)

  }
}
