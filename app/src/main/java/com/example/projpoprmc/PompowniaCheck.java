package com.example.projpoprmc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PompowniaCheck extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pompownia_check);
        TextView S =(TextView) this.findViewById(R.id.textViewP);
        TextView p1in =(TextView) this.findViewById(R.id.P1IleNum);
        TextView p2in =(TextView) this.findViewById(R.id.P2ileNum);
        TextView p1i =(TextView) this.findViewById(R.id.P1ile);
        TextView p2i =(TextView) this.findViewById(R.id.p2ile);

        String pompownia = getIntent().getStringExtra("Pompownia");
        boolean serwisant = Boolean.parseBoolean(getIntent().getStringExtra("serwis"));
        Toast.makeText(this, "A teraz tu! "+String.valueOf(serwisant), Toast.LENGTH_LONG).show();
        S.setText(pompownia);
        if(serwisant==true) {
            p1in.setVisibility(View.VISIBLE);
            p2in.setVisibility(View.VISIBLE);
            p1i.setVisibility(View.INVISIBLE);
            p2i.setVisibility(View.INVISIBLE);
        }

        Log.d("Pompownia", "Pompownia: "+pompownia);



    }
}