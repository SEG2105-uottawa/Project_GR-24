package com.example.servicenovigrad.ui.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.servicenovigrad.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SearchByHours extends SearchPage {

    Spinner daySpinner;
    EditText iOpen, iClose;
    String open, close;
    ArrayAdapter adapter;
    String selectedDay;
    Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_hours);

        //Get views
        daySpinner = findViewById(R.id.search_by_hours_spinner);
        iOpen = findViewById(R.id.search_by_hours_open);
        iClose = findViewById(R.id.search_by_hours_close);
        searchBtn = findViewById(R.id.search_by_hours_search);

        //Fill spinner
        adapter = ArrayAdapter.createFromResource(this, R.array.days, R.layout.support_simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDay = Integer.toString(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Search listener
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchByHours();
            }
        });
    }

    private void searchByHours(){
        open = iOpen.getText().toString();
        close = iClose.getText().toString();

        final boolean emptyOpen = TextUtils.isEmpty(open);
        final boolean emptyClose = TextUtils.isEmpty(close);

        boolean invalid = false;
        if (emptyOpen && emptyClose){
            iOpen.setError("Fill at least one field");
            iClose.setError("Fill at least one field");
            invalid = true;
        }
        else{
            iOpen.setError(null);
            iClose.setError(null);
            if (!emptyOpen && !(open.matches("(((0[1-9])|(1[0-2])):([0-5])(0|5)\\s(A|P|a|p)(M|m))"))){
                iOpen.setError("Example Format: 09:30 AM");
                invalid = true;
            }
            if (! emptyClose && !(close.matches("(((0[1-9])|(1[0-2])):([0-5])(0|5)\\s(A|P|a|p)(M|m))"))){
                iClose.setError("Example Format: 06:45 PM");
                invalid = true;
            }
        }

        if (invalid) return;

        allUsersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                searchResults = new HashMap<>();

                for (DataSnapshot user: snapshot.getChildren()){
                    if (isBranchEmployee(user)){

                        //Check open time
                        if (!emptyOpen && user.child("hours").child(selectedDay).child("openTime").exists()){
                            String bOpen = user.child("hours").child(selectedDay).child("openTime").getValue().toString();
                            if (open.equals(bOpen)){
                                searchResults.put(getBranchEmployee(user), user.getRef());
                                continue;
                            }
                        }

                        //Check close time
                        if (!emptyClose && user.child("hours").child(selectedDay).child("closeTime").exists()) {
                            String bClose = user.child("hours").child(selectedDay).child("closeTime").getValue().toString();
                            if (close.equals(bClose)) {
                                searchResults.put(getBranchEmployee(user), user.getRef());
                            }
                        }
                    }
                }
                if(searchResults.size() == 0){
                    Toast.makeText(getApplicationContext(), "No results found", Toast.LENGTH_SHORT).show();
                }
                else startActivity(new Intent(SearchByHours.this, SearchResults.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}