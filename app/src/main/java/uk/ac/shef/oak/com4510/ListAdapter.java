package uk.ac.shef.oak.com4510;


import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import uk.ac.shef.oak.com4510.Entity.RecordMsg;
import uk.ac.shef.oak.com4510.com4510.R;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.View_Holder> {
    private Context context;
    private static ArrayList<ArrayList<RecordMsg>> items;

    public ListAdapter(ArrayList<ArrayList<RecordMsg>> items) {
        this.items = items;
    }

    public ListAdapter(Context cont, ArrayList<ArrayList<RecordMsg>> items) {
        super();
        this.items = items;
        context = cont;
    }
    ArrayList<RecordMsg> getItem(int id) {
        return items.get(id);
    }
    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_content,
                parent, false);
        View_Holder holder = new View_Holder(v);
        context= parent.getContext();
        return holder;
    }


    @Override
    public void onBindViewHolder(View_Holder holder, final int position) {
        //Use the provided View Holder on the onCreateViewHolder method to populate the
        // current row on the RecyclerView
        if (holder!=null && position < items.size()) {

            Log.d("qwqw" + position,items.get(position).size() +"");
            for(int i = 0;i < items.get(position).size();i++){
                Log.d("qwqw" + position,items.get(position).get(i).getDate());
            }
            String date = items.get(position).get(0).getDate();
            String title = items.get(position).get(0).getTitle();
            Log.d("qwqw" + position,date +" test");
            Log.d("qwqw" + position,title +" test");
            holder.title.setText(date);
            holder.preview.setText(title);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PictureActivity.class);
                    intent.putExtra("date", items.get(position).get(0).getDate());
                    context.startActivity(intent);
                }
            });
        }
        //animate(holder);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class View_Holder extends RecyclerView.ViewHolder{
        TextView title;
        TextView preview;

        View_Holder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            preview = (TextView) itemView.findViewById(R.id.preview);
        }
    }
}
