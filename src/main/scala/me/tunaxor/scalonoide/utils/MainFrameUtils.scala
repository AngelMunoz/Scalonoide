package me.tunaxor.scalonoide.utils
import java.io.File
import java.util.Scanner

import me.tunaxor.scalonoide.data.Database
import me.tunaxor.scalonoide.models.{FileData, WeatherCondition}
import org.mongodb.scala.Completed

import scala.collection.mutable.ArrayBuffer

object MainFrameUtils {

  // Get the file contents to present inside the text area of the main frame
  def getFileContents(file: File): FileData = {
    val scanner = new Scanner(file)
    var content = ""
    while (scanner.hasNextLine) {
      content += s"${scanner.nextLine()}\n"
    }
    scanner.close()
    FileData(file.getAbsolutePath, file.getName, content)
  }

  def processFileContents(
      file: File,
      doNext: (Completed, Long) => Any,
      onDone: () => Unit,
      batchSize: Int = 200
  ) {
    var current = 0
    var total = 0L
    val scanner = new Scanner(file)
    val conditions = new ArrayBuffer[WeatherCondition]()
    // Skip the First line because those are the headers
    if (scanner.hasNextLine) {
      scanner.nextLine
    }
    // Keep reading until there are no more lines left
    while (scanner.hasNextLine) {
      conditions += processLine(scanner.nextLine().trim().split(','))
      current += 1
      total += 1L
      // if we're hitting the batch size, insert those and clean up
      // for a new batch of insertions
      if (current >= batchSize) {
        Database.weatherConditionsCol
          .insertMany(conditions)
          .subscribe((completed: Completed) => doNext(completed, total))
        conditions.clear()
        current = 0
      }
    }
    // if we're under the batch size but we're done reading
    // insert what's left on the buffered array
    if (conditions.nonEmpty) {
      Database.weatherConditionsCol
        .insertMany(conditions)
        .subscribe((completed: Completed) => doNext(completed, total))
    }
    // we're done, close the scanner
    scanner.close()
    // call the done callback
    onDone()
  }

  // pull the comma separated values and create a WeatherConditon Record
  def processLine(row: Array[String]): WeatherCondition = {
    val temp: Float = row(0).toFloat
    val unitTemp: String = row(1)
    val windSpeed: Float = row(2).toFloat
    val windSpeedUnit: String = row(3)
    WeatherCondition(temp, unitTemp, windSpeed, windSpeedUnit)
  }
}
