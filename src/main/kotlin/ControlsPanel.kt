import java.awt.Button
import java.awt.Color
import java.awt.Component
import java.awt.Font
import javax.swing.JPanel
import javax.swing.border.LineBorder

class ControlsPanel {
    val playButton: Button
    val jPanel: Component

    init {
        playButton = initPlayButton()
        jPanel = initJPanel()
    }

    private fun initPlayButton(): Button {
        return Button("Play").apply {
            font = Font("Tahoma", Font.PLAIN, 15)
        }
    }

    private fun initJPanel(): Component {
        val jPanel = JPanel().apply {
            border = LineBorder(Color.ORANGE, 10)
        }.run {
            add(playButton)
        }
        return jPanel
    }
}
