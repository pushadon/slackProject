package info.androidhive.retrofit.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import info.androidhive.retrofit.util.NetIncomeFactory;
import info.androidhive.retrofit.util.PriceContentFactory;
import info.androidhive.retrofit.util.ReveneueFactory;

import static android.util.Log.d;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    // TODO - insert your themoviedb.org API KEY here
    private final static String API_KEY = "7e8f60e325cd06e164799af1e317d7a7";
    EditText et_stockNum;
    EditText et_stockPEHigh;
    EditText et_stockPHLow;
    Button btn;
    RecyclerView recyclerView;
    Boolean isYoyOK = false;
    Boolean isIncomeOK = false;
    Boolean isPriceOK = false;
    EstimatedRevenue myEstimate;

    TextView estHighPrice;
    TextView estLowPrice;
    TextView stockPrice;
    TextView peHigh;
    TextView peLow;
    TextView tv_detail;
    TextView estEPS;
    TextView riskRatio;
    TextView tvLink;

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
        recyclerView = (RecyclerView)findViewById(R.id.movies_recycler_view);
        myEstimate = new EstimatedRevenue();
        et_stockNum = (EditText)findViewById(R.id.stockNum);



        // show result
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
                NetIncomeFactory.getNetIncomeQueryResult(Integer.parseInt(et_stockNum.getText().toString()));
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

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

        if(okCount == 5) {
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

        if(okCount == 5) {
            showResultAndUpdate();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StockCapitalEvent event) {
        d("Main totalYear",event.getCapitalContent().toString());
        myEstimate.setStockCapital(event.getCapitalContent());
        okCount++;

        if(okCount == 5) {
            showResultAndUpdate();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StockFinancialRatioEvent event) {
        d("Main totalYear",event.getYearEpsList().toString());
        myEstimate.setStockYearEps(event.getYearEpsList());
        PriceContentFactory.getRelatePriceAndSendEvent(Integer.parseInt(et_stockNum.getText().toString()),event.getEpsBaseYear(),event.getEpsBaseMonth());
        okCount++;
        if(okCount == 5) {
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

        if(okCount == 5) {
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

        estHighPrice.setText(String.format("%.2f", (double)myEstimate.getEstimateHighest()));
        estLowPrice.setText(String.format("%.2f", (double)myEstimate.getEstimateLowestPrice()));
        stockPrice.setText(String.format("%.2f", (double)myEstimate.getCurrentPrice()));
        estEPS.setText(String.format("%.2f", (double)myEstimate.getEstimateEPS()));
        riskRatio.setText( String.format("%.2f", (double)myEstimate.getRiskRatio()));
        tv_detail.setText(myEstimate.getDetailContent());
        resultLayout.setVisibility(View.VISIBLE);

//        revenuesList.add(myEstimate);
//        recyclerView.setAdapter(new StockRevenueAdapter(revenuesList, R.layout.list_item_movie, getApplicationContext()));
    }

//    public void getRevenueQueryResult(String[] queryList) {
//        final List<EstimatedRevenue> revenuesList = new ArrayList<>();
//        ApiInterface apiService =  ApiClient.getClientWithXmlConverter().create(ApiInterface.class);
//        for(int i=0; i<queryList.length; i++) {
//            String BASE_URL = "http://data.xq.com.tw/jds/46/1/"+queryList[i].toString()+"/TW/GetTAData4Unit.jdxml?SID="+queryList[i].toString()+".TW&ST=1&a=10&b=10&c=20160201&f=0";
//
//            Call<StockQueryFactory.stockRevenue> call = apiService.getRevenueItem(QueryUrl.getStockRevenueUrl(2330,20160101,20170101));
//            call.enqueue(new Callback<StockQueryFactory.stockRevenue>() {
//                @Override
//                public void onResponse(Call<StockQueryFactory.stockRevenue> call, Response<StockQueryFactory.stockRevenue> response) {
//                    int statusCode = response.code();
//                    StockQueryFactory.stockRevenue result = response.body();
//
//                    Log.e("statusCode",""+statusCode);
//                    Log.e("item:","ID:"+result.getID());
//
//                    Double  totalRevenue = 0.0;
//                    for(int j=0; j<3; j++) {
//
//                        Log.e("date:",result.getStockList().get(j).getDate());
//                        Log.e("value:",result.getStockList().get(j).getValue1());
//                        totalRevenue +=Double.parseDouble(result.getStockList().get(j).getValue1());
//                    }
//                    int myEstimateResult = (int)(totalRevenue/3/Double.parseDouble(result.getStockList().get(11).getValue1())*100 );
//
//                    Log.e("date:",result.getStockList().get(11).getDate());
//                    Log.e("value:",result.getStockList().get(11).getValue1());
//                    Log.e("date12:",result.getStockList().get(12).getDate());
//                    Log.e("value12:",result.getStockList().get(12).getValue1());
//                    Log.e("result:",myEstimateResult-100+"%");
//                    int myCurrentResult = (int)(Double.parseDouble(result.getStockList().get(0).getValue1())/Double.parseDouble(result.getStockList().get(12).getValue1())*100 );
//                    //EstimatedRevenue myEstimate = new EstimatedRevenue(result.getID(),myCurrentResult-100+"%",myEstimateResult-100+"%");
//                    revenuesList.add(myEstimate);
//                    recyclerView.setAdapter(new StockRevenueAdapter(revenuesList, R.layout.list_item_movie, getApplicationContext()));
//                }
//
//                @Override
//                public void onFailure(Call<StockQueryFactory.stockRevenue> call, Throwable t) {
//                    // Log error here since request failed
//                    Log.e(TAG, t.toString());
//                }
//            });
//        }
//
//
//    }
//
//
//    public void getNetIncomeQueryResult() {
//        ApiInterface apiService =  ApiClient.getClientWithXmlConverter().create(ApiInterface.class);
//
//        Call<StockQueryFactory.stockNetIncomeRatio> call = apiService.getNetIncomeRatioItem(QueryUrl.getStockNetIncomeUrl(2330,20160101,20170101));
//        call.enqueue(new Callback<StockQueryFactory.stockNetIncomeRatio>() {
//            @Override
//            public void onResponse(Call<StockQueryFactory.stockNetIncomeRatio> call, Response<StockQueryFactory.stockNetIncomeRatio> response) {
//                int statusCode = response.code();
//                StockQueryFactory.stockNetIncomeRatio result = response.body();
//
//                Log.e("statusCode",""+statusCode);
//                Log.e("item:","ID:"+result.getID());
//
//                Double  totalRevenue = 0.0;
//                totalRevenue +=Double.parseDouble(result.getStockList().get(0).getValue1());
//                Log.e("item:","totalRevenue:"+totalRevenue);
//            }
//
//            @Override
//            public void onFailure(Call<StockQueryFactory.stockNetIncomeRatio> call, Throwable t) {
//                // Log error here since request failed
//                Log.e(TAG, t.toString());
//            }
//        });
//    }
//
//
//    public void getPriceContentQueryResult() {
//        ApiInterface apiService =  ApiClient.getClientWithXmlConverter().create(ApiInterface.class);
//
//        Call<StockQueryFactory.stockPriceContent> call = apiService.getPriceContentItem(QueryUrl.getStockPriceContentUrl(2330,20160101,20170101));
//        call.enqueue(new Callback<StockQueryFactory.stockPriceContent>() {
//            @Override
//            public void onResponse(Call<StockQueryFactory.stockPriceContent> call, Response<StockQueryFactory.stockPriceContent> response) {
//                int statusCode = response.code();
//                StockQueryFactory.stockPriceContent result = response.body();
//
//                Log.e("statusCode",""+statusCode);
//                Log.e("item:","ID:"+result.getID());
//
//                Double  totalRevenue = 0.0;
//                totalRevenue +=Double.parseDouble(result.getStockList().get(0).getClosePrice());
//                Log.e("item:","totalRevenue:"+totalRevenue);
//            }
//
//            @Override
//            public void onFailure(Call<StockQueryFactory.stockPriceContent> call, Throwable t) {
//                // Log error here since request failed
//                Log.e(TAG, t.toString());
//            }
//        });
//    }
}
