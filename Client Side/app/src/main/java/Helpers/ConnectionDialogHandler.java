package Helpers;

/**
 * Handler for no connection dialog
 * @author Itay Kahalani
 * @date 28/03/2021
 * @version 1.0.0
 * @since 1.0
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;

public final class ConnectionDialogHandler implements DialogInterface.OnClickListener {

    @Override
    public void onClick(DialogInterface dialogInterface, int button) {
        if (dialogInterface.getClass() == AlertDialog.class) {
            Activity activity = ((Dialog) dialogInterface).getOwnerActivity(); // Gets the activity the dialog created on
            if (button == Dialog.BUTTON_POSITIVE) {
                if (Build.VERSION.SDK_INT >= 11) // recreate function was added in API 11
                    activity.recreate(); // Recreates activity
                else {
                    activity.finish(); // Closing Activity
                    activity.overridePendingTransition(android.R.anim.fade_in, 0); // Changing animation
                    activity.startActivity(activity.getIntent()); // Starting the same activity
                }
            }
            else if (button == Dialog.BUTTON_NEGATIVE)
                activity.finish(); // finish activity
        }
    }
}
