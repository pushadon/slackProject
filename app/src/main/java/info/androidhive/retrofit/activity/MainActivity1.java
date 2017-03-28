package info.androidhive.retrofit.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.InputType;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.event.StockCapitalEvent;
import info.androidhive.retrofit.event.StockFinancialRatioEvent;
import info.androidhive.retrofit.event.StockIncomeRatioEvent;
import info.androidhive.retrofit.event.StockPriceContentEvent;
import info.androidhive.retrofit.event.StockRevenueEvent;
import info.androidhive.retrofit.model.EstimatedRevenue;
import info.androidhive.retrofit.util.CapitalStockFactory;
import info.androidhive.retrofit.util.FinancialRatioFactory;
import info.androidhive.retrofit.util.PriceContentFactory;
import info.androidhive.retrofit.util.ReveneueFactory;

import static android.util.Log.d;

public class MainActivity1 extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity1.class.getSimpleName();


    // TODO - insert your themoviedb.org API KEY here
    private final static String API_KEY = "7e8f60e325cd06e164799af1e317d7a7";
    EditText et_stockNum;
    int modifyValueType = 0;

    Button btn;
    Boolean isYoyOK = false;
    Boolean isIncomeOK = false;
    Boolean isPriceOK = false;
    EstimatedRevenue myEstimate;
    TextView estHighPrice;
    TextView estLowPrice;
    TextView stockPrice;

    TextView tv_detail;
    TextView estYoy;
    TextView estIncomeRatio;

    TextView estEPS;
    TextView riskRatio;
    TextView tvLink;
    TextView tvPEHigh;
    TextView tvPELow;
    TextView tvPECurrent;

    LinearLayout resultLayout;
    int okCount = 0;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myEstimate = new EstimatedRevenue();
        et_stockNum = (EditText)findViewById(R.id.stockNum);


        // show result
        tvPEHigh = (TextView) findViewById(R.id.tv_pe_high);
        estYoy = (TextView) findViewById(R.id.tv_estimate_yoy);
        estIncomeRatio = (TextView) findViewById(R.id.tv_estimate_income_ratio);
        tvPELow = (TextView) findViewById(R.id.tv_pe_low);
        tvPECurrent = (TextView) findViewById(R.id.tv_pe_current);
        estHighPrice = (TextView)findViewById(R.id.tv_est_high_price);
        estLowPrice = (TextView)findViewById(R.id.tv_est_low_price);
        stockPrice = (TextView)findViewById(R.id.stock_price);
        estEPS = (TextView)findViewById(R.id.tv_eps);
        riskRatio = (TextView)findViewById(R.id.tv_risk);
        tv_detail = (TextView)findViewById(R.id.tv_detail);
        tvLink = (TextView) findViewById(R.id.tv_link);
        resultLayout = (LinearLayout)findViewById(R.id.resultLayout);



        btn = (Button) findViewById(R.id.send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okCount = 0;
                resultLayout.setVisibility(View.GONE);
                tvLink.setText(Html.fromHtml("<a href=http://goodinfo.tw/StockInfo/ShowK_ChartFlow.asp?RPT_CAT=PER&STOCK_ID="+et_stockNum.getText().toString()+"&CHT_CAT=MONTH>"+et_stockNum.getText().toString()+"PE interval"));
                tvLink.setMovementMethod(LinkMovementMethod.getInstance());
                //NetIncomeFactory.getNetIncomeQueryResult(Integer.parseInt(et_stockNum.getText().toString()));
                //PriceContentFactory.getPriceContentQueryResult(Integer.parseInt(et_stockNum.getText().toString()));
                ReveneueFactory.getHalfYearAverageYoy(Integer.parseInt(et_stockNum.getText().toString()));
                CapitalStockFactory.getStockCapitalQueryResult(Integer.parseInt(et_stockNum.getText().toString()));
                FinancialRatioFactory.getStockFinancialRatioQueryResult(Integer.parseInt(et_stockNum.getText().toString()));
                myEstimate.setStockName(et_stockNum.getText().toString());

            }
        });

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }



    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StockRevenueEvent event) {
        d("Main  RevenueYoy",event.getRevenueYoyList().toString());
        d("Main AverageYoy",event.getAverageYoy().toString());
        d("Main Suitableyoy",event.getSuitableYoy().toString());
        myEstimate.setRevenueYoy(event.getSuitableYoy());
        myEstimate.setYearReveneue(event.getYearTotalRevenue());
        myEstimate.setStockYoyList(event.getRevenueYoyList());
        okCount++;

        if(okCount == 4) {
            showResultAndUpdate();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StockIncomeRatioEvent event) {
        d("Main IncomeRatioList",event.getIncomeRatioList().toString());
        d("Main IncomeAverage",event.getIncomeRationAverage().toString());
        d("Main SuitableIncome",event.getSuitableRatio().toString());
        myEstimate.setIncomeRatioAverage(event.getSuitableRatio());
        myEstimate.setIncomeRatioList(event.getIncomeRatioList());
        okCount++;

        if(okCount == 4) {
            showResultAndUpdate();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StockCapitalEvent event) {
        d("Main totalYear",event.getCapitalContent().toString());
        myEstimate.setStockCapital(event.getCapitalContent());
        okCount++;

        if(okCount == 4) {
            showResultAndUpdate();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StockFinancialRatioEvent event) {
        d("Main totalYear",event.getYearEpsList().toString());
        myEstimate.setStockYearEps(event.getYearEpsList());
        myEstimate.setIncomeRatioAverage(event.getIncomeRatioAverage());
        myEstimate.setIncomeRatioList(event.getMyIncomeRatioList());
        PriceContentFactory.getRelatePriceAndSendEvent(Integer.parseInt(et_stockNum.getText().toString()),event.getEpsBaseYear(),event.getEpsBaseMonth());
        okCount++;
        if(okCount == 4) {
            showResultAndUpdate();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StockPriceContentEvent event) {
        d("Main H price",event.getYearHightestPrice().toString());
        d("Main L price",event.getYearLowestPrice().toString());
        d("Main C price",event.getCurrentPrice().toString());
        myEstimate.setCurrentPrice(event.getCurrentPrice());
        myEstimate.setStockYearPriceBond(event.getYearPriceBond());
        okCount++;

        if(okCount == 4) {
            showResultAndUpdate();
        }
    }


    public void showResultAndUpdate() {
        Log.d("showResultAndUpdate","showResultAndUpdate");

        isIncomeOK = false;
        isYoyOK = false;
        isPriceOK = false;
        final List<EstimatedRevenue> revenuesList = new ArrayList<>();
        myEstimate.getEstimateHighest();
        myEstimate.getEstimateLowest();
        tvPEHigh.setText(String.format("%.2f", (double)myEstimate.getStockYearPEAverage()[0]));
        tvPELow.setText(String.format("%.2f", (double)myEstimate.getStockYearPEAverage()[1]));
        tvPECurrent.setText(String.format("%.2f", (double)myEstimate.getCurrentPrice()/myEstimate.getStockYearEps().get(0)));
        estHighPrice.setText(String.format("%.2f", (double)myEstimate.getEstimateHighest()));
        estLowPrice.setText(String.format("%.2f", (double)myEstimate.getEstimateLowestPrice()));
        stockPrice.setText(String.format("%.2f", (double)myEstimate.getCurrentPrice()));
        estEPS.setText(String.format("%.2f", (double)myEstimate.getEstimateEPS()));
        riskRatio.setText( String.format("%.2f", (double)myEstimate.getRiskRatio()));
        tv_detail.setText(myEstimate.getDetailContent());
        estYoy.setText( String.format("%.2f", (double)myEstimate.getRevenueYoy()));
        estIncomeRatio.setText( String.format("%.2f", (double)myEstimate.getIncomeRatioAverage()));

        estYoy.setOnClickListener(this);
        estIncomeRatio.setOnClickListener(this);
        tvPEHigh.setOnClickListener(this);
        tvPELow.setOnClickListener(this);
        resultLayout.setVisibility(View.VISIBLE);

//        revenuesList.add(myEstimate);
//        recyclerView.setAdapter(new StockRevenueAdapter(revenuesList, R.layout.list_item_movie, getApplicationContext()));
    }

    @Override
    public void onClick(View v) {
        showEditAlert(v);
    }


    private void showEditAlert(View v) {
        String showString = "";
        switch(v.getId()) {
            case R.id.tv_estimate_yoy :
                showString = "Modify Revenue Yoy (%)";
                break;
            case R.id.tv_estimate_income_ratio :
                modifyValueType =1;
                showString = "Modify Income Ratio(%)";
                break;
            case R.id.tv_pe_high:
                modifyValueType =2;
                showString = "Modify PE High";
                break;
            case R.id.tv_pe_low:
                modifyValueType = 3;
                showString = "Modify PE Low";
                break;
        }
        // TODO Auto-generated method stub
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);
        builder.setMessage(showString).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(modifyValueType ==0) {
                            myEstimate.setRevenueYoy(Double.parseDouble(input.getText().toString()));
                        } else if(modifyValueType ==1){
                            myEstimate.setIncomeRatioAverage(Double.parseDouble(input.getText().toString()));
                        } else if(modifyValueType ==2){
                            myEstimate.setHistoryPEHigh(Double.parseDouble(input.getText().toString()));
                        } else if(modifyValueType ==3){
                            myEstimate.setHistoryPELow(Double.parseDouble(input.getText().toString()));
                        }
                        showResultAndUpdate();
                    }
                });
        AlertDialog about_dialog = builder.create();
        about_dialog.show();
    }

}
