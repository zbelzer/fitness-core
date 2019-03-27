package com.fivebyfive.fitness.application

import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import com.fivebyfive.fitness.actor.{GenerateWorkout, WorkoutGenerator}
import com.fivebyfive.fitness.model.{History, ScoredWorkout, WorkoutSettings}
import com.fivebyfive.fitness.strategy.routine.{HypertrophicBuilder, RoutineBuilder}
import com.fivebyfive.fitness.strategy.scoring.Scoring
import com.fivebyfive.fitness.strategy.scoring.algorithms.{EvenGroupDistribution, IgnoreExercises, RepeatedExercises}
import org.joda.time.DateTime

import scala.concurrent.Await
import scala.concurrent.duration._

object GenerateActorWorkoutApp extends App {
  implicit val timeout = Timeout(5.seconds)

  println("Loading history from CSV")

  val history = History.fromResource("/belzer.csv")

  println(s"Found ${history.workouts.size} workouts")
  println

  println("Starting actory system")
  val system = ActorSystem("fitness")

  val settings = WorkoutSettings.default
  val scoring = Scoring(Seq(
    EvenGroupDistribution,
    RepeatedExercises,
    IgnoreExercises(Nil)
  ))

  val today = DateTime.now
  val seed = 12345L

  case class WorkoutMetadata(
    history: History,
    scoring: Scoring,
    settings: WorkoutSettings,
    routineBuilder: RoutineBuilder
  )

  val generator = system.actorOf(WorkoutGenerator.props, "generator")
  val metadata = WorkoutMetadata(
    history = history,
    scoring = scoring,
    settings = settings,
    routineBuilder = new HypertrophicBuilder
  )

  val message = GenerateWorkout(
    date = today,
    workoutMetadata = metadata,
    seed = seed
  )

  val futureWorkout =
    generator
      .ask(message)
      .mapTo[Some[ScoredWorkout]]

  val workout = Await.result(futureWorkout, 10.seconds)
  println(workout)
}
