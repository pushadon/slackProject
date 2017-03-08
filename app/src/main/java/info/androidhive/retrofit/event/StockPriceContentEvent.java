package info.androidhive.retrofit.event;

import java.util.List;

/**
 * Created by Don_Chiang on 2017/3/6.
 */

public class StockPriceContentEvent {

    private Double yearLowestPrice;
    private Double currentPrice;
    private Double yearHighestPrice;
    private List<Double[]> yearPriceBond;

    public Double getYearHightestPrice() {
        return yearHighestPrice;
    }

    public void setYearHightestPrice(Double yearHightestPrice) {
        this.yearHighestPrice = yearHightestPrice;
    }


    public Double getYearLowestPrice() {
        return yearLowestPrice;
    }

    public void setYearLowestPrice(Double yearLowestPrice) {
        this.yearLowestPrice = yearLowestPrice;
    }


    public List<Double[]> getYearPriceBond() {
        return yearPriceBond;
    }

    public void setYearPriceBond(List<Double[]> yearPriceBond) {
        this.yearPriceBond = yearPriceBond;
    }


    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

}
