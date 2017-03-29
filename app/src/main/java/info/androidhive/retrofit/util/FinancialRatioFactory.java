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

public class FinancialRatioFactory {

    public static String TAG = "FinancialRatioFactory";

    static List<Double> myYearEpsList= new ArrayList<>();
    static List<Double> myIncomeRatioList= new ArrayList<>();


    static int epsTargetYear = 0;
    static int epsTargetMonth = 0;
    private static EventBus mEventBus;
    static int queryYear = 0;


    public static void getStockFinancialRatioQueryResult(int stockNum) {
        mEventBus = EventBus.getDefault();
        myYearEpsList.clear();
        myIncomeRatioList.clear();
        ApiInterface apiService =  ApiClient.getClientWithGsonConverter().create(ApiInterface.class);

        Call<StockQueryFactory.stockFinancialRatio> call = apiService.getFinanicalRationItem(QueryUrl.getStockFinanicalRatio(stockNum,20150101,0));
        call.enqueue(new Callback<StockQueryFactory.stockFinancialRatio>() {
            @Override
            public void onResponse(Call<StockQueryFactory.stockFinancialRatio> call, Response<StockQueryFactory.stockFinancialRatio> response) {
                StockQueryFactory.stockFinancialRatio result = response.body();
                String dataYear = result.getRows().get(0).getRow().get(2).split("/")[0];
                String dataMonth = result.getRows().get(0).getRow().get(2).split("/")[1];
                Log.e("Financial Result","dataYear:"+dataYear+"  dataMonth:"+dataMonth);
                myYearEpsList = getQueryYearEpsList(response,dataYear,dataMonth);
                Log.e("my Year List:",myYearEpsList.toString());
                Log.e("my income list:",myIncomeRatioList.toString());

                epsTargetYear = Integer.valueOf(dataYear);
                epsTargetMonth = Integer.valueOf(dataMonth);
                sendEvent();

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

    private static List<Double> getQueryYearEpsList(Response<StockQueryFactory.stockFinancialRatio> response, String currentDateYear, String currentDataMonth) {
        int startIndex = 0;

        if(Integer.valueOf(currentDataMonth) != 12) {
            startIndex = Integer.valueOf(currentDataMonth)/3;
        }
        Double myYearEps = 0.0;
        Double myQuarIncomeRatio = 0.0;

        for(int i=0; i<5; i++) {
            myYearEps =0.0;
            myQuarIncomeRatio =Double.parseDouble(response.body().getRows().get(i).getRow().get(7).toString());
            Log.e("cal income ratio date:",response.body().getRows().get(i).getRow().get(2).toString());
            Log.e("cal income ratio :",response.body().getRows().get(i).getRow().get(7).toString());
            myIncomeRatioList.add(myQuarIncomeRatio);
            for(int j=0; j<4; j++) {

                myYearEps +=Double.parseDouble(response.body().getRows().get(startIndex+(4*i)+j).getRow().get(13).toString());
                Log.e("cal year eps  date:",response.body().getRows().get(startIndex+(4*i)+j).getRow().get(2).toString());
                Log.e("cal year eps  value:",response.body().getRows().get(startIndex+(4*i)+j).getRow().get(13).toString());
            }
            myYearEpsList.add(myYearEps);
        }

        return myYearEpsList;
    }

    private static List<Double> getQueryIncomeRatio(Response<StockQueryFactory.stockFinancialRatio> response) {
        myIncomeRatioList = getQueryIncomeRatio(response);


        for(int i=0; i<4; i++) {

        }
        return myIncomeRatioList;
    }

}
