//MyAdapter
package com.s22010151.parkspotter_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.s22010151.parkspotter_app.DataClass;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<DataClass> dataList;

    public MyAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getImageURL()).into(holder.recImage);

        DataClass dataClass = dataList.get(position);

        holder.recPlace.setText(dataList.get(position).getPlace());
        holder.recArrival.setText(dataList.get(position).getArrival());
        holder.recAvailable.setText(dataList.get(position).getAvailable());
        holder.recCharge.setText(dataList.get(position).getCharge());


        holder.recCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(context, Details.class);
                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getImageURL());
                intent.putExtra("place", dataList.get(holder.getAdapterPosition()).getPlace());
                intent.putExtra("arrival", dataList.get(holder.getAdapterPosition()).getArrival());
                intent.putExtra("available", dataList.get(holder.getAdapterPosition()).getAvailable());
                intent.putExtra("charge", dataList.get(holder.getAdapterPosition()).getCharge());
                intent.putExtra("Key", dataList.get(holder.getAdapterPosition()).getKey());

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(ArrayList<DataClass> searchList) {
        dataList = searchList;
        notifyDataSetChanged();
    }


}
class MyViewHolder extends RecyclerView.ViewHolder{
    Context context;
    List<DataClass> dataList;
    ImageView recImage;
    TextView recPlace, recAvailable, recCharge, recArrival;
    CardView recCard;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage = itemView.findViewById(R.id.recImage);
        recPlace = itemView.findViewById(R.id.recPlace);
        recArrival = itemView.findViewById(R.id.recArrival);
        recAvailable = itemView.findViewById(R.id.recAvailable);
        recCharge = itemView.findViewById(R.id.recCharge);
        recCard = itemView.findViewById(R.id.recCard);
    }
}