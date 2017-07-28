package com.campin.Fragments;


import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.campin.Activities.DetailActivity;
import com.campin.R;
import com.campin.Utils.CircleTransform;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    String[] equipmensList;
    EquipmenstAdapter eqpmAdapter;

    public DetailsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        equipmensList = getResources().getStringArray(R.array.trip_equipments_content);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        collapsingToolbar.setTitle(getString(R.string.item_title));

        // Screen Title
        int postion = getActivity().getIntent().getIntExtra(DetailActivity.EXTRA_POSITION, 0);
        Resources resources = getResources();
        String[] places = resources.getStringArray(R.array.places);
        collapsingToolbar.setTitle(places[postion % places.length]);

        // Screen Image
        TypedArray placePictures = resources.obtainTypedArray(R.array.places_picture);
        ImageView placePicutre = (ImageView) view.findViewById(R.id.image);
        placePicutre.setImageDrawable(placePictures.getDrawable(postion % placePictures.length()));
        placePictures.recycle();

        // Set on details row click listener
        TableRow detailsRow = (TableRow) view.findViewById(R.id.det_details_row);
        detailsRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView detailsContent = (TextView) view.findViewById(R.id.det_details_content);

                if (detailsContent.getText().equals("")){
                    detailsContent.setText(getString(R.string.trip_details_content));
                }
                else{
                    detailsContent.setText("");
                }
            }
        });

        // Set on equipment row click listener
        TableRow eqpmtRow = (TableRow) view.findViewById(R.id.det_equipment_row);
        eqpmtRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView eqpmtContent = (TextView) view.findViewById(R.id.det_equipment_content);
                String eqpmtString = "";

                if (eqpmtContent.getText().equals("")){
                    for (int i = 0; i < equipmensList.length; i++)   {
                        eqpmtString = eqpmtString.concat(equipmensList[i] + "\n");
                    }

                    eqpmtContent.setText(eqpmtString);
                }
                else{
                    eqpmtContent.setText("");
                }
            }
        });

        final RelativeLayout comment = (RelativeLayout) view.findViewById(R.id.det_comment);
        comment.setVisibility(View.GONE);

        TableRow commentsRow = (TableRow) view.findViewById(R.id.det_comments_row);
        commentsRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comment.getVisibility() == View.GONE){
                    comment.setVisibility(View.VISIBLE);
                }
                else{
                    comment.setVisibility(View.GONE);
                }
            }
        });

        ImageView avatar = (ImageView) view.findViewById(R.id.det_avatar);
//        Bitmap circleTransform = new CircleTransform(getActivity().getApplicationContext()).transform(
//                getResources().getDrawable(R.drawable.avatar_elza),
//                getResources().getDimension(R.dimen.avator_size),
//                getResources().getDimension(R.dimen.avator_size));
        avatar.setImageBitmap(CircleTransform.circleCrop(BitmapFactory.decodeResource(getResources(), R.drawable.avatar_elza)));

        return view;
    }

    class EquipmenstAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return equipmensList.length;
        }

        @Override
        public String getItem(int i) {
            return equipmensList[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                view = inflater.inflate(R.layout.trip_equipment_row, null);
            }

            ((TextView) view.findViewById(R.id.det_equipment_row)).setText(getItem(i));

            return view;
        }
    }
}
