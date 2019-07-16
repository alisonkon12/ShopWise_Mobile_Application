package com.example.shopwise;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class ShopListActivity extends AppCompatActivity {
    private RecyclerView shopListView;
    private FirebaseFirestore mFirestore;
    private static final String TAG = "ShopList";

    private List<ShopList> shopListitem;
    private ShopListAdapter shopListAdapter;
    private EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("category");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(Html.fromHtml("<font color='#FFFFFF'>" + message + " </font>"));
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        searchText = (EditText) findViewById(R.id.search_box);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                filter(editable.toString());

            }
        });

        shopListitem = new ArrayList<>();
        shopListAdapter = new ShopListAdapter(shopListitem, getApplicationContext());
        shopListView = (RecyclerView) findViewById(R.id.shoplist_rv);
        shopListView.setHasFixedSize(true);
        shopListView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        shopListView.setAdapter(shopListAdapter);
        mFirestore = FirebaseFirestore.getInstance();

        if (message.equals("All Shops")) {
            mFirestore.collection("all_shop").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.d(TAG, "Error" + e.getMessage());
                    }

                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            String userID = doc.getDocument().getId();
                            Log.d(TAG, "Shop ID" + userID);
                            ShopList shopList = doc.getDocument().toObject(ShopList.class).withId(doc.getDocument().getId());
                            shopListitem.add(shopList);
                            shopListAdapter.notifyDataSetChanged();

                        }
                    }
                }
            });
        } else {
            mFirestore.collection("retailers").document(message).collection("RetailerList").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.d(TAG, "Error" + e.getMessage());
                    }

                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            ShopList shopList = doc.getDocument().toObject(ShopList.class).withId(doc.getDocument().getId());
                            shopListitem.add(shopList);
                            shopListAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void filter(String text) {
        ArrayList<ShopList> filteredList = new ArrayList<>();

        for (ShopList item : shopListitem) {
            if (item.getShopName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        shopListAdapter.filterList(filteredList);
    }

}
