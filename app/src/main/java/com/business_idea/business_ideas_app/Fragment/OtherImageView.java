package com.business_idea.business_ideas_app.Fragment;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.business_idea.business_ideas_app.R;

public class OtherImageView extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.imgdialog, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imgdialog=view.findViewById(R.id.imgdialog);
        if(getDefaults("fromblog",getActivity()).equals("Yes")) {
            RequestOptions requestOptions = new RequestOptions();
            setDefaults("fromblog","No",getActivity());
            requestOptions.placeholder(R.drawable.loading);
            Glide.with(getActivity()).load(getDefaults("bloguri", getActivity())).apply(requestOptions).into(imgdialog);
        }
        else if(getDefaults("frombloggr",getActivity()).equals("Yes"))
        {      setDefaults("frombloggr","No",getActivity());
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.loading);
            Glide.with(getActivity()).load(getDefaults("bloggeruri", getActivity())).apply(requestOptions).into(imgdialog);
        }
    }
    public static void setDefaults(String key,String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();

    }
    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

}
