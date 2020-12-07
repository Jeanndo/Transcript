package com.example.mytranscript.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mytranscript.Model.SentModel;
import com.example.mytranscript.R;

import java.util.List;


public class SentAdapter  extends RecyclerView.Adapter<SentAdapter.Holder> {
    Context cxt;
    List<SentModel> sentRequests;
    FragmentActivity act;
    public SentAdapter(Context cxt, List<SentModel> requests,FragmentActivity act) {
        this.cxt = cxt;
        this.sentRequests = requests;
        this.act = act;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(cxt).inflate(R.layout.custom_sent_request, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
     SentModel sentModel=sentRequests.get(position);
     String slipType=sentModel.getType();
     String slipImage=sentModel.getPaymentSlip();

     holder.type.setText("Type: "+slipType);
     Glide.with(holder.requestCoverImage).load(slipImage).into(holder.requestCoverImage);


    }


    @Override
    public int getItemCount() {
        return sentRequests.size();
    }



    public class Holder extends RecyclerView.ViewHolder {
        TextView type;
        ImageView requestCoverImage;
        public Holder(@NonNull View itemView) {
            super(itemView);
            type=(TextView) itemView.findViewById(R.id.slipType);
            requestCoverImage=itemView.findViewById(R.id.slipImage);
        }
    }
}
