package com.example.shopwise.promotion;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.shopwise.MainActivity;
import com.example.shopwise.R;
import com.example.shopwise.SignupActivity;
import com.example.shopwise.UserLogin;
import com.example.shopwise.model.ShoppingListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.shopwise.MiTujuApplication.TAG;

public class PromotionListAdapter extends RecyclerView.Adapter<PromotionListAdapter.ViewHolder> {

    public List<PromotionItem> promotionItemList;
    public List<PromotionItem> favouriteList;
    private FirebaseFirestore mfirestore;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private CollectionReference userShoppingListRef;

    public Context context;

    public PromotionListAdapter(Context context, List<PromotionItem> promotionItemList) {
        this.promotionItemList = promotionItemList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promotion_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.shopName.setText(promotionItemList.get(position).getShopName());
        holder.description.setText(promotionItemList.get(position).getDealDetail());
        holder.dealName.setText(promotionItemList.get(position).getDealTitle());
        Picasso.with(context).load(promotionItemList.get(position).getImageAddress()).into(holder.image);

        final String promotion_item_id = promotionItemList.get(position).promotionItemId;
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Item ID : " + promotion_item_id, Toast.LENGTH_SHORT).show();
            }
        });

        final String ShopName = holder.shopName.getText().toString().trim();
        final String DealDesc = holder.description.getText().toString().trim();
        final String ImageUrl = promotionItemList.get(position).getImageAddress();
        final String DealTitle = holder.dealName.getText().toString().trim();


        holder.favouritebtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FirebaseUser islogin = FirebaseAuth.getInstance().getCurrentUser();
                    if (islogin == null) {
                        Toast.makeText(context, "Please Login to save you item.", Toast.LENGTH_SHORT).show();
                    } else {
                        mfirestore = FirebaseFirestore.getInstance();
                        DocumentReference docRef = mfirestore.collection("deal_of_the_day").document(promotion_item_id);
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.getId().equals(promotion_item_id)) {
                                        userShoppingListRef = mfirestore.collection("shoppinglist").document(user.getEmail()).collection("usershoppinglist");
                                        ShoppingListModel shoppingListModel = new ShoppingListModel(user.getEmail(), promotion_item_id, DealTitle, ImageUrl, new Date(), DealDesc,ShopName);
                                        userShoppingListRef.document(promotion_item_id).set(shoppingListModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(context, "Item Saved Successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        Log.d(TAG, "No such document");
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                } else {
                    FirebaseUser islogin = FirebaseAuth.getInstance().getCurrentUser();
                    if (islogin == null) {
                        Toast.makeText(context, "Please Login to save you item.", Toast.LENGTH_SHORT).show();
                    } else {
                        DocumentReference docRef = mfirestore.collection("deal_of_the_day").document(promotion_item_id);
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.getId().equals(promotion_item_id)) {
                                        userShoppingListRef = mfirestore.collection("shoppinglist").document(user.getEmail()).collection("usershoppinglist");
                                        ShoppingListModel shoppingListModel = new ShoppingListModel(user.getEmail(), promotion_item_id, DealTitle, ImageUrl, new Date(), DealDesc,ShopName);
                                        userShoppingListRef.document(promotion_item_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(context, "Item Deleted Successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        Toast.makeText(context, "Item not deleted successfully", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return promotionItemList.size();
    }

    public void filterList(ArrayList<PromotionItem> filteredList) {
        promotionItemList = filteredList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public TextView dealName;
        public TextView description;
        public TextView shopName;
        public ImageView image;
        public ToggleButton favouritebtn;


        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            dealName = (TextView) mView.findViewById(R.id.dealTitle);
            description = (TextView) mView.findViewById(R.id.promotionItemcontent);
            shopName = (TextView) mView.findViewById(R.id.shopName);
            image = (ImageView) mView.findViewById(R.id.promotionItem_image);
            favouritebtn = (ToggleButton) mView.findViewById(R.id.button_favorite);
        }
    }
}
