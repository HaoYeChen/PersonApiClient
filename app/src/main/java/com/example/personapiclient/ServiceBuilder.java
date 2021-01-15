package com.example.personapiclient;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceBuilder {
    //URL
    private static final String URL = "http://192.168.56.1:8080/WebApi/api/";

//    private static Retrofit retrofit = new Retrofit.Builder().baseUrl(URL)
//                                        .addConverterFactory(GsonConverterFactory.create())
//                                        .build();
//
//    public static <S> S buildService(Class<S> serviceType) //serviceType er en interface
//    {
//        return retrofit.create(serviceType); // returner en method som kan hente data from webapi
//    }

    private static Retrofit getRetrofit()
    {
        //With help of HttpLoggingInterceptor u can see information like type of request like GET, POST, PUT and DELETE in Run menu
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }
    //Getting PersonService = API calls and returns a method that can fetch data from WebAPI
    public static PersonService getPersonService(){
        PersonService personService = getRetrofit().create(PersonService.class);
        return personService;
    }
}
