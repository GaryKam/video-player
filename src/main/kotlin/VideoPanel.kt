import javafx.embed.swing.JFXPanel
import java.awt.Color
import javax.swing.border.LineBorder

class VideoPanel {
    val jfxPanel: JFXPanel

    init {
        jfxPanel = initJfxPanel()
    }

    private fun initJfxPanel(): JFXPanel {
        val jfxPanel = JFXPanel().apply {
            border = LineBorder(Color.RED, 10)
        }
        return jfxPanel
    }
}