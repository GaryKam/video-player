import javafx.beans.binding.Bindings
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView
import java.awt.*
import java.io.File
import javax.swing.JPanel
import javax.swing.border.LineBorder

class VideoPanel {
    val jPanel: Component
    private val jfxPanel: JFXPanel
    private val jfxPanel2: JFXPanel
    private val mediaPlayers = mutableListOf<MediaPlayer>()
    var playing = false
        private set

    init {
        jfxPanel = initJfxPanel()
        jfxPanel2 = initJfxPanel()
        jPanel = initJPanel()
    }

    fun playVideo() {
        playing = true
        if (mediaPlayers.isNotEmpty()) {
            mediaPlayers.forEach { mediaPlayer -> mediaPlayer.play() }
            return
        }
        val videoFile = File("bread.mp4")
        val media = Media(videoFile.toURI().toString())
        val mediaPlayer = MediaPlayer(media)
        val mediaView = MediaView(mediaPlayer).apply {
            fitWidthProperty().bind(Bindings.selectDouble(sceneProperty(), "width"))
            fitHeightProperty().bind(Bindings.selectDouble(sceneProperty(), "height"))
        }
        val media2 = Media(videoFile.toURI().toString())
        val mediaPlayer2 = MediaPlayer(media2)
        val mediaView2 = MediaView(mediaPlayer2).apply {
            fitWidthProperty().bind(Bindings.selectDouble(sceneProperty(), "width"))
            fitHeightProperty().bind(Bindings.selectDouble(sceneProperty(), "height"))
        }
        jfxPanel.scene = Scene(StackPane(mediaView), javafx.scene.paint.Color.BLACK)
        jfxPanel2.scene = Scene(StackPane(mediaView2), javafx.scene.paint.Color.BLACK)
        mediaPlayer.setOnReady { mediaPlayer.play() }
        mediaPlayer2.setOnReady { mediaPlayer2.play() }
        mediaPlayer.setOnError { println("Error") }
        mediaPlayer2.setOnError { println("Error2") }
        mediaPlayer.setOnEndOfMedia {
            mediaPlayers.remove(mediaPlayer)
            if (mediaPlayers.isEmpty()) {
                playing = false
            }
        }
        mediaPlayer2.setOnEndOfMedia {
            mediaPlayers.remove(mediaPlayer2)
            if (mediaPlayers.isEmpty()) {
                playing = false
            }
        }
        mediaPlayers.addAll(listOf(mediaPlayer, mediaPlayer2))
    }

    fun pauseVideo() {
        playing = false
        mediaPlayers.forEach { mediaPlayer -> mediaPlayer.pause() }
    }

    private fun initJfxPanel(): JFXPanel {
        val jfxPanel = JFXPanel().apply {
            border = LineBorder(Color.RED, 10)
        }
        return jfxPanel
    }

    private fun initJPanel(): Component {
        val jPanel = JPanel().apply {
            layout = GridBagLayout()
        }
        jPanel.run {
            val constraints = GridBagConstraints()
            add(jfxPanel, constraints.apply {
                fill = GridBagConstraints.BOTH
                weightx = 1.0
                weighty = 1.0
            })
            add(jfxPanel2, constraints.apply {
                gridy = 1
            })
        }
        return jPanel
    }
}
