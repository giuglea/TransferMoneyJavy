package com.example.transferrapidjavy.ui.home;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.transferrapidjavy.DataModel;
import com.example.transferrapidjavy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseDataHistory;


    TextView sumText;
    TextView label100,label500,label1000;
    TextView percentFeeText,feeText;
    TextView destinationText;

    Button transferH;
    Button button100,button500,button1000;

    SeekBar seekBar;


   // DatabaseSQLite myDB;

    Integer caseGlobal = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //myDB = new DatabaseSQLite(getContext());

        createUI(root);
        createTransferH(root);
        createButtonForSum(root);
        createSeekbar(root);
        sumSeekerModifier(0);



        mAuth = FirebaseAuth.getInstance();
        String currentUser = mAuth.getCurrentUser().getEmail();
        //Toast(getActivity(),currentUser,Toast.LENGTH_SHORT).show();
        currentUser = getUserFromMail(currentUser);
        databaseDataHistory = FirebaseDatabase.getInstance().getReference(currentUser);

        return root;
    }

    public void createSeekbar(View root){
        seekBar = (SeekBar)root.findViewById(R.id.seekBar);
        seekBar.setMax(98);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            Integer progressValue ;
            Integer fee ;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                progressValue = Math.round(progress);

                switch (caseGlobal){
                    case 0:
                        progressValue +=2;
                        fee = Math.round(2*progressValue/100);
                        break;
                    case 1:
                        progressValue +=100;
                        fee = Math.round(3*progressValue/100);
                        break;
                    case 2:
                        progressValue+=500;
                        fee = Math.round(4*progressValue/100);
                        break;
                }
                if(fee<1)fee=1;
                String progressString = Integer.toString(progressValue);
                sumText.setText(progressString+"€");

                feeText.setText(Integer.toString(fee)+"€");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    public void sumSeekerModifier(Integer caseOf){

        String sumTextValue = sumText.getText().toString();
        sumTextValue = removeLastChar(sumTextValue);
        Integer sumValue = Integer.parseInt(sumTextValue);

        switch (caseOf){
            case 0:
                label100.setText("2€");
                label500.setText("50€");
                label1000.setText("100€");
                caseGlobal = 0;
                seekBar.setMax(1);
                seekBar.setMax(98);
                sumText.setText("2€");
                feeText.setText("1€");
                percentFeeText.setText("2%");
                break;
            case 1:
                label100.setText("100€");
                label500.setText("300€");
                label1000.setText("500€");
                caseGlobal = 1;
                seekBar.setMax(1);
                seekBar.setMax(400);
                sumText.setText("100€");
                feeText.setText("2€");
                percentFeeText.setText("3%");
                break;
            case 2:
                label100.setText("500€");
                label500.setText("750€");
                label1000.setText("1000€");
                caseGlobal = 2;
                seekBar.setMax(1);
                seekBar.setMax(500);
                sumText.setText("500€");
                feeText.setText("15€");
                percentFeeText.setText("4%");
                break;

        }

    }

    public void createButtonForSum(View root){
        button100 = (Button)root.findViewById(R.id.button100);
        button500 = (Button)root.findViewById(R.id.button500);
        button1000 = (Button)root.findViewById(R.id.button1000);


        button100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumSeekerModifier(0);
            }
        });
        button500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumSeekerModifier(1);
            }
        });
        button1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumSeekerModifier(2);
            }
        });
    }


    public void createTransferH(View root){

        transferH = (Button)root.findViewById(R.id.transferH);
        transferH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(destinationText.getText().toString().length()<3){
                    Toast.makeText(getActivity(),"Insert Destination!",Toast.LENGTH_SHORT).show();
                }
                else{


                    String sender = mAuth.getCurrentUser().getEmail().toString();
                    String receiver = destinationText.getText().toString();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                    Date date = new Date();
                    String amountText = sumText.getText().toString();

                    amountText = removeLastChar(amountText);
                    Integer amount = Integer.parseInt(amountText);


                    DataModel dataModel = new DataModel(sender,receiver,date.toString(),amount);
                    String id = databaseDataHistory.push().getKey();
                    databaseDataHistory.child(id).setValue(dataModel);

                   Toast toast =  Toast.makeText(getActivity(),"You have sent "+amountText+"€ to "+receiver,Toast.LENGTH_SHORT );
                   toast.setGravity(Gravity.CENTER,0,0);
                   toast.show();


                }
            }
        });
    }


    public void createUI(View root){
        sumText = (TextView)root.findViewById(R.id.sumTextH);
        sumText.setText("2€");

        label100 = (TextView)root.findViewById(R.id.label100);
        label500 = (TextView)root.findViewById(R.id.label500);
        label1000 = (TextView)root.findViewById(R.id.label1000);

        percentFeeText = (TextView)root.findViewById(R.id.percentFeeText);
        feeText = (TextView)root.findViewById(R.id.feeText);
        destinationText = (TextView)root.findViewById(R.id.destinationTextH);

    }

    private  String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    private  String getUserFromMail(String str) {
        return str.substring(0, str.length() - 10);///TODO:10/11
    }




}