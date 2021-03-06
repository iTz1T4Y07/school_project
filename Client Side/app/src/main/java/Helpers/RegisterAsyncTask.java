package Helpers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import static android.app.Activity.RESULT_OK;
import static Helpers.GlobalVars.ErrorCode;

public class RegisterAsyncTask extends AsyncTask<String, Void, ErrorCode> {
    //AsyncTask to show the progress dialog while trying to register a new user.

    private ProgressDialog dialog;
    private Context context;

    public RegisterAsyncTask(Activity activity){
        this.dialog = new ProgressDialog(activity);
        this.context = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.setMessage("מבצע רישום, נא להמתין...");
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    @Override
    protected ErrorCode doInBackground(String... args) {

        String username = args[0];
        String password = args[1];
        String privateName = args[2];
        String lastName = args[3];
        String email = args[4];
        String birthdayDate = args[5].replace('/', '-');

        if (!DataProcess.checkConnectionAvailability(this.context.getApplicationContext()))
            return ErrorCode.No_Connection;
        ErrorCode errorCode = DataProcess.registerNewUser(username, password, privateName, lastName, email, birthdayDate);
        return errorCode;

    }


    @Override
    protected void onPostExecute(ErrorCode errorCode) {
        if (errorCode == ErrorCode.No_Connection)
            Toast.makeText(this.context, "ההרשמה נכשלה, אנא בדוק את החיבור לאינטרנט", Toast.LENGTH_LONG).show();
        else if (errorCode == ErrorCode.Username_Exist)
            Toast.makeText(this.context, "שם המשתמש שהזנת תפוס, נא להזין שם משתמש אחר", Toast.LENGTH_LONG).show();
        else if (errorCode == ErrorCode.Email_Exist)
            Toast.makeText(this.context, "האימייל שהזנת תפוס, נא להזין אימייל אחר", Toast.LENGTH_LONG).show();
        else if (errorCode == ErrorCode.Action_Failed)
            Toast.makeText(this.context, "ההרשמה נכשלה, אנא נסה שוב", Toast.LENGTH_LONG).show();
        else {
            Toast.makeText(this.context, "ההרשמה הושלמה, מיד תועבר לחלון ההתחברות", Toast.LENGTH_LONG).show();
            ((Activity)this.context).setResult(RESULT_OK);
            ((Activity)this.context).finish();
        }

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
