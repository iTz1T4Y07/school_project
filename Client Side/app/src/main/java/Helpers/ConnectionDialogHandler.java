package Helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;

public final class ConnectionDialogHandler implements DialogInterface.OnClickListener {

    @Override
    public void onClick(DialogInterface dialogInterface, int button) {
        if (dialogInterface.getClass() == AlertDialog.class) {
            Activity activity = ((Dialog) dialogInterface).getOwnerActivity();
            if (button == Dialog.BUTTON_POSITIVE) {
                if (Build.VERSION.SDK_INT >= 11) // recreate function was added in API 11
                    activity.recreate();
                else {
                    activity.finish();
                    activity.overridePendingTransition(android.R.anim.fade_in, 0);
                    activity.startActivity(activity.getIntent());
                }
            }
            else if (button == Dialog.BUTTON_NEGATIVE)
                activity.finish();
        }
    }
}
