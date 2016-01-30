package com.fivebyfive.fitness.model

abstract class Progression(history: Seq[Routine], goal: Routine) {
}

class LinearProgression(history: Seq[Routine], goal: Routine) extends Progression(history, goal) {
  def next = {
  }

  // (10, 4,
//  def stream: Iterator[Workout] = {
//
//  }
}
