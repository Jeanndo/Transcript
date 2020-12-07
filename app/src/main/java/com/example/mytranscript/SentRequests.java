package com.example.mytranscript;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mytranscript.Adapter.SentAdapter;
import com.example.mytranscript.Model.SentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SentRequests extends Fragment {
    RecyclerView recyclerView;
    List<SentModel> requests;
    Context cxt;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        View view =inflater.inflate(R.layout.sent_requests,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        requests = new ArrayList<>();
        getAllSentRequest();
        return view;
    }
    public void getAllSentRequest(){
        ProgressDialog progressDialog=new ProgressDialog(cxt.getApplicationContext());
        progressDialog.setMessage("Loading Data");
        progressDialog.show();

        String url="https://transcript-ur.herokuapp.com/api/v1/request/get-all";
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("tokenPrefs", Context.MODE_PRIVATE);
        String token=sharedPreferences.getString("token","");

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            /**
             * Called when a response is received.
             *
             * @param response
             */
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    System.out.println(response);
                    JSONArray secondArr = response.getJSONArray("data");
                    for (int i=0;i<secondArr.length();i++) {
                        JSONObject requestObject=secondArr.getJSONObject(i);
                        SentModel sentModel=new SentModel(requestObject.getString("paymentSlip").toString(),
                                requestObject.getString("transcriptType").toString());
                        requests.add(sentModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                final SentAdapter sentAdapter = new SentAdapter( getContext(), requests,getActivity());
                recyclerView.setAdapter(sentAdapter);
            }
            },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);


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
        queue.add(jsonObjectRequest);

        };


    }

