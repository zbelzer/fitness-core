package com.fivebyfive.fitness.actor

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.routing.{BalancingPool, RoundRobinPool}
import com.fivebyfive.fitness.actor.WorkoutScorer.ScoringRequest
import com.fivebyfive.fitness.application.GenerateActorWorkoutApp.WorkoutMetadata
import com.fivebyfive.fitness.model._
import com.fivebyfive.fitness.strategy.routine.RoutineBuilder
import com.fivebyfive.fitness.strategy.scoring.Scoring
import org.joda.time.DateTime

case class GenerateWorkout(
  date: DateTime,
  workoutMetadata: WorkoutMetadata,
  seed: Long
)

object WorkoutGenerator {
  def props = {
    Props(new WorkoutGenerator())
  }
}

class WorkoutGenerator extends Actor with ActorLogging {
  override def preStart(): Unit = log.info("Workout Application started")
  override def postStop(): Unit = log.info("Workout Application stopped")

  val workouts = scala.collection.mutable.ArrayBuffer.empty[ScoredWorkout]
  var outstandingCount = 0

  val pool = context.actorOf(
    BalancingPool(5).props(SampleWorkoutGenerator.props()), "sample-pool"
  )


  val scorerProps = WorkoutScorer.props(self)
  val scorer = context.actorOf(scorerProps, "scorer")

  def receive = {
    case GenerateWorkout(date, metadata, seed) =>
      log.info("Received generate request")

      val exercises = Exercise.randomPermutations(metadata.settings.iterations, Some(seed))

      exercises
        .foreach { exercises =>
          outstandingCount += 1
//          log.info(s"Generating workout ${outstandingCount}")
          pool ! GenerateSampleWorkout(date, exercises)
        }

    case workout: Workout =>
//      log.info(s"Received unscored workout")
      scorer ! ScoringRequest(metadata.scoring, metadata.history, workout)

    case NoWorkout =>
//      log.info(s"Received no workout")
      checkForCompletion

    case workout: ScoredWorkout =>
//      log.info(s"Received scored workout")

      workouts.append(workout)
      checkForCompletion
  }

  def checkForCompletion {
    outstandingCount -= 1

    if (outstandingCount == 0) {
      log.info("Received all messages")

      val bestWorkout =
        workouts
          .sortBy(_.totalScore)(Ordering[Double].reverse)
          .headOption

      foo ! bestWorkout
    }
  }
}
