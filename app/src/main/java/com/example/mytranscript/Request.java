package com.example.mytranscript;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Request extends Fragment {
    EditText paymentSlip,type;
    Button btnSend;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.request_transcript,container,false);
        paymentSlip=view.findViewById(R.id.payment_slip);
        type=view.findViewById(R.id.editText_transcript_number);
        btnSend=view.findViewById(R.id.Submit);
       btnSend.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               submitRequest(
                       ""+paymentSlip.getText().toString(),
                       ""+type.getText().toString()
               );
           }
       });
       return view;
    }

    private void submitRequest(String paymentSlip, String transcriptType) {
        ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Data");
        progressDialog.show();
        final JSONObject jsonObject = new JSONObject();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());


        String url="https://transcript-ur.herokuapp.com/api/v1/request/add-request";
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("tokenPrefs", Context.MODE_PRIVATE);
        String token=sharedPreferences.getString("token","");


            try {
            jsonObject.put("paymentSlip", paymentSlip);
            jsonObject.put("transcriptType",transcriptType);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        //build response
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    progressDialog.dismiss();


                } catch (Exception e) {
                    Toast.makeText(getContext(), "Big Error" + e.toString() , Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "ERROR: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @NonNull
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", token);

                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
    }

