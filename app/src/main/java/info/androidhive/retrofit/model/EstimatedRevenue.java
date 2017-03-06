package info.androidhive.retrofit.model;

import android.util.Log;

/**
 * Created by Pushadon on 2017/2/28.
 */

public class EstimatedRevenue {

    public Double getCurrentPrice() {
        return currentPrice;
    }

    private Double currentPrice;
    private Double estimateRevenue;
    private Double revenueYoy;
    private Double yearReveneue;
    private Double incomeRatioAverage;
    private Double stockCapitalContent;

    public String getDetailContent() {
        return "\n\nDetail \n yearRevenue:"+yearReveneue+"\n revenueYOY:"+revenueYoy/100+"\n incomeRatioAverage:"+incomeRatioAverage/100+"\n stockCapitalContent:"+stockCapitalContent;
    }

    private String detailContent;
    public Double getEstimateEPS() {
        return estimateEPS;
    }

    private Double estimateEPS = 0.0;

    private Double estimateHighestPrice = 0.0;

    public Double getEstimateLowestPrice() {
        return estimateLowestPrice;
    }

    private Double estimateLowestPrice = 0.0;

    public Double getEstimateRiskRatio() {
        return estimateRiskRatio;
    }

    private Double estimateRiskRatio = 0.0;

    public Double getHistoryPEHigh() {
        return historyPEHigh;
    }

    private Double historyPEHigh = 0.0;

    public Double getHistoryPELow() {
        return historyPELow;
    }

    private Double historyPELow = 0.0;

    private String stockName;

    public EstimatedRevenue() { }

    public void setCurrentPrice(Double currentPrice) {
        Log.d("EstimatedRevenue","setCurrentPrice:"+currentPrice);

        this.currentPrice = currentPrice;
    }

    public void setEstimateRevenue(Double estimateRevenue) {
        Log.d("EstimatedRevenue","estimateRevenue:"+estimateRevenue);

        this.estimateRevenue = estimateRevenue;
    }

    public void setRevenueYoy(Double revenueYoy) {
        Log.d("EstimatedRevenue","setRevenueYoy:"+revenueYoy);
        this.revenueYoy = revenueYoy;
    }

    public void setYearReveneue(Double yearReveneue) {
        Log.d("EstimatedRevenue","setYearReveneue:"+yearReveneue);
        this.yearReveneue = yearReveneue;
    }

    public void setIncomeRatioAverage(Double incomeRatioAverage) {
        Log.d("EstimatedRevenue","setIncomeRatioAverage:"+incomeRatioAverage);
        this.incomeRatioAverage = incomeRatioAverage;
    }

    public void setStockCapital(Double stockCleanValue) {
        Log.d("EstimatedRevenue","setStockCapital:"+stockCleanValue);
        this.stockCapitalContent = stockCleanValue;
    }

    public void setEstimateHighestPrice(Double estimateHighestPrice) {
        Log.d("EstimatedRevenue","setEstimateHighestPrice:"+estimateHighestPrice);

        this.estimateHighestPrice = estimateHighestPrice;
    }

    public void setEstimateLowestPrice(Double estimateLowestPrice) {
        Log.d("EstimatedRevenue","setEstimateLowestPrice:"+estimateLowestPrice);

        this.estimateLowestPrice = estimateLowestPrice;
    }
    public void setHistoryPEHigh(Double historyPEHigh) {
        Log.d("EstimatedRevenue","setHistoryPEHigh:"+historyPEHigh);

        this.historyPEHigh = historyPEHigh;
    }

    public void setHistoryPELow(Double historyPELow) {
        Log.d("EstimatedRevenue","setHistoryPELow:"+historyPELow);

        this.historyPELow = historyPELow;
    }

    public void setStockName(String stockName) {
        Log.d("EstimatedRevenue","setStockName:"+stockName);

        this.stockName = stockName;
    }

    public Double getEstimateHighest() {
        Log.e("EstimateHighest", "currentPrice:"+currentPrice);
        Log.e("EstimateHighest", "historyPEHigh:"+historyPEHigh);
        Log.e("EstimateHighest", "incomeRatioAverage:"+incomeRatioAverage);
        Log.e("EstimateHighest", "stockCapitalContent:"+ stockCapitalContent);
        Log.e("EstimateHighest", "yearReveneue:"+yearReveneue);
        Log.e("EstimateHighest", "revenueYoy:"+revenueYoy);


        estimateEPS = yearReveneue*incomeRatioAverage/100*(1+revenueYoy/100)*10/ stockCapitalContent;
        estimateHighestPrice = historyPEHigh*estimateEPS;
        Log.d("EstimatedRevenue","getEstimateHighest:"+estimateHighestPrice);

        return estimateHighestPrice;
    }
    public Double getEstimateLowest() {
        Log.e("EstimateLowest", "currentPrice:"+currentPrice);
        Log.e("EstimateLowest", "historyPELow:"+historyPELow);
        Log.e("EstimateLowest", "incomeRatioAverage:"+incomeRatioAverage/100);
        Log.e("EstimateLowest", "stockCapitalContent:"+ stockCapitalContent);
        Log.e("EstimateLowest", "yearReveneue:"+yearReveneue);
        Log.e("EstimateLowest", "revenueYoy:"+(1+revenueYoy/100));
        estimateLowestPrice = historyPELow*estimateEPS;
        Log.d("EstimatedRevenue","getEstimateLowest:"+estimateLowestPrice);


        estimateRiskRatio = Math.abs((historyPEHigh-currentPrice)/(currentPrice-historyPELow));
        return estimateLowestPrice;
    }
    public Double getRiskRatio(){return estimateRiskRatio;}
    public String getStockName() {
        return stockName;
    }



}
