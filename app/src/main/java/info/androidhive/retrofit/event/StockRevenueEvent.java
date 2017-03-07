package info.androidhive.retrofit.event;

import android.util.Log;

import java.util.List;

/**
 * Created by Don_Chiang on 2017/3/6.
 */

public class StockRevenueEvent {

    private List<Double> revenueYoy;
    private Double averageYoy;
    private Double totalYearRevenue;

    public void setCurrentMonth(int currentMonth) {
        this.currentMonth = currentMonth;
    }

    private int currentMonth;
    private boolean checkYoyMinus = false;


    public Double getAverageYoy() {
        return averageYoy;
    }

    public void setAverageYoy(Double averageYoy) {
        this.averageYoy = averageYoy;
    }


    public List<Double> getRevenueYoy() {
        return revenueYoy;
    }

    public void setRevenueYoy(List<Double> revenueYoy) {
        this.revenueYoy = revenueYoy;
        for (Double item : revenueYoy) {
            if(item <0)
                checkYoyMinus = true;
        }
    }

    public boolean isYoyMinus() {
        return checkYoyMinus;
    }

    public Double getSuitableYoy() {
        //return averageYoy;
        if(currentMonth == 1 || currentMonth ==2) {
            return averageYoy;
        } else {
            return Math.min(averageYoy,revenueYoy.get(0));
        }
    }

    public Double getYearTotalRevenue() { return totalYearRevenue;}
    public void setYearTotalRevenue(Double yearTotalRevenue) {
        this.totalYearRevenue = yearTotalRevenue;
    }
}
