package com.project.intership.intershiptask;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * Created by ahmedabouelfadle on 15/06/16.
 */
public class RegisterationActivity extends AppCompatActivity {

    Button confirm;
    EditText username, password, confPassword, email,phone;


    String REGISTER_URL="http://socyle.com/intership/index.php/register";
    String USERNAME_KEY="username";
    String EMAIL_KEY="email";
    String PHONNE_KEY="phone";
    String PASSWORD_KEY="password";
    String HEAD="user";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeration);
        init();



        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (username.getText().toString().equals("")||password.getText().toString().equals("")||
                        confPassword.getText().toString().equals("")||email.getText().toString().equals("")||
                        phone.getText().toString().equals(""))
                {
                    Toast.makeText(RegisterationActivity.this, "Please complete your DATA", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    userRegisteration();

                }

            }
        });
    }


    public void init()
    {

        username = (EditText) findViewById(R.id.username_et);
        password = (EditText) findViewById(R.id.pass_et);
        confPassword = (EditText) findViewById(R.id.editText5);
        email = (EditText) findViewById(R.id.email_et);
        phone = (EditText) findViewById(R.id.phone_et);

        confirm = (Button) findViewById(R.id.submit);
    }


    public void userRegisteration()
    {
        final String name = username.getText().toString().trim();
        final String pass=password.getText().toString().trim();
        final String mail=email.getText().toString().trim();
        final String phoneNo=phone.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(RegisterationActivity.this, "Successfully LOGIN"+response, Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject read=new JSONObject(response);
                            int result=read.getInt("result");
                            Toast.makeText(RegisterationActivity.this, "Successfully LOGIN   "+name, Toast.LENGTH_SHORT).show();
                            if (result==0)
                            {

                                Toast.makeText(RegisterationActivity.this, "your email is already exist please try anew one", Toast.LENGTH_SHORT).show();
                            }
                            else if (result==1)
                            {
                                Toast.makeText(RegisterationActivity.this, "Succesfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterationActivity.this,HomeActivity.class));




                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterationActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Toast.makeText(RegisterationActivity.this, "5arrra", Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(USERNAME_KEY,name);
                params.put(PASSWORD_KEY,pass);
                 params.put(PHONNE_KEY, phoneNo);
                params.put(EMAIL_KEY, mail);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
