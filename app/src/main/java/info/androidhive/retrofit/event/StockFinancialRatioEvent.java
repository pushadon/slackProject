package info.androidhive.retrofit.event;

import java.util.List;

import static android.R.id.list;

/**
 * Created by Don_Chiang on 2017/3/6.
 */

public class StockFinancialRatioEvent {

    private List<Double> myYearEpsList;


    public List<Double> getYearEpsList() {
        return myYearEpsList;
    }
    public void setYearEpsList(List<Double> list ) {
        this.myYearEpsList = list;
    }



}
