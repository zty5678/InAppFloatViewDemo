package com.zty5678.inappfloatview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class CustomerServiceActivity extends AppCompatActivity {
    public static void start(Context context) {
        Intent starter = new Intent(context, CustomerServiceActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);
    }
}