package io.github.yoobi.fullscreendialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource

class MainActivity : AppCompatActivity() {
    private var player: ExoPlayer? = null
    private var playerView: StyledPlayerView? = null
    private var currentPlayer: StyledPlayerView? = null
//    private val videoUrl: Uri = Uri.parse("https://storage.googleapis.com/gvabox/media/samples/stock.mp4")
    private val videoUrl: Uri = Uri.parse("https://www.w3schools.com/html/movie.mp4")
//    private val videoUrl: Uri = Uri.parse("http://file-dev.jlpay.com/group1/M00/5A/32/rBQFm2Q6OjSAI2JsAL2__SbjutU992.mp3?ts=1689229479&token=fc58eecdb68bbd7d256c0f6956585f47")

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        // Our Main Player View.
        playerView = findViewById(R.id.player_view)
        // Keep the current Player View instance.
        currentPlayer = playerView
        // SetUp Full Screen Button
        setFullScreenListener()
        Toast.makeText(this,"dialog_ex",Toast.LENGTH_SHORT).show()
    }

    private fun initPlayer() {
        val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(this)
        val mediaSourceFactory: MediaSource.Factory = DefaultMediaSourceFactory(dataSourceFactory)

        // Create an ExoPlayer and set it as the player for content.
        player = ExoPlayer.Builder(this).setMediaSourceFactory(mediaSourceFactory).build()
        playerView?.player = player

        // Create the MediaItem to play, specifying the content URI and ad tag URI.
        val mediaItem: MediaItem.Builder = MediaItem.Builder().setUri(videoUrl)

        // Prepare the content and ad to be played with the SimpleExoPlayer.
        player!!.setMediaItem(mediaItem.build())
        player!!.prepare()

        // Set Player Properties
        player!!.playWhenReady = true
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun setFullScreenListener() {
        // Creating a new Player View and place it inside a Full Screen Dialog.
//        val fullScreenPlayerView :StyledPlayerView = (LayoutInflater.from(this).inflate(R.layout.full_screen_player_view,null,false) as StyledPlayerView).apply {
//            findViewById<ImageButton>(R.id.exo_fullscreen).setBackgroundResource(R.drawable.exo_ic_fullscreen_exit)
//        }

        val fullScreenPlayerView = StyledPlayerView(this)
        val dialog = object : Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen){
            @Deprecated("Deprecated in Java")
            override fun onBackPressed() {
                // User pressed back button. Exit Full Screen Mode.
                playerView?.findViewById<ImageButton>(com.google.android.exoplayer2.ui.R.id.exo_fullscreen)?.setImageResource(R.drawable.exo_ic_fullscreen_enter)
                player?.let { StyledPlayerView.switchTargetView(it, fullScreenPlayerView, playerView) }
                currentPlayer = playerView
                this@MainActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                super.onBackPressed()
            }

        }
        dialog.addContentView(
            fullScreenPlayerView,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        // Adding Full Screen Button Click Listeners.
        playerView?.setFullscreenButtonClickListener {
            // If full Screen Dialog is not visible, make player full screen.
            if(!dialog.isShowing){
                dialog.show()
                fullScreenPlayerView.findViewById<ImageButton>(com.google.android.exoplayer2.ui.R.id.exo_fullscreen).setImageResource(R.drawable.exo_ic_fullscreen_exit)
                player?.let { StyledPlayerView.switchTargetView(it, playerView, fullScreenPlayerView) }
                currentPlayer = fullScreenPlayerView
                this@MainActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
        }
        fullScreenPlayerView.setFullscreenButtonClickListener {
            // Exit Full Screen.
            playerView?.findViewById<ImageButton>(com.google.android.exoplayer2.ui.R.id.exo_fullscreen)?.setImageResource(R.drawable.exo_ic_fullscreen_enter)
            player?.let { StyledPlayerView.switchTargetView(it, fullScreenPlayerView, playerView) }
            currentPlayer = playerView
            this@MainActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            dialog.dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT > 23) {
            initPlayer()
            currentPlayer?.onResume()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT <= 23) {
            initPlayer()
            currentPlayer?.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT <= 23) {
            currentPlayer?.player = null
            player!!.release()
             player = null
        }
    }

    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT > 23) {
            currentPlayer?.player = null
            player!!.release()
            player = null
        }
    }
}