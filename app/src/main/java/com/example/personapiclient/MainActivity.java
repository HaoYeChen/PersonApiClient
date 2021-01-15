package com.example.personapiclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PersonAdapter.ClickedItem{

    RecyclerView recyclerView;
    PersonAdapter personAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recylerview);

        //diver between rows
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        personAdapter = new PersonAdapter(this);

        //calling getAllPerson method
        getAllPersons();


        //Fab button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreatePersonActivity.class);
                startActivity(intent);
            }
        });







//        PersonService service = ServiceBuilder.buildService(PersonService.class); //laver object af en class som impmenter til interface
//        Call<ArrayList<Person>> request = service.getAllPerson();
//        request.enqueue(new Callback<ArrayList<Person>>()
//        {
//            @Override
//            public void onResponse(Call<ArrayList<Person>> call, Response<ArrayList<Person>> response)
//            {
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Person>> call, Throwable t)
//            {
//
//            }
//        });


        //        getPersonById
//        PersonService service = ServiceBuilder.buildService(PersonService.class); //laver object af en class som impmenter til interface
//        Call<Person> request = service.getPersonById(1001);
//        request.enqueue(new Callback<Person>()
//        {
//            @Override
//            public void onResponse(Call<Person> call, Response<Person> response)
//            {
//                Person p = response.body();     //body returner det der er sendt som er person
//                txtMessage.setText(p.getName());
//            }
//
//            @Override
//            public void onFailure(Call<Person> call, Throwable t)
//            {
//                txtMessage.setText(t.getMessage());
//            }
//        });

    }


    //getAllPersons method
    public void getAllPersons()
    {
        Call<List<Person>> personList = ServiceBuilder.getPersonService().getAllPerson();
        personList.enqueue((new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (response.isSuccessful())
                {
                    List<Person> p = response.body();
                    personAdapter.setData(p);
                    recyclerView.setAdapter(personAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                Log.e("Fail",t.getLocalizedMessage());
            }
        }));
    }

    //If clicked on person load the persons details
    public void ClickedPerson(Person person)
    {
        startActivity(new Intent(this, PersonDetailsActivity.class).putExtra("data",person));
    }
}