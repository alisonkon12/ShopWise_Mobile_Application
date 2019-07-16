package com.example.shopwise.home;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.example.shopwise.MapActivity;
import com.example.shopwise.MiTujuApplication;
import com.example.shopwise.R;
import com.example.shopwise.ShopListActivity;
import com.example.shopwise.promotion.PromotionFragment;
import com.example.shopwise.promotion.PromotionItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.luolc.emojirain.EmojiRainLayout;

import my.mimos.mituju.v2.ilpservice.db.TblPoints;

import static com.example.shopwise.MiTujuApplication.TAG;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private MiTujuApplication app;
    private TextView item_title;
    private CardView promotionCard;
    private FirebaseFirestore mfirestore;

    private List<HomeItem> homelist;
    private List<RewardItem> rewardlist;
    private HomeRecyclerAdapter homeRecyclerAdapter;
    private RewardRecyclerAdapter rewardRecyclerAdapter;
    private RecyclerView recyclerView , rewardRecyclerView;

    String[] listCategory;
    boolean[] checkItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    private EmojiRainLayout mcontainer;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
            }
        });


        mcontainer=(EmojiRainLayout) view.findViewById(R.id.activity_main);
        promotionCard = (CardView) view.findViewById(R.id.promotionCard);
        promotionCard.setOnClickListener(this);


        homelist = new ArrayList<>();
        homeRecyclerAdapter = new HomeRecyclerAdapter(getContext(), homelist);
        recyclerView = (RecyclerView) view.findViewById(R.id.home_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(homeRecyclerAdapter);

        rewardlist = new ArrayList<>();
        rewardRecyclerAdapter = new RewardRecyclerAdapter(rewardlist,getContext());
        rewardRecyclerView = (RecyclerView) view.findViewById(R.id.reward_rv);
        rewardRecyclerView.setHasFixedSize(true);
        rewardRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rewardRecyclerView.setAdapter(rewardRecyclerAdapter);



//        LinearLayoutManager horizontalLayoutManager
//                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(horizontalLayoutManager);


        //-----------------------------------------------------------------------------------------------
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference photoReference = storageReference.child("monthlyPromotion/hariRaya.jpg");

        final ImageView image = (ImageView) view.findViewById(R.id.promotion_image);

        final long ONE_MEGABYTE = 1024 * 1024;
        photoReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                image.setImageBitmap(bmp);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getActivity().getApplicationContext(), "No Such file or Path found!!", Toast.LENGTH_LONG).show();
            }
        });

        //-------------------------------------------------------------------------------------------------
        Button shop = (Button) view.findViewById(R.id.btn_shop);
        Button food = (Button) view.findViewById(R.id.btn_food);
        Button entertainment = (Button) view.findViewById(R.id.btn_entertainment);
        Button grocery = (Button) view.findViewById(R.id.btn_grocery);
        Button fashion = (Button) view.findViewById(R.id.btn_fashion);
        Button dealShare = (Button) view.findViewById(R.id.dealShare);
        Button subscribe = (Button) view.findViewById(R.id.btn_subscibe);
        Button rewardShare = (Button) view.findViewById(R.id.reward_share);
        Button contact =(Button) view.findViewById(R.id.btn_contact);


        shop.setOnClickListener(this);
        food.setOnClickListener(this);
        entertainment.setOnClickListener(this);
        grocery.setOnClickListener(this);
        fashion.setOnClickListener(this);
        dealShare.setOnClickListener(this);
        subscribe.setOnClickListener(this);
        rewardShare.setOnClickListener(this);
        contact.setOnClickListener(this);

        listCategory = getResources().getStringArray(R.array.category_arrays);
        checkItems = new boolean[listCategory.length];


        mfirestore = FirebaseFirestore.getInstance();
        mfirestore.collection("deal_of_the_day").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d(TAG, "Error :" + e.getMessage());
                }
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        HomeItem homeItem = doc.getDocument().toObject(HomeItem.class).withId(doc.getDocument().getId());
                        homelist.add(homeItem);
                        homeRecyclerAdapter.notifyDataSetChanged();
                    }

                }
            }
        });

        mfirestore = FirebaseFirestore.getInstance();
        mfirestore.collection("rewards").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d(TAG, "Error :" + e.getMessage());
                }
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        RewardItem rewardItem = doc.getDocument().toObject(RewardItem.class).withId(doc.getDocument().getId());
                        rewardlist.add(rewardItem);
                        rewardRecyclerAdapter.notifyDataSetChanged();
                    }

                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_shop:
                Intent intent = new Intent(getActivity(), ShopListActivity.class);
                intent.putExtra("category", "All Shops");
                startActivity(intent);
                break;
            case R.id.btn_food:
                Intent food = new Intent(getActivity(), ShopListActivity.class);
                food.putExtra("category", "Food");
                startActivity(food);
                break;
            case R.id.btn_entertainment:
                Intent entertainment = new Intent(getActivity(), ShopListActivity.class);
                entertainment.putExtra("category", "Leisure");
                startActivity(entertainment);
                break;
            case R.id.btn_fashion:
                Intent fashion = new Intent(getActivity(), ShopListActivity.class);
                fashion.putExtra("category", "Fashion");
                startActivity(fashion);
                break;
            case R.id.btn_grocery:
                Intent grocery = new Intent(getActivity(), ShopListActivity.class);
                grocery.putExtra("category", "Grocery");
                startActivity(grocery);
                break;
            case R.id.dealShare:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Share these deal to your friends";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Deal Of The Day");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;
            case R.id.reward_share:
                Intent rewardSharing = new Intent(android.content.Intent.ACTION_SEND);
                rewardSharing.setType("text/plain");
                String rewardshareBody = "Share these rewards to your friends";
                rewardSharing.putExtra(android.content.Intent.EXTRA_SUBJECT, "Special Rewards");
                rewardSharing.putExtra(android.content.Intent.EXTRA_TEXT, rewardshareBody);
                startActivity(Intent.createChooser(rewardSharing, "Share via"));
                break;
            case R.id.btn_subscibe:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                mBuilder.setTitle("Subscribe Your Categories");
                mBuilder.setMultiChoiceItems(listCategory, checkItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked) {
                            if (!mUserItems.contains(position)) {
                                mUserItems.add(position);

                            } else {
                                mUserItems.remove(position);
                            }
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + listCategory[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item + ",";
                            }

                        }
                        String[] items = item.split(",");
                        for (final String category : items) {
                            System.out.println("item = " + category);
                            FirebaseMessaging.getInstance().subscribeToTopic(category).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(), "You have subscribe to : " + category, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                          Toast.makeText(getContext(), "Sucesss", Toast.LENGTH_SHORT).show();
                    }
                });
                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkItems.length; i++) {
                            checkItems[i] = false;
                            mUserItems.clear();
                            ;

                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            case R.id.btn_contact:
                Intent Email = new Intent(Intent.ACTION_SEND);
                Email.setType("text/email");
                Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "admin@shopwise.com" });
                Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback about ShopWise");
                Email.putExtra(Intent.EXTRA_TEXT, "Dear ShopWise Admin," + "");
                startActivity(Intent.createChooser(Email, "Send Feedback:"));
                break;
            case R.id.promotionCard:
                mcontainer.addEmoji(R.drawable.ketupat);
                mcontainer.addEmoji(R.drawable.pelita);
                mcontainer.stopDropping();
                mcontainer.setPer(10);
                mcontainer.setDuration(7200);
                mcontainer.setDropDuration(2400);
                mcontainer.setDropFrequency(1000);
                mcontainer.startDropping();
        }


    }
}
