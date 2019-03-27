package com.fivebyfive.fitness.actor

import akka.actor.{Actor, ActorLogging, Props}
import com.fivebyfive.fitness.actor.WorkoutScorer.ScoringRequest
import com.fivebyfive.fitness.model.{History, Workout}
import com.fivebyfive.fitness.strategy.scoring.Scoring

object WorkoutScorer {
  case class ScoringRequest(scoring: Scoring, history: History, workout: Workout)

  def props = {
    Props(new WorkoutScorer)
  }
}

class WorkoutScorer extends Actor with ActorLogging {
  def receive = {
    case ScoringRequest(scoring, history, workout) =>
      val scoredWorkout = scoring.run(history, workout)
      sender() ! scoredWorkout
  }
}
