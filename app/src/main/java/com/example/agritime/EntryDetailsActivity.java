package com.example.agritime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class EntryDetailsActivity extends AppCompatActivity {

    private EditText mName_editText;
    private EditText mtime_editText;
    private Spinner mEntry_categories_spinner;

    private Button mUpdate_btn;
    private Button mDelete_btn;
    private Button mback_btn;

    private String name;
    private String key;
    private String time;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_details);

        key = getIntent().getStringExtra("key");
        name = getIntent().getStringExtra("name");
        category = getIntent().getStringExtra("category");
        time = getIntent().getStringExtra("time");


        mName_editText = (EditText)  findViewById(R.id.name_editText);
        mName_editText.setText(name);
        mtime_editText = (EditText) findViewById(R.id.hours_editText);
        mtime_editText.setText(time);
        mEntry_categories_spinner = (Spinner)findViewById(R.id.category_editText);
        mEntry_categories_spinner.setSelection(getIndex_SpinnerTime(mEntry_categories_spinner, category));
        mUpdate_btn = (Button) findViewById(R.id.update_button);
        mDelete_btn = (Button) findViewById(R.id.delete_button);
        mback_btn= (Button) findViewById(R.id.back_button);

        mUpdate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Entry entry = new Entry();
            entry.setName(mName_editText.getText().toString());
            entry.setCategory(mEntry_categories_spinner.getSelectedItem().toString());
            entry.setTime(mtime_editText.getText().toString());

            new FirebaseDatabaseHelper().updateEntry(key, entry, new FirebaseDatabaseHelper.DataStatus() {
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
                new FirebaseDatabaseHelper().deleteEntry(key, new FirebaseDatabaseHelper.DataStatus() {
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
                        finish(); return;
                    }
                });
            }

        });

        mback_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); return;
            }
        });
    };

    private int getIndex_SpinnerTime(Spinner spinner, String item){
        int index = 0;
        for (int i = 0; i<spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).equals(item)){
                index =i;
                break;

            }
        }
        return index;
    }
}