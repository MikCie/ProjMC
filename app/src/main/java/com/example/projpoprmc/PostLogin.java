package com.example.projpoprmc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PostLogin extends MainActivity {
    int i = 0;
    boolean serwis = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postlogin);
        Spinner lista = (Spinner) this.findViewById(R.id.spinnerPompowni);
        TextView p = (TextView) this.findViewById(R.id.powitanie);
        List<String> spinnerArray =  new ArrayList<String>();

        String UID = "";

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            UID = user.getUid();

            //Pobrania danych odpowiedniego klienta
            DocumentReference docRef = FirebaseFirestore.getInstance().collection("Klienci").document(UID);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NotNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc.exists()) {
                            p.setText("Witaj "+doc.getString("Imie")+"!");
                            serwis=Boolean.valueOf(doc.getBoolean("CzySerwis"));
                            if(serwis){
                                p.setText("Kryształ");

                            }else{
                                List<String> extra = (List<String>) doc.get("Pompownie");
                                int z= extra.size();
                                while(i<z) {
                                    spinnerArray.add(extra.get(i));
                                    i++;
                                }
                                Log.d("doc", String.valueOf(doc.get("Pompownie")));
                            }
                        } else {
                            Log.d("Document", "NoData");
                        }
                    }
                }
            });

            spinnerArray.add("S1");
            spinnerArray.add("S2");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            lista.setAdapter(adapter);
            //lista.setSelection(1);
        }

    }

    public void Przenieś(View view) {
        Spinner lista = (Spinner) this.findViewById(R.id.spinnerPompowni);
        String pomp = lista.getSelectedItem().toString();
        if(pomp.isEmpty()){
            Toast.makeText(this, "Wybierz prawidłową pompownie", Toast.LENGTH_LONG).show();
        }else {

            Log.d("guzikers", "Wartość klku pompowni: " + pomp);
            Intent i = new Intent(this, PompowniaCheck.class);
            i.putExtra("Pompownia", pomp);
            i.putExtra("serwis", String.valueOf(serwis));
            Toast.makeText(this, String.valueOf(serwis), Toast.LENGTH_LONG).show();
            startActivity(i);
        }
    }
}
