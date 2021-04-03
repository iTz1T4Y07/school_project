package Helpers;

/**
 * Async task to check login credentials of a user  while showing a progress dialog
 * @author Itay Kahalani
 * @date 28/03/2021
 * @version 1.0.0
 * @since 1.0
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.myproject.LoginPage;

import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_OK;


public class LoginAsyncTask extends AsyncTask<String, Void, Integer> {

    private ProgressDialog dialog;
    private Context context;

    /**
     * Creates LoginAsyncTask with the specified context
     * @param activity The activity that will be used as a context for the asyncTask
     */
    public LoginAsyncTask(Activity activity) {
        this.dialog = new ProgressDialog(activity); // Creates new progress dialog
        this.context = activity;
    }

    @Override
    protected void onPreExecute() {
        this.dialog.setMessage("מבצע התחברות, נא להמתין..."); // Sets dialog message
        this.dialog.setCancelable(false); // Sets dialog cancelable option
        this.dialog.show(); // Shows dialog
    }
    @Override
    protected Integer doInBackground(String... args) {

        String username = args[0];
        String password = args[1];


        if (!DataProcess.checkConnectionAvailability(this.context.getApplicationContext())) // If there is no connection
            return GlobalVars.ErrorCode.No_Connection.getCode() * (-1);
        int errorCode = DataProcess.loginCheck(username, password);
        return errorCode;


    }
    @Override
    protected void onPostExecute(Integer id) {

        if (id == GlobalVars.ErrorCode.No_Connection.getCode() * (-1))
            Toast.makeText(this.context, "ההתחברות נכשלה, אנא בדוק את החיבור לאינטרנט", Toast.LENGTH_LONG).show();
        else if (id == GlobalVars.ErrorCode.Action_Failed.getCode() * (-1))
            Toast.makeText(this.context, "ההתחברות נכשלה, אנא נסה שוב", Toast.LENGTH_LONG).show();
        else if (id == GlobalVars.ErrorCode.Wrong_User_Credentials.getCode() * (-1))
            Toast.makeText(this.context, "שם המשתמש והסיסמא אינם מתאימים אחד לשני, אנא בדוק כי הפרטים נכונים", Toast.LENGTH_LONG).show();
        else {
            Toast.makeText(this.context, "ההתחברות הושלמה, מיד תועבר לחלון הראשי", Toast.LENGTH_LONG).show();
            GlobalVars.setConnectedState(true); //Change user status from guest to registered user
            GlobalVars.setConnectedId(id); //Set user id
            ((Activity)this.context).setResult(RESULT_OK);
            ((Activity)this.context).finish();
        }

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
