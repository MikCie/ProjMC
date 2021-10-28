package com.example.projpoprmc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    boolean serKlik = false;
    int p1Czas=0;
    int p2Czas=0;
    long p1PRoz=0;
    long p2PRoz=0;
    long p1PPra=0;
    long p2PPra=0;
    int p1zala=0;
    int p2zala=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pompownia_check);
        TextView S =(TextView) this.findViewById(R.id.textViewP);
        Button bs=(Button) this.findViewById(R.id.buttonSerwis);
        TextView p1pr =(TextView) this.findViewById(R.id.p1pr);
        TextView p2pr =(TextView) this.findViewById(R.id.p2pr);
        TextView p1pp =(TextView) this.findViewById(R.id.p1pp);
        TextView p2pp =(TextView) this.findViewById(R.id.p2pp);
        TextView p1c =(TextView) this.findViewById(R.id.p1c);
        TextView p2c =(TextView) this.findViewById(R.id.p2c);
        TextView p1i =(TextView) this.findViewById(R.id.P1ile);
        TextView p2i =(TextView) this.findViewById(R.id.p2ile);

        String pompownia = getIntent().getStringExtra("Pompownia");
        boolean serwisant = Boolean.parseBoolean(getIntent().getStringExtra("serwis"));
        Toast.makeText(this, "A teraz tu! "+String.valueOf(serwisant), Toast.LENGTH_LONG).show();
        S.setText("Stacja: "+pompownia);

        DocumentReference docRef = FirebaseFirestore.getInstance().collection("Stacje").document(pompownia);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NotNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        p1Czas=doc.getLong("P1Czas").intValue();
                        p2Czas=doc.getLong("P2Czas").intValue();
                        p1PRoz=doc.getLong("P1Start");
                        p2PRoz=doc.getLong("P2Start");
                        p1PPra=doc.getLong("P1Praca");
                        p2PPra=doc.getLong("P2Praca");
                        p1zala=doc.getLong("P1IloscZalaczen").intValue();
                        p2zala=doc.getLong("P2IloscZalaczen").intValue();
                        p1c.setText(p1Czas+" min");
                        p2c.setText(p2Czas+" min");
                        p1pr.setText(p1PRoz+" A");
                        p2pr.setText(p2PRoz+" A");
                        p1pp.setText(p1PPra+" V");
                        p2pp.setText(p2PPra+" V");
                        p1i.setText(p1zala+"");
                        p2i.setText(p2zala+"");

                    } else {
                        Log.d("Document", "NoData");
                    }
                }
            }
        });

        if(serwisant==true) {
            bs.setVisibility(View.VISIBLE);
        }

        Log.d("Pompownia", "Pompownia: "+pompownia);



    }


    public void serwisPrzejście(View view) {
        EditText p1in =(EditText) this.findViewById(R.id.P1IleNum);
        EditText p2in =(EditText) this.findViewById(R.id.P2ileNum);
        TextView p1i =(TextView) this.findViewById(R.id.P1ile);
        TextView p2i =(TextView) this.findViewById(R.id.p2ile);
        Button bs=(Button) this.findViewById(R.id.buttonSerwis);
        TextView p1pr =(TextView) this.findViewById(R.id.p1pr); //A
        TextView p2pr =(TextView) this.findViewById(R.id.p2pr);
        TextView p1pp =(TextView) this.findViewById(R.id.p1pp); //V
        TextView p2pp =(TextView) this.findViewById(R.id.p2pp);
        TextView p1c =(TextView) this.findViewById(R.id.p1c);
        TextView p2c =(TextView) this.findViewById(R.id.p2c);
        EditText p1prn=(EditText) this.findViewById(R.id.p1prnum);
        EditText p2prn=(EditText) this.findViewById(R.id.p2prnum);
        EditText p1cn=(EditText) this.findViewById(R.id.p1cnum);
        EditText p2cn=(EditText) this.findViewById(R.id.p2cnum);
        EditText p1ppn=(EditText) this.findViewById(R.id.p1ppnum);
        EditText p2ppn=(EditText) this.findViewById(R.id.p2ppnum);
        if(serKlik==false){
            serKlik=true;

            int p1ilezal=0;
            int p2ilezal=0;

            bs.setText("Zatwierdź nowe zmienne");
            p1in.setVisibility(View.VISIBLE);
            p2in.setVisibility(View.VISIBLE);
            p1i.setVisibility(View.INVISIBLE);
            p2i.setVisibility(View.INVISIBLE);
            p1pr.setVisibility(View.INVISIBLE);
            p2pr.setVisibility(View.INVISIBLE);
            p1pp.setVisibility(View.INVISIBLE);
            p2pp.setVisibility(View.INVISIBLE);
            p1prn.setVisibility(View.VISIBLE);
            p2prn.setVisibility(View.VISIBLE);
            p1ppn.setVisibility(View.VISIBLE);
            p2ppn.setVisibility(View.VISIBLE);
            p1c.setVisibility(View.INVISIBLE);
            p2c.setVisibility(View.INVISIBLE);
            p1cn.setVisibility(View.VISIBLE);
            p2cn.setVisibility(View.VISIBLE);
            //Zupdate'owany UI
        }else if(serKlik==true){
            serKlik=false;
            bs.setText("Wprowadź nowe zmienne");
            p1in.setVisibility(View.INVISIBLE);
            p2in.setVisibility(View.INVISIBLE);
            p1i.setVisibility(View.VISIBLE);
            p2i.setVisibility(View.VISIBLE);
            p1pr.setVisibility(View.VISIBLE);
            p2pr.setVisibility(View.VISIBLE);
            p1pp.setVisibility(View.VISIBLE);
            p2pp.setVisibility(View.VISIBLE);
            p1prn.setVisibility(View.INVISIBLE);
            p2prn.setVisibility(View.INVISIBLE);
            p1ppn.setVisibility(View.INVISIBLE);
            p2ppn.setVisibility(View.INVISIBLE);
            p1c.setVisibility(View.VISIBLE);
            p2c.setVisibility(View.VISIBLE);
            p1cn.setVisibility(View.INVISIBLE);
            p2cn.setVisibility(View.INVISIBLE);

        }else{
            Toast.makeText(this, "Błąd, zrestartuj aplikacje", Toast.LENGTH_LONG).show();
        }
    }
}