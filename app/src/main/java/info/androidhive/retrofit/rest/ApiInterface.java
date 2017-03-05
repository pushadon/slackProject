package info.androidhive.retrofit.rest;

import info.androidhive.retrofit.model.StockQueryFactory;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface ApiInterface {


    @GET
    Call<StockQueryFactory.stockRevenue> getRevenueItem(@Url String url);

    @GET
    Call<StockQueryFactory.stockNetIncomeRatio> getNetIncomeRatioItem(@Url String url);

    @GET
    Call<StockQueryFactory.stockPriceContent> getPriceContentItem(@Url String url);
}
