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
    double p1PRoz=0;
    double p2PRoz=0;
    double p1PPra=0;
    double p2PPra=0;
    int p1zala=0;
    int p2zala=0;

    int p1CzasNWar=0;
    int p2CzasNWar=0;
    double p1PRozNWar=0;
    double p2PRozNWar=0;
    double p1PPraNWar=0;
    double p2PPraNWar=0;
    int p1zalaNWar=0;
    int p2zalaNWar=0;
    String pompownia="";

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

        pompownia = getIntent().getStringExtra("Pompownia");
        boolean serwisant = Boolean.parseBoolean(getIntent().getStringExtra("serwis"));
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
                        p1PRoz= doc.getDouble("P1Start");
                        p2PRoz=doc.getDouble("P2Start");
                        p1PPra=doc.getDouble("P1Praca");
                        p2PPra=doc.getDouble("P2Praca");
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
        }else if(serKlik==true) {
            p1CzasNWar = (int) Double.parseDouble(p1cn.getText().toString());
            p2CzasNWar = (int) Double.parseDouble(p2cn.getText().toString());
            p1PRozNWar = Double.parseDouble(p1prn.getText().toString());
            p2PRozNWar = Double.parseDouble(p2prn.getText().toString());
            p1PPraNWar = Double.parseDouble(p1ppn.getText().toString());
            p2PPraNWar = Double.parseDouble(p2ppn.getText().toString());
            p1zalaNWar = (int) Double.parseDouble(p1in.getText().toString());
            p2zalaNWar = (int) Double.parseDouble(p2in.getText().toString());

            if(p1Czas<=p1CzasNWar)
                FirebaseFirestore.getInstance().collection("Stacje").document(pompownia).update("P1Czas", p1CzasNWar);
            if(p2Czas<=p2CzasNWar)
                FirebaseFirestore.getInstance().collection("Stacje").document(pompownia).update("P2Czas", p2CzasNWar);
            FirebaseFirestore.getInstance().collection("Stacje").document(pompownia).update("P1Start", p1PRozNWar);
            FirebaseFirestore.getInstance().collection("Stacje").document(pompownia).update("P2Start", p2PRozNWar);
            FirebaseFirestore.getInstance().collection("Stacje").document(pompownia).update("P1Praca", p1PPraNWar);
            FirebaseFirestore.getInstance().collection("Stacje").document(pompownia).update("P2Praca", p2PPraNWar);
            if(p1zala<=p1zalaNWar)
                FirebaseFirestore.getInstance().collection("Stacje").document(pompownia).update("P1IloscZalaczen", p1zalaNWar);
            if(p2zala<=p2zalaNWar)
                FirebaseFirestore.getInstance().collection("Stacje").document(pompownia).update("P2IloscZalaczen", p2zalaNWar);
            FirebaseFirestore.getInstance().collection("Stacje").document(pompownia).update("CzyWymaga", false);



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

            DocumentReference docRef = FirebaseFirestore.getInstance().collection("Stacje").document(pompownia);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NotNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc.exists()) {
                            p1Czas=doc.getLong("P1Czas").intValue();
                            p2Czas=doc.getLong("P2Czas").intValue();
                            p1PRoz= doc.getDouble("P1Start");
                            p2PRoz=doc.getDouble("P2Start");
                            p1PPra=doc.getDouble("P1Praca");
                            p2PPra=doc.getDouble("P2Praca");
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
        }else{
            Toast.makeText(this, "Błąd, zrestartuj aplikacje", Toast.LENGTH_LONG).show();
        }
    }
}