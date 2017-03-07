package info.androidhive.retrofit.model;

/**
 * Created by Pushadon on 2017/2/28.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.util.List;

public class StockFinancialRatioItem {

    @SerializedName("Row")
    @Expose
    private List<String> row = null;
    @SerializedName("dt")
    @Expose
    private String dt;
    @SerializedName("StkPrice")
    @Expose
    private Object stkPrice;
    @SerializedName("ExpireType")
    @Expose
    private Object expireType;

    public List<String> getRow() {
        return row;
    }

    public void setRow(List<String> row) {
        this.row = row;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public Object getStkPrice() {
        return stkPrice;
    }

    public void setStkPrice(Object stkPrice) {
        this.stkPrice = stkPrice;
    }

    public Object getExpireType() {
        return expireType;
    }

    public void setExpireType(Object expireType) {
        this.expireType = expireType;
    }

}