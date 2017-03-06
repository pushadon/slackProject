package info.androidhive.retrofit.event;

import java.util.List;

/**
 * Created by Don_Chiang on 2017/3/6.
 */

public class StockIncomeRatioEvent {

    private List<Double> incomeRatioList;
    private Double incomeRationAverage;

    private boolean checkRatioDecreaseToMuch = false;


    public Double getIncomeRationAverage() {
        return incomeRationAverage;
    }

    public void setIncomeRationAverage(Double incomeRationAverage) {
        this.incomeRationAverage = incomeRationAverage;
    }


    public List<Double> getIncomeRatioList() {
        return incomeRatioList;
    }

    public void setIncomeRatioList(List<Double> incomeRatioList) {
        this.incomeRatioList = incomeRatioList;
//        for (Double item : incomeRatioList) {
//            if(item > incomeRationAverage*1.2  || item<incomeRationAverage*.8)
//                checkRatioDecreaseToMuch = true;
//        }
    }

    public boolean getFlagStrange() {
        return checkRatioDecreaseToMuch;
    }

    public Double getSuitableRatio() {
        return Math.min(incomeRationAverage, incomeRatioList.get(0));
    }



}
