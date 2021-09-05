import javafx.application.Platform
import java.awt.*
import java.awt.image.BufferedImage
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
                if (videoPanel.playing) {
                    videoPanel.pauseVideo()
                    controlsPanel.playButton.label = "Play"
                } else {
                    videoPanel.playVideo()
                    controlsPanel.playButton.label = "Pause"
                }
            }
        }
    }
}
