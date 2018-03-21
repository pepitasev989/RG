package mk.com.tasev.pepi.rewardgateway.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spanned;

import mk.com.tasev.pepi.rewardgateway.R;

/**
 * Created by ptasev on 3/14/18.
 */

public class Utils {

    public static boolean checkForPermission(final Context context, final String permissionString, final String messageExplanation, final int requestCode) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, permissionString) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permissionString)) {
                    showPermissionDialog(messageExplanation, context, permissionString, requestCode);
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{permissionString}, requestCode);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    private static void showPermissionDialog(final String msg, final Context context, final String permission, final int requestPermissionCode) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, requestPermissionCode);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    public static void showInternetConnectionDialog(final Context context) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(false);
        alertBuilder.setTitle(R.string.title_error);
        alertBuilder.setMessage(R.string.internet_connection_required);
        alertBuilder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    public static boolean checkForInternetConnection(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static Spanned getHtmlFormattedString(String baseString) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return Html.fromHtml(baseString, Html.FROM_HTML_MODE_COMPACT);
        else
            return Html.fromHtml(baseString);
    }
}
