package info.androidhive.retrofit.util;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.retrofit.event.StockIncomeRatioEvent;
import info.androidhive.retrofit.model.StockQueryFactory;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Don_Chiang on 2017/3/6.
 */

public class NetIncomeFactory {

    public static String TAG = "NetIncomeFactory";

    final static List<Double> incomeRationList = new ArrayList<>();
    static Double yearAverageRatio = 0.0;
    private static EventBus mEventBus;


    public static void getNetIncomeQueryResult(int stockNum) {
        mEventBus = EventBus.getDefault();

        ApiInterface apiService =  ApiClient.getClient().create(ApiInterface.class);

        Call<StockQueryFactory.stockNetIncomeRatio> call = apiService.getNetIncomeRatioItem(QueryUrl.getStockNetIncomeUrl(stockNum,20150101,0));
        call.enqueue(new Callback<StockQueryFactory.stockNetIncomeRatio>() {
            @Override
            public void onResponse(Call<StockQueryFactory.stockNetIncomeRatio> call, Response<StockQueryFactory.stockNetIncomeRatio> response) {
                int statusCode = response.code();
                StockQueryFactory.stockNetIncomeRatio result = response.body();
                Double totalIncomeRatio = 0.0;
                Log.e("statusCode",""+statusCode);
                Log.e("item:","ID:"+result.getID());
                for(int i=0; i<6; i++) {
                    totalIncomeRatio += Double.parseDouble(result.getStockList().get(i).getValue1());
                    incomeRationList.add(Double.parseDouble(result.getStockList().get(i).getValue1()));
                }
                yearAverageRatio = totalIncomeRatio/6;
                Log.e("item:","netIncome:"+incomeRationList.toString());
                Log.e("item:","netIncome average:"+totalIncomeRatio/6);
                sendEvent();

            }

            @Override
            public void onFailure(Call<StockQueryFactory.stockNetIncomeRatio> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });


    }

    private static void sendEvent() {
        StockIncomeRatioEvent event = new StockIncomeRatioEvent();
        event.setIncomeRatioList(incomeRationList);
        event.setIncomeRationAverage(yearAverageRatio);
        mEventBus.post(event);
    }

}
