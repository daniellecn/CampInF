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
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.campin.Activities.DetailActivity;
import com.campin.Activities.LoginActivity;
import com.campin.DB.Model;
import com.campin.R;
import com.campin.Utils.CircleTransform;
import com.campin.Utils.Trip;
import com.campin.Utils.TripComments;
import com.campin.Utils.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    Trip trip;
    CommentAdapter commentAdapter;

    public DetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_details, container, false);

        // Get position
        String tripId = getActivity().getIntent().getStringExtra(DetailActivity.EXTRA_POSITION);
        trip =  Model.instance().getTripById(tripId);

        // Set comments
        commentAdapter = new CommentAdapter();
        final ListView comments = (ListView) view.findViewById(R.id.det_comments);
        comments.setAdapter(commentAdapter);

        // Set title of Detail page
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(trip.getName());

        // Set image
        Model.instance().getTripImage(trip, 0, new Model.GetImageListener() {
            @Override
            public void onSuccess(Bitmap image) {
                ImageView detImage = (ImageView) view.findViewById(R.id.det_image);
                detImage.setImageBitmap(image);
            }

            @Override
            public void onFail() {
                // TODO - message toast
            }
        });

        // Set where
        TextView where = (TextView) view.findViewById(R.id.det_where);
        where.setText(Model.instance().getAreaByCode(trip.getArea()).getDescription());

        // Set when
        TextView when = (TextView) view.findViewById(R.id.det_when);
        String whenText = new String();

        whenText = trip.getSeasons().get(0);
        for (int i = 1; i < trip.getSeasons().size(); i++){
            whenText = whenText.concat(", ");
            whenText = whenText.concat(trip.getSeasons().get(i));
        }
        whenText = whenText.concat("\n");
        when.setText(whenText);

        // Set who
        TextView who = (TextView) view.findViewById(R.id.det_who);
        who.setText(trip.getFriendsNum() + " " + getResources().getString(R.string.who));

        // Set on details row click listener
        TableRow detailsRow = (TableRow) view.findViewById(R.id.det_details_row);
        detailsRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView detailsContent = (TextView) view.findViewById(R.id.det_details_content);

                if (detailsContent.getText().equals("")){
                    detailsContent.setText(trip.getDetails());
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
                    // Set equipments
                    TextView equipment = (TextView) view.findViewById(R.id.det_equipment_content);
                    String equipmentText = new String();

                    for (int i = 0; i < trip.getEquipment().size(); i++){
                        equipmentText = equipmentText.concat(trip.getEquipment().get(i));
                        equipmentText = equipmentText.concat("\n");
                    }

                    equipment.setText(equipmentText);
                }
                else{
                    eqpmtContent.setText("");
                }
            }
        });

        comments.setVisibility(View.GONE);

        TableRow commentsRow = (TableRow) view.findViewById(R.id.det_comments_row);
        commentsRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comments.getVisibility() == View.GONE){
                    comments.setVisibility(View.VISIBLE);
                }
                else{
                    comments.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    class CommentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return trip.getComments().size();
        }

        @Override
        public TripComments getItem(int i) {
            return trip.getComments().get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                view = inflater.inflate(R.layout.trip_comments_row, null);
            }

            // Set user image
            ImageView userImage = (ImageView) view.findViewById(R.id.det_avatar);

            userImage.setImageBitmap(CircleTransform.circleCrop(
                    LoginActivity.getFacebookProfilePicture(trip.getComments().get(i).get_userId())));

            // Set comment content
            TextView commentContent = (TextView) view.findViewById(R.id.det_comments_content);
            commentContent.setText(trip.getComments().get(i).get_tripComment());

            // Set Rating bar
            RatingBar ratingBar = (RatingBar) view.findViewById(R.id.det_rating);
            ratingBar.setNumStars(5);
            ratingBar.setRating(calcRating(trip.getComments().get(i).get_commentScore()));

            // Set user name
            final TextView[] userName = {(TextView) view.findViewById(R.id.det_reagent)};
            Model.instance().getUserById(trip.getComments().get(i).get_userId(), new Model.GetUserListener() {
                @Override
                public void onComplete(User user) {
                    userName[0].setText(user.getFullName());
                }

                @Override
                public void onCancel() {
                    // TODO: Toast
                }
            });

            return view;
        }

        private float calcRating(Double score){
            float rating = 0;

            if (score <= -0.6){
                rating = 1;
            }
            else if (score > -0.6 && score <= -0.2){
                rating = 2;
            }
            else if (score > -0.2 && score <= 0.2){
                rating = 3;
            }
            else if (score > 0.2 && score <= 0.6){
                rating = 4;
            }
            else if (score > 0.6 && score <= 1){
                rating = 5;
            }

            return rating;
        }
    }
}
