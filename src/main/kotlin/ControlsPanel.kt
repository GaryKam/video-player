import java.awt.*
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JPanel

class ControlsPanel {
    val jPanel: Component
    val playButton: JButton
    val nextButton: JButton
    val mediaButton: JButton
    val scenesButton: JComboBox<String>

    init {
        playButton = initPlayButton()
        nextButton = initNextButton()
        mediaButton = initMediaButton()
        scenesButton = initScenesButton()
        jPanel = initJPanel()
    }

    fun updatePlayButton(playing: Boolean) {
        val image = ImageIO.read(this@ControlsPanel.javaClass.getResource(
            if (playing) "pause_icon.png" else "play_icon.png"))
        val scaledImage = image.getScaledInstance(15, 15, Image.SCALE_SMOOTH)
        playButton.icon = ImageIcon(scaledImage)
    }

    private fun initPlayButton(): JButton {
        return initImageButton("play_icon.png")
    }

    private fun initNextButton(): JButton {
        return initImageButton("next_icon.png")
    }

    private fun initMediaButton(): JButton {
        return initImageButton("media_icon.png")
    }

    private fun initScenesButton(): JComboBox<String> {
        return JComboBox(arrayOf("1 Scene", "2 Scenes", "3 Scenes", "4 Scenes")).apply {
            font = Font("Tahoma", Font.PLAIN, 15)
        }
    }

    private fun initJPanel(): Component {
        val jPanel = JPanel().apply {
            background = Color.BLACK
            layout = GridBagLayout()
        }
        jPanel.run {
            val constraints = GridBagConstraints()
            add(playButton, constraints)
            add(nextButton, constraints.apply {
                gridx = 1
                insets = Insets(0, 5, 0, 200)
            })
            add(mediaButton, constraints.apply {
                weightx = 0.0
                gridx = 2
                insets = Insets(0, 0, 0, 5)
            })
            add(scenesButton, constraints.apply {
                fill = GridBagConstraints.VERTICAL
                gridx = 3
            })
        }
        return jPanel
    }

    private fun initImageButton(name: String): JButton {
        val button = JButton().apply {
            isOpaque = false
            isContentAreaFilled = false
            isBorderPainted = false
            isFocusPainted = false
        }
        button.run {
            val image = ImageIO.read(this@ControlsPanel.javaClass.getResource(name))
            val scaledImage = image.getScaledInstance(15, 15, Image.SCALE_SMOOTH)
            icon = ImageIcon(scaledImage)
        }
        return button
    }
}
