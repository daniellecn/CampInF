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
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.campin.Activities.DetailActivity;
import com.campin.Activities.LoginActivity;
import com.campin.Activities.MainActivity;
import com.campin.DB.Model;
import com.campin.R;
import com.campin.Utils.Trip;
import com.campin.Utils.User;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Provides UI for the view with Cards.
 */
public class CardContentFragment extends Fragment {
    List<Trip> tripListData = new ArrayList<Trip>();
    ContentAdapter adapter;

    public CardContentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        adapter = new ContentAdapter(recyclerView.getContext());

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadTripListData();

        return recyclerView;
    }

    private void loadTripListData(){
        Model.instance().getAllTripAsynch(new Model.GetAllTripsListener() {
            @Override
            public void onComplete(List<Trip> tripsList, int currentMaxKey) {
                tripListData = tripsList;
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


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public TextView description;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));

            picture     = (ImageView) itemView.findViewById(R.id.card_image);
            name        = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_POSITION, String.valueOf(tripListData.get(getAdapterPosition()).getId()));
                    context.startActivity(intent);
                }
            });
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of Card in RecyclerView.


        public ContentAdapter(Context context)
        {
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            if (tripListData != null) {
                Model.instance().getTripImage(tripListData.get(position), 0, new Model.GetImageListener() {
                   @Override
                   public void onSuccess(Bitmap image) {
                        holder.picture.setImageBitmap(image);
                    }

                   @Override
                   public void onFail() {
                    }
                });

                holder.name.setText(tripListData.get(position).getName());
                holder.description.setText(tripListData.get(position).getDetails());
            }
        }

        @Override
        public int getItemCount() {
            return tripListData.size();
        }
    }
}
