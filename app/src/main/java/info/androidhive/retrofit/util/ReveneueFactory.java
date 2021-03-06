package info.androidhive.retrofit.util;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.androidhive.retrofit.event.StockRevenueEvent;
import info.androidhive.retrofit.model.StockQueryFactory;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Don_Chiang on 2017/3/6.
 */

public class ReveneueFactory {

    public static String TAG = "ReveneueFactory";

    final static List<Double> reveneueYoyList = new ArrayList<>();
    static Double halfYearAverageYoy = 0.0;
    static Double yearTotalRevenue = 0.0;
    static int currentMonth = 0;
    private static EventBus mEventBus;



    public static void getHalfYearAverageYoy(int stockNum) {
        mEventBus = EventBus.getDefault();

        ApiInterface apiService =  ApiClient.getClientWithXmlConverter().create(ApiInterface.class);

            Call<StockQueryFactory.stockRevenue> call = apiService.getRevenueItem(QueryUrl.getStockRevenueUrl(stockNum,0,0));
            call.enqueue(new Callback<StockQueryFactory.stockRevenue>() {
                @Override
                public void onResponse(Call<StockQueryFactory.stockRevenue> call, Response<StockQueryFactory.stockRevenue> response) {
                    int statusCode = response.code();
                    StockQueryFactory.stockRevenue result = response.body();
                    reveneueYoyList.clear();
                    Log.e("statusCode",""+statusCode);
                    Log.e("item:","ID:"+result.getID());

                    Double monthRevenue = 0.0;
                    Double lastYearMonthRevenue = 0.0;
                    Double  totalRevenueYOY = 0.0;
                    Double YOYresult = 0.0;
                    for(int j=0; j<6; j++) {

                        monthRevenue = Double.parseDouble(result.getStockList().get(j).getValue1());
                        lastYearMonthRevenue = Double.parseDouble(result.getStockList().get(j+12).getValue1());
                        YOYresult = (monthRevenue/lastYearMonthRevenue*100)-100;
                        reveneueYoyList.add(YOYresult);
                        Log.e("lastMonthYOY  :", "date:"+result.getStockList().get(j).getDate()+"  YOY:"+YOYresult+"%");
                        totalRevenueYOY +=YOYresult;
                    }


                    SimpleDateFormat getCurrentMonth = new SimpleDateFormat("MM");
                    SimpleDateFormat getCurrentYear = new SimpleDateFormat("yyyy");
                    Date dt=new Date();
                    String dateMonth = getCurrentMonth.format(dt);

                    String listCloseMonth = result.getStockList().get(0).getDate().toString();
                    currentMonth = Integer.valueOf(listCloseMonth.substring(4,6));
                    String dataYear = getCurrentYear.format(dt);
                    String compareDate =dataYear;
                    if(Integer.parseInt(dateMonth) <=12) {
                        compareDate = String.valueOf(Integer.parseInt(dataYear)-1);
                    }
                    Log.w("compareDate",compareDate);

                    int yearRevenueIndex = 0;
                    for(int k=0; k<12; k++) {
                        Log.w("checkCompareDate:",result.getStockList().get(k).getDate());
                        if(result.getStockList().get(k).getDate().contains(compareDate)) {
                            yearRevenueIndex = k;
                            Log.w("getDate:",result.getStockList().get(k).getDate());
                            break;
                        }
                    }
                    yearTotalRevenue = 0.0;
                    for(int l=yearRevenueIndex;l<yearRevenueIndex+12;l++) {
                        Log.e("totalRevenue","totalRevenue:"+yearTotalRevenue);
                        yearTotalRevenue += Double.parseDouble(result.getStockList().get(l).getValue1());
                    }

                    int halfYearYOY = (int)(totalRevenueYOY/6);
                    halfYearAverageYoy = totalRevenueYOY/6;
                    Log.e("halfYearYOY:",halfYearYOY+"%");

                    Double lastMonthRevenue = Double.parseDouble(result.getStockList().get(0).getValue1());
                    Double lastYearRevenue = Double.parseDouble(result.getStockList().get(12).getValue1());
                    Log.e("lastMonthRevenue:",lastMonthRevenue+"");
                    Log.e("lastYearRevenue:",lastYearRevenue+"");

                    Log.e("halfYearYOY:",lastYearRevenue+"");
                    Log.e("lastMonthYOY:",(lastMonthRevenue/lastYearRevenue*100)-100+"%");
                    sendEvent();


                }

                @Override
                public void onFailure(Call<StockQueryFactory.stockRevenue> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });


    }

    private static void sendEvent() {
        StockRevenueEvent event = new StockRevenueEvent();
        event.setRevenueYoyList(reveneueYoyList);
        event.setAverageYoy(halfYearAverageYoy);
        event.setYearTotalRevenue(yearTotalRevenue*10000);
        event.setCurrentMonth(currentMonth);
        mEventBus.post(event);
    }

    public static String getSingleMonthYoy(ApiInterface apiInterface,int stockNum) {
        return "";

    }
}
