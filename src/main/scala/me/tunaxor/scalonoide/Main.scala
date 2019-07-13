package me.tunaxor.scalonoide
import me.tunaxor.scalonoide.data.Database
import me.tunaxor.scalonoide.frames.ScalonoideFrame
import me.tunaxor.scalonoide.utils.ArgsParse

import scala.swing.SwingApplication

object Main extends SwingApplication {
  private var _env = "production"

  /**
    * Connect to the database at the startup then open the main window
   **/
  def startup(args: Array[String]): Unit = {
    val options = ArgsParse.getOptions(args)
    _env = options.env
    Database.createClient(options.dbOptions.url, options.dbOptions.dbname)
    ScalonoideFrame.startup(options)
  }
}
