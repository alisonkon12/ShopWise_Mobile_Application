package com.example.shopwise.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.example.shopwise.ItemDetailActivity;
import com.example.shopwise.ItemDisplayActivity;
import com.example.shopwise.MapActivity;
import com.example.shopwise.R;
import com.example.shopwise.ShopListActivity;
import com.example.shopwise.search.SearchView;
import com.squareup.picasso.Picasso;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {
    public List<HomeItem> home_list;

    public Context context;

    public HomeRecyclerAdapter(Context context,List<HomeItem> home_list) {
        this.home_list = home_list;
        this.context = context;
    }

    //    @NonNull
//    @Override
//    public HomeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
//
//        return new ViewHolder(view);
//    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.desc.setText(home_list.get(position).getDealTitle());
        holder.detail.setText(home_list.get(position).getDealDetail());

         Picasso.with(context).load(home_list.get(position).getImageAddress()).into(holder.image2);

        final String home_item_id = home_list.get(position).homeitemId;
        final String home_item_name = home_list.get(position).getShopName();
        final String home_item_description = home_list.get(position).getDealDetail();
        final String home_item_image = home_list.get(position).getImageAddress();
        final String home_item_dealTitle = home_list.get(position).getDealTitle();

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, ItemDisplayActivity.class);
                intent.putExtra("shopID",home_item_id);
                intent.putExtra("shopName",home_item_name);
                intent.putExtra("desc",home_item_description);
                intent.putExtra("imageUrl",home_item_image);
                intent.putExtra("dealTitle",home_item_dealTitle);
                context.startActivity(intent);
            }
        });


//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(view.getContext(), ItemDetailActivity.class);
////                view.getContext().startActivity(intent);
////                Snackbar.make(view, holder.desc.getText(), Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//
//                final Dialog dialog = new Dialog(view.getContext());
//                dialog.setContentView(R.layout.activity_item_detail);

//                Button cancelBtn = (Button) view.findViewById(R.id.cancelbtn);
//                // if button is clicked, close the custom dialog
//                cancelBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//
//                Button mapBtn = (Button) dialog.findViewById(R.id.mapbutton);
//                // if button is clicked, close the custom dialog
//                mapBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(v.getContext(), MapActivity.class);
//                        v.getContext().startActivity(intent);
//                    }
////                });
//
//                dialog.show();
//            }
////
//        });
    }


    @Override
    public int getItemCount() {
        return home_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private TextView desc;
        private TextView detail;
        private ImageView image2;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.item_cardview);
            mView = itemView;
            desc = mView.findViewById(R.id.item_desc);
            image2 = mView.findViewById(R.id.item_image);
            detail = mView.findViewById(R.id.item_detail);


        }


    }
}
