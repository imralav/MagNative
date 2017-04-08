package pl.com.imralav.magisternative;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import pl.com.imralav.magisternative.utils.DrawablesHelper;

public class DataAccessCheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_access_check);
    }

    public void checkPhotos(View view) {
        TextView statusText = (TextView) findViewById(R.id.checkPhotosStatusText);
        statusText.setText(R.string.unavailable);
    }

    public void checkCalendar(View view) {
        TextView statusText = (TextView) findViewById(R.id.checkCalendarStatusText);
        statusText.setText(R.string.sent);
        int iconId = DrawablesHelper.getDataAccessibleIconId(getResources(), getPackageName());
        statusText.setCompoundDrawablesRelativeWithIntrinsicBounds(iconId, 0, 0, 0);
    }

    public void goToCameraCheck(View view) {
        Intent intent = new Intent(this, CameraCheckActivity.class);
        startActivity(intent);
    }
}
