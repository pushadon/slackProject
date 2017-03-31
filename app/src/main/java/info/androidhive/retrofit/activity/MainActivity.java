package info.androidhive.retrofit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;

import info.androidhive.retrofit.R;

import static android.widget.Toast.makeText;


public class MainActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private AutoCompleteTextView etStockNum;
    private String TAG = MainActivity.class.toString();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String[] stockList = getResources().getStringArray(R.array.stockList);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,
                stockList);
        setContentView(R.layout.activity_main);
        Button btnSend = (Button)findViewById(R.id.send);
        etStockNum = (AutoCompleteTextView) findViewById(R.id.stockNum) ;
        etStockNum.setThreshold(2);
        etStockNum.setAdapter(adapter);
        Intent errorIntent = getIntent();
        if(errorIntent != null) {
            Log.e(TAG,"error: "+ errorIntent.getStringExtra("error"));
            if(errorIntent.getStringExtra("error") != null)
                Toast.makeText(getApplicationContext(),"server data error",Toast.LENGTH_LONG).show();
        }
        btnSend.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(Arrays.asList(stockList).contains(etStockNum.getText().toString())) {
                    Intent newAct = new Intent();
                    newAct.putExtra("STOCK_NUM",etStockNum.getText().toString().split(",")[0]);
                    newAct.setClass(MainActivity.this,StockInfoActivity.class);
                    startActivity(newAct);
                } else {
                    makeText(getApplicationContext(),"enter correct number",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

}