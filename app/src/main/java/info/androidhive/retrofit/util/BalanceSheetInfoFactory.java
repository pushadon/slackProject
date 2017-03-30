package info.androidhive.retrofit.util;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.retrofit.event.StockBalanceSheetInfoEvent;
import info.androidhive.retrofit.event.StockCashFlowInfoEvent;
import info.androidhive.retrofit.model.StockQueryFactory;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static info.androidhive.retrofit.util.CashFlowInfoFactory.mCashFlowQuarArray;
import static info.androidhive.retrofit.util.IncomeStatementInfoFactory.mQuarOperatingRevenue;

/**
 * Created by Don_Chiang on 2017/3/6.
 */

public class BalanceSheetInfoFactory {

    public static String TAG = "BalanceSheetInfoFactory";

    static List<Double> mQuarmQuarInventories= new ArrayList<>();  //存貨
    static List<Double> mQuarmQuarReceivables= new ArrayList<>();  //應收帳款


    private static EventBus mEventBus;


    public static void getBalanceSheetQueryResult(int stockNum) {
        mEventBus = EventBus.getDefault();
        mQuarmQuarInventories.clear();
        mQuarmQuarReceivables.clear();
        ApiInterface apiService =  ApiClient.getClientWithGsonConverter().create(ApiInterface.class);
        Log.e(TAG,"stockNum:"+stockNum);

        Call<StockQueryFactory.stockBalanceSheet> call = apiService.getBalanceSheetItem(QueryUrl.getStockBalanceSheetUrl(stockNum,20150101,0));
        call.enqueue(new Callback<StockQueryFactory.stockBalanceSheet>() {
            @Override
            public void onResponse(Call<StockQueryFactory.stockBalanceSheet> call, Response<StockQueryFactory.stockBalanceSheet> response) {
                StockQueryFactory.stockBalanceSheet result = response.body();
                for(int i=0; i<4; i++) {
                    Log.e(TAG,"Date:"+result.getRows().get(i).getmDate().toString());
                    Log.e(TAG,"inventories:"+result.getRows().get(i).getmInventories().toString());
                    Log.e(TAG,"Receivables:"+result.getRows().get(i).getmReceivables().toString());
                    mQuarmQuarInventories.add(Double.parseDouble(result.getRows().get(i).getmInventories().toString()));
                    mQuarmQuarReceivables.add(Double.parseDouble(result.getRows().get(i).getmReceivables().toString()));
                }
                sendEvent();

            }

            @Override
            public void onFailure(Call<StockQueryFactory.stockBalanceSheet> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
    private static void sendEvent() {
        StockBalanceSheetInfoEvent event = new StockBalanceSheetInfoEvent();
        event.setmQuarmQuarInventories(mQuarmQuarInventories);
        event.setmQuarmQuarReceivables(mQuarmQuarReceivables);
        mEventBus.post(event);
    }
}
