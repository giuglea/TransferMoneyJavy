package com.example.transferrapidjavy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {

    TextView emailText ;
    TextView passwordText;
    TextView passwordCheckText;
    Button logInButton;
    Button registerButton;

    private FirebaseAuth mAuth;
    private boolean isSuccessful = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        emailText = (TextView)findViewById(R.id.emailEditText);
        passwordText = (TextView)findViewById(R.id.passwordEditText);
        passwordCheckText = (TextView)findViewById(R.id.passwordCheck);
        passwordCheckText.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        createLogInButton();
        createRegisterButton();






    }

    private void createRegisterButton() {
        registerButton = (Button)findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register(){
        Intent goRegister = new Intent(this,Register.class);
        goRegister.putExtra("callingActivity","Register");
        startActivity(goRegister);
    }

    private void createLogInButton(){
        logInButton = (Button)findViewById(R.id.logInButton);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });
    }

    private void logIn(){


        if(logInFirebase() == true){
            Toast.makeText(LogIn.this, Boolean.toString(isSuccessful),
                    Toast.LENGTH_SHORT).show();

        }

    }

    private boolean logInFirebase(){
         isSuccessful = false;
        mAuth.signInWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                           // Toast.makeText(LogIn.this, "Succes!",Toast.LENGTH_SHORT).show();
                            isSuccessful = true;
                            Intent goToMainController = new Intent(LogIn.this,MainController.class);
                            goToMainController.putExtra("callingActivity","MainController");
                            startActivity(goToMainController);

                        } else {

                            Toast.makeText(LogIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                //deprecated in API 26
                                v.vibrate(500);
                            }


                        }

                        // ...
                    }
                });
        return isSuccessful;

    }


}



