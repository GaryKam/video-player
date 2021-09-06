import java.awt.*
import javax.swing.JComboBox
import javax.swing.JPanel

class ControlsPanel {
    val jPanel: Component
    val playButton: Button
    val nextButton: Button
    val mediaButton: Button
    val scenesButton: JComboBox<String>

    init {
        playButton = initPlayButton()
        nextButton = initNextButton()
        mediaButton = initMediaButton()
        scenesButton = initScenesButton()
        jPanel = initJPanel()
    }

    private fun initPlayButton(): Button {
        return Button("Play").apply {
            font = Font("Tahoma", Font.PLAIN, 15)
        }
    }

    private fun initNextButton(): Button {
        return Button("Next").apply {
            font = Font("Tahoma", Font.PLAIN, 15)
        }
    }

    private fun initMediaButton(): Button {
        return Button("Media").apply {
            font = Font("Tahoma", Font.PLAIN, 15)
        }
    }

    private fun initScenesButton(): JComboBox<String> {
        return JComboBox(arrayOf("1 Scene", "2 Scenes", "3 Scenes", "4 Scenes"))
    }

    private fun initJPanel(): Component {
        val jPanel = JPanel().apply {
            background = Color.BLACK
            layout = GridBagLayout()
        }
        jPanel.run {
            val constraints = GridBagConstraints()
            add(playButton, constraints.apply {

            })
            add(nextButton, constraints.apply {
                weightx = 0.0
                gridx = 1
                insets = Insets(0, 0, 0, 200)
            })
            add(mediaButton, constraints.apply {
                weightx = 0.0
                gridx = 2
                insets = Insets(0, 0, 0, 0)
            })
            add(scenesButton, constraints.apply {
                gridx = 3
            })
        }
        return jPanel
    }
}
