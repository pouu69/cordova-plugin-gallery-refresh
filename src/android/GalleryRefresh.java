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


/**
 * This class echoes a string called from JavaScript.
 */
public class GalleryRefresh extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {

          if (action.equals("refresh")) {
            String filePath = _checkFilePath(args.getString(0));

            if (filePath.equals("")) {
              callbackContext.error("Invalid File Path");
            }

            File file = new File(filePath);

            this._scanPhoto(file);
          }

          callbackContext.success("Success Scan File");
          return true;
        } catch (Exception e) {
          callbackContext.error(e.getMessage());
          return false;
        }
    }

    private void _scanPhoto(File imageFile) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(imageFile);
        mediaScanIntent.setData(contentUri);
        cordova.getActivity().sendBroadcast(mediaScanIntent);
    }

    private String _checkFilePath(String filePath) {
      String return_value = "";

      try {
        return_value = filePath.replaceAll("^file://", "");
      } catch (Exception e) {
        throw new RuntimeException("Error transfering file, error: " + e.getMessage());
      }
      return return_value;
    }
}
