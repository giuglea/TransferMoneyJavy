package com.example.transferrapidjavy.ui.notifications;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.ContentView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.transferrapidjavy.MainController;
import com.example.transferrapidjavy.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;


    Button goToSite;
    Button logOutButton;

    SeekBar selectSecuritySeek;

    TextView moneySelectedText;
    TextView currentUserText;
    TextView securityLevelText;


    private FirebaseAuth mAuth;
    double ron = 4.78,  dollar = 1.11, euro = 1.00;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);


        createGoToSiteButton(root);
        createUI(root);
        createSelectMoneySeekBar(root);
        createSpinner(root);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void createSelectMoneySeekBar(View root){
        selectSecuritySeek = (SeekBar)root.findViewById(R.id.seekBar2);
        selectSecuritySeek.setMax(0);
        selectSecuritySeek.setMax(3);
        securityLevelText.setText("No Security!");
        selectSecuritySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                switch (progress){
                    case 0:
                        securityLevelText.setText("No Security!");
                        break;
                    case 1:
                        securityLevelText.setText("Low Level Security");
                        break;
                    case 2:
                        securityLevelText.setText("Medium Level Security");
                        break;
                    case 3:
                        securityLevelText.setText("High Level Security");
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void createLogOutButton(View root){
        logOutButton = (Button)root.findViewById(R.id.logOutButton);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
            }
        });
    }


    public void createGoToSiteButton(View root){
        goToSite = (Button)root.findViewById(R.id.goToSite);
        goToSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/giuglea"));
                startActivity(browserIntent);
            }
        });

    }

    public void createSpinner(View root){
        final List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Euro");
        spinnerArray.add("Dollar");
        spinnerArray.add("Ron");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner)root.findViewById(R.id.spinner);
        sItems.setAdapter(adapter);

        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int myPosition, long myID) {


                switch (parentView.getItemAtPosition(myPosition).toString()){
                    case "Euro":
                        moneySelectedText.setText(euro+parentView.getItemAtPosition(myPosition).toString());
                        break;
                    case "Dollar":
                        moneySelectedText.setText(dollar+parentView.getItemAtPosition(myPosition).toString()+"i");
                        break;
                    case "Ron":
                        moneySelectedText.setText(ron+parentView.getItemAtPosition(myPosition).toString());
                        break;
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


}


    public void createUI(View root){

        currentUserText = (TextView)root.findViewById(R.id.currentUserText);
        mAuth = FirebaseAuth.getInstance();
        String currentUser = mAuth.getCurrentUser().getEmail();
        currentUserText.setText(currentUser);


        moneySelectedText = (TextView)root.findViewById(R.id.textView16);
        moneySelectedText.setText("Euro");

        securityLevelText = (TextView)root.findViewById(R.id.textView17);




    }

}