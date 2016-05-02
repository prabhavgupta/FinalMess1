package com.example.weirdo.myapp1;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Weirdo on 25-04-2016.
 */
public class AdminEnter extends AppCompatActivity {


    Button aebok , logoutb;
    EditText e1, e2, e3;
    String roll, item, price;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminenter);

       // Toast.makeText(getApplication(), "asdsdsdsdsdsdsdsdsds", Toast.LENGTH_LONG).show();
        e1 = (EditText) findViewById(R.id.editText);
        e2 = (EditText) findViewById(R.id.editText1);
        e3 = (EditText) findViewById(R.id.editText2);
        logoutb = (Button) findViewById(R.id.logoutbutton);

        aebok = (Button) findViewById(R.id.adminenter);
        aebok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roll = e1.getText().toString();
                item = e2.getText().toString();
                price = e3.getText().toString();

                Response.Listener<String> listener1 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (!success) {
                                Toast.makeText(getApplication(), "Roll Number Does Not Exist", Toast.LENGTH_LONG).show();

                            } else {
                                Response.Listener<String> listener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {

                                            JSONObject jsonObject = new JSONObject(response);
                                            boolean success = jsonObject.getBoolean("success");

                                            if (success) {
                                                Toast.makeText(getApplication(), "Item Added", Toast.LENGTH_LONG).show();

                                            } else {
                                                Toast.makeText(getApplication(), "Network Failure", Toast.LENGTH_LONG).show();

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };

                                RegisterRequest registerRequest = new RegisterRequest(roll, item, price, listener);
                                RequestQueue queue = Volley.newRequestQueue(getApplication());
                                queue.add(registerRequest);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RollRequest rollRequest = new RollRequest(roll, listener1);
                RequestQueue queue = Volley.newRequestQueue(getApplication());
                queue.add(rollRequest);








            }


        });





        logoutb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String l = new String("unlock");
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");


                            if(success){

                                Toast.makeText(getApplication(), "Unlocked", Toast.LENGTH_LONG).show();

                                Intent i = new Intent(getApplication() , AdminMain.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                                finish();

                            }
                            else{
                                Toast.makeText(getApplication(), "Lock Error", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                Lockrequest lr= new Lockrequest(l, listener);
                RequestQueue queue = Volley.newRequestQueue(getApplication());
                queue.add(lr);
            }
        });


    }

    @Override
    public void onBackPressed() {
        String l = new String("unlock");
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");


                    if(success){

                        Toast.makeText(getApplication(), "Unlocked", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(getApplication() , AdminMain.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();

                    }
                    else{
                        Toast.makeText(getApplication(), "Lock Error", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


        Lockrequest lr= new Lockrequest(l, listener);
        RequestQueue queue = Volley.newRequestQueue(getApplication());
        queue.add(lr);

    }
}


