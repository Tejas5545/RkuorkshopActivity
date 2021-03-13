package com.example.rkuworkshopactivitiy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Welcome extends AppCompatActivity {
    RecyclerView rcvNotices;
    NotificationAdapter adapter;
    ProgressDialog loading;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
        //loadNotices();
        loadNoticesFromGoogleSheet();
    }

    private void init() {
        rcvNotices = findViewById(R.id.rcvNotices);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcvNotices.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcvNotices.getContext(),LinearLayoutManager.VERTICAL);
        rcvNotices.addItemDecoration(dividerItemDecoration);
    }
    private void loadNoticesFromGoogleSheet(){
        loading = ProgressDialog.show(this,"Fetching","Please wait",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbzJ85nvoPaji40FuTZKHBrX2w-p79WosAXq92icrLbLdKUnoBM6yHByH5uTtGq5pf7Y/exec?action=getItems",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Welcome.this, response, Toast.LENGTH_SHORT).show();
                        ArrayList<Notifications> list = parseItems(response);
                        adapter = new NotificationAdapter(list);
                        rcvNotices.setAdapter(adapter);
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        int timeout = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(timeout,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }

    private ArrayList<Notifications> parseItems(String jsonResponse){
        ArrayList<Notifications> list = new ArrayList<>();
        try{
            JSONObject jobj = new JSONObject(jsonResponse);
            JSONArray jarray = jobj.getJSONArray("items");
            for(int i=0;i<jarray.length();i++){
                JSONObject jo = jarray.getJSONObject(i);
                String nTitle = jo.getString("Notice_Title");
                String nMessage = jo.getString("Notice_Message");
                String nDepartment = jo.getString("Notice_Department");

                HashMap<String,String> item = new HashMap<>();
                item.put("Notice_Title",nTitle);
                item.put("Notice_Message",nMessage);
                item.put("Notice_Department",nDepartment);
                list.add(new Notifications(nTitle,nMessage));
            }
            return list;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuLogout:
                logoutUser();
                break;

            case R.id.mnuAddNotice:
                Intent in = new Intent(Welcome.this,FormNotice.class);
                startActivity(in);


        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        Intent i=new Intent(getApplicationContext(),FormLogin.class);
        SharedPreferences preferences=getSharedPreferences(MyUtil.prefFile,MODE_PRIVATE);
        editor.putString(MyUtil.prefKey_User,null);
        editor.commit();
        startActivity(i);

    }
}
