import javafx.application.Platform
import javafx.beans.binding.Bindings
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView
import javafx.scene.paint.Color
import java.awt.*
import java.awt.image.BufferedImage
import java.io.File
import javax.swing.JFrame

const val SIZE = 700.0

class VideoPlayerWindow {
    private val jFrame: JFrame
    private val videoPanel = VideoPanel()
    private val controlsPanel = ControlsPanel()

    init {
        jFrame = initJFrame()
        initControls()
    }

    fun display() {
        jFrame.isVisible = true
    }

    private fun initJFrame(): JFrame {
        val jFrame = JFrame().apply {
            bounds = Rectangle(-5, 0, SIZE.toInt(), SIZE.toInt())
            iconImage = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE)
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            layout = GridBagLayout()
        }
        jFrame.contentPane.run {
            val constraints = GridBagConstraints()
            add(videoPanel.jfxPanel, constraints.apply {
                fill = GridBagConstraints.BOTH
                weightx = 1.0
                weighty = 1.0
                gridy = 0
            })
            add(controlsPanel.jPanel, constraints.apply {
                weightx = 0.0
                weighty = 0.0
                gridy = 1
            })
        }
        return jFrame
    }

    private fun initControls() {
        controlsPanel.playButton.addActionListener {
            Platform.runLater { playVideo() }
        }
    }

    private fun playVideo() {
        val videoFile = File("bread.mp4")
        val media = Media(videoFile.toURI().toString())
        val mediaPlayer = MediaPlayer(media)
        val mediaView = MediaView(mediaPlayer).apply {
            fitWidthProperty().bind(Bindings.selectDouble(sceneProperty(), "width"))
            fitHeightProperty().bind(Bindings.selectDouble(sceneProperty(), "height"))
        }

        val stackPane = StackPane(mediaView)
        val scene = Scene(stackPane, SIZE, SIZE, Color.BLACK)
        videoPanel.jfxPanel.scene = scene

        mediaPlayer.setOnReady {
            mediaPlayer.play()
        }
        mediaPlayer.setOnError {
            println("Error")
        }
    }
}