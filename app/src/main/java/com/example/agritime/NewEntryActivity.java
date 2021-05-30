package com.example.agritime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewEntryActivity extends AppCompatActivity {

    private EditText mName_editText;
    private EditText mTime_editText;
    private Spinner mEntry_categories_spinner;
    private Button mNewCategory_button;
    private Button mUpload_button;
    private Button mBack_button;

    public interface Callback {
        void onCallback(String[] array);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        mName_editText = (EditText) findViewById(R.id.name_editText);
        mTime_editText = (EditText) findViewById(R.id.hours_editText);
        mEntry_categories_spinner = (Spinner) findViewById(R.id.category_spinner);

        mNewCategory_button = (Button) findViewById(R.id.new_category);
        mUpload_button = (Button) findViewById(R.id.update_button);
        mBack_button = (Button) findViewById(R.id.back_button2);

        readCategories(new Callback() {
            @Override
            public void onCallback(String[] array) {
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(NewEntryActivity.this, android.R.layout.simple_spinner_dropdown_item, array);
                mEntry_categories_spinner.setAdapter(spinnerArrayAdapter);
            }
        });

        mUpload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entry entry = new Entry();
                entry.setName(mName_editText.getText().toString());
                entry.setTime(mTime_editText.getText().toString());
                entry.setCategory(mEntry_categories_spinner.getSelectedItem().toString());
                new EntryFirebaseDatabaseHelper().addEntry(entry, new EntryFirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Entry> entries, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(NewEntryActivity.this, "Added successfully.", Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });

            }
        });

        mBack_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });

        mNewCategory_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewCategoryActivity();
            }
        });
    }

    // source: https://stackoverflow.com/questions/14773264/convert-listobject-to-string-in-java
    public void readCategories(Callback callback) {
        FirebaseDatabase.getInstance().getReference("category").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> categories = new ArrayList<>();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Category category = keyNode.getValue(Category.class);
                    categories.add(category.getName());
                }

                String[] array = new String[categories.size()];
                int index = 0;
                for (Object value : categories) {
                    array[index] = (String) value;
                    index++;
                }

                callback.onCallback(array);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void openNewCategoryActivity() {
        Intent intent = new Intent(this, NewCategoryActivity.class);
        startActivity(intent);
    }
}