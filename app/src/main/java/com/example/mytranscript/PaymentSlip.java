package com.example.mytranscript;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class PaymentSlip extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private final int REQUESTSTORAGE =111;
    private final int REQUESFILE =222;
    private String stringPath;
    private Intent iData;

    Button SelectImage,SendBTN;
    ImageView DisplayImage;
    Spinner spinnerSelectTranscript;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_slip);

        SelectImage = findViewById(R.id.ChooseImg);
        SendBTN = findViewById(R.id.Submit);
        DisplayImage =findViewById(R.id.imageView);
        spinnerSelectTranscript = findViewById(R.id.Transcript);
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSelectTranscript.setAdapter(adapter);

        spinnerSelectTranscript.setOnItemSelectedListener(this);

        SendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentSlip.this,UserDashBoard.class);
                Toast.makeText(PaymentSlip.this,"Submitted",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        SelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(PaymentSlip.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUESTSTORAGE);

                }else{
                    SelectImage();
                }
            }
        });

        DisplayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckReadStorageAllowed()) {
                    File imageFile = new File(stringPath);

                    if (imageFile.exists()) {
                        Uri uri;
                        uri = FileProvider.getUriForFile(PaymentSlip.this, PaymentSlip.this.getPackageName() + "," + BuildConfig.APPLICATION_ID+".provider",imageFile);

                        Intent intent = new Intent();
                        intent.setAction(intent.ACTION_VIEW);
                        intent.addFlags(intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setDataAndType(uri, "/image");
                        PaymentSlip.this.startActivity(intent);
                    }else{

                        ActivityCompat.requestPermissions((Activity) PaymentSlip.this,new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"},REQUESTSTORAGE);
                    }
                }

            }
        });



    }


    private void SelectImage() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQUESFILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESFILE && resultCode == RESULT_OK){

            if(data!=null){
                Uri uri = data.getData();
                iData = data;
                getStringPath(uri);
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);

                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    DisplayImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private String getStringPath(Uri myUri){
        String[]filePathColumn ={MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(myUri,filePathColumn,null,null,null);

        if(cursor == null){
            stringPath = myUri.getPath();
        }else{
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            stringPath = cursor.getString(columnIndex);
            cursor.close();
        }
        return stringPath;
    }
    private boolean CheckReadStorageAllowed() {

        if(Build.VERSION.SDK_INT<23||ContextCompat.checkSelfPermission(PaymentSlip.this,"android.permission.WRITE_EXTERNAL_STORAGE")==0){

            return  true;
        }
        return  false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String Text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),Text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
