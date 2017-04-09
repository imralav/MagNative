package pl.com.imralav.magisternative;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;

import pl.com.imralav.magisternative.camera.CamPreview;

public class CameraCheckActivity extends AppCompatActivity {
    private CamPreview frontCamPreview;
    private CamPreview backCamPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_check);
        preparePreviews();
    }

    private void preparePreviews() {
        SurfaceView frontPreview = (SurfaceView) findViewById(R.id.frontCamView);
        frontCamPreview = CamPreview
                .frontCamPreviewOn(frontPreview)
                .withContext(this);
        SurfaceView backPreview = (SurfaceView) findViewById(R.id.backCamView);
        backCamPreview = CamPreview
                .backCamPreviewOn(backPreview)
                .withContext(this);
    }

    public void goToDataCheck(View view) {
        Intent intent = new Intent(this, DataAccessCheckActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        frontCamPreview.open();
        backCamPreview.open();
        startPreview();
    }

    @Override
    public void onPause() {
        frontCamPreview.stop();
        backCamPreview.stop();
        super.onPause();
    }

    private void startPreview() {
        frontCamPreview.start();
        backCamPreview.start();
    }
}
