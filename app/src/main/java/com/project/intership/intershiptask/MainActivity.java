package com.project.intership.intershiptask;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.support.v4.app.ActivityCompat.startActivity;

public class MainActivity extends AppCompatActivity {

    EditText username,password;
    CheckBox checkBox;
    Button loginBtn,register;
    String LOGIN_URL="http://socyle.com/intership/index.php/login";
    String USERNAME_KEY="username";
    String PASSWORD_KEY="password";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();


        //************************************************************************************************************
        //this code help user to remeber his username and password using SharePrefernce Storage


         sharedPreferences=getSharedPreferences("remember",MODE_PRIVATE);
        String cb=sharedPreferences.getString("key","false");



        if (cb.equals("true"))
        {
            checkBox.setChecked(true);
            String name=sharedPreferences.getString("username","ahmed");
            String pass=sharedPreferences.getString("password","ahmed");
            username.setText(name);
            password.setText(pass);

        }else
        {
            checkBox.setChecked(false);

        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cb=sharedPreferences.getString("key","true");
                Toast.makeText(MainActivity.this, "key  "+cb, Toast.LENGTH_SHORT).show();

                if (checkBox.isChecked())
                {
                    checkBox.setChecked(true);
                    editor=sharedPreferences.edit();
                    editor.putString("key","true");
                    editor.putString("username",username.getText().toString());
                    editor.putString("password",password.getText().toString());
                    editor.commit();
                }else
                {
                    checkBox.setChecked(false);
                    editor=sharedPreferences.edit();
                    editor.putString("key","false");
                    editor.remove("username");
                    editor.remove("password");
                    editor.commit();

                }
            }
        });


//****************************************************************************************************************




        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (username.getText().toString().equals("")||password.getText().toString().equals(""))
                {
                    Toast.makeText(MainActivity.this, "Please complete your data", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    userLogin();



                }
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterationActivity.class));
            }
        });
        

    }




    public void init()
    {
        username= (EditText) findViewById(R.id.editText_username);
        password= (EditText) findViewById(R.id.editText_password);
        checkBox= (CheckBox) findViewById(R.id.checkBox);
        loginBtn= (Button) findViewById(R.id.button_login);
        register= (Button) findViewById(R.id.button_re);


    }
    public void userLogin(){
        final String name = username.getText().toString().trim();
        final String pass=password.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       ///
                         Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();

                     try {
                            JSONObject read=new JSONObject(response);
                            int result=read.getInt("result");
                            Toast.makeText(MainActivity.this, "Successfully LOGIN   "+name, Toast.LENGTH_SHORT).show();
                         if (result==0)
                         {

                             Toast.makeText(MainActivity.this, "your email or password is'nt correct", Toast.LENGTH_SHORT).show();
                         }
                         else if (result==1)
                         {
                             Toast.makeText(MainActivity.this, "Succesfully", Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(MainActivity.this,HomeActivity.class));


                         }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Toast.makeText(MainActivity.this, "5arrra", Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(USERNAME_KEY,name);
                params.put(PASSWORD_KEY,pass);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

