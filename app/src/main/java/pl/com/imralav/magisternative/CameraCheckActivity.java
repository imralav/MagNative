package pl.com.imralav.magisternative;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CameraCheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_check);
    }

    public void goToDataCheck(View view) {
        Intent intent = new Intent(this, DataAccessCheckActivity.class);
        startActivity(intent);
    }
}
