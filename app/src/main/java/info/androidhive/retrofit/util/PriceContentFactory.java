package info.androidhive.retrofit.util;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.androidhive.retrofit.event.StockPriceContentEvent;
import info.androidhive.retrofit.model.StockQueryFactory;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static info.androidhive.retrofit.R.id.stockNum;
import static info.androidhive.retrofit.util.FinancialRatioFactory.queryYear;

/**
 * Created by Don_Chiang on 2017/3/6.
 */

public class PriceContentFactory {

    public static String TAG = "PriceContentFactory";

    static Double yearHightesPrice = 0.0;
    static Double yearLowestsPrice = 100000.0;
    static Double currentPrice = 0.0;
    static List<Double[]> myYearPriceBondList = new ArrayList<>();
    private static EventBus mEventBus;


    public static void getPriceContentQueryResult(int stockNum, final int targetMonth , final int targetYear) {
        mEventBus = EventBus.getDefault();
        yearHightesPrice = 0.0;
        yearLowestsPrice = 100000.0;
        currentPrice = 0.0;
        ApiInterface apiService =  ApiClient.getClientWithXmlConverter().create(ApiInterface.class);
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");


        Call<StockQueryFactory.stockPriceContent> call = apiService.getPriceContentItem(QueryUrl.getStockPriceContentUrl(stockNum,Integer.valueOf(sdf.format(currentDate).toString())-10000,0));
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


                Log.e("item:","yearLowestsPrice:"+yearLowestsPrice);
                Log.e("item:","yearHightesPrice:"+yearHightesPrice);
                Log.e("item:","todayPrice:"+currentPrice);
                //sendEvent();
                //getRelatePriceAndSendEvent(targetYear,targetMonth);
            }

            @Override
            public void onFailure(Call<StockQueryFactory.stockPriceContent> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });


    }

    public static void getRelatePriceAndSendEvent(int stockNum, int targetYear, int targetMonth) {
        mEventBus = EventBus.getDefault();

        ApiInterface apiService =  ApiClient.getClientWithXmlConverter().create(ApiInterface.class);
        myYearPriceBondList.clear();

        queryYear = targetYear;
        if(targetMonth != 12)
            queryYear --;
        Log.e("query year:",""+queryYear);

        Log.e("query date",(queryYear-4)*10000+101+"");
        Call<StockQueryFactory.stockPriceContent> call = apiService.getPriceContentItem(QueryUrl.getStockPriceContentUrl(stockNum,((targetYear-4)*10000)+101,0));
        call.enqueue(new Callback<StockQueryFactory.stockPriceContent>() {
            @Override
            public void onResponse(Call<StockQueryFactory.stockPriceContent> call, Response<StockQueryFactory.stockPriceContent> response) {
                StockQueryFactory.stockPriceContent result = response.body();
                Double tempLowPrice = 100000.0;
                Double tempHighPrice = 0.0;
                Double tempPrice = 0.0;
                currentPrice = Double.parseDouble(result.getStockList().get(0).getClosePrice());
                Log.e("Year Price List", "Date:"+result.getStockList().get(0).getDate().substring(0,4));
                int breakYear = queryYear-5;

                for(int i=0; i<result.getStockList().size(); i++) {
                    //Log.e("Year Price List", "break year:"+breakYear);

                    if(i<4) {
                        tempPrice = Double.parseDouble(result.getStockList().get(i).getHightPrice());
                        if(tempPrice > yearHightesPrice)
                            yearHightesPrice = tempPrice;
                        tempPrice = Double.parseDouble(result.getStockList().get(i).getLowPrice());

                        if(tempPrice < yearLowestsPrice)
                            yearLowestsPrice = tempPrice;

                    }

                    if(Integer.valueOf(result.getStockList().get(i).getDate().substring(0,4)) == queryYear) {
                        //Log.e("if loop", "Date:"+result.getStockList().get(i).getDate().toString());

                        tempPrice = Double.parseDouble(result.getStockList().get(i).getLowPrice());
                        if(tempPrice < tempLowPrice)
                            tempLowPrice = tempPrice;
                        Log.e("Year Price List:","date:"+Double.parseDouble(result.getStockList().get(i).getHightPrice())+"high price:"+tempPrice);
                        tempPrice = Double.parseDouble(result.getStockList().get(i).getHightPrice());

                        if(tempPrice > tempHighPrice)
                            tempHighPrice = tempPrice;
                    } else {
                        if(Integer.valueOf(result.getStockList().get(i).getDate().substring(0,4)) < queryYear || i == result.getStockList().size()-1) {
                            //Log.e("Year Price List", "Date:"+result.getStockList().get(i).getDate().substring(0,4));
                            queryYear--;
                            myYearPriceBondList.add(new Double[]{Double.parseDouble(result.getStockList().get(i-1).getDate().substring(0,4)),tempHighPrice, tempLowPrice});
                            if(breakYear == queryYear)
                                break;
                            tempHighPrice =0.0;
                            tempLowPrice = 10000.0;
                            tempPrice = Double.parseDouble(result.getStockList().get(i).getHightPrice());
                            if(tempPrice < tempLowPrice)
                                tempLowPrice = tempPrice;
                            tempPrice = Double.parseDouble(result.getStockList().get(i).getLowPrice());
                            Log.e("Year Price List:","high price:"+tempPrice);

                            if(tempPrice > tempHighPrice)
                                tempHighPrice = tempPrice;
                        }
                    }
                }
                Log.e("item:","yearLowestsPrice:"+yearLowestsPrice);
                Log.e("item:","yearHightesPrice:"+yearHightesPrice);
                Log.e("item:","todayPrice:"+currentPrice);
                for(int i=0; i<myYearPriceBondList.size();i++) {
                    Log.e("Year Price List","year :"+myYearPriceBondList.get(i)[0]);
                    Log.e("Year Price List","year highest price:"+myYearPriceBondList.get(i)[1]);
                    Log.e("Year Price List","year lowest price:"+myYearPriceBondList.get(i)[2]);

                }
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
        event.setYearPriceBond(myYearPriceBondList);
        mEventBus.post(event);
    }

}
