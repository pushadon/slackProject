package info.androidhive.retrofit.event;

import java.util.List;

/**
 * Created by Don_Chiang on 2017/3/6.
 */

public class StockRevenueEvent {

    private List<Double> revenueYoy;
    private Double averageYoy;

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
        return Math.min(averageYoy,revenueYoy.get(0));
    }



}
