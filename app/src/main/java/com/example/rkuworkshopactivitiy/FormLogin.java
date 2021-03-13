package com.example.rkuworkshopactivitiy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FormLogin extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    String valUsername, valPassword;
    Button btnLogin;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //For Database
        dbHelper = new DatabaseHelper(this);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //For FormLogin using Database
                getDatabaseLogin();
            }
        });
    }

    private void getDatabaseLogin() {
        valUsername = edtUsername.getText().toString();
        valPassword = edtPassword.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String whereClause = "username=? and password=?";
        String[] whereArgs = {valUsername, valPassword};

        Cursor cursor = db.query(MyUtil.TBL_USER, null, whereClause, whereArgs, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            //Code for successful login
            loginSuccess();
        } else {
            Toast.makeText(FormLogin.this, "FormLogin Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void openRegistrationForm(View view) {
        startActivity(new Intent(FormLogin.this, FormRegistration.class));
    }

    //Code for successful login
    private void loginSuccess() {
        preferences = getSharedPreferences(MyUtil.prefFile, MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(MyUtil.prefKey_User, valUsername);
        editor.commit();

        Toast.makeText(FormLogin.this, "FormLogin Successful.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(FormLogin.this, Welcome.class));
        finish();
    }
}
