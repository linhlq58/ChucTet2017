package com.tetstudio.linhlee.chuctet.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

/**
 * Created by lequy on 1/18/2017.
 */

public class Constant {
    public static void initShareIntent(Context context, String type, String shareBody) {
        Toast.makeText(context, "Đang xử lý...", Toast.LENGTH_SHORT).show();

        boolean found = false;
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");

        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(share, 0);
        if (!resInfo.isEmpty()){
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(type) ||
                        info.activityInfo.name.toLowerCase().contains(type)) {
                    share.putExtra(Intent.EXTRA_TEXT, shareBody);
                    share.setPackage(info.activityInfo.packageName);
                    found = true;
                    break;
                }
            }
            if (!found) {
                Toast.makeText(context, "Ứng dụng này chưa được cài đặt", Toast.LENGTH_SHORT).show();
                return;
            }

            context.startActivity(Intent.createChooser(share, "Share via"));
        }
    }

    public static int convertDpIntoPixels(int sizeInDp, Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (sizeInDp * scale + 0.5f);
        return dpAsPixels;
    }

    public static void increaseHitArea(final View button) {
        final View parent = (View) button.getParent();  // button: the view you want to enlarge hit area
        parent.post(new Runnable() {
            public void run() {
                final Rect rect = new Rect();
                button.getHitRect(rect);
                rect.top -= 100;    // increase top hit area
                rect.left -= 100;   // increase left hit area
                rect.bottom += 100; // increase bottom hit area
                rect.right += 100;  // increase right hit area
                parent.setTouchDelegate(new TouchDelegate(rect, button));
            }
        });
    }
}
