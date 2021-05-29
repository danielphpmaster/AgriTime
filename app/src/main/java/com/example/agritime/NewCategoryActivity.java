package com.example.agritime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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
    }
}