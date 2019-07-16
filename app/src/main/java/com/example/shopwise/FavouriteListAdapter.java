package com.example.shopwise;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopwise.model.ShoppingListModel;
import com.example.shopwise.search.SearchView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FavouriteListAdapter extends RecyclerView.Adapter<FavouriteListAdapter.ViewHolder> {

    public List<ShoppingListModel> favouriteList;
    public Context context;

    public FavouriteListAdapter(List<ShoppingListModel> favouriteList, Context context) {
        this.favouriteList = favouriteList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_list_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.shopName.setText(favouriteList.get(position).getShoppingListName());
        holder.description.setText(favouriteList.get(position).getShoppingListDescription());
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        holder.date.setText("Created on : "+dateFormatter.format(favouriteList.get(position).getCreatedDate()));
        Picasso.with(context).load(favouriteList.get(position).getImageUrl()).into(holder.image);

        final String favourite_item_ID = favouriteList.get(position).getShoppingListID();
        final String shop = favouriteList.get(position).getShopName();
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Item ID : " + favourite_item_ID, Toast.LENGTH_SHORT).show();
            }
        });
        holder.map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent(view.getContext(), MapActivity.class);
                intent.putExtra("shopName", shop);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public TextView shopName, description, date;
        public ImageView image;
        public Button map;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            shopName = (TextView) mView.findViewById(R.id.favouriteitem_title);
            description = (TextView) mView.findViewById(R.id.favouriteitem_desc);
            date = (TextView) mView.findViewById(R.id.addedDate);
            image = (ImageView) mView.findViewById(R.id.favouriteitem_image);
            map = (Button) mView.findViewById(R.id.button_map);
        }
    }
}
