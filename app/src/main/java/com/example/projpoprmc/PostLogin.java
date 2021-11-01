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
                                CollectionReference firestore= FirebaseFirestore.getInstance().collection("Stacje");
                                firestore.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            List<String> list = new ArrayList<>();
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                list.add(document.getId());
                                            }
                                            int z=list.size();
                                            int i=0;
                                            while(i<z){
                                                spinnerArray.add(list.get(i));
                                                Log.d("Lista", "Stacja: " + list.get(i));
                                                i++;
                                            }
                                            Log.d("TAG", list.toString());
                                        } else {
                                            Log.d("TAG", "Error getting documents: ", task.getException());
                                        }
                                    }
                                });

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

            spinnerArray.add("Wybierz stację");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            lista.setAdapter(adapter);
        }

    }

    public void Przenieś(View view) {
        Spinner lista = (Spinner) this.findViewById(R.id.spinnerPompowni);
        String pomp = lista.getSelectedItem().toString();
        if(pomp.equals("Wybierz stację")){
            Toast.makeText(this, "Wybierz prawidłową pompownie", Toast.LENGTH_LONG).show();
        }else {

            Intent i = new Intent(this, PompowniaCheck.class);
            i.putExtra("Pompownia", pomp);
            i.putExtra("serwis", String.valueOf(serwis));
            Toast.makeText(this, String.valueOf(serwis), Toast.LENGTH_LONG).show();
            startActivity(i);
        }
    }
}
