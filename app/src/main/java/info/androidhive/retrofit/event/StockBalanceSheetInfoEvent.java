package info.androidhive.retrofit.event;

import java.util.List;

/**
 * Created by Don_Chiang on 2017/3/6.
 */

public class StockBalanceSheetInfoEvent {

    private List<Double> mQuarmQuarInventories;

    private List<Double> mQuarmQuarReceivables;

    public List<Double> getmQuarmQuarInventories() {
        return mQuarmQuarInventories;
    }

    public void setmQuarmQuarInventories(List<Double> mQuarmQuarInventories) {
        this.mQuarmQuarInventories = mQuarmQuarInventories;
    }

    public List<Double> getmQuarmQuarReceivables() {
        return mQuarmQuarReceivables;
    }

    public void setmQuarmQuarReceivables(List<Double> mQuarmQuarReceivables) {
        this.mQuarmQuarReceivables = mQuarmQuarReceivables;
    }
}
