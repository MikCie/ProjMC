package com.example.projpoprmc;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.firebase.firestore.auth.User;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PostLogin extends MainActivity {
    int i = 0;
    int z = 0;
    boolean serwis = false;
    boolean cwCheck=false;
    String transfer;
    String przedluzacz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postlogin);
        Spinner lista = (Spinner) this.findViewById(R.id.spinnerPompowni);
        TextView p = (TextView) this.findViewById(R.id.powitanie);
        TextView serwNot = (TextView) this.findViewById(R.id.SerwText);
        List<String> spinnerArray = new ArrayList<String>();
        List<String> holder = new ArrayList<String>();
        List<String> list = new ArrayList<>();


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
                            p.setText("Witaj " + doc.getString("Imie") + "!");
                            serwis = Boolean.valueOf(doc.getBoolean("CzySerwis"));
                            if (serwis) {
                                serwNot.setVisibility(View.VISIBLE);
                                CollectionReference firestore = FirebaseFirestore.getInstance().collection("Stacje");
                                firestore.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                list.add(document.getId());
                                            }
                                            z = list.size();
                                            int i = 0;
                                            while (i < z) {
                                                transfer=list.get(i);
                                                spinnerArray.add(transfer);
                                                holder.add(transfer);
                                                i++;
                                            }
                                            z=holder.size();
                                            serwNot.setText("Żadna pompownia nie została zgłoszona do serwisu.");
                                            final String[] tekstInside = {"Te pompownie zostały zgłoszone do sewisu: "};
                                            i=0;
                                            while(i<holder.size()){
                                                transfer=holder.get(i);
                                                DocumentReference docPomp = FirebaseFirestore.getInstance().collection("Stacje").document(transfer);
                                                docPomp.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NotNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            DocumentSnapshot doc = task.getResult();
                                                            cwCheck =Boolean.valueOf(doc.getBoolean("CzyWymaga"));
                                                            if(cwCheck==true){
                                                                tekstInside[0] += " " + docPomp.getId();
                                                                serwNot.setText(tekstInside[0]);
                                                            }
                                                        }else{
                                                            Log.d("Document", "Brak dokumentu");
                                                        }
                                                    }
                                                });
                                                i++;
                                            }
                                        } else {
                                            Log.d("TAG", "Error getting documents: ", task.getException());
                                        }
                                    }
                                });
                            } else {
                                List<String> extra = (List<String>) doc.get("Pompownie");
                                int z = extra.size();
                                while (i < z) {
                                    spinnerArray.add(extra.get(i));
                                    i++;
                                }
                            }
                        } else {
                            Log.d("Document", "NoData");
                        }
                    }
                }
            });
            if(serwis){
                Log.d("Kryzys", String.valueOf(holder.size()));
            }

            spinnerArray.add("Wybierz stację");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            lista.setAdapter(adapter);
        }
    }

    public void Przenieś(View view) {
        Spinner lista = (Spinner) this.findViewById(R.id.spinnerPompowni);
        String pomp = lista.getSelectedItem().toString();
        if (pomp.equals("Wybierz stację")) {
            Toast.makeText(this, "Wybierz prawidłową pompownie", Toast.LENGTH_LONG).show();
        } else {
            Intent i = new Intent(this, PompowniaCheck.class);
            i.putExtra("Pompownia", pomp);
            i.putExtra("serwis", String.valueOf(serwis));
            startActivity(i);
        }
    }

    public void PassChange(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            new AlertDialog.Builder(this)
                    .setTitle("Zmiana Hasła")
                    .setMessage("Czy na pewno chcesz zmienić hasło?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Zmiana hasła
                            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("Reset hasła: ", "Email wysłany.");
                                            }
                                        }
                                    });
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else {
            Toast.makeText(this, "Żaden użytkownik nie jest zalogowany, hasło nie zostało zmienione", Toast.LENGTH_LONG).show();
        }
    }
}
