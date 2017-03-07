package info.androidhive.retrofit.util;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.androidhive.retrofit.event.StockCapitalEvent;
import info.androidhive.retrofit.event.StockFinancialRatioEvent;
import info.androidhive.retrofit.model.StockFinancialRatioItem;
import info.androidhive.retrofit.model.StockQueryFactory;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static info.androidhive.retrofit.util.CapitalStockFactory.capitcalStockContent;

/**
 * Created by Don_Chiang on 2017/3/6.
 */

public class FinancialRatioFactory {

    public static String TAG = "FinancialRatioFactory";

    static List<Double> myYearEpsList= new ArrayList<>();
    private static EventBus mEventBus;


    public static void getStockFinancialRatioQueryResult(int stockNum) {
        mEventBus = EventBus.getDefault();
        myYearEpsList.clear();
        ApiInterface apiService =  ApiClient.getClientWithGsonConverter().create(ApiInterface.class);

        Call<StockQueryFactory.stockFincialRatio> call = apiService.getFinanicalRationItem(QueryUrl.getStockFinanicalRatio(stockNum,20150101,0));
        call.enqueue(new Callback<StockQueryFactory.stockFincialRatio>() {
            @Override
            public void onResponse(Call<StockQueryFactory.stockFincialRatio> call, Response<StockQueryFactory.stockFincialRatio> response) {
                StockQueryFactory.stockFincialRatio result = response.body();
                String dataYear = result.getRows().get(0).getRow().get(2).split("/")[0];
                String dataMonth = result.getRows().get(0).getRow().get(2).split("/")[1];
                Log.e("Financial Result","dataYear:"+dataYear+"  dataMonth:"+dataMonth);
                myYearEpsList = getQueryYearEpsList(response,dataYear,dataMonth);
                Log.e("my Year List:",myYearEpsList.toString());
                sendEvent();
            }

            @Override
            public void onFailure(Call<StockQueryFactory.stockFincialRatio> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });


    }

    private static void sendEvent() {
        StockFinancialRatioEvent event = new StockFinancialRatioEvent();
        event.setYearEpsList(myYearEpsList);
        mEventBus.post(event);
    }

    private static List<Double> getQueryYearEpsList(Response<StockQueryFactory.stockFincialRatio> response, String currentDateYear, String currentDataMonth) {
        int startIndex = 0;

        if(Integer.valueOf(currentDataMonth) != 12) {
            startIndex = Integer.valueOf(currentDataMonth)/3;
        }
        Double myYearEps = 0.0;

        for(int i=0; i<5; i++) {
            myYearEps =0.0;

            for(int j=0; j<4; j++) {
                myYearEps +=Double.parseDouble(response.body().getRows().get(startIndex+(4*i)+j).getRow().get(13).toString());
                Log.e("cal year eps  date:",response.body().getRows().get(startIndex+(4*i)+j).getRow().get(2).toString());
                Log.e("cal year eps  value:",response.body().getRows().get(startIndex+(4*i)+j).getRow().get(13).toString());
            }
            myYearEpsList.add(myYearEps);
        }

        return myYearEpsList;
    }

}
