package pl.com.imralav.magisternative;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import pl.com.imralav.magisternative.utils.DrawablesHelper;

public class DataAccessCheckActivity extends AppCompatActivity {

    private static final int PHOTO_GALLERY_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_access_check);
    }

    public void checkPhotos(View view) {
        TextView statusText = (TextView) findViewById(R.id.checkPhotosStatusText);
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PHOTO_GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PHOTO_GALLERY_REQUEST_CODE) {
            if(resultCode != RESULT_OK) {
                setPhotosStatusToInaccessible();
            } else {
                setPhotosStatusToAccessible();
                Toast.makeText(this, "Photo URI: " + data.getData().toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setPhotosStatusToAccessible() {
        TextView statusText = (TextView) findViewById(R.id.checkPhotosStatusText);
        statusText.setText(R.string.sent);
        int iconId = DrawablesHelper.getDataAccessibleIconId(getResources(), getPackageName());
        statusText.setCompoundDrawablesRelativeWithIntrinsicBounds(iconId, 0, 0, 0);
    }

    private void setPhotosStatusToInaccessible() {
        TextView statusText = (TextView) findViewById(R.id.checkPhotosStatusText);
        statusText.setText(R.string.unavailable);
        int iconId = DrawablesHelper.getDataInaccessibleIconId(getResources(), getPackageName());
        statusText.setCompoundDrawablesRelativeWithIntrinsicBounds(iconId, 0, 0, 0);
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
