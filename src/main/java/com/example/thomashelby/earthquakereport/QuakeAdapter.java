package com.example.thomashelby.earthquakereport;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;
import java.util.ArrayList;
import java.util.List;

public class QuakeAdapter extends ArrayAdapter<Word> {
    public QuakeAdapter(Activity context, ArrayList<Word> androidFlavors) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, androidFlavors);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Word current = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView mag = (TextView) listItemView.findViewById(R.id.magnitude);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        mag.setText(Double.toString(current.getMagnitude()));
        GradientDrawable magnitudeCircle = (GradientDrawable) mag.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude

        double f=Math.ceil(current.getMagnitude());
        switch((int) f){
            case 0:
            case 1:
            case 2:
                magnitudeCircle.setColor(ContextCompat.getColor(getContext(), R.color.magnitude1));
                break;
            case 3:
                magnitudeCircle.setColor(ContextCompat.getColor(getContext(), R.color.magnitude2));
                break;
            case 4:
                magnitudeCircle.setColor(ContextCompat.getColor(getContext(), R.color.magnitude3));
            break;
                case 5:
                magnitudeCircle.setColor(ContextCompat.getColor(getContext(), R.color.magnitude4));
            break;
                case 6:
                magnitudeCircle.setColor(ContextCompat.getColor(getContext(), R.color.magnitude5));
            break;
                case 7:
                magnitudeCircle.setColor(ContextCompat.getColor(getContext(), R.color.magnitude6));
            break;
                case 8:
                magnitudeCircle.setColor(ContextCompat.getColor(getContext(), R.color.magnitude7));
            break;
                case 9:
                magnitudeCircle.setColor(ContextCompat.getColor(getContext(), R.color.magnitude8));
            break;
                case 10:
                magnitudeCircle.setColor(ContextCompat.getColor(getContext(), R.color.magnitude9));
                default:
                    magnitudeCircle.setColor(ContextCompat.getColor(getContext(), R.color.magnitude10plus));
break;

        }


        // Set the color on the magnitude circle



        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView place1 = (TextView) listItemView.findViewById(R.id.place1);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        place1.setText(current.getPlace1());
        TextView place2 = (TextView) listItemView.findViewById(R.id.place2);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        place2.setText(current.getPlace2());

        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        TextView date = (TextView) listItemView.findViewById(R.id.date);
        // Get the image resource ID from the current AndroidFlavor object and
        // set the image to iconView
        date.setText(current.getDate());

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }

}
