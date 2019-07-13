package me.tunaxor.scalonoide.utils

import me.tunaxor.scalonoide.models.{AppOptions, DbOptions}

object ArgsParse {

  def getOptions(args: Array[String]): AppOptions = {

    /**
      * Find a "separator" definition if there's any
     **/
    val separator = args.find(arg => arg.contains("separator")) match {
      case Some(value) => value.trim().charAt(9)
      case None        => '='
    }

    /**
      * get the final option parser, with the arguments and the separator
     **/
    val findWithOptions = findOption(args, separator)
    val title = findWithOptions("title", "Scalonoide App")
    val env = findWithOptions("env", "production")
    val url = findWithOptions("db_url", "mongodb://localhost")
    val dbname = findWithOptions("db_name", "scalonoidedb")
    // return the App Options
    AppOptions(env, title, DbOptions(url, dbname))
  }

  def findOption(
      args: Array[String],
      separator: Char = '='
  ): (String, String) => String =
    (name: String, defaultValue: String) => {
      args.find(arg => arg.contains(name)) match {
        case Some(value) => parseIndividual(value, separator)
        case None        => defaultValue
      }
    }

  /**
    * the String should come in the following Form
    * key<SEPARATOR>value
    *
    * @example title=my-title
    * Spaces are not taken into account
   **/
  def parseIndividual(strValue: String, separator: Char): String = {
    strValue.split(separator).last.trim()
  }
}
