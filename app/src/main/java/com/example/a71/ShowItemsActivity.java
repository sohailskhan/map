package com.example.a71;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowItemsActivity extends AppCompatActivity {

    private LinearLayout itemsLayout;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items);

        itemsLayout = findViewById(R.id.layout_items);
        dbHelper = new DatabaseHelper(this);

        displayItems();
    }

    private void displayItems() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_DESCRIPTION,
                DatabaseHelper.COLUMN_LOCATION,
                DatabaseHelper.COLUMN_DATE
        };

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_ITEMS,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            final int itemId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPTION));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LOCATION));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));

            // Textviews for each advert
            TextView itemTextView = new TextView(this);
            itemTextView.setText("FOUND -\n" +
                    "NAME: " + name + "\n" +
                    "DESCRIPTION: " + description + "\n" +
                    "LOCATION: " + location + "\n" +
                    "DATE: " + date + "\n");
            itemTextView.setTextSize(18);
            itemTextView.setPadding(16, 16, 16, 16);


            Button deleteButton = new Button(this);
            deleteButton.setText("Delete");
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    deleteItem(itemId);

                    itemsLayout.removeView((View) v.getParent());
                }
            });

            // Add item detail using TextView and delete button to a remove each advert
            LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setOrientation(LinearLayout.VERTICAL);
            itemLayout.addView(itemTextView);
            itemLayout.addView(deleteButton);


            itemsLayout.addView(itemLayout);
        }
        cursor.close();
    }

    private void deleteItem(int itemId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = DatabaseHelper.COLUMN_ID + "?";
        String[] selectionArgs = {String.valueOf(itemId)};
        db.delete(DatabaseHelper.TABLE_ITEMS, selection, selectionArgs);
        db.close();
    }
}