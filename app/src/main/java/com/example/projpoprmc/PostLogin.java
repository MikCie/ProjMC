package com.example.projpoprmc;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.List;

public class PostLogin extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postlogin);

        List<String> spinnerArray =  new ArrayList<String>();

        Spinner lista = (Spinner) this.findViewById(R.id.spinnerPompowni);
        String UID = "";
        String pompGetter="S";

        TextView p = (TextView) this.findViewById(R.id.powitanie);

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
                            Boolean Serwisant=Boolean.valueOf(doc.getBoolean("CzySerwis"));

                            /*spinnerArray.add(doc.getString("Pompownie"));
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            lista.setAdapter(adapter);*/
                            if(Serwisant){
                                p.setText("Kokos");
                            }
                        } else {
                            Log.d("Document", "NoData");
                        }
                    }
                }
            });
        }
    }

}
