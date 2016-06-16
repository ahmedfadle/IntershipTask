package com.project.intership.intershiptask;
import android.os.Bundle;;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ahmedabouelfadle on 16/06/16.
 */
public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                new FragmentA()).commit();
    }
}
