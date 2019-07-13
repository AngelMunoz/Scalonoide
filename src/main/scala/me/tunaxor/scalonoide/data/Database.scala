package me.tunaxor.scalonoide.data

import me.tunaxor.scalonoide.models.WeatherCondition
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala._
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._

/**
  * Manage Database operations here
 **/
object Database {
  private val _codecRegistry = fromRegistries(
    /**
      * Add more classes if needed
     **/
    fromProviders(classOf[WeatherCondition]),
    DEFAULT_CODEC_REGISTRY
  )
  private var _client: MongoClient = _
  private var _dbName: String = _

  def createClient(dbstr: String, dbname: String) {
    _client = MongoClient(dbstr)
    _dbName = dbname
  }

  def closeConnection(): Unit = _client.close()

  /**
    * if you feel like needing, you can define
    * different methods to pull collections directly
    * just remember to register the class with the codec registry above
   **/
  def weatherConditionsCol: MongoCollection[WeatherCondition] =
    database(_dbName)
      .getCollection[WeatherCondition]("weather-conditions")

  def database(name: String): MongoDatabase =
    _client
      .getDatabase(name)
      .withCodecRegistry(_codecRegistry)
}
