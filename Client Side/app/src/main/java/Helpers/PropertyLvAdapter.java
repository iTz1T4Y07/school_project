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

    /**
     * Creates an adapter with the specified context, resource, textViewResourceId and data
     * @param context The context of the adapter
     * @param resource The resource ID for a layout file containing a layout to use when instantiating views.
     * @param textViewResourceId The id of the TextView within the layout resource to be populated
     * @param data the data the adapter holds
     */
    public PropertyLvAdapter(Context context, int resource, int textViewResourceId, List<Property> data){
        super(context, resource, textViewResourceId, data);
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = ((Activity)this.context).getLayoutInflater(); // Gets layout inflater
        View item = layoutInflater.inflate(R.layout.property_layout, parent, false); // Inflate a layout to our view
        TextView tvName = (TextView)item.findViewById(R.id.tv_Property_Name);
        TextView tvValue = (TextView)item.findViewById(R.id.tv_Property_Value);

        Property temp = this.data.get(position);

        tvName.setText(temp.getName()); // Sets the name of the property
        tvValue.setText(temp.getValue()); // Sets the value of the property
        return item;
    }
}
