package pl.com.imralav.magisternative;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import pl.com.imralav.magisternative.utils.DrawablesHelper;

public class DataAccessCheckActivity extends AppCompatActivity {

    private static final int PHOTO_GALLERY_REQUEST_CODE = 0;
    private static final int CONTACTS_REQUEST_CODE = 1;
    private static final String TAG = DataAccessCheckActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_access_check);
    }

    public void goToCameraCheck(View view) {
        Intent intent = new Intent(this, CameraCheckActivity.class);
        startActivity(intent);
    }

    public void checkPhotos(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PHOTO_GALLERY_REQUEST_CODE);
    }

    public void checkContacts(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(Phone.CONTENT_TYPE);
        startActivityForResult(intent, CONTACTS_REQUEST_CODE);
    }

    public void checkMessages(View view) {
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
        StringBuilder message = new StringBuilder();
        if (cursor != null && cursor.moveToFirst()) { // must check the result to prevent exception
            do {
                int column = cursor.getColumnIndex("address");
                message.append(cursor.getString(column)).append(":");
                column = cursor.getColumnIndex("body");
                message.append(cursor.getString(column).substring(0, 15)).append("...");
            } while (cursor.moveToNext());
            cursor.close();
        }
        setCheckMsgStatusToAccessible(message.toString());
    }

    private void setCheckMsgStatusToAccessible(String text) {
        setAccessibleStatusOnResource(R.id.checkMsgStatusText, text);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_GALLERY_REQUEST_CODE:
                handlePhotoGalleryResult(resultCode, data);
                break;
            case CONTACTS_REQUEST_CODE:
                handleContactsResult(resultCode, data);
                break;
            default:
                break;
        }
    }

    private void handleContactsResult(int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            setCheckContactsStatusToInaccessible();
        } else {
            Uri contactUri = data.getData();
            String[] projection = {Phone.NUMBER};
            Cursor cursor = getContentResolver()
                    .query(contactUri, projection, null, null, null);
            cursor.moveToFirst();
            int column = cursor.getColumnIndex(Phone.NUMBER);
            String number = cursor.getString(column);
            String message = String.format("Contact number: %s", number);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            setCheckContactsStatusToAccessibleWithText(message);
        }
    }


    private void setCheckContactsStatusToAccessibleWithText(String text) {
        setAccessibleStatusOnResource(R.id.checkContactsStatusText, text);
    }

    private void setCheckContactsStatusToInaccessible() {
        setInaccessibleStatusOnResource(R.id.checkContactsStatusText);
    }

    private void setInaccessibleStatusOnResource(int resourceId) {
        TextView statusText = (TextView) findViewById(resourceId);
        statusText.setText(R.string.unavailable);
        int iconId = DrawablesHelper.getDataInaccessibleIconId(getResources(), getPackageName());
        statusText.setCompoundDrawablesRelativeWithIntrinsicBounds(iconId, 0, 0, 0);
    }

    private void handlePhotoGalleryResult(int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            setPhotosStatusToInaccessible();
        } else {
            setPhotosStatusToAccessible();
            Intent intent = new Intent(this, PhotoPreviewActivity.class);
            String photoUri = data.getData().toString();
            intent.putExtra(MagNativeConstants.PHOTO_URI_KEY, photoUri);
            Log.i(TAG, "Starting photo preview activity for URI " + photoUri);
            startActivity(intent);
        }
    }

    private void setAccessibleStatusOnResource(int resourceId) {
        setAccessibleStatusOnResource(resourceId, getResources().getString(R.string.sent));
    }

    private void setAccessibleStatusOnResource(int resourceId, String text) {
        TextView statusText = (TextView) findViewById(resourceId);
        statusText.setText(text);
        int iconId = DrawablesHelper.getDataAccessibleIconId(getResources(), getPackageName());
        statusText.setCompoundDrawablesRelativeWithIntrinsicBounds(iconId, 0, 0, 0);
    }

    private void setPhotosStatusToAccessible() {
        setAccessibleStatusOnResource(R.id.checkPhotosStatusText);
    }

    private void setPhotosStatusToInaccessible() {
        setInaccessibleStatusOnResource(R.id.checkPhotosStatusText);
    }
}
