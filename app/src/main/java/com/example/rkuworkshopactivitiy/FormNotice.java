package com.example.rkuworkshopactivitiy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class FormNotice extends AppCompatActivity {
    EditText edtNoticeDate, edtTitle, edtMessage, edtDepartment;
    String valNoticeDate, valTitle, valMessage, valDepartment;
    Button btnAddNotice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_notice);

        edtNoticeDate = findViewById(R.id.edtNoticeDate);
        edtTitle = findViewById(R.id.edtTitle);
        edtMessage = findViewById(R.id.edtMessage);
        edtDepartment = findViewById(R.id.edtDepartment);

        btnAddNotice = findViewById(R.id.btnAddNotice);

        btnAddNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valNoticeDate = edtNoticeDate.getText().toString();
                valTitle = edtTitle.getText().toString();
                valMessage = edtMessage.getText().toString();
                valDepartment = edtDepartment.getText().toString();

                saveDataToGoogleSheet();

            }
        });


    }
    private void saveDataToGoogleSheet() {
        final ProgressDialog loading = ProgressDialog.show(this, "Inserting Data", "Please Wait");
        final String ndate = valNoticeDate.trim();
        final String nTitle = valTitle.trim();
        final String nMessage = valMessage.trim();
        final String nDepartment = valDepartment.trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbzJ85nvoPaji40FuTZKHBrX2w-p79WosAXq92icrLbLdKUnoBM6yHByH5uTtGq5pf7Y/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Toast.makeText(FormNotice.this, "Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),Welcome.class));


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("action","addItem");
                params.put("Notice_Date",ndate);
                params.put("Notice_Title",nTitle);
                params.put("Notice_Message",nMessage);
                params.put("Notice_Department",nDepartment);
                return params;

            }
        };
        int timeout = 50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(timeout,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}
