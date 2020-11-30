package com.example.servicenovigrad.ui.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.servicenovigrad.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class SearchByAddress extends SearchPage {

    String streetNumber, streetName, postalCode;
    EditText iStreetNumber, iStreetName, iPostalCode;
    Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_address);

        //Get views
        iStreetNumber = findViewById(R.id.search_by_address_streetNumber);
        iStreetName = findViewById(R.id.search_by_address_streetName);
        iPostalCode = findViewById(R.id.search_by_address_postalCode);
        searchBtn = findViewById(R.id.search_by_address_search);

        //Listeners
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByAddress();
            }
        });
    }

    private void searchByAddress(){
        streetNumber = iStreetNumber.getText().toString().trim();
        streetName = iStreetName.getText().toString().trim();
        postalCode = iPostalCode.getText().toString().trim().toUpperCase();

        final boolean emptyNumber = TextUtils.isEmpty(streetNumber);
        final boolean emptyName = TextUtils.isEmpty(streetName);
        final boolean emptyCode = TextUtils.isEmpty(postalCode);

        //Form validation
        boolean invalid = false;
        if(emptyNumber && emptyName && emptyCode){
            iStreetNumber.setError("Fill at least one field");
            iStreetName.setError("Fill at least one field");
            iPostalCode.setError("Fill at least one field");
            invalid = true;
        }
        else{
            iStreetNumber.setError(null);
            iStreetName.setError(null);
            iPostalCode.setError(null);
            if (!emptyNumber && !streetNumber.matches("^\\d+$")){
                iStreetNumber.setError("Street number must be numeric");
                invalid = true;
            }
            if (!emptyCode && !postalCode.matches("^[A-Z|a-z]\\d[A-Z|a-z]\\s\\d[A-Z|a-z]\\d$")){
                iPostalCode.setError("Invalid postal code format: LNL NLN");
                invalid = true;
            }
        }

        if(invalid) return;

        allUsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                searchResults = new HashMap<>();

                for (DataSnapshot user : snapshot.getChildren()){
                    if (isBranchEmployee(user)){

                        //Check if street number CONTAINS search number
                        if (!emptyNumber && user.child("streetNumber").exists()){
                            String bStreetNumber = user.child("streetNumber").getValue().toString();
                            if (bStreetNumber.contains(streetNumber)){
                                searchResults.put(getBranchEmployee(user), user.getRef());
                                continue;
                            }
                        }

                        //Check if street name CONTAINS search name
                        if (!emptyName && user.child("streetName").exists()){
                            String bStreetName = user.child("streetName").getValue().toString();
                            if (bStreetName.contains(streetName)){
                                searchResults.put(getBranchEmployee(user), user.getRef());
                                continue;
                            }
                        }

                        //Check if postal code MATCHES search code
                        if (!emptyCode && user.child("postalCode").exists()){
                            String bPostalCode = user.child("postalCode").getValue().toString();
                            if (bPostalCode.equals(postalCode)){
                                searchResults.put(getBranchEmployee(user), user.getRef());
                            }
                        }
                    }
                }
                if(searchResults.size() == 0){
                    Toast.makeText(getApplicationContext(), "No results found", Toast.LENGTH_SHORT).show();
                }
                else startActivity(new Intent(SearchByAddress.this, SearchResults.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error retrieving data...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}