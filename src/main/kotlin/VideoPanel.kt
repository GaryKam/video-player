import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleBooleanProperty
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView
import java.awt.Color
import java.awt.Component
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.io.File
import java.io.FilenameFilter
import javax.swing.JPanel

class VideoPanel {
    val jPanel: Component
    var playing = SimpleBooleanProperty(false)
    private val jfxPanel: JFXPanel = JFXPanel()
    private val jfxPanel2: JFXPanel = JFXPanel()
    private val media = mutableListOf<File>()
    private val mediaPlayers = mutableListOf<MediaPlayer>()

    init {
        jPanel = initJPanel()
    }

    fun playVideo() {
        if (media.isEmpty()) {
            playing.value = false
            return
        }
        playing.value = true
        if (mediaPlayers.isNotEmpty()) {
            mediaPlayers.forEach { mediaPlayer -> mediaPlayer.play() }
            return
        }
        val videoFile = media.random()
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
                playing.value = false
            }
        }
        mediaPlayer2.setOnEndOfMedia {
            mediaPlayers.remove(mediaPlayer2)
            if (mediaPlayers.isEmpty()) {
                playing.value = false
            }
        }
        mediaPlayers.addAll(listOf(mediaPlayer, mediaPlayer2))
    }

    fun pauseVideo() {
        playing.value = false
        mediaPlayers.forEach { mediaPlayer -> mediaPlayer.pause() }
    }

    fun setMedia(directory: File?) {
        if (directory != null) {
            pauseVideo()
            mediaPlayers.clear()
            media.clear()
            media.addAll(directory.listFiles(FilenameFilter { _, name -> name.endsWith(".mp4") })!!)
        }
    }

    private fun initJPanel(): Component {
        val jPanel = JPanel().apply {
            layout = GridBagLayout().apply {
                background = Color.BLACK
            }
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
