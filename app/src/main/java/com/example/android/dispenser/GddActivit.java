package com.example.android.dispenser;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.R.id.input;
import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import static java.net.Proxy.Type.HTTP;

public class GddActivit extends AppCompatActivity implements View.OnClickListener {


    int day1,day2,month1,month2;
    private EditText fromDateEtxt;
    private EditText toDateEtxt;

    private DatePickerDialog fromDatePickerDialog;
    //private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    TextView textView1,textView2;
    Button button,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gdd);
        final String crop_selected = getIntent().getStringExtra("crop_selected");
        String fertilizer_selected = getIntent().getStringExtra("fertilizer_selected");
        textView1 = (TextView) findViewById(R.id.text1);
        textView1.setText(crop_selected);

        textView2 = (TextView) findViewById(R.id.text2);
        textView2.setText(fertilizer_selected);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        findViewsById();

        setDateTimeField();
        button2 = (Button)findViewById(R.id.btnBack);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GddActivit.this,FertilizerActivity.class);
                startActivity(intent);
            }
        });
        button = (Button) findViewById(R.id.btnSubmit);
        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                String serverURL = "https://agent.electricimp.com/LNb8i-SBCk7w?data=" + crop_selected + ":" + day1 + "." + month1 + ":" + day2 + "." + month2 + "&task=CropSeason";
                Log.d("url",serverURL);
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                URL url = null;
                try {
                    url = new URL(serverURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("url","connection opened");
                try {
                    Context context = getApplicationContext();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    String st=readStream(in);
                    String response=st.split(":")[0];
                    String suggestion=st.split(":")[1];
                    Log.d("o/p",st);
                    if(response.contentEquals("OK")){
                        Toast.makeText(context,suggestion,Toast.LENGTH_LONG).show();
                    }
                    else{
                        Log.d("URL",readStream(in));
                        Toast.makeText(context,suggestion+"Please go back and reselect crop/period",Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {

                    e.printStackTrace();
                } finally {

                    urlConnection.disconnect();
                }
              //  Intent intent = new Intent(GddActivit.this,SubmitActivity.class);

            }
        });
    }
    private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.etxt_fromdate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();


    }


    private boolean setDateTimeField() {
        fromDateEtxt.setOnClickListener((View.OnClickListener) this);
       // toDateEtxt.setOnClickListener((View.OnClickListener) this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {


            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                day1 = dayOfMonth;
                month1 = monthOfYear;
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        return true;
    }




    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onClick(View view) {
        if(view == fromDateEtxt) {
            fromDatePickerDialog.show();  } else if(view == toDateEtxt) {

        }
    }
    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }

            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }

}
