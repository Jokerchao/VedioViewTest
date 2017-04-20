package com.example.vedioviewtest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView= (VideoView) findViewById(R.id.VideoView);
        Button btn_playVideo= (Button) findViewById(R.id.play_video);
        Button btn_pauseVideo= (Button) findViewById(R.id.pause_video);
        Button btn_replayVideo= (Button) findViewById(R.id.replay_video);
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else{
            initVideoView();
        }

        btn_playVideo.setOnClickListener(this);
        btn_pauseVideo.setOnClickListener(this);
        btn_replayVideo.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(videoView!=null){
            videoView.suspend();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    initVideoView();
                }else{
                    Toast.makeText(this,"拒绝授予权限导致程序无法运行",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }
    public void initVideoView(){
        File file=new File(Environment.getExternalStorageDirectory(),"video.wmv");
        videoView.setVideoPath(file.getPath());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.play_video:
                if(!videoView.isPlaying()){
                    videoView.start();
                }
                break;
            case R.id.pause_video:
                if(videoView.isPlaying()){
                    videoView.pause();
                }
                break;
            case R.id.replay_video:
                if(videoView.isPlaying()){
                    videoView.resume();
                }
                break;
            default:
                break;
        }

    }
}
