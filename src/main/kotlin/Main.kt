import javafx.embed.swing.JFXPanel
import javax.swing.SwingUtilities
import javax.swing.UIManager

fun main() {
    SwingUtilities.invokeLater {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel")
        JFXPanel()
        VideoPlayerWindow().display()
    }
}
