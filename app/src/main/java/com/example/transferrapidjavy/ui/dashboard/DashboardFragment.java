package com.example.transferrapidjavy.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.transferrapidjavy.DataHistory;
import com.example.transferrapidjavy.DataModel;
import com.example.transferrapidjavy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private DatabaseReference dataModelFire;
    ListView listViewData;
    List<DataModel> dataHistory;
    private FirebaseAuth mAuth;

    Button deleteHistory;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        mAuth = FirebaseAuth.getInstance();
        String currentUser = mAuth.getCurrentUser().getEmail();
        currentUser = getUserFromMail(currentUser);


        dataModelFire = FirebaseDatabase.getInstance().getReference(currentUser);
        listViewData = (ListView)root.findViewById(R.id.listViewData);
        dataHistory = new ArrayList<>();
        createDeleteHButton(root,dataModelFire);
        return root;
    }


    public void createDeleteHButton(View root, final DatabaseReference toDelete){

        deleteHistory = (Button)root.findViewById(R.id.deleteHistoryD);
        deleteHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDelete.removeValue();
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();

        dataModelFire.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                dataHistory.clear();


                for(DataSnapshot dataModelSnapshot : dataSnapshot.getChildren()){

                    DataModel dataModel = dataModelSnapshot.getValue(DataModel.class);
                    dataHistory.add(dataModel);
                }
                if(getActivity()!=null) {
                    DataHistory dataHistory1 = new DataHistory(getActivity(), dataHistory);
                    listViewData.setAdapter(dataHistory1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private  String getUserFromMail(String str) {
        return str.substring(0, str.length() - 10);///TODO:10/11
    }
}