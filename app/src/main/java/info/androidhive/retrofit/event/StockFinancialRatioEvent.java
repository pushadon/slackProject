package info.androidhive.retrofit.event;

import java.util.List;

import static android.R.id.list;

/**
 * Created by Don_Chiang on 2017/3/6.
 */

public class StockFinancialRatioEvent {

    private List<Double> myYearEpsList;
    private List<Double> myIncomeRatioList;

    private int epsBaseYear;
    private int epsBaseMonth;
    private double incomeRatioAverage;


    public int getEpsBaseYear() {
        return epsBaseYear;
    }

    public void setEpsBaseYear(int epsBaseYear) {
        this.epsBaseYear = epsBaseYear;
    }

    public List<Double> getMyIncomeRatioList() {
        return myIncomeRatioList;
    }

    public void setMyIncomeRatioList(List<Double> myIncomeRatioList) {
        this.myIncomeRatioList = myIncomeRatioList;
    }

    public double getIncomeRatioAverage() {
        double ratioTotal = 0.0;
        for(int i=0; i<myIncomeRatioList.size(); i++) {
            ratioTotal += myIncomeRatioList.get(i);
        }
        return ratioTotal/(double)myIncomeRatioList.size();
    }
    public int getEpsBaseMonth() {
        return epsBaseMonth;
    }

    public void setEpsBaseMonth(int epsBaseMonth) {
        this.epsBaseMonth = epsBaseMonth;
    }

    public List<Double> getYearEpsList() {
        return myYearEpsList;
    }
    public void setYearEpsList(List<Double> list ) {
        this.myYearEpsList = list;
    }



}
