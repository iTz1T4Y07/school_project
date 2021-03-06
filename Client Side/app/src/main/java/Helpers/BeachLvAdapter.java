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

import Objects.Beach;

public class BeachLvAdapter extends ArrayAdapter<Beach> {

    private Context context;
    private List<Beach> data;

    public BeachLvAdapter(Context context, int resource, int textViewResourceId, List<Beach> data){
        super(context, resource, textViewResourceId, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int pos, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)this.context).getLayoutInflater();
        View item = layoutInflater.inflate(R.layout.beach_item_layout, parent, false);
        TextView tvName = (TextView)item.findViewById(R.id.tv_Beach_Name);
        Beach temp = this.data.get(pos);

        tvName.setText(temp.getName());

        return item;
    }
}
