import javafx.application.Platform
import javafx.stage.DirectoryChooser
import javafx.stage.Stage
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Rectangle
import java.awt.image.BufferedImage
import javax.swing.JFrame

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
            bounds = Rectangle(-5, 0, SIZE, SIZE)
            iconImage = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE)
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            layout = GridBagLayout()
        }
        jFrame.contentPane.run {
            val constraints = GridBagConstraints()
            add(videoPanel.jPanel, constraints.apply {
                fill = GridBagConstraints.BOTH
                weightx = 1.0
                weighty = 1.0
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
            Platform.runLater {
                if (videoPanel.playing.value) {
                    videoPanel.pauseVideos()
                } else {
                    videoPanel.playVideos()
                }
            }
        }
        videoPanel.playing.addListener { _, _, newValue ->
            controlsPanel.updatePlayButton(newValue)
        }
        controlsPanel.nextButton.addActionListener {
            Platform.runLater {
                videoPanel.setVideoCount(videoPanel.videoCount)
            }
        }
        controlsPanel.mediaButton.addActionListener {
            Platform.runLater {
                videoPanel.setSource(DirectoryChooser().apply {
                    title = "Select source directory"
                }.showDialog(Stage()))
            }
        }
        controlsPanel.scenesButton.addActionListener {
            Platform.runLater {
                videoPanel.setVideoCount(controlsPanel.scenesButton.selectedIndex + 1)
            }
        }
    }

    companion object {
        const val SIZE = 700
    }
}
