package com.example.personallifelogger.utils

import android.content.Context
import android.media.MediaRecorder
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AudioRecorder(private val context: Context) {
    private var mediaRecorder: MediaRecorder? = null
    private var audioFilePath: String? = null

    fun startRecording(): Boolean {
        return try {
            // Create a directory for audio files
            val audioDir = File(context.getExternalFilesDir(null), "audio_recordings")
            if (!audioDir.exists()) {
                audioDir.mkdirs()
            }

            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.getDefault()).format(Date())
            val audioFileName = "AUDIO_$timeStamp.mp4"
            val audioFile = File(audioDir, audioFileName)
            audioFilePath = audioFile.absolutePath

            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(audioFilePath)

                prepare()
                start()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun stopRecording(): String? {
        return try {
            mediaRecorder?.apply {
                stop()
                release()
            }
            mediaRecorder = null

            // Return the file path if it exists
            val file = audioFilePath?.let { File(it) }
            if (file != null && file.exists() && file.length() > 0) {
                audioFilePath
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}