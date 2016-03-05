package com.fivebyfive.fitness.model

import org.joda.time.DateTime

case class Goal(workout: Workout) {
  def date: DateTime = workout.date
}
