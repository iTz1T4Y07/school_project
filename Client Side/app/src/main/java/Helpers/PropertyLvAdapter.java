package Helpers;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myproject.R;

import java.util.List;

import Objects.BeachEvent;
import Objects.Property;

public class PropertyLvAdapter extends ArrayAdapter<Property> {

    private Context context;
    private List<Property> data;

    public PropertyLvAdapter(Context context, int resource, int textViewResourceId, List<Property> data){
        super(context, resource, textViewResourceId, data);
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = ((Activity)this.context).getLayoutInflater();
        View item = layoutInflater.inflate(R.layout.property_layout, parent, false);
        TextView tvName = (TextView)item.findViewById(R.id.tv_Property_Name);
        TextView tvValue = (TextView)item.findViewById(R.id.tv_Property_Value);

        Property temp = this.data.get(position);

        tvName.setText(temp.getName());
        tvValue.setText(temp.getValue());
        return item;
    }
}
