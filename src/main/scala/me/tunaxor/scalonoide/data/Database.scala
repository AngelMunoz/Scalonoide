package me.tunaxor.scalonoide.data

import org.mongodb.scala._
import me.tunaxor.scalonoide.models.WeatherCondition
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{
  fromRegistries,
  fromProviders
}

/**
  * Manage Database operations here
 **/
object Database {
  private var _client: MongoClient = null;
  private var _dbName: String = null;
  private var _database: MongoDatabase = null;
  private val _codecRegistry = fromRegistries(
    /**
      * Add more classes if needed
     **/
    fromProviders(classOf[WeatherCondition]),
    DEFAULT_CODEC_REGISTRY
  )

  def createClient(dbstr: String, dbname: String) {
    _client = MongoClient(dbstr)
    _dbName = dbname
  }

  def closeConnection = _client.close()

  def database(name: String) =
    _client
      .getDatabase(name)
      .withCodecRegistry(_codecRegistry)

  /**
    * if you feel like needing, you can define
    * different methods to pull collections directly
    * just remember to register the class with the codec registry above
   **/
  def weatherConditionsCol =
    database(_dbName)
      .getCollection[WeatherCondition]("weather-conditions")
}
