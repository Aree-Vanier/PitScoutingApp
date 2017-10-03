package ca.lakeeffect.scoutingapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomList extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] titles;
    private final Integer[] imageId;
    int layout;


    public CustomList(Activity context, String[] titles, Integer[] imageId, int layout) {
        super(context, layout, titles);
        this.context = context;
        this.titles = titles;
        this.imageId = imageId;
        this.layout = layout;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(layout, null, true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        txtTitle.setText(titles[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}

