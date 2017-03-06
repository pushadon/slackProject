package info.androidhive.retrofit.util;

import android.util.Log;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.adapter.StockRevenueAdapter;
import info.androidhive.retrofit.model.EstimatedRevenue;
import info.androidhive.retrofit.model.StockQueryFactory;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

/**
 * Created by Don_Chiang on 2017/3/6.
 */

public class ReveneueFactory {

    public static String TAG = "ReveneueFactory";

    public static void getHalfYearAverageYoy(int stockNum) {
        ApiInterface apiService =  ApiClient.getClient().create(ApiInterface.class);

            Call<StockQueryFactory.stockRevenue> call = apiService.getRevenueItem(QueryUrl.getStockRevenueUrl(stockNum,0,0));
            call.enqueue(new Callback<StockQueryFactory.stockRevenue>() {
                @Override
                public void onResponse(Call<StockQueryFactory.stockRevenue> call, Response<StockQueryFactory.stockRevenue> response) {
                    int statusCode = response.code();
                    StockQueryFactory.stockRevenue result = response.body();

                    Log.e("statusCode",""+statusCode);
                    Log.e("item:","ID:"+result.getID());

                    Double monthRevenue = 0.0;
                    Double lastYearMonthRevenue = 0.0;
                    Double  totalRevenueYOY = 0.0;
                    Double YOYresult = 0.0;
                    for(int j=1; j<7; j++) {

                        monthRevenue = Double.parseDouble(result.getStockList().get(j).getValue1());
                        lastYearMonthRevenue = Double.parseDouble(result.getStockList().get(j+12).getValue1());
                        YOYresult = (monthRevenue/lastYearMonthRevenue*100)-100;
                        Log.e("lastMonthYOY  :", "date:"+result.getStockList().get(j).getDate()+"  YOY:"+YOYresult+"%");
                        totalRevenueYOY +=YOYresult;
                    }
                    int halfYearYOY = (int)(totalRevenueYOY/6);

                    Log.e("halfYearYOY:",halfYearYOY+"%");

                    Double lastMonthRevenue = Double.parseDouble(result.getStockList().get(0).getValue1());
                    Double lastYearRevenue = Double.parseDouble(result.getStockList().get(12).getValue1());
                    Log.e("lastMonthRevenue:",lastMonthRevenue+"");
                    Log.e("lastYearRevenue:",lastYearRevenue+"");

                    Log.e("halfYearYOY:",lastYearRevenue+"");
                    Log.e("lastMonthYOY:",(lastMonthRevenue/lastYearRevenue*100)-100+"%");


                }

                @Override
                public void onFailure(Call<StockQueryFactory.stockRevenue> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });


    }

    public static String getSingleMonthYoy(ApiInterface apiInterface,int stockNum) {
        return "";

    }
}
