package info.androidhive.retrofit.util;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.retrofit.event.StockCashFlowInfoEvent;
import info.androidhive.retrofit.event.StockIncomeStatementInfoEvent;
import info.androidhive.retrofit.model.StockQueryFactory;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static info.androidhive.retrofit.util.CashFlowInfoFactory.mCashFlowQuarArray;

/**
 * Created by Don_Chiang on 2017/3/6.
 */

public class IncomeStatementInfoFactory {

    public static String TAG = "IncomeStatementFactory";

    static List<Double> mQuarOperatingRevenue= new ArrayList<>();


    private static EventBus mEventBus;


    public static void getIncomeStatementQueryResult(int stockNum) {
        mEventBus = EventBus.getDefault();
        mQuarOperatingRevenue.clear();
        ApiInterface apiService =  ApiClient.getClientWithGsonConverter().create(ApiInterface.class);
        Log.e(TAG,"stockNum:"+stockNum);

        Call<StockQueryFactory.stockFinancialRatio> call = apiService.getFinanicalRationItem(QueryUrl.getStockIncomeStatementInfoUrl(stockNum,20150101,0));
        call.enqueue(new Callback<StockQueryFactory.stockFinancialRatio>() {
            @Override
            public void onResponse(Call<StockQueryFactory.stockFinancialRatio> call, Response<StockQueryFactory.stockFinancialRatio> response) {
                StockQueryFactory.stockFinancialRatio result = response.body();
                for(int i=0; i<4; i++) {
                    Log.e(TAG,"Date:"+result.getRows().get(i).getRow().get(2).toString());
                    Log.e(TAG,"CashFlow:"+result.getRows().get(i).getRow().get(3).toString());
                    mQuarOperatingRevenue.add(Double.parseDouble(result.getRows().get(i).getRow().get(3).toString()));
                }
                sendEvent();

            }

            @Override
            public void onFailure(Call<StockQueryFactory.stockFinancialRatio> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
    private static void sendEvent() {
        StockIncomeStatementInfoEvent event = new StockIncomeStatementInfoEvent();
        event.setMyQuarOperatingRevenue(mQuarOperatingRevenue);
        mEventBus.post(event);
    }
}
