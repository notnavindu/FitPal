package com.unicodedev.fitpal.dietplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.unicodedev.fitpal.R;

import java.util.ArrayList;

public class IntensityAdapter extends ArrayAdapter <IntensityItem> {
    public IntensityAdapter(Context context, ArrayList<IntensityItem> intensityList){
        super(context, 0, intensityList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.intensities_spinner_rows, parent, false
            );
        }
        TextView textViewName = convertView.findViewById(R.id.intensity_input_text);
        IntensityItem currentItem = getItem(position);

        if(currentItem != null) {
            textViewName.setText(currentItem.getIntensityItem());
        }

        return convertView;

    }
}
