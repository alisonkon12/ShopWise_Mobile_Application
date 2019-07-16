package com.example.shopwise.promotion;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopwise.R;
import com.example.shopwise.home.HomeRecyclerAdapter;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PromotionFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "PromotionLog";
    private RecyclerView recycleView;
    private FirebaseFirestore mfirestore;
    private EditText searchText;
    private Button filter;

    private PromotionListAdapter promotionListAdapter;
    private List<PromotionItem> promotionitemList;

    String[] category = {"All", "Fashion", "Food", "Grocery", "Leisure"};


    public PromotionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promotion, container, false);

        promotionitemList = new ArrayList<>();
        promotionListAdapter = new PromotionListAdapter(getContext(), promotionitemList);

        recycleView = (RecyclerView) view.findViewById(R.id.promotionitem_list);
        recycleView.setHasFixedSize(true);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleView.setAdapter(promotionListAdapter);

//        spinner = view.findViewById(R.id.spinner);
        filter = view.findViewById(R.id.btn_filter);
        filter.setOnClickListener(this);


        searchText = (EditText) view.findViewById(R.id.search_box);
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

        mfirestore = FirebaseFirestore.getInstance();

        mfirestore.collection("deal_of_the_day").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (e != null) {
                    Log.d(TAG, "Error :" + e.getMessage());

                }
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {

                        PromotionItem promotionItem = doc.getDocument().toObject(PromotionItem.class).withId(doc.getDocument().getId());
                        promotionitemList.add(promotionItem);
                        promotionListAdapter.notifyDataSetChanged();
                    }

                }
            }
        });

        return view;
    }

    private void filter(String text) {
        ArrayList<PromotionItem> filteredList = new ArrayList<>();

        for (PromotionItem item : promotionitemList) {
            if (item.getShopName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        promotionListAdapter.filterList(filteredList);
    }

    @Override
    public void onClick(View view) {
        openDialog();
    }

    public void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.layout_xml, null);
        builder.setTitle("Find Shops");
        final Spinner spinner = (Spinner) view.findViewById(R.id.categorySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.promotion_category_array));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!spinner.getSelectedItem().toString().equalsIgnoreCase("Choose A Category")) {
                    ArrayList<PromotionItem> filteredList = new ArrayList<>();

                    for (PromotionItem item : promotionitemList) {
                        if (item.getCategory().equalsIgnoreCase(spinner.getSelectedItem().toString())) {
                            filteredList.add(item);
                        }
                    }
                    promotionListAdapter.filterList(filteredList);
                }
            }});
        builder.setNegativeButton("Dismiss",new DialogInterface.OnClickListener()

            {
                @Override
                public void onClick (DialogInterface dialogInterface,int i){
                dialogInterface.dismiss();
            }
            });
        builder.setView(view);
            AlertDialog dialog = builder.create();
        dialog.show();
        }
    }
