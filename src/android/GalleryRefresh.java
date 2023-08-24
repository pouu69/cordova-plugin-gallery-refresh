package com.ung.galleryrefresh;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;

public class GalleryRefresh extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
            if (action.equals("refresh")) {
                String filePath = _checkFilePath(args.getString(0));

                File file = new File(filePath);

                if (!file.exists()) {
                    callbackContext.error("Invalid File Path");
                    return false;
                }

                this._scanFile(file);
            }

            callbackContext.success("Success Scan File");
            return true;
        } catch (Exception e) {
            callbackContext.error(e.getMessage());
            return false;
        }
    }

    private void _scanFile(File contentFile) {
        Uri contentUri = Uri.fromFile(contentFile);

        // deprecated
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(contentUri);
        cordova.getActivity().sendBroadcast(mediaScanIntent);

        // new using MediaStore
        String mimeType;
        if (ContentResolver.SCHEME_CONTENT.equals(contentUri.getScheme())) {
            mimeType = cordova.getActivity().getContentResolver().getType(contentUri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(contentUri.toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
        }
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, contentFile.getAbsolutePath());
        Uri externalContentUri = null;
        if (mimeType != null) {
            values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
            if (mimeType.contains("audio/")) {
                externalContentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            } else if (mimeType.contains("image/")) {
                externalContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if (mimeType.contains("video/")) {
                externalContentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else {
                if (Build.VERSION.SDK_INT >= 29) {
                    externalContentUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
                }
            }
        }
        if (externalContentUri != null) {
            cordova.getActivity().getContentResolver().insert(externalContentUri, values);
        }
    }

    private String _checkFilePath(String filePath) {
        try {
            return filePath.replaceAll("^file://", "");
        } catch (Exception e) {
            throw new RuntimeException("Error transferring file, error: " + e.getMessage());
        }
    }
}
