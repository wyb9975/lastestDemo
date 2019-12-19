package uk.ac.shef.oak.com4510;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uk.ac.shef.oak.com4510.Entity.RecordMsg;
import uk.ac.shef.oak.com4510.com4510.R;

/**
 * adapter interface that connects path data and pathActivity .
 *
 * @author Yuzhou Zhang
 * @version 1.0
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.View_Holder> {
    private Context context;
    private static ArrayList<ArrayList<RecordMsg>> items;

    /**
     * Instantiates a new List adapter.
     *
     * @param items the items
     */
    public ListAdapter(ArrayList<ArrayList<RecordMsg>> items) {
        this.items = items;
    }

    /**
     * Instantiates a new List adapter.
     *
     * @param cont  the cont
     * @param items the items
     */
    public ListAdapter(Context cont, ArrayList<ArrayList<RecordMsg>> items) {
        super();
        this.items = items;
        context = cont;
    }

    /**
     * Gets item.using position of the item.
     *
     * @param id the id
     * @return the item
     */
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

            String date = items.get(position).get(0).getDate();
            String title = items.get(position).get(0).getTitle();
            holder.title.setText(date);
            holder.preview.setText(title);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PictureActivity.class);
                    intent.putExtra("date", items.get(position).get(0).getDate());
                    intent.putExtra("title", items.get(position).get(0).getTitle());
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

    /**
     * The type View holder.
     */
    public class View_Holder extends RecyclerView.ViewHolder{

        TextView title;
        TextView preview;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        View_Holder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            preview = (TextView) itemView.findViewById(R.id.preview);
        }
    }
}
