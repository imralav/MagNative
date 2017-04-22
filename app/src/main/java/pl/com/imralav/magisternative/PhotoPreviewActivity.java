package pl.com.imralav.magisternative;

import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.ImageView;

public class PhotoPreviewActivity extends AppCompatActivity {
    private static final String TAG = PhotoPreviewActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);
        String photoUri = getIntent().getStringExtra(MagNativeConstants.PHOTO_URI_KEY);
        Log.i(TAG, "Displaying photo for URI: " + photoUri);
        ImageView photoPreview = (ImageView) findViewById(R.id.photoPreview);
        photoPreview.setImageURI(Uri.parse(photoUri));
    }
}
