package Helpers;

/**
 * Adapter for ListView of events
 * @author Itay Kahalani
 * @date 28/03/2021
 * @version 1.0.0
 * @since 1.0
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.example.myproject.R;

import java.text.SimpleDateFormat;
import java.util.List;

import Objects.BeachEvent;

public class EventLvAdapter extends ArrayAdapter<BeachEvent> {

    private Context context;
    private List<BeachEvent> data;

    /**
     * Creates an adapter with the specified context, resource, textViewResourceId and data
     * @param context The context of the adapter
     * @param resource The resource ID for a layout file containing a layout to use when instantiating views.
     * @param textViewResourceId The id of the TextView within the layout resource to be populated
     * @param data the data the adapter holds
     */
    public EventLvAdapter(Context context, int resource, int textViewResourceId, List<BeachEvent> data){
        super(context, resource, textViewResourceId, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int pos, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)this.context).getLayoutInflater(); // Gets layout inflater
        View item = layoutInflater.inflate(R.layout.event_item_layout, parent, false); // Inflate a layout to our view
        TextView tvDay = (TextView)item.findViewById(R.id.tv_Day);
        TextView tvDate = (TextView)item.findViewById(R.id.tv_Date);
        TextView tvMorningCount = (TextView)item.findViewById(R.id.tv_morning_count);
        TextView tvAfternoonCount = (TextView)item.findViewById(R.id.tv_afternoon_count);
        TextView tvEveningCount = (TextView)item.findViewById(R.id.tv_evening_count);
        CardView btnMorning = (CardView)item.findViewById(R.id.btn_Morning);
        CardView btnAfternoon = (CardView)item.findViewById(R.id.btn_afternoon);
        CardView btnEvening = (CardView)item.findViewById(R.id.btn_evening);
        LinearLayout layMorning = (LinearLayout)item.findViewById(R.id.lay_Morning);
        LinearLayout layAfternoon = (LinearLayout)item.findViewById(R.id.lay_Afternoon);
        LinearLayout layEvening = (LinearLayout)item.findViewById(R.id.lay_evening);

        BeachEvent temp = this.data.get(pos);

        tvDay.setText(temp.getDay()); // Sets the name of the day in the week
        tvDate.setText(new SimpleDateFormat("dd/MM").format(temp.getDate())); // Sets the date
        tvMorningCount.setText(String.valueOf(temp.getMorningCounter())); // Sets the number of people participate in the morning
        tvAfternoonCount.setText(String.valueOf(temp.getAfternoonCounter())); // Sets the number of people participate in the afternoon
        tvEveningCount.setText(String.valueOf(temp.getEveningCounter())); // Sets the number of people participate in the evening

        if (GlobalVars.getConnectedState() == true){ // If user is connected
            if (temp.isMorning()) { // If user participate in the morning
                btnMorning.setTag(true);
                layMorning.setBackgroundResource(R.drawable.event_btn_gradient_participate);
            }
            else
                btnMorning.setTag(false);;
            if (temp.isAfternoon()) { // If user participate in the afternoon
                btnAfternoon.setTag(true);
                layAfternoon.setBackgroundResource(R.drawable.event_btn_gradient_participate);
            }
            else
                btnAfternoon.setTag(false);
            if (temp.isEvening()) { // If user participate in the evening
                btnEvening.setTag(true);
                layEvening.setBackgroundResource(R.drawable.event_btn_gradient_participate);
            }
            else
                btnEvening.setTag(false);

            btnMorning.setOnClickListener(new EventClickListener());
            btnAfternoon.setOnClickListener(new EventClickListener());
            btnEvening.setOnClickListener(new EventClickListener());
        }
        else{ // If user is not connected
            btnMorning.setClickable(false);
            btnAfternoon.setClickable(false);
            btnEvening.setClickable(false);
        }

        item.setTag(temp.getId());

        return item;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}


