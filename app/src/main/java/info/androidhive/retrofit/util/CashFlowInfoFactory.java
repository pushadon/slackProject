package info.androidhive.retrofit.util;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.retrofit.event.StockFinancialRatioEvent;
import info.androidhive.retrofit.model.StockQueryFactory;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Don_Chiang on 2017/3/6.
 */

public class CashFlowInfoFactory {

    public static String TAG = "CashFlowInfoFactory";

    static List<Double> myYearEpsList= new ArrayList<>();
    static List<Double> myIncomeRatioList= new ArrayList<>();


    static int epsTargetYear = 0;
    static int epsTargetMonth = 0;
    private static EventBus mEventBus;
    static int queryYear = 0;


    public static void getCashFlowInfoQueryResult(int stockNum) {
        mEventBus = EventBus.getDefault();
        myYearEpsList.clear();
        myIncomeRatioList.clear();
        ApiInterface apiService =  ApiClient.getClientWithGsonConverter().create(ApiInterface.class);
        Log.e(TAG,"stockNum:"+stockNum);

        Call<StockQueryFactory.stockFinancialRatio> call = apiService.getFinanicalRationItem(QueryUrl.getStockCashFlowInfo(stockNum,20150101,0));
        call.enqueue(new Callback<StockQueryFactory.stockFinancialRatio>() {
            @Override
            public void onResponse(Call<StockQueryFactory.stockFinancialRatio> call, Response<StockQueryFactory.stockFinancialRatio> response) {
                StockQueryFactory.stockFinancialRatio result = response.body();
                //String dataYear = result.getRows().get(0).getRow().get(2).split("/")[0];
                //String dataMonth = result.getRows().get(0).getRow().get(2).split("/")[1];
                Log.e(TAG,"Date:"+result.getRows().get(0).getRow().get(2).toString());

                //sendEvent();

            }

            @Override
            public void onFailure(Call<StockQueryFactory.stockFinancialRatio> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    private static void sendEvent() {
        StockFinancialRatioEvent event = new StockFinancialRatioEvent();
        event.setYearEpsList(myYearEpsList);
        event.setEpsBaseMonth(epsTargetMonth);
        event.setEpsBaseYear(epsTargetYear);
        event.setMyIncomeRatioList(myIncomeRatioList);
        mEventBus.post(event);
    }


}
