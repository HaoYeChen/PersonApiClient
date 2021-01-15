package com.example.personapiclient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.DnsResolver;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePersonActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText etName, etAddress, etPhone, etNote;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Spinner spinnerFavorit;
    Button btnCreate, btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_person);

        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etPhone = findViewById(R.id.etPhone);
        etNote = findViewById(R.id.etNote);

        radioGroup = findViewById(R.id.radioGroup);

        spinnerFavorit = findViewById(R.id.spinnerFavorit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bool,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFavorit.setAdapter(adapter);
        spinnerFavorit.setOnItemSelectedListener(this);




        //Cancel button
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreatePersonActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Create new person Button
        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Person p = new Person(
                        etName.getText().toString(),
                        Boolean.parseBoolean(spinnerFavorit.getSelectedItem().toString()),
                        radioGroup.indexOfChild(radioGroup.findViewById( radioGroup.getCheckedRadioButtonId())),
                        etAddress.getText().toString(),
                        etPhone.getText().toString(),
                        etNote.getText().toString()
                );

                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton= findViewById(radioId);

                AlertDialog.Builder alert = new AlertDialog.Builder(CreatePersonActivity.this);
                alert.setTitle("Create This Person?");
                alert.setMessage("Are You Sure You Wanna Create?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        addNewPerson(p);


                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                    }
                });
                alert.show();

            }
        });

    }

    //spinner display when item is selected
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        String text = parent.getItemAtPosition(position).toString();
    }
    //nothing is selected
    public void onNothingSelected(AdapterView<?> parent){    }


    //Checking the radio button which one is clicked
    public void checkButton(View view)
    {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
    }

    //Create new person
    public void addNewPerson(Person p)
    {

        Call<Void> voidCall = ServiceBuilder.getPersonService().addNewPerson(p);
        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Intent intent = new Intent(CreatePersonActivity.this, MainActivity.class);
                startActivity(intent);
//                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}