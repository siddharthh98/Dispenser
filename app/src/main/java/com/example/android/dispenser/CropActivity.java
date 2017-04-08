package com.example.android.dispenser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CropActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button btnNext,btnPrev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        spinner = (Spinner) findViewById(R.id.spinner1);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPrev = (Button) findViewById(R.id.btnBack);

        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropActivity.this,FertilizerActivity.class);
                String string = spinner.getSelectedItem().toString();
                intent.putExtra("crop_selected",string);
                startActivity(intent);
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CropActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
