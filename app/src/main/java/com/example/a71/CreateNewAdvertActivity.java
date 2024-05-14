package com.example.a71;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateNewAdvertActivity extends AppCompatActivity {

    private EditText itemNameEditText;
    private EditText itemDescriptionEditText;
    private EditText itemLocationEditText;
    private EditText itemDateEditText;
    private Button saveItemButton;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_advert);

        itemNameEditText = findViewById(R.id.edit_text_item_name);
        itemDescriptionEditText = findViewById(R.id.edit_text_item_description);
        itemLocationEditText = findViewById(R.id.edit_text_item_location);
        itemDateEditText = findViewById(R.id.edit_text_item_date);
        saveItemButton = findViewById(R.id.button_save_item);

        dbHelper = new DatabaseHelper(this);

        saveItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });
    }

    private void saveItem() {
        String itemName = itemNameEditText.getText().toString().trim();
        String itemDescription = itemDescriptionEditText.getText().toString().trim();
        String itemLocation = itemLocationEditText.getText().toString().trim();
        String itemDate = itemDateEditText.getText().toString().trim();

        if (!itemName.isEmpty()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_NAME, itemName);
            values.put(DatabaseHelper.COLUMN_DESCRIPTION, itemDescription);
            values.put(DatabaseHelper.COLUMN_LOCATION, itemLocation);
            values.put(DatabaseHelper.COLUMN_DATE, itemDate);
            long newRowId = db.insert(DatabaseHelper.TABLE_ITEMS, null, values);
            if (newRowId != -1) {
                Toast.makeText(this, "Item saved successfully", Toast.LENGTH_SHORT).show();

                itemNameEditText.getText().clear();
                itemDescriptionEditText.getText().clear();
                itemLocationEditText.getText().clear();
                itemDateEditText.getText().clear();
                finish();
            } else {
                Toast.makeText(this, "Error saving item", Toast.LENGTH_SHORT).show();
            }
            db.close();
        } else {
            Toast.makeText(this, "Please enter item name", Toast.LENGTH_SHORT).show();
        }
    }
}
