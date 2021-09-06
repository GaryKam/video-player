import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleBooleanProperty
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView
import java.awt.Color
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.io.File
import java.io.FilenameFilter
import javax.swing.JOptionPane
import javax.swing.JPanel

class VideoPanel {
    val jPanel: JPanel
    var playing = SimpleBooleanProperty(false)
    var videoCount = 1
        private set
    private val jfxPanels = mutableListOf<JFXPanel>()
    private val media = mutableListOf<File>()
    private val mediaPlayers = mutableListOf<MediaPlayer>()


    init {
        jPanel = initJPanel()
    }

    fun playVideos() {
        if (media.isEmpty()) {
            playing.value = false
            JOptionPane.showMessageDialog(null, "No media found!")
            return
        }
        playing.value = true
        if (mediaPlayers.isNotEmpty()) {
            mediaPlayers.forEach { mediaPlayer -> mediaPlayer.play() }
            return
        }
        initJfxPanels()
        mediaPlayers.forEach { mediaPlayer ->
            mediaPlayer.setOnReady { mediaPlayer.play() }
        }
    }

    fun pauseVideos() {
        playing.value = false
        mediaPlayers.forEach { mediaPlayer -> mediaPlayer.pause() }
    }

    fun setSource(directory: File?) {
        if (directory != null) {
            val videos = directory.listFiles(FilenameFilter { _, name -> name.endsWith(".mp4") })!!
            if (videos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No media found!")
            } else {
                stopVideos()
                media.clear()
                media.addAll(videos)
            }
        }
    }

    fun setVideoCount(count: Int) {
        videoCount = count
        if (media.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No media found!")
        } else {
            stopVideos()
            initJfxPanels()
        }
    }

    private fun initJPanel(): JPanel {
        return JPanel().apply {
            layout = GridBagLayout().apply {
                background = Color.BLACK
            }
        }
    }

    private fun initJfxPanels() {
        repeat(videoCount) { initVideo() }
        jPanel.run {
            removeAll()
            val constraints = GridBagConstraints()
            var x = 0
            var y = 0
            for (i in 0 until videoCount) {
                add(jfxPanels[i], constraints.apply {
                    fill = GridBagConstraints.BOTH
                    weightx = 1.0
                    weighty = 1.0
                    gridx = x
                    gridy = y++
                })
                if (y == 2) {
                    y = 0
                    x++
                }
            }
            revalidate()
            repaint()
        }
    }

    private fun initVideo() {
        val mediaFile = media.random()
        val media = Media(mediaFile.toURI().toString())
        val mediaPlayer = MediaPlayer(media)
        val mediaView = MediaView(mediaPlayer).apply {
            fitWidthProperty().bind(Bindings.selectDouble(sceneProperty(), "width"))
            fitHeightProperty().bind(Bindings.selectDouble(sceneProperty(), "height"))
        }
        JFXPanel().apply {
            scene = Scene(StackPane(mediaView), javafx.scene.paint.Color.BLACK)
        }.also {
            jfxPanels.add(it)
        }
        mediaPlayer.apply {
            setOnError { println("Media Player Error") }
            setOnEndOfMedia {
                mediaPlayers.remove(mediaPlayer)
                if (mediaPlayers.isEmpty()) {
                    playing.value = false
                }
            }
        }.also {
            mediaPlayers.add(it)
        }
    }

    private fun stopVideos() {
        playing.value = false
        mediaPlayers.forEach { mediaPlayer -> mediaPlayer.stop() }
        mediaPlayers.clear()
        jfxPanels.clear()
    }
}
