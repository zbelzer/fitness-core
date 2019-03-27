package com.fivebyfive.fitness.actor

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import com.fivebyfive.fitness.actor.WorkoutScorer.ScoringRequest
import com.fivebyfive.fitness.model.Exercise.{BicepCurls, LegExtensions, SitUps}
import com.fivebyfive.fitness.model._
import com.fivebyfive.fitness.strategy.scoring.algorithms.{EvenGroupDistribution, RepeatedExercises}
import com.fivebyfive.fitness.strategy.scoring.{Score, Scoring}
import com.fivebyfive.fitness.{StopSystemAfterAll, TestFixtures}
import org.scalatest.FunSpecLike

import scala.collection.immutable.ListMap

class WorkoutScorererSpec extends TestKit(ActorSystem("testsystem"))
  with FunSpecLike
  with ImplicitSender
  with TestFixtures
  with StopSystemAfterAll {

  describe("WorkoutScorer") {

    it("scores a repeated workout with 0") {
      val scoring = Scoring(Seq(
        EvenGroupDistribution,
        RepeatedExercises
      ))

      val scorer = system.actorOf(WorkoutScorer.props(scoring))

      val newWorkout =
        Workout(ListMap(
          BicepCurls -> Map(8 -> 10, 8 -> 10),
          LegExtensions -> Map(8 -> 20, 8 -> 20),
          SitUps -> Map(15 -> None, 15 -> None)
        ), "2016-01-02")

      val history = History(Seq(newWorkout))

      scorer ! ScoringRequest(history, newWorkout)

      val expectedScores = Map(
        EvenGroupDistribution -> 1.0,
        RepeatedExercises -> 0.0
      )

      expectMsg(ScoredWorkout(newWorkout, expectedScores))
    }

    it("scores a repeated workout with 0") {
      val scoring = Scoring(Seq(
        EvenGroupDistribution,
        RepeatedExercises
      ))

      val scorer = system.actorOf(WorkoutScorer.props(scoring))

      val newWorkout =
        Workout(Seq(
          Routine(BicepCurls, Map(8 -> 10, 8 -> 10)),
          Routine(LegExtensions, Map(8 -> 20, 8 -> 20)),
          Routine(SitUps, Seq(15, 15))
        ), "2016-01-02")

      val history = History(Seq(newWorkout))

      scorer ! ScoringRequest(history, newWorkout)

      val expectedScores = Map(
        EvenGroupDistribution -> 1.0,
        RepeatedExercises -> 0.0
      )

      expectMsg(ScoredWorkout(newWorkout, expectedScores))
    }

  }
}
