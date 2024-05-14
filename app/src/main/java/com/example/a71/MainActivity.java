package com.example.a71;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button newAdvertButton;
    private Button showItemsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newAdvertButton = findViewById(R.id.button_new_advert);
        showItemsButton = findViewById(R.id.button_show_items);

        newAdvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CreateNewAdvertActivity
                startActivity(new Intent(MainActivity.this, CreateNewAdvertActivity.class));
            }
        });

        showItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, ShowItemsActivity.class));
            }
        });
    }
}
