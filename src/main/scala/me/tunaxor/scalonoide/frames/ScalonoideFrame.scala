package me.tunaxor.scalonoide.frames
import java.io.File

import javax.swing.filechooser.FileNameExtensionFilter
import me.tunaxor.scalonoide.data.Database
import me.tunaxor.scalonoide.models.{AppOptions, FileData}
import me.tunaxor.scalonoide.utils.MainFrameUtils
import org.mongodb.scala.Completed

import scala.swing._

object ScalonoideFrame {
  private val _dirLabel = new Label("")
  private val _progressCounter = new Label("")
  private val _chooser = new FileChooser { chooser =>
    {
      chooser.fileSelectionMode = FileChooser.SelectionMode.FilesOnly
      chooser.name = "conditions"
      chooser.fileFilter = new FileNameExtensionFilter("CSV File", "csv")
    }
  }
  private val _txtArea = new TextArea(rows = 25, columns = 100) { textarea =>
    {
      textarea.editable = false
    }
  }
  private val _btnSaveDb = new Button("Save to Database") {
    reactions += {
      case event.ButtonClicked(_) => saveToDb(_chooser.selectedFile)
    }
    enabled = false
  }
  private var _mainFrame: MainFrame = _

  /**
    * Pass the file that was picked by the User
   **/
  def saveToDb(file: File) {
    MainFrameUtils.processFileContents(
      file,
      /**
        * This function may be called a bunch of times if the file
        * contains more records than the configured batch size
       **/
      (_: Completed, total: Long) => {
        _progressCounter.text = s"Processed: [$total] Records"
      },
      () => {
        _btnSaveDb.enabled = false
      }
    )
  }

  def startup(options: AppOptions) {
    _mainFrame = createMainFrame(options.title)
    _mainFrame.centerOnScreen()
    _mainFrame.open()
  }

  private def createMainFrame(appTitle: String): MainFrame = {
    val frame = new MainFrame {
      title = appTitle
      contents = frameContent
      reactions += {
        case event.WindowClosing(_) => Database.closeConnection()
      }
    }
    frame.pack()
    frame.size = new Dimension(800, 600)
    frame
  }

  private def frameContent: Panel = {
    new BoxPanel(Orientation.Vertical) { panel =>
      {
        panel.border = Swing.EmptyBorder(24, 24, 24, 24)
      }
      contents += new BoxPanel(Orientation.Horizontal) {
        innerPanel =>
        {
          innerPanel.border = Swing.EmptyBorder(12, 12, 12, 12)
        }
        contents += new Button("Select File") {
          reactions += {
            case event.ButtonClicked(_) => openFile()
          }
        }
        contents += _dirLabel
      }
      contents += new ScrollPane(_txtArea)
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += _progressCounter
        contents += _btnSaveDb
      }
    }
  }

  def openFile(): Unit = {
    _chooser.showOpenDialog(_mainFrame)
    updateContent(
      MainFrameUtils.getFileContents(_chooser.selectedFile)
    )
    _btnSaveDb.enabled = true
  }

  def updateContent(fileData: FileData) {
    _dirLabel.text = fileData.path
    _txtArea.text = fileData.content
  }
}
