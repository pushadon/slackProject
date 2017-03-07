package info.androidhive.retrofit.rest;

import info.androidhive.retrofit.model.StockFinancialRatioItem;
import info.androidhive.retrofit.model.StockQueryFactory;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

import static android.R.attr.apiKey;


public interface ApiInterface {


    @GET
    Call<StockQueryFactory.stockRevenue> getRevenueItem(@Url String url);

    @GET
    Call<StockQueryFactory.stockNetIncomeRatio> getNetIncomeRatioItem(@Url String url);

    @GET
    Call<StockQueryFactory.stockPriceContent> getPriceContentItem(@Url String url);

    @GET
    Call<StockQueryFactory.stockCapital> getStockCapitalItem(@Url String url);

    @GET
    Call<StockQueryFactory.stockFincialRatio> getFinanicalRationItem(@Url String url);
}
