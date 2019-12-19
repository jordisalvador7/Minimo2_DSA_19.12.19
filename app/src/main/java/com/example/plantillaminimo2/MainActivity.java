
package com.example.plantillaminimo2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.plantillaminimo2.models.Element;
import com.example.plantillaminimo2.models.Museums;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private API myMuseoApi;
    TextView textViewNombre;
    ImageView  imageViewMuseo;
    public List<Element> data;

    public Adapter recycler ;
    public RecyclerView recyclerView;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recycler = new Adapter(this);
        recyclerView.setAdapter((RecyclerView.Adapter) recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));





        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Cargando museos...");
        progressDialog.setMessage("Espere a que el servidor responda");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();


        myMuseoApi = API.retrofit.create(API.class);

        getData();


    }









    private void getData(){

        Call<Museums> elementCall = myMuseoApi.getMuseums(1,11);

        elementCall.enqueue(new Callback<Museums>() {
            @Override
            public void onResponse(Call<Museums> call, Response<Museums> response) {
                if(response.isSuccessful()){
                    Museums museo = response.body();

                    data = museo.getElements();
                    recycler.rellenarLista(data);
                    progressDialog.hide();

                }else{
                    progressDialog.hide();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                    alertDialogBuilder
                            .setTitle("Error")
                            .setMessage(response.message())
                            .setCancelable(false);
                            //.setPositiveButton("OK");

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();


                }

            }

            @Override
            public void onFailure(Call<Museums> call, Throwable t) {

                progressDialog.hide();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                alertDialogBuilder
                        .setTitle("Error")
                        .setMessage(t.getMessage())
                        .setCancelable(false);
                        //.setPositiveButton("OK", (dialog, which) -> finish());

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });


    }
}