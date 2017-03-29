package info.androidhive.retrofit.event;

import java.util.List;

/**
 * Created by Don_Chiang on 2017/3/6.
 */

public class StockCashFlowInfoEvent {

    public List<Double> getMyQuarCashFlowOfYear() {
        return myQuarCashFlowOfYear;
    }

    public void setMyQuarCashFlowOfYear(List<Double> myQuarCashFlowOfYear) {
        this.myQuarCashFlowOfYear = myQuarCashFlowOfYear;
    }

    private List<Double> myQuarCashFlowOfYear;
}
