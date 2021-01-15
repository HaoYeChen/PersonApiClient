package com.example.personapiclient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    TextView txtId;
    Button btnUpdate, btnDelete, btnBack;
    Person p;
    EditText etName, etAddress, etPhone, etNote;
    RadioGroup radioGroupFromPersonDetails;
    RadioButton radioButtonFromPersonDetails;
    Spinner spinnerFavoritFromPersonDetails;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);

        txtId = findViewById(R.id.txtIdPD);
        etName = findViewById(R.id.etNamePD);
        etAddress = findViewById(R.id.etAddressPD);
        etPhone = findViewById(R.id.etPhonePD);
        etNote = findViewById(R.id.etNotePD);
        radioGroupFromPersonDetails = findViewById(R.id.radioGroupFromPersonDetails);
        //radioButtonFromPersonDetails= findViewById(R.id.radio);

        spinnerFavoritFromPersonDetails = findViewById(R.id.spinnerFavoritFromPersonDetails);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bool,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFavoritFromPersonDetails.setAdapter(adapter);
        spinnerFavoritFromPersonDetails.setOnItemSelectedListener(this);




        //Loading the person details
        Intent intent = getIntent();
        if (intent.getExtras() !=null){
            p = (Person) intent.getSerializableExtra("data");

            int personId = p.getId();
            String personName = p.getName();
            Boolean personFavorit = p.isFavorit();
            int personHairColor = p.getHairColor();
            String personAddress = p.getAddress();
            String personPhone = p.getPhone();
            String personNote = p.getNote();

            txtId.setText(String.valueOf(personId));           //return the string representation of the int argument
            etName.setText(personName);


//            ArrayAdapter<String> mfavouriteSpinnerAdapter;
//            String[] favoriteTxt = {"False","True" };
//            Spinner spin = spinnerFavoritFromPersonDetails;
//            mfavouriteSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, favoriteTxt);
//            mfavouriteSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spin.setAdapter(mfavouriteSpinnerAdapter);
//            spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });

//            spinnerFavoritFromPersonDetails.setSelection(String.valueOf(String.valueOf(personFavorit))); //return the string representation of the boolean argument
//            radioButtonFromPersonDetails.setText(personHairColor+"");          //String.valueOf and +"" is the same as above

            spinnerFavoritFromPersonDetails.setSelection(p.isFavorit()?1:0);
            radioGroupFromPersonDetails.check(radioGroupFromPersonDetails.getChildAt(personHairColor).getId());
            etAddress.setText(personAddress);
            etPhone.setText(personPhone);
            etNote.setText(personNote);
        }

        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Person p = new Person(
                        Integer.parseInt(txtId.getText().toString()),
                        etName.getText().toString(),
                        Boolean.parseBoolean(spinnerFavoritFromPersonDetails.getSelectedItem().toString()),
                        radioGroupFromPersonDetails.indexOfChild(radioGroupFromPersonDetails.findViewById( radioGroupFromPersonDetails.getCheckedRadioButtonId())),
                        etAddress.getText().toString(),
                        etPhone.getText().toString(),
                        etNote.getText().toString()
                );

                int radioId = radioGroupFromPersonDetails.getCheckedRadioButtonId();
                radioButtonFromPersonDetails= findViewById(radioId);

                AlertDialog.Builder alert = new AlertDialog.Builder(PersonDetailsActivity.this);
                alert.setTitle("Update This Person?");
                alert.setMessage("Are You Sure You Wanna Update?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        updatePerson(p);

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


        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(PersonDetailsActivity.this);
                alert.setTitle("Delete This Person?");
                alert.setMessage("Are You Sure You Wanna Delete?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton)
                    {
                        deletePersonById();
                        Intent intent = new Intent(PersonDetailsActivity.this, MainActivity.class);
                        startActivity(intent);
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

        //Back button
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonDetailsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    //Update person
    public void updatePerson(Person p)
    {
//        p = new Person(
//        etName.getText().toString(),
//        Boolean.parseBoolean(spinnerFavoritFromPersonDetails.getSelectedItem().toString()),
//        Integer.parseInt(radioButtonFromPersonDetails.getText().toString()),
//        etAddress.getText().toString(),
//        etPhone.getText().toString(),
//        etNote.getText().toString()
//        );

//        p = new Person(
//                etName.getText().toString(),
//                Boolean.parseBoolean(spinnerFavoritFromPersonDetails.getSelectedItem().toString()),
//                radioGroupFromPersonDetails.indexOfChild(radioGroupFromPersonDetails.findViewById( radioGroupFromPersonDetails.getCheckedRadioButtonId())),
//                etAddress.getText().toString(),
//                etPhone.getText().toString(),
//                etNote.getText().toString()
//        );


        Call<Void> voidCalll = ServiceBuilder.getPersonService().updatePerson(p.getId(),p);
        //Call<Void> voidCalll = ServiceBuilder.getPersonService().updatePerson(p);
        voidCalll.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
//                Intent intent = new Intent(PersonDetailsActivity.this, MainActivity.class);
//                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }

    //spinner
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        String text = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> parent){    }

    //Checking the radio button which one is clicked
    public void checkButton(View view)
    {
        int radioId = radioGroupFromPersonDetails.getCheckedRadioButtonId();
        radioButtonFromPersonDetails = findViewById(radioId);
    }

    //Delete Person with Id
    public void deletePersonById()
    {

        Call<Void> voidCall = ServiceBuilder.getPersonService().deletePersonById(Integer.parseInt(txtId.getText().toString()));

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }
}