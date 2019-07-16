package com.example.shopwise;


import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopwise.model.ShoppingListModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

import javax.annotation.Nullable;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_SWIPE;
import static com.example.shopwise.MiTujuApplication.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteListFragment extends Fragment {

    private CollectionReference userShoppingListRef;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private ImageView image;
    private TextView title, description, header;

    private RecyclerView favouriteList;
    private FirebaseFirestore mfirestore;

    private List<ShoppingListModel> userfavouriteList;
    private FavouriteListAdapter favouriteListAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    public FavouriteListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_favouritelist, container, false);
        FloatingActionButton fab = view.findViewById(R.id.listfab);

        image = (ImageView) view.findViewById(R.id.image_favourite);
        title = (TextView) view.findViewById(R.id.title);
        header = (TextView) view.findViewById(R.id.favtitle);
        description = (TextView) view.findViewById(R.id.description);

        favouriteList = (RecyclerView) view.findViewById(R.id.favourite_list_rv);
        userfavouriteList = new ArrayList<>();
        favouriteListAdapter = new FavouriteListAdapter(userfavouriteList, getContext());

        favouriteList.setHasFixedSize(true);
        favouriteList.setLayoutManager(new LinearLayoutManager(getActivity()));
        favouriteList.setAdapter(favouriteListAdapter);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        favouriteListAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), "Works!", Toast.LENGTH_LONG).show();
                    }
                }, 20);

            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                final String shoppingitemID = userfavouriteList.get(position).getShoppingListID();
                final String shoppingitemName = userfavouriteList.get(position).getShoppingListName();
                userShoppingListRef = mfirestore.collection("shoppinglist").document(user.getEmail()).collection("usershoppinglist");
                userShoppingListRef.document(shoppingitemID).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        userfavouriteList.remove(userfavouriteList.get(position));
                        favouriteListAdapter.notifyDataSetChanged();
                        Snackbar.make(view, shoppingitemName+" is Deleted Successfully.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }

        });
        helper.attachToRecyclerView(favouriteList);

        mfirestore = FirebaseFirestore.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            header.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
            description.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                           builder.setTitle("Create Your Favourite List");
                                           builder.setMessage("Login and create your favourite list now. Browse the promotions and click the love icon to save your item into your list.");
                                           builder.setIcon(R.drawable.ic_shopping_cart_24dp);
                                           builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                       @Override
                                                       public void onClick(DialogInterface dialogInterface, int i) {
                                                           Intent intent = new Intent(getActivity(), UserLogin.class);
                                                           startActivity(intent);
                                                       }
                                                   }
                                           );
                                           AlertDialog alertDialog = builder.create();
                                           alertDialog.show();
                                       }
                                   }
            );
        } else {
            header.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
            title.setVisibility(View.GONE);
            description.setVisibility(View.GONE);
            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_delete_black_24dp));
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Swipe to delete your item");
                    builder.setMessage("Swipe left / right to delete your item.");
                    builder.setIcon(R.drawable.ic_shopping_cart_24dp);

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }
                    );
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            });

            mfirestore.collection("shoppinglist").document(user.getEmail()).collection("usershoppinglist").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.d(TAG, "Error:" + e.getMessage());
                    }
                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            ShoppingListModel shoppingListModel = doc.getDocument().toObject(ShoppingListModel.class);
                            userfavouriteList.add(shoppingListModel);
                            favouriteListAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        return view;
    }

}
