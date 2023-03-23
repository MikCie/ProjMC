package com.example.projpoprmc;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String mCustomToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

    }

    public void updateUI(String UID){
        if(UID!=null) {
            Intent i = new Intent(this, PostLogin.class);
            i.putExtra("UID", UID);
            startActivity(i);
        }
        else{
            Toast.makeText(this, "Błędny login lub hasło", Toast.LENGTH_LONG).show();
        }
    }

    public void zalogujClick(View view) {

        EditText email = (EditText) this.findViewById(R.id.Emailbox);
        EditText pass = (EditText) this.findViewById(R.id.PassBox);
        String EmCheck = email.getText().toString().trim();
        String PassCheck = pass.getText().toString().trim();


        if(EmCheck.isEmpty() || PassCheck.isEmpty()){
            Toast.makeText(this, "Wprowadź dane aby się zalogować.", Toast.LENGTH_LONG).show();
        }else {
            mAuth.signInWithEmailAndPassword(EmCheck, PassCheck)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                String UID =currentUser.getUid();
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(UID);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Błędne dane logowania", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    public void mailClear(View view) {
        EditText email = (EditText) this.findViewById(R.id.Emailbox);
        email.selectAll();
        email.setText("");
    }

    public void passClear(View view) {
        EditText pass = (EditText) this.findViewById(R.id.PassBox);
        pass.selectAll();
        pass.setText("");
    }
}