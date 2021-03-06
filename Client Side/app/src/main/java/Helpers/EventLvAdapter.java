package Helpers;

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

    public EventLvAdapter(Context context, int resource, int textViewResourceId, List<BeachEvent> data){
        super(context, resource, textViewResourceId, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int pos, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)this.context).getLayoutInflater();
        View item = layoutInflater.inflate(R.layout.event_item_layout, parent, false);
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

        tvDay.setText(temp.getDay());
        tvDate.setText(new SimpleDateFormat("dd/MM").format(temp.getDate()));
        tvMorningCount.setText(String.valueOf(temp.getMorningCounter()));
        tvAfternoonCount.setText(String.valueOf(temp.getAfternoonCounter()));
        tvEveningCount.setText(String.valueOf(temp.getEveningCounter()));

        if (GlobalVars.getConnectedState() == true){
            if (temp.isMorning()) {
                btnMorning.setTag(true);
                layMorning.setBackgroundResource(R.drawable.event_btn_gradient_participate);
            }
            else
                btnMorning.setTag(false);;
            if (temp.isAfternoon()) {
                btnAfternoon.setTag(true);
                layAfternoon.setBackgroundResource(R.drawable.event_btn_gradient_participate);
            }
            else
                btnAfternoon.setTag(false);
            if (temp.isEvening()) {
                btnEvening.setTag(true);
                layEvening.setBackgroundResource(R.drawable.event_btn_gradient_participate);
            }
            else
                btnEvening.setTag(false);

            btnMorning.setOnClickListener(new EventClickListener());
            btnAfternoon.setOnClickListener(new EventClickListener());
            btnEvening.setOnClickListener(new EventClickListener());
        }
        else{
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


