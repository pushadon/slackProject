package info.androidhive.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;

import java.util.List;

import info.androidhive.retrofit.model.StockNetIcomeRatioItem;
import info.androidhive.retrofit.model.StockPriceItem;
import info.androidhive.retrofit.model.StockRevenueItem;

/**
 * Created by Pushadon on 2017/2/28.
 */


public  class StockQueryFactory {

    public static class stockRevenue {
        @Attribute(name = "Expires")
        private String Expires;

        @Attribute(name = "ID")
        @Path("Data")
        private String ID;

        @Attribute(name = "TADataID")
        @Path("Data")
        private String TADataID;

        @Attribute(name = "FreqType")
        @Path("Data")
        private String FreqType;

        @Attribute(name = "FieldCount")
        @Path("Data")
        private String FieldCount;

        @Attribute(name = "AckDate")
        @Path("Data")
        private String AckDate;

        @Attribute(name = "MFlag")
        @Path("Data")
        private String MFlag;

        @Attribute(name = "Unit")
        @Path("Data")
        private String Unit;

        @Attribute(name = "VU1")
        @Path("Data")
        private String VU1;

        @Attribute(name = "VU2")
        @Path("Data")
        private String VU2;

        @Attribute(name = "VU3")
        @Path("Data")
        private String VU3;

        @Attribute(name = "VU4")
        @Path("Data")
        private String VU4;

        @ElementList(name="Item", inline=true)
        @Path("Data")
        private List<StockRevenueItem> stockItemList;

        public String getID() {
            return ID;
        }

        public List<StockRevenueItem> getStockList() {
            return stockItemList;
        }

    }

    public static class stockNetIncomeRatio {
        @Attribute(name = "Expires")
        private String Expires;

        @Attribute(name = "ID")
        @Path("Data")
        private String ID;

        @Attribute(name = "TADataID")
        @Path("Data")
        private String TADataID;

        @Attribute(name = "FreqType")
        @Path("Data")
        private String FreqType;

        @Attribute(name = "FieldCount")
        @Path("Data")
        private String FieldCount;

        @Attribute(name = "AckDate")
        @Path("Data")
        private String AckDate;

        @Attribute(name = "MFlag")
        @Path("Data")
        private String MFlag;

        @ElementList(name="Item", inline=true)
        @Path("Data")
        private List<StockNetIcomeRatioItem> stockNetIcomItem;

        public String getID() {
            return ID;
        }

        public List<StockNetIcomeRatioItem> getStockList() {
            return stockNetIcomItem;
        }

    }

    public static class stockPriceContent {

        @Attribute(name = "Expires")
        private String Expires;

        @Attribute(name = "ID")
        @Path("Data")
        private String ID;

        @Attribute(name = "FreqType")
        @Path("Data")
        private String FreqType;

        @Attribute(name = "Unit")
        @Path("Data")
        private String unit;

        @ElementList(name="Item", inline=true)
        @Path("Data")
        private List<StockPriceItem> stockPriceItem;

        public String getID() {
            return ID;
        }

        public List<StockPriceItem> getStockList() {
            return stockPriceItem;
        }

    }


    public static class stockCapital {
        @Attribute(name = "Expires")
        private String Expires;

        @Attribute(name = "ID")
        @Path("Data")
        private String ID;

        @Attribute(name = "TADataID")
        @Path("Data")
        private String TADataID;

        @Attribute(name = "FreqType")
        @Path("Data")
        private String FreqType;

        @Attribute(name = "FieldCount")
        @Path("Data")
        private String FieldCount;

        @Attribute(name = "AckDate")
        @Path("Data")
        private String AckDate;

        @Attribute(name = "MFlag")
        @Path("Data")
        private String MFlag;

        @ElementList(name="Item", inline=true)
        @Path("Data")
        private List<StockCapitalItem> stockCapitalItem;

        public String getID() {
            return ID;
        }

        public List<StockCapitalItem> getStockList() {
            return stockCapitalItem;
        }

    }

    public static class stockFincialRatio {
        @SerializedName("rows")
        @Expose
        private List<StockFinancialRatioItem> rows = null;
        @SerializedName("ColName")
        @Expose
        private List<String> colName = null;
        @SerializedName("dt")
        @Expose
        private String dt;
        @SerializedName("ExpireType")
        @Expose
        private String expireType;
        @SerializedName("SERVERNAME")
        @Expose
        private String sERVERNAME;

        public List<StockFinancialRatioItem> getRows() {
            return rows;
        }

        public void setRows(List<StockFinancialRatioItem> rows) {
            this.rows = rows;
        }

        public List<String> getColName() {
            return colName;
        }

        public void setColName(List<String> colName) {
            this.colName = colName;
        }

        public String getDt() {
            return dt;
        }

        public void setDt(String dt) {
            this.dt = dt;
        }

        public String getExpireType() {
            return expireType;
        }

        public void setExpireType(String expireType) {
            this.expireType = expireType;
        }

        public String getSERVERNAME() {
            return sERVERNAME;
        }

        public void setSERVERNAME(String sERVERNAME) {
            this.sERVERNAME = sERVERNAME;
        }
    }

}