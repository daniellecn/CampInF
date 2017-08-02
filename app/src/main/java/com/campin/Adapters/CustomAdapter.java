package com.campin.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.bumptech.glide.util.Util;
import com.campin.Activities.LoginActivity;
import com.campin.R;
import com.campin.Utils.TripLevel;
import com.campin.Utils.TripType;
import com.campin.Utils.User;
import com.campin.Utils.Utils;
import com.facebook.login.LoginManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
    ArrayList<String> _names;
    ArrayList<String> _id;
    List<TripType> _tripTypes;
    List<TripLevel> _tripLevels;
    public ArrayList<String> _friends;
    public static List<Integer> _preferedAreas = new LinkedList<>();
    Activity context;
    String value;

    public CustomAdapter(Activity context, ArrayList<String> names, ArrayList<String> id,
                         List<TripType> types, List<TripLevel> levels) {
        super();
        this.context = context;
        this._names = new ArrayList<String>();
        this._tripLevels = new ArrayList<TripLevel>();
        this._tripLevels = levels;
        this._tripTypes = new ArrayList<TripType>();
        this._tripTypes = types;
        this._names = names;
        this._id = new ArrayList<String>();
        this._id = id;
    }

    private class ViewHolder {
        CheckedTextView txtView;
    }

    public int getCount() {
        return _names.size();
    }

    @Override
    public String getItem(int position) {
        return _names.get(position);
    }


    public String getIdItem(int position) {
        return _id.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.check_list, null);
            holder = new ViewHolder();

            holder.txtView = (CheckedTextView) convertView.findViewById(R.id.simpleCheckedTextView);
            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        String name = getItem(position);
        holder.txtView.setText(name);

        if (User.getInstance().isShowFriends())
        {
            Bitmap bitmap = LoginActivity.getFacebookProfilePicture(getIdItem(position));
            Drawable d = new BitmapDrawable(context.getResources(), Utils.getCroppedBitmap(bitmap));
            holder.txtView.setCompoundDrawablesWithIntrinsicBounds(null,null,d,null);
            holder.txtView.setTag(getIdItem(position));
            holder.txtView.setText(getItem(position));
            holder.txtView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            _friends = new ArrayList<String>();
        }
        else
        {
            String id = getIdItem(position);
            holder.txtView.setTag(id);

            if (User.getInstance().getPreferedAreas().contains(Integer.parseInt(id)))
            {
                holder.txtView.setCheckMarkDrawable(R.drawable.checked);
                holder.txtView.setChecked(true);
            }
            else
            {
                  holder.txtView.setCheckMarkDrawable(null);
                  holder.txtView.setChecked(false);
            }
        }

        holder.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.txtView.isChecked()) {
                    // set cheek mark drawable and set checked property to false
                    value = "un-Checked";
                    holder.txtView.setCheckMarkDrawable(null);
                    holder.txtView.setChecked(false);
                    if (User.getInstance().isShowFriends() == true) {
                        _friends.remove(holder.txtView.getTag().toString());
                    }
                    else {

                        int count = 0;
                        for (Integer i : _preferedAreas)
                        {
                            if (i ==  Integer.parseInt(holder.txtView.getTag().toString()))
                            {
                                _preferedAreas.remove(count);
                                break;
                            }

                            count++;
                        }

                    }
                } else {
                    // set cheek mark drawable and set checked property to true
                    value = "Checked";

                    if (User.getInstance().isShowFriends() == true) {
                        _friends.add(holder.txtView.getTag().toString());
                    }
                    else {
                        _preferedAreas.add(Integer.parseInt(holder.txtView.getTag().toString()));
                    }

                    holder.txtView.setCheckMarkDrawable(R.drawable.checked);
                    holder.txtView.setChecked(true);
                }
            }
        });

        return convertView;
    }
}