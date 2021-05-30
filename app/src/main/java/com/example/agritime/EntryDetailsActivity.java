package com.example.agritime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class EntryDetailsActivity extends AppCompatActivity {

    private EditText mName_editText;
    private EditText mtime_editText;
    private Spinner mEntry_categories_spinner;

    private Button mUpdate_btn;
    private Button mDelete_btn;
    private Button mBack_btn;

    private String name;
    private String key;
    private String time;
    private String category;

    public interface Callback {
        void onCallback(String[] array);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_details);

        key = getIntent().getStringExtra("key");
        name = getIntent().getStringExtra("name");
        category = getIntent().getStringExtra("category");
        time = getIntent().getStringExtra("time");


        mName_editText = (EditText) findViewById(R.id.name_editText);
        mName_editText.setText(name);
        mtime_editText = (EditText) findViewById(R.id.hours_editText);
        mtime_editText.setText(time);
        mEntry_categories_spinner = (Spinner) findViewById(R.id.category_details_spinner);
        mUpdate_btn = (Button) findViewById(R.id.update_button);
        mDelete_btn = (Button) findViewById(R.id.delete_button);
        mBack_btn = (Button) findViewById(R.id.back_button);

        readCategories(new EntryDetailsActivity.Callback() {
            @Override
            public void onCallback(String[] array) {
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(EntryDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, array);
                mEntry_categories_spinner.setAdapter(spinnerArrayAdapter);
                mEntry_categories_spinner.setSelection(getIndex_SpinnerTime(mEntry_categories_spinner, category));
            }
        });

        mUpdate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entry entry = new Entry();
                entry.setName(mName_editText.getText().toString());
                entry.setCategory(mEntry_categories_spinner.getSelectedItem().toString());
                entry.setTime(mtime_editText.getText().toString());

                new EntryFirebaseDatabaseHelper().updateEntry(key, entry, new EntryFirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Entry> entries, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(EntryDetailsActivity.this, "Updated successfully.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });

        mDelete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EntryFirebaseDatabaseHelper().deleteEntry(key, new EntryFirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Entry> entries, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {
                        Toast.makeText(EntryDetailsActivity.this, "Deleted successfully.", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                });
            }

        });

        mBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });
    }


    // source: https://stackoverflow.com/questions/14773264/convert-listobject-to-string-in-java
    public void readCategories(EntryDetailsActivity.Callback callback) {
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

    private int getIndex_SpinnerTime(Spinner spinner, String item) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(item)) {
                index = i;
                break;

            }
        }
        return index;
    }
}