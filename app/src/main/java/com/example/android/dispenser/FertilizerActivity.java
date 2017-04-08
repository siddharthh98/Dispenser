package com.example.android.dispenser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class FertilizerActivity extends AppCompatActivity {

    String crop_selected;
    private Spinner spinner;
    private Button btnNext,btnPrev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizer);
        Bundle extras = getIntent().getExtras();
        crop_selected = getIntent().getStringExtra("crop_selected");
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        spinner = (Spinner) findViewById(R.id.spinner);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPrev = (Button) findViewById(R.id.btnBack);

        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FertilizerActivity.this,GddActivit.class);
                intent.putExtra("fertilizer_selected",spinner.getSelectedItem().toString());
                intent.putExtra("crop_selected",crop_selected);
                startActivity(intent);
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FertilizerActivity.this,CropActivity.class);
                startActivity(intent);
            }
        });
    }
}
