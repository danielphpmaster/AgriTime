package com.example.agritime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewEntryActivity extends AppCompatActivity {

    private EditText mName_editText;
    private EditText mTime_editText;
    private Spinner mEntry_catgories_spinner;
    private Button mNewCategory_button;
    private Button mUpload_button;
    private Button mBack_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        mName_editText = (EditText) findViewById(R.id.name_editText);
        mTime_editText = (EditText) findViewById(R.id.hours_editText);
        mEntry_catgories_spinner = (Spinner) findViewById(R.id.category_editText);

        mNewCategory_button = (Button) findViewById(R.id.new_category);
        mUpload_button = (Button) findViewById(R.id.update_button);
        mBack_button = (Button) findViewById(R.id.back_button2);

        // fill spinner
        List<String> spinnerArray =  new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEntry_catgories_spinner.setAdapter(adapter);
        new CategoryFirebaseDatabaseHelper().readCategories(new CategoryFirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Category> categories, List<String> keys) {
                for(int i = 0; i < categories.size(); i++) {
                    spinnerArray.add(categories.get(i).getName());
                }
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

        mUpload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entry entry = new Entry();
                entry.setName(mName_editText.getText().toString());
                entry.setTime(mTime_editText.getText().toString());
                entry.setCategory(mEntry_catgories_spinner.getSelectedItem().toString());
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

    public void openNewCategoryActivity() {
        Intent intent = new Intent(this, NewCategoryActivity.class);
        startActivity(intent);
    }
}