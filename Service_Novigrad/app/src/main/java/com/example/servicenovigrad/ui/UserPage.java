package com.example.servicenovigrad.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.servicenovigrad.R;
import com.example.servicenovigrad.data.BranchReview;
import com.example.servicenovigrad.data.Service;
import com.example.servicenovigrad.data.ServiceRequest;
import com.example.servicenovigrad.data.WorkingHours;
import com.example.servicenovigrad.ui.admin.AdminEditAllServices;
import com.example.servicenovigrad.ui.admin.AdminEditService;
import com.example.servicenovigrad.ui.homepages.BranchEmployeeHomePage;
import com.example.servicenovigrad.users.Account;
import com.example.servicenovigrad.users.BranchEmployee;
import com.example.servicenovigrad.users.Customer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class UserPage extends AppCompatActivity {

    protected static Account userObject;
    protected static DatabaseReference userRef;
    protected static FirebaseUser curUser;
    protected static final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
    protected static final DatabaseReference serviceRef = databaseRef.child("services");
    protected static final DatabaseReference allUsersRef = databaseRef.child("users");
    protected static final StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    //Used for displaying in list views
    protected static ArrayList<Service> allServices;
    protected static HashMap<String, Service> allServicesMap;

    protected BranchEmployee branchObject() {
        return (BranchEmployee) userObject;
    }

    protected Customer customerObject() {
        return (Customer) userObject;
    }

    //Auto updates allServices
    protected void linkAllServices() {
        serviceRef.addValueEventListener(new ValueEventListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Clear local data after a change
                allServices = new ArrayList<>();
                allServicesMap = new HashMap<>();

                //Get JSON tree of all services (Firebase sends it as nested HashMaps)
                HashMap<String, Object> servicesByName = (HashMap<String, Object>) snapshot.getValue();

                //Loop through outer HashMap (Service names)
                for (Map.Entry<String, Object> serviceName : servicesByName.entrySet()) {
                    //Convert each value to a HashMap of strings (this is the service object)
                    HashMap<String, Object> serviceData = (HashMap<String, Object>) serviceName.getValue();
                    //Get price, form fields and document types
                    String price;
                    HashMap<String, String> formFields, documentTypes;
                    price = (String) serviceData.get("price");
                    formFields = (HashMap<String, String>) serviceData.get("formFields");
                    documentTypes = (HashMap<String, String>) serviceData.get("documentTypes");

                    //Convert data to objects
                    Service service = new Service(serviceName.getKey(), Double.parseDouble(price));
                    //Loop through form fields (if any)
                    if (formFields != null) {
                        for (Map.Entry<String, String> formField : formFields.entrySet()) {
                            service.addFormField(formField.getKey());
                        }
                    }
                    //Loop through document types (if any)
                    if (documentTypes != null) {
                        for (Map.Entry<String, String> documentType : documentTypes.entrySet()) {
                            service.addDocType(documentType.getKey());
                        }
                    }
                    allServices.add(service);
                    allServicesMap.put(service.getName(), service);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error retrieving data...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected boolean isBranchEmployee(DataSnapshot snapshot){
        return snapshot.child("role").getValue().equals("BRANCH_EMPLOYEE");
    }

    //Returns a branch employee given a DataSnapshot
    @SuppressWarnings("unchecked")
    protected BranchEmployee getBranchEmployee(DataSnapshot snapshot) {
        if (!isBranchEmployee(snapshot)){
            throw new IllegalArgumentException("Make sure the snapshot contains a branch employee");
        }

        BranchEmployee branchEmployee;
        branchEmployee = new BranchEmployee(
                (String) snapshot.child("firstName").getValue(),
                (String) snapshot.child("lastName").getValue(),
                (String) snapshot.child("userName").getValue(),
                (String) snapshot.child("branchName").getValue()
        );

        //Get phone number
        if (snapshot.child("phoneNumber").exists()){
            branchEmployee.setPhoneNumber((String) snapshot.child("phoneNumber").getValue());
        }

        //Get address
        String streetNumber = (String) snapshot.child("streetNumber").getValue();
        String streetName = (String) snapshot.child("streetName").getValue();
        String postalCode = (String) snapshot.child("postalCode").getValue();
        if(streetNumber != null && streetName != null && postalCode != null){
            branchEmployee.setStreetNumber(streetNumber);
            branchEmployee.setStreetName(streetName);
            branchEmployee.setPostalCode(postalCode);
        }

        //Get working hours
        ArrayList<Object> hours = (ArrayList<Object>) snapshot.child("hours").getValue();
        if (hours != null) {
            ArrayList<WorkingHours> workingHours = new ArrayList<>();
            for (int i = 0; i < hours.size(); i++) {
                HashMap<String, String> day = (HashMap<String, String>) hours.get(i);
                String dayOpen = day.get("openTime");
                String dayClose = day.get("closeTime");
                workingHours.add(new WorkingHours(i, dayOpen, dayClose));
            }
            branchEmployee.setHours(workingHours);
        }
        //Initialize hours in the database (for old accounts)
        else snapshot.getRef().child("hours").setValue(branchEmployee.getHours());

        // Get reviews
        HashMap<String, Object> reviewsByDate = (HashMap<String, Object>) snapshot.child("reviews").getValue();
        if (reviewsByDate!=null) {
            for (Map.Entry<String, Object> review: reviewsByDate.entrySet()) {
                String date = review.getKey();
                String comment = (String) snapshot.child("reviews").child(date).child("comment").getValue();
                String rating = (String) snapshot.child("reviews").child(date).child("rating").getValue();

                branchEmployee.addBranchReview(new BranchReview(date, comment, rating));
            }

        }
        else snapshot.getRef().child("reviews").setValue(branchEmployee.getBranchReviews());

        //Get services Offered
        if (snapshot.child("servicesOffered").exists()) {
            //Add all services
            for (DataSnapshot serviceName : snapshot.child("servicesOffered").getChildren()) {
                //If service doesn't exist, delete it
                if (!allServicesMap.containsKey(serviceName.getKey())){
                    snapshot.getRef().child("servicesOffered").child(serviceName.getKey()).removeValue();
                }
                else branchEmployee.addService(allServicesMap.get(serviceName.getKey()));
            }
        }
        //Get service requests
        //TBD - GET DOCUMENT IMAGES!!!
        if (snapshot.child("serviceRequests").exists()) {
            for (DataSnapshot serviceRequest : snapshot.child("serviceRequests").getChildren()) {
                String dateCreated = (String) serviceRequest.child("dateCreated").getValue();
                String requestID = (String) serviceRequest.child("requestID").getValue();
                String serviceName = (String) serviceRequest.child("serviceName").getValue();
                //If service doesn't exist, delete it
                if (!allServicesMap.containsKey(serviceName)) {
                    serviceRequest.getRef().child(requestID).removeValue();
                } else {
                    Service service = allServicesMap.get(serviceName);
                    ServiceRequest request = new ServiceRequest(service, dateCreated, requestID);
                    if (serviceRequest.child("formFields").exists()){
                        for (DataSnapshot formField : serviceRequest.child("formFields").getChildren()){
                            request.fillFormField(formField.getKey(), formField.getValue().toString());
                        }
                    }
                    if (serviceRequest.child("documentTypes").exists()){
                        for (DataSnapshot docType : serviceRequest.child("documentTypes").getChildren()){
                            request.addDocument(docType.getKey(), docType.getValue().toString());
                        }
                    }
                    branchEmployee.addServiceRequest(request);
                }
            }
        }

        return branchEmployee;
    }
}