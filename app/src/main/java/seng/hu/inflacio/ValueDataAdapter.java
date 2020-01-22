package seng.hu.inflacio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ValueDataAdapter extends ArrayAdapter<ValueData> {

    private Context context;
    private  int resource;
    private ArrayList<ValueData> objects;

    public ValueDataAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ValueData> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView textViewCol1 = convertView.findViewById(R.id.textViewCol1);
        TextView textViewCol2 = convertView.findViewById(R.id.textViewCol2);
        TextView textViewCol3 = convertView.findViewById(R.id.textViewCol3);
        TextView textViewCol4 = convertView.findViewById(R.id.textViewCol4);
        TextView textViewCol5 = convertView.findViewById(R.id.textViewCol5);

        textViewCol1.setText(String.valueOf(getItem(position).getOldValue()));
        textViewCol2.setText(String.valueOf(getItem(position).getNewValue()));
        textViewCol3.setText(String.valueOf(getItem(position).getNewRoundedValue()));
        textViewCol4.setText(String.valueOf(getItem(position).getQuantity()));
        textViewCol5.setText(String.valueOf(getItem(position).getNewPrice()));

        return convertView;
    }
}
