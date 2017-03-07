package info.androidhive.retrofit.util;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.retrofit.event.StockIncomeRatioEvent;
import info.androidhive.retrofit.event.StockPriceContentEvent;
import info.androidhive.retrofit.model.StockQueryFactory;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static info.androidhive.retrofit.util.NetIncomeFactory.incomeRationList;
import static info.androidhive.retrofit.util.NetIncomeFactory.yearAverageRatio;

/**
 * Created by Don_Chiang on 2017/3/6.
 */

public class PriceContentFactory {

    public static String TAG = "PriceContentFactory";

    static Double yearHightesPrice = 0.0;
    static Double yearLowestsPrice = 100000.0;
    static Double currentPrice = 0.0;
    private static EventBus mEventBus;


    public static void getPriceContentQueryResult(int stockNum) {
        mEventBus = EventBus.getDefault();
        yearHightesPrice = 0.0;
        yearLowestsPrice = 100000.0;
        currentPrice = 0.0;
        ApiInterface apiService =  ApiClient.getClient().create(ApiInterface.class);

        Call<StockQueryFactory.stockPriceContent> call = apiService.getPriceContentItem(QueryUrl.getStockPriceContentUrl(stockNum,20160101,0));
        call.enqueue(new Callback<StockQueryFactory.stockPriceContent>() {
            @Override
            public void onResponse(Call<StockQueryFactory.stockPriceContent> call, Response<StockQueryFactory.stockPriceContent> response) {
                int statusCode = response.code();
                StockQueryFactory.stockPriceContent result = response.body();

                Double tempPrice = 0.0;
                for(int i=0; i<result.getStockList().size(); i++) {

                    tempPrice = Double.parseDouble(result.getStockList().get(i).getClosePrice());
                    if(tempPrice < yearLowestsPrice)
                        yearLowestsPrice = tempPrice;
                    if(tempPrice > yearHightesPrice)
                        yearHightesPrice = tempPrice;
                }
                Log.e("statusCode",""+statusCode);
                Log.e("item:","ID:"+result.getID());
                currentPrice = Double.parseDouble(result.getStockList().get(0).getClosePrice());


                Log.e("item:","yearLowestsPrice:"+yearLowestsPrice);
                Log.e("item:","yearHightesPrice:"+yearHightesPrice);
                Log.e("item:","todayPrice:"+currentPrice);
                sendEvent();

            }

            @Override
            public void onFailure(Call<StockQueryFactory.stockPriceContent> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });


    }

    private static void sendEvent() {
        StockPriceContentEvent event = new StockPriceContentEvent();
        event.setYearHightestPrice(yearHightesPrice);
        event.setYearLowestPrice(yearLowestsPrice);
        event.setCurrentPrice(currentPrice);
        mEventBus.post(event);
    }

}
