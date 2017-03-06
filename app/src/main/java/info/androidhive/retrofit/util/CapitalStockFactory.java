package info.androidhive.retrofit.util;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;


import info.androidhive.retrofit.event.StockCapitalEvent;
import info.androidhive.retrofit.model.StockQueryFactory;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Don_Chiang on 2017/3/6.
 */

public class CapitalStockFactory {

    public static String TAG = "CapitalStockFactory";

    static Double capitcalStockContent = 0.0;
    private static EventBus mEventBus;


    public static void getStockCapitalQueryResult(int stockNum) {
        mEventBus = EventBus.getDefault();

        ApiInterface apiService =  ApiClient.getClient().create(ApiInterface.class);

        Call<StockQueryFactory.stockCapital> call = apiService.getStockCapitalItem(QueryUrl.getStockCapital(stockNum,20150101,0));
        call.enqueue(new Callback<StockQueryFactory.stockCapital>() {
            @Override
            public void onResponse(Call<StockQueryFactory.stockCapital> call, Response<StockQueryFactory.stockCapital> response) {
                StockQueryFactory.stockCapital result = response.body();
                capitcalStockContent = Double.parseDouble(result.getStockList().get(0).getValue1());
                sendEvent();
            }

            @Override
            public void onFailure(Call<StockQueryFactory.stockCapital> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });


    }

    private static void sendEvent() {
        StockCapitalEvent event = new StockCapitalEvent();
        event.setCapitalContent(capitcalStockContent);
        mEventBus.post(event);
    }

}
