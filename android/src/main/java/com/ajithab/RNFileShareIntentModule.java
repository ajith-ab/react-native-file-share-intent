
package com.ajithab;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import com.ajithab.RNFileShareIntentPackage;

import java.util.Map;
import java.util.ArrayList;

import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;


public class RNFileShareIntentModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNFileShareIntentModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }


  protected void onNewIntent(Intent intent) {
    Activity mActivity = getCurrentActivity();

    if(mActivity == null) { return; }

    mActivity.setIntent(intent);
  }

  @ReactMethod
  public void getFilepath(Callback successCallback) {
    Activity mActivity = getCurrentActivity();

    if(mActivity == null) { return; }

    Intent intent = mActivity.getIntent();
    String action = intent.getAction();
    String type = intent.getType();

    if (Intent.ACTION_SEND.equals(action) && type != null) {
      if ("text/plain".equals(type)) {
        String input = intent.getStringExtra(Intent.EXTRA_TEXT);
        successCallback.invoke(input, type);
      } else if (type.startsWith("image/") || type.startsWith("video/") || type.startsWith("application/")) {
        Uri fileUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (fileUri != null) {
          successCallback.invoke(fileUri.toString(), type);
        }
      } else {
        Toast.makeText(reactContext, "Type is not support", Toast.LENGTH_SHORT).show();
      }
    } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
        if (type.startsWith("image/") || type.startsWith("video/") || type.startsWith("application/")) {
          ArrayList<Uri> fileUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
          if (fileUris != null) {
            String completeString = new String();
            for (Uri uri: fileUris) {
              completeString += uri.toString() + ",";
            }
            successCallback.invoke(completeString, type);
          }
        } else {
          Toast.makeText(reactContext, "Type is not support", Toast.LENGTH_SHORT).show();
        }
    }
  }

  @ReactMethod
  public void clearFilePath() {
    Activity mActivity = getCurrentActivity();

    if(mActivity == null) { return; }

    Intent intent = mActivity.getIntent();
    String type = intent.getType();
    if ("text/plain".equals(type)) {
      intent.removeExtra(Intent.EXTRA_TEXT);
    } else if (type.startsWith("image/") || type.startsWith("video/") || type.startsWith("application/")) {
      intent.removeExtra(Intent.EXTRA_STREAM);
    }
  }
  @Override
  public String getName() {
    return "RNFileShareIntent";
  }
}