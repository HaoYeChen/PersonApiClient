package com.example.personapiclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PersonService {
    //read
    @GET("Person")      //URL/person
    Call<List<Person>> getAllPerson();

    //read
    @GET("Person/{id}")     //URL/person/id     After person comes id as parameter
    Call<Person> getPersonById(@Path("id")int id);

    //create
    @POST("Person")
    Call<Void> addNewPerson(@Body Person p);

    //update
    @PUT("Person/{id}")
    Call<Void> updatePerson(@Path("id")int id, @Body Person p);

    //delete
    @DELETE("Person/{id}")
    Call<Void> deletePersonById(@Path("id")int id);
}
