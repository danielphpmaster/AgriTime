package com.example.agritime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class NewEntryActivity extends AppCompatActivity {

    private EditText mName_editText;
    private EditText mTime_editText;
    private Spinner mEntry_catgories_spinner;
    private Button mUpload_button;
    private Button mBack_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        mName_editText = (EditText) findViewById(R.id.name_editText);
        mTime_editText = (EditText) findViewById(R.id.hours_editText);
        mEntry_catgories_spinner = (Spinner) findViewById(R.id.category_editText);

        mUpload_button = (Button) findViewById(R.id.update_button);
        mBack_button = (Button) findViewById(R.id.back_button2);

        mUpload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entry entry = new Entry();
                entry.setName(mName_editText.getText().toString());
                entry.setTime(mTime_editText.getText().toString());
                entry.setCategory(mEntry_catgories_spinner.getSelectedItem().toString());
                new FirebaseDatabaseHelper().addEntry(entry, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Entry> entries, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(NewEntryActivity.this, "Added successfully.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });

                mBack_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        return;
                    }
                });

            }
        });

    }


}