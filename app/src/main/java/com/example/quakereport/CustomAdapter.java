package com.example.quakereport;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Earthquake> {

    public CustomAdapter(Activity context, List<Earthquake> earthquakes){
      super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_item_view, parent, false);
        }
        Earthquake currentearthquake = getItem(position);

        TextView textView = itemView.findViewById(R.id.magnitude);
        String formattedDecimal = formatDecimal(currentearthquake.getMmagnitude());
        textView.setText(formattedDecimal);

        GradientDrawable magnitudeCircle = (GradientDrawable) textView.getBackground();
        int magnitudeColor = getMagnitudeColor(currentearthquake.getMmagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        TextView textView1= itemView.findViewById(R.id.PlaceoffSet);
        textView1.setText(currentearthquake.getMplaceoffSet());

        TextView locationtextView = itemView.findViewById(R.id.location);
        locationtextView.setText(currentearthquake.getLocation());

        Date dateobject= new Date(currentearthquake.getMtimeInMilliSeconds());
        TextView dateView = itemView.findViewById(R.id.Date);
        String formatteDate = formateDate(dateobject);
        TextView textView2 = itemView.findViewById(R.id.Date);
        textView2.setText(formatteDate);

        String formattedTime = formatTime(dateobject);
        TextView timeTextView = itemView.findViewById(R.id.time);
        timeTextView.setText(formattedTime);
        return itemView;
    }

    public String formateDate(Date dateobject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateobject);
    }

    public String formatTime(Date dateobject){
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateobject);
    }

    public String formatDecimal(double decimalObject){
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(decimalObject);
    }

    private int getMagnitudeColor(double mMagnitude){
        int magnitudeColorReferenceID ;
        int magnitudeFloor = (int) Math.floor(mMagnitude);
        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorReferenceID = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorReferenceID = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorReferenceID = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorReferenceID = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorReferenceID = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorReferenceID = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorReferenceID = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorReferenceID = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorReferenceID = R.color.magnitude9;
                break;
            default:
                magnitudeColorReferenceID = R.color.magnitude10plus;
        }
//Once we find the right color resource ID, we still have one more step to convert it into an actual color value.
// Remember that color resource IDs just point to the resource we defined, but not the value of the color. For example,
// R.layout.earthquake_list_item is a reference to tell us where the layout is located. It’s just a number, not the full XML layout.
////You can call ContextCompat getColor() to convert the color resource ID into an actual integer color value,
// and return the result as the return value of the getMagnitudeColor() helper method.

        return ContextCompat.getColor(getContext(), magnitudeColorReferenceID);
    }
}
