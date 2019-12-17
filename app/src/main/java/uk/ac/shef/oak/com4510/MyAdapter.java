package uk.ac.shef.oak.com4510;

/*
 * Copyright (c) 2017. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

import uk.ac.shef.oak.com4510.com4510.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.View_Holder> {
    static private Context context;
    private static List<String> files;

    public MyAdapter(List<String> files) {
        this.files = files;
    }

    public MyAdapter(Context cont, List<String> files) {
        super();
        this.files = files;
        context = cont;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image,
                parent, false);
        View_Holder holder = new View_Holder(v);
        context= parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {

        //Use the provided View Holder on the onCreateViewHolder method to populate the
        // current row on the RecyclerView
        if (holder!=null && files.get(position)!=null) {
            Bitmap myBitmap = BitmapFactory.decodeFile(files.get(position));
            holder.imageView.setImageBitmap(myBitmap);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PicDetailActivity.class);
                    intent.putExtra("position", files.get(position));
                    context.startActivity(intent);
                }
            });
        }
        //animate(holder);
    }


    // convenience method for getting data at click position
    String getItem(int id) {
        return files.get(id);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class View_Holder extends RecyclerView.ViewHolder  {
        ImageView imageView;


        View_Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_item);

        }


    }

    public static List<String> getItems() {
        return files;
    }

    public static void setItems(List<String> files) {
        MyAdapter.files = files;
    }
}