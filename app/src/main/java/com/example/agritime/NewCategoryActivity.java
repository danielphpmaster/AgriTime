package com.example.agritime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class NewCategoryActivity extends AppCompatActivity {

    private EditText mCategory_editText;
    private Button mAdd_button;
    private Button mBack_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        mCategory_editText = (EditText) findViewById(R.id.categoryName_editText);

        mAdd_button = (Button) findViewById(R.id.add_category_button);
        mBack_button = (Button) findViewById(R.id.back_button_category);

        mAdd_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category category = new Category();
                category.setName(mCategory_editText.getText().toString());
                new CategoryFirebaseDatabaseHelper().addCategory(category, new CategoryFirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Category> categories, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(NewCategoryActivity.this, "Added successfully", Toast.LENGTH_LONG).show();
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
    }
}