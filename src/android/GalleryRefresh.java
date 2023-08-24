package com.ung.galleryrefresh;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

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
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(contentUri);
        cordova.getActivity().sendBroadcast(mediaScanIntent);
    }

    private String _checkFilePath(String filePath) {
        try {
            return filePath.replaceAll("^file://", "");
        } catch (Exception e) {
            throw new RuntimeException("Error transferring file, error: " + e.getMessage());
        }
    }
}
