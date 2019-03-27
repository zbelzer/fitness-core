package com.fivebyfive.fitness.actor

import akka.actor.{Actor, ActorLogging, Props}
import akka.event.Logging
import com.fivebyfive.fitness.application.GenerateActorWorkoutApp.WorkoutMetadata
import com.fivebyfive.fitness.model._
import com.fivebyfive.fitness.strategy.routine.RoutineBuilder
import com.fivebyfive.fitness.strategy.volume.VolumePredictor
import org.joda.time.DateTime

import scala.util.control.Breaks._

case class GenerateSampleWorkout(
  date: DateTime,
  metadata: WorkoutMetadata,
  exerciseList: Seq[Exercise]
)
case object NoWorkout

object SampleWorkoutGenerator {
  def props(
//      history: History,
//      routineBuilder: RoutineBuilder,
//      settings: WorkoutSettings
  ) = {
    Props(new SampleWorkoutGenerator)//history, routineBuilder, settings))
  }
}

class SampleWorkoutGenerator(
//    history: History,
//    routineBuilder: RoutineBuilder,
//    settings: WorkoutSettings
) extends Actor with ActorLogging {
  override def preStart(): Unit = log.info("Workout Generator started")
  override def postStop(): Unit = log.info("Workout Generator stopped")


  def receive = {
    case GenerateSampleWorkout(date, metadata, exerciseList) =>
      log.info("Generating sample workout")

      var currentRoutines = List[Routine]()
      val routineHistory = metadata.history.volumeHistoryByExercise

      breakable {
        exerciseList.foreach { exercise =>
          val latestRoutineForExercise = metadata.history.latestRoutine(exercise).getOrElse(defaultRoutine(exercise))

          val data = routineHistory.getOrElse(exercise, Seq((date, latestRoutineForExercise.volume)))
          val nextVolume = VolumePredictor.next(data, date)

          val nextRoutine = metadata.routineBuilder.buildRoutine(latestRoutineForExercise, nextVolume)
          currentRoutines :+= nextRoutine

          if (currentRoutines.map(_.duration).sum > metadata.settings.targetDuration.toMinutes) {
            sender() ! Workout(currentRoutines, date)
            break
          }
        }
      }

      sender() ! NoWorkout
  }

  def defaultRoutine(exercise: Exercise): Routine = {
    Routine(exercise, Seq(RepSet(8, Some(5)), RepSet(8, Some(5))))
  }
}
