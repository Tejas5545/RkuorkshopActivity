package com.example.rkuworkshopactivitiy;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FormRegistration extends AppCompatActivity {

    // FirebaseAuth auth;
    EditText edtFirstname, edtLastname, edtRUsername, edtRPassword;
    Button btnRegistration;
    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        dbHelper = new DatabaseHelper(this);

        edtFirstname = findViewById(R.id.edtFirstname);
        edtLastname = findViewById(R.id.edtLastname);
        edtRUsername = findViewById(R.id.edtRUsername);
        edtRPassword = findViewById(R.id.edtRPassword);
        btnRegistration = findViewById(R.id.btnRegistration);

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Method call for User FormRegistration
                getDatabaseRegister();
            }
        });
    }

    private void getDatabaseRegister() {
        String valFirstname = edtFirstname.getText().toString();
        String valLastname = edtLastname.getText().toString();
        String valUsername = edtRUsername.getText().toString();
        String valPassword = edtRPassword.getText().toString();

        ContentValues values = new ContentValues();
        values.put(MyUtil.USER_COL_FNAME, valFirstname);
        values.put(MyUtil.USER_COL_LNAME, valLastname);
        values.put(MyUtil.USER_COL_USERNAME, valUsername);
        values.put(MyUtil.USER_COL_PASSWORD, valPassword);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = db.insert(MyUtil.TBL_USER, null, values);

        //result will be the row ID of the newly inserted row, or -1 if an error occurred
        if (result != -1) {
            Toast.makeText(FormRegistration.this, "User registration done.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(FormRegistration.this, "User registration failed.", Toast.LENGTH_LONG).show();
        }

        startActivity(new Intent(FormRegistration.this, FormLogin.class));
        finish();
    }
}
