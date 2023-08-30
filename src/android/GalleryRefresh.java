package com.ung.galleryrefresh;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.LOG;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;

public class GalleryRefresh extends CordovaPlugin {
    private static final String LOG_TAG = "GalleryRefresh";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
            if (action.equals("refresh")) {
                File file = new File(args.optString(0).replace("file://", ""));

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

    private void _scanFile(File inputFile) {
        // deprecated
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(inputFile));
        cordova.getContext().sendBroadcast(mediaScanIntent);

        _scanFileWithScanner(inputFile.getAbsolutePath(), true);
    }

    private void _scanFileWithScanner(String filePath, boolean retry) {
        MediaScannerConnection.scanFile(cordova.getContext(), new String[]{filePath}, null, new MediaScannerConnection.OnScanCompletedListener() {
            public void onScanCompleted(String path, Uri uri) {
                LOG.d(LOG_TAG, "Scanned " + path + " -> uri=" + uri);
                if (uri == null) {
                    if (retry) {
                        LOG.d(LOG_TAG, "Scan result uri is null ; will retry once");
                        _scanFileWithScanner(path, false);
                    } else {
                        LOG.w(LOG_TAG, "Scan result uri is null but no retry will be done");
                    }
                }
            }
        });
    }
}
