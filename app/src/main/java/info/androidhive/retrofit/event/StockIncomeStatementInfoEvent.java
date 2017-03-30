package info.androidhive.retrofit.event;

import java.util.List;

/**
 * Created by Don_Chiang on 2017/3/6.
 */

public class StockIncomeStatementInfoEvent {

    public List<Double> getMyQuarOperatingRevenue() {
        return mQuarOperatingRevenue;
    }

    public void setMyQuarOperatingRevenue(List<Double> mQuarOperatingRevenue) {
        this.mQuarOperatingRevenue = mQuarOperatingRevenue;
    }

    private List<Double> mQuarOperatingRevenue;
}
