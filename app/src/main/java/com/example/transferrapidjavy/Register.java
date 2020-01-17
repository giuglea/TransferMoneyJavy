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
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class Register extends AppCompatActivity {

    TextView emailText;
    TextView passwordText;
    TextView passwordCheckText;

    Button registerButton;
    boolean isSucessful ;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        emailText = (TextView)findViewById(R.id.emailTextR);
        createPasswordText();
        createPasswordCheck();

        mAuth = FirebaseAuth.getInstance();
        createRegisterButton();

    }

    private void createPasswordText(){
        passwordText = (TextView)findViewById(R.id.passwordTextR);
        passwordText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    if(passwordText.getText().toString().length()<7){
                        Toast.makeText(Register.this, "Password must be at least 6 characters long.",
                                Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void createPasswordCheck(){
        passwordCheckText = (TextView)findViewById(R.id.checkPasswordR);
        passwordCheckText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId == EditorInfo.IME_ACTION_DONE){
                    if(passwordCheckText.getText().toString() != passwordText.getText().toString()){
                        Toast.makeText(Register.this, "Passwords don't match.",
                                Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
    }


    private void createRegisterButton(){
        registerButton = (Button)findViewById(R.id.registerButtonR);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();

            }
        });
    }

    private void register() {
        registerFirebase();
        if(isSucessful){
           // Intent goBack = new Intent(this,LogIn.class);
           // goBack.putExtra("Done!");
           // setResult(RESULT_OK,goBack);
            finish();
        }
    }


    private void registerFirebase(){
        isSucessful = false;
        mAuth.createUserWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Register.this, "Succes!\nPlease Log In!",
                                    Toast.LENGTH_SHORT).show();
                            isSucessful = true;

                        } else {

                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {
                                //deprecated in API 26
                                v.vibrate(500);
                            }
                            isSucessful = false;

                        }

                        // ...
                    }
                });
    }
}
