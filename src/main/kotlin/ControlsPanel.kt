import java.awt.*
import javax.swing.JPanel

class ControlsPanel {
    val jPanel: Component
    val playButton: Button
    val mediaButton: Button

    init {
        playButton = initPlayButton()
        mediaButton = initMediaButton()
        jPanel = initJPanel()
    }

    private fun initPlayButton(): Button {
        return Button("Play").apply {
            font = Font("Tahoma", Font.PLAIN, 15)
        }
    }

    private fun initMediaButton(): Button {
        return Button("Media").apply {
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
            add(playButton, constraints.apply {
                weightx = 1.0
            })
            add(mediaButton, constraints.apply {
                weightx = 0.0
                gridx = 1
            })
        }
        return jPanel
    }
}
