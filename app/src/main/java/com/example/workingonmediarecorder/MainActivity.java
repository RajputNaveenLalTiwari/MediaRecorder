package com.example.workingonmediarecorder;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = "MainActivity";
    private MediaPlayer mediaPlayer = null;
    private MediaRecorder mediaRecorder = null;
    private static String file_name = null;

    private Button record_button;
    private Button play_button;

    private boolean isRecording = true;
    private boolean isPlaying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        record_button = (Button) findViewById(R.id.record_button);
        play_button = (Button) findViewById(R.id.play_button);

        record_button.setOnClickListener(this);
        play_button.setOnClickListener(this);

        file_name = getExternalCacheDir().getAbsolutePath();
        file_name += "/record_test.3gp";
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.record_button:
                onRecordButton(isRecording);
                if(isRecording)
                {
                    record_button.setText("Stop Recording");
                }
                else
                {
                    record_button.setText("Start Recording");
                }
                isRecording = !isRecording;
                break;
            case R.id.play_button:
                onPlayButton(isPlaying);
                if(isPlaying)
                {
                    play_button.setText("Stop Playing");
                }
                else
                {
                    play_button.setText("Start Playing");
                }
                isPlaying = !isPlaying;
                break;
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        if(mediaRecorder != null)
        {
            mediaRecorder.release();
            mediaRecorder = null;
        }

        if(mediaPlayer != null)
        {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void onRecordButton(boolean start)
    {
        if(start)
        {
            startRecording();
        }
        else
        {
            stopRecording();
        }
    }

    private void onPlayButton(boolean start)
    {
        if(start)
        {
            startPlaying();
        }
        else
        {
            stopPlaying();
        }
    }

    private void startRecording()
    {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(file_name);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try
        {
            mediaRecorder.prepare();
        }
        catch (IOException e)
        {
            Log.e(TAG,"Error while preparing Media Recorder "+e.getMessage());
        }

        mediaRecorder.start();
    }

    private void stopRecording()
    {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    private void startPlaying()
    {
        mediaPlayer = new MediaPlayer();
        try
        {
            mediaPlayer.setDataSource(file_name);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        catch (IOException e)
        {
            Log.e(TAG,"Error while preparing Media Player "+e.getMessage());
        }
    }

    private void stopPlaying()
    {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}
