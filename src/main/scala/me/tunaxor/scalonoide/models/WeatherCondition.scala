package me.tunaxor.scalonoide.models
import org.mongodb.scala.bson.ObjectId

object WeatherCondition {
  def apply(
      temp: Float,
      tempUnit: String,
      windSpeed: Float,
      windUnit: String
  ): WeatherCondition =
    WeatherCondition(new ObjectId(), temp, tempUnit, windSpeed, windUnit)
}
case class WeatherCondition(
    _id: ObjectId,
    temp: Float,
    tempUnit: String,
    windSpeed: Float,
    windUnit: String
)
