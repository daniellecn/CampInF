/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.campin.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.campin.Activities.DetailActivity;
import com.campin.DB.Model;
import com.campin.R;
import com.campin.Utils.PlannedTrip;
import com.campin.Utils.Trip;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides UI for the view with Tile.
 */
public class TileContentFragment extends Fragment {

    ArrayList<PlannedTrip> tripListData = new ArrayList<PlannedTrip>();
    TileContentFragment.ContentAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        recyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        loadTripListData();
        return recyclerView;
    }

    private void loadTripListData(){
        Model.instance().getTripsUserBelongs(new Model.getTripsUserBelongsListener() {

            @Override
            public void onComplete(ArrayList<PlannedTrip> plannedTripList, int currentMaxKey) {
                tripListData = plannedTripList;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancel() {
                // Display message
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.errorOccure),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_tile, parent, false));
            picture = (ImageView) itemView.findViewById(R.id.tile_picture);
            name = (TextView) itemView.findViewById(R.id.tile_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    //intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
                    intent.putExtra
                            (DetailActivity.EXTRA_POSITION,
                                   name.getTag().toString());
                    intent.putExtra
                            (DetailActivity.FRIENDS_NUM,
                               Integer.parseInt( picture.getTag().toString()));
                    context.startActivity(intent);
                }
            });
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of Tiles in RecyclerView.
        private static final int LENGTH = 18;

        public ContentAdapter(Context context)
        {
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            if (tripListData != null && tripListData.size() > 0 && tripListData.size() > position) {

                tripListData.get(position).setTrip(Model.instance().getTripById(tripListData.get(position).getId()));

                Model.instance().getTripImage(tripListData.get(position).getTrip(), 0, new Model.GetImageListener() {
                            @Override
                            public void onSuccess(Bitmap image) {
                                holder.picture.setImageBitmap(image);
                                holder.picture.setTag(tripListData.get(position).getFriends().size());
                                holder.name.setText(tripListData.get(position).getTrip().getName());
                                holder.name.setTag(tripListData.get(position).getTrip().getId());
                            }

                            @Override
                            public void onFail() {
                            }
                        });
                    // holder.description.setText(tripListData.get(position).getDetails());
            }


            //holder.picture.setImageDrawable(mPlacePictures[position % mPlacePictures.length]);
        }

        @Override
        public int getItemCount() {
            return tripListData.size();
        }
        /*private final String[] mPlaces;
        private final Drawable[] mPlacePictures;
        public ContentAdapter(Context context) {
            Resources resources = context.getResources();

            mPlaces = resources.getStringArray(R.array.places);
            TypedArray a = resources.obtainTypedArray(R.array.places_picture);
            mPlacePictures = new Drawable[a.length()];


            for (int i = 0; i < mPlacePictures.length; i++) {
                mPlacePictures[i] = a.getDrawable(i);
            }
            a.recycle();*/
    }
}