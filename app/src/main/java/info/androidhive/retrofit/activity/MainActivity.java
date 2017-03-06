package info.androidhive.retrofit.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.adapter.StockRevenueAdapter;
import info.androidhive.retrofit.event.StockIncomeRatioEvent;
import info.androidhive.retrofit.event.StockPriceContentEvent;
import info.androidhive.retrofit.event.StockRevenueEvent;
import info.androidhive.retrofit.model.EstimatedRevenue;
import info.androidhive.retrofit.model.StockQueryFactory;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import info.androidhive.retrofit.util.NetIncomeFactory;
import info.androidhive.retrofit.util.PriceContentFactory;
import info.androidhive.retrofit.util.QueryUrl;
import info.androidhive.retrofit.util.ReveneueFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.util.Log.d;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    // TODO - insert your themoviedb.org API KEY here
    private final static String API_KEY = "7e8f60e325cd06e164799af1e317d7a7";
    EditText et_stockNum;
    EditText et_stockPEHigh;
    EditText et_stockPHLow;
    EditText et_stockCapital;
    EditText et_stockYearRevenue;
    Button btn;
    RecyclerView recyclerView;
    Boolean isYoyOK = false;
    Boolean isIncomeOK = false;
    Boolean isPriceOK = false;
    EstimatedRevenue myEstimate;
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
        et_stockCapital = (EditText)findViewById(R.id.stock_capital_stock);
        et_stockPEHigh = (EditText)findViewById(R.id.stock_pe_high);
        et_stockPHLow = (EditText)findViewById(R.id.stock_pe_low);
        et_stockYearRevenue = (EditText)findViewById(R.id.stock_year_revenue);

        btn = (Button) findViewById(R.id.send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okCount = 0;
//                String[] myStockList = et.getText().toString().split(",");
//                getRevenueQueryResult(myStockList);
//                getNetIncomeQueryResult();
//                getPriceContentQueryResult();
//                ReveneueFactory.getHalfYearAverageYoy(2330);
                NetIncomeFactory.getNetIncomeQueryResult(Integer.parseInt(et_stockNum.getText().toString()));
                PriceContentFactory.getPriceContentQueryResult(Integer.parseInt(et_stockNum.getText().toString()));
                ReveneueFactory.getHalfYearAverageYoy(Integer.parseInt(et_stockNum.getText().toString()));
                myEstimate.setHistoryPELow(Double.parseDouble(et_stockPHLow.getText().toString()));
                myEstimate.setHistoryPEHigh(Double.parseDouble(et_stockPEHigh.getText().toString()));
                myEstimate.setYearReveneue(Double.parseDouble(et_stockYearRevenue.getText().toString()));
                myEstimate.setStockCapital(Double.parseDouble(et_stockCapital.getText().toString()));
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
        d("Main  RevenueYoy",event.getRevenueYoy().toString());
        d("Main AverageYoy",event.getAverageYoy().toString());
        d("Main Suitableyoy",event.getSuitableYoy().toString());
        myEstimate.setRevenueYoy(event.getSuitableYoy());
        okCount++;

        if(okCount == 3) {
            showResultAndUpdate();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StockIncomeRatioEvent event) {
        d("Main IncomeRatioList",event.getIncomeRatioList().toString());
        d("Main IncomeAverage",event.getIncomeRationAverage().toString());
        d("Main SuitableIncome",event.getSuitableRatio().toString());
        myEstimate.setIncomeRatioAverage(event.getSuitableRatio());
        okCount++;

        if(okCount == 3) {
            showResultAndUpdate();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StockPriceContentEvent event) {
        d("Main H price",event.getYearHightestPrice().toString());
        d("Main L price",event.getYearLowestPrice().toString());
        d("Main C price",event.getCurrentPrice().toString());
        myEstimate.setCurrentPrice(event.getCurrentPrice());
        okCount++;

        if(okCount == 3) {
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
        revenuesList.add(myEstimate);
        recyclerView.setAdapter(new StockRevenueAdapter(revenuesList, R.layout.list_item_movie, getApplicationContext()));
    }

//    public void getRevenueQueryResult(String[] queryList) {
//        final List<EstimatedRevenue> revenuesList = new ArrayList<>();
//        ApiInterface apiService =  ApiClient.getClient().create(ApiInterface.class);
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
//        ApiInterface apiService =  ApiClient.getClient().create(ApiInterface.class);
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
//        ApiInterface apiService =  ApiClient.getClient().create(ApiInterface.class);
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
