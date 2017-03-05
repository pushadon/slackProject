package info.androidhive.retrofit.model;

/**
 * Created by Pushadon on 2017/2/28.
 */

public class EstimatedRevenue {

    private String currentYoy;
    private String estimateRevenue;

    private String stockName;

    public EstimatedRevenue(String name, String currentValue,String value) {
        this.stockName = name;
        this.currentYoy =currentValue;
        this.estimateRevenue = value;

    }
    public void setEstimateRevenue(String estimateValue) {
        estimateRevenue = estimateValue;
    }

    public String getEstimateRevenue() {
        return estimateRevenue;
    }
    public String getCurrentYoy() {
        return currentYoy;
    }

    public void setStockName(String name) {
        stockName = name;
    }

    public String getStockName() {
        return stockName;
    }



}
