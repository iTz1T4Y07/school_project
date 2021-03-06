package Helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.BeachPage;
import com.example.myproject.MainActivity;
import com.example.myproject.R;
import Helpers.GlobalVars.ErrorCode;
import Helpers.GlobalVars.DayPart;
import Helpers.GlobalVars.EventUpdateMethod;

public class EventClickListener implements View.OnClickListener, DialogInterface.OnClickListener {

    private View selectedView;

    @Override
    public void onClick(View view) {
        this.selectedView = view;
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("אישור");
        builder.setCancelable(true);
        if((Boolean)view.getTag() == true) //If user is already participating
            builder.setMessage("האם אתה בטוח שברצונך לבטל את ההגעה?");
        else
            builder.setMessage("האם אתה בטוח שברצונך לאשר את ההגעה?");
        builder.setNegativeButton("לא", null);
        builder.setPositiveButton("כן", this);
        AlertDialog dialog = builder.create();
        dialog.setOwnerActivity(((Activity)view.getContext()));
        dialog.show();

    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        //Dialog listener
        if (i == DialogInterface.BUTTON_POSITIVE){

            int eventID = -1;
            GlobalVars.DayPart dayPart = null;
            Boolean participate = null;

            View dayView = (View)this.selectedView.getParent().getParent(); // Listview Item
            eventID = Integer.parseInt(dayView.getTag().toString()); //Day ID

            if (this.selectedView.getId() == R.id.btn_Morning)
                dayPart = GlobalVars.DayPart.MORNING;
            else if (this.selectedView.getId() == R.id.btn_afternoon)
                dayPart = GlobalVars.DayPart.AFTERNOON;
            else if (this.selectedView.getId() == R.id.btn_evening)
                dayPart = GlobalVars.DayPart.EVENING;

            participate = !((Boolean) this.selectedView.getTag()); //New participate status equals to the opposite of the current participate status

            if (eventID == -1 || dayPart == null || participate == null){
                Toast.makeText(this.selectedView.getContext(), "אירעה שגיאה, אנא נסה שוב", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d("test", String.format("dayID: %d, dayPart: %s, participe: %s", eventID,dayPart.getValue(),String.valueOf(participate)));


            //TODO: Call a function that connects to server and asks to update if user is participating or not
            //TODO: returns 1 if the update completed, if failed to update returns -1
            //TODO: If there is no connection it returns -2
            //TODO: Provides user ID, beach ID, Day ID, Part of the day (Morning/Afternoon/Evening), Participate or not in the call.

            ErrorCode resultCode;
            if (!DataProcess.checkConnectionAvailability(this.selectedView.getContext()))
                resultCode = ErrorCode.No_Connection;
            else
                if (participate) // new status is participating
                    resultCode = DataProcess.updateEvent(EventUpdateMethod.ADD_USER, GlobalVars.getConnectedId(), eventID, dayPart);
                else // new status is not participating
                    resultCode = DataProcess.updateEvent(EventUpdateMethod.REMOVE_USER, GlobalVars.getConnectedId(), eventID, dayPart);

            /*
            //For testing!!
            int resultCode = dayID % 3 ;
            switch(resultCode){
                case 0:
                    resultCode = 1;
                    break;
                case 1:
                    resultCode = -1;
                    break;
                case 2:
                    resultCode = -2;
                    break;
            }*/

            if (resultCode == ErrorCode.No_Connection)
                Toast.makeText(this.selectedView.getContext(), "הפעולה נכשלה, אנא בדוק את חיבור האינטרנט ונסה שוב", Toast.LENGTH_SHORT).show();
            else if (resultCode ==  ErrorCode.Action_Failed)
                Toast.makeText(this.selectedView.getContext(), "אירעה שגיאה, אנא נסה שוב", Toast.LENGTH_SHORT).show();
            else if (resultCode == ErrorCode.Success) {
                Toast.makeText(this.selectedView.getContext(), "הפעולה בוצעה בהצלחה", Toast.LENGTH_SHORT).show();
                //Reloading activity:
                Activity activity = ((Dialog) dialogInterface).getOwnerActivity(); //Gets the activity
                if (Build.VERSION.SDK_INT >= 11) // // recreate function was added in API 11
                    activity.recreate();
                else {
                    activity.finish();
                    activity.startActivity(activity.getIntent());
                }
            }

        }
    }
}
