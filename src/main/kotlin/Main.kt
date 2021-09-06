import javafx.embed.swing.JFXPanel
import javax.swing.SwingUtilities

fun main() {
    SwingUtilities.invokeLater {
        JFXPanel()
        VideoPlayerWindow().display()
    }
}
