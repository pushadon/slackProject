package info.androidhive.retrofit.event;

import java.util.List;

import static android.R.id.list;

/**
 * Created by Don_Chiang on 2017/3/6.
 */

public class StockFinancialRatioEvent {

    private List<Double> myYearEpsList;
    private int epsBaseYear;
    private int epsBaseMonth;

    public int getEpsBaseYear() {
        return epsBaseYear;
    }

    public void setEpsBaseYear(int epsBaseYear) {
        this.epsBaseYear = epsBaseYear;
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
