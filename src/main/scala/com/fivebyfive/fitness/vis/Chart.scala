package com.fivebyfive.fitness.vis

import java.io.File

import com.fivebyfive.fitness.model.History
import org.jfree.chart.{ChartFactory, ChartUtilities, JFreeChart}
import org.jfree.data.time.{Day, TimeSeries, TimeSeriesCollection}

object Chart {
  def toChart(history: History): JFreeChart = {
    val collection = new TimeSeriesCollection

    history.volumeHistoryByExercise.foreach { case (e, data) =>
      val ts = new TimeSeries(e.entryName)
      data.foreach { case (date, value) =>
        ts.add(new Day(date.toDate), value)
      }

      collection.addSeries(ts)
    }

    ChartFactory.createTimeSeriesChart("Workout History", "Date", "Volume", collection)
  }

  def printChartAsJPEG(chart: JFreeChart, name: String, height: Int, width: Int): Unit = {
    val file = new File(name)
    ChartUtilities.saveChartAsJPEG(file, chart, width, height)
  }
}
