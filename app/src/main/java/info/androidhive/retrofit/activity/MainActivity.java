package info.androidhive.retrofit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import info.androidhive.retrofit.R;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Button btnSend = (Button)findViewById(R.id.send);
        EditText etStockNum = (EditText) findViewById(R.id.stockNum) ;
        btnSend.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent newAct = new Intent();
                newAct.set
                newAct.setClass(MainActivity.this,StockInfoActivity.class);
                startActivity(newAct);
            }
        });

    }

}