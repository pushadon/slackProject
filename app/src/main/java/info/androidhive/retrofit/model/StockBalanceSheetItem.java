package info.androidhive.retrofit.model;

/**
 * Created by Pushadon on 2017/2/28.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StockBalanceSheetItem {

    @SerializedName("0170")
    @Expose
    private String mInventories; //存貨

    @SerializedName("0130")
    @Expose
    private String mReceivables; // 應收帳款

    @SerializedName("YYMM")
    @Expose
    private String mDate;


    public String getmInventories() {
        return mInventories;
    }

    public void setmInventories(String mInventories) {
        this.mInventories = mInventories;
    }


    public String getmReceivables() {
        return mReceivables;
    }

    public void setmReceivables(String mReceivables) {
        this.mReceivables = mReceivables;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }


}