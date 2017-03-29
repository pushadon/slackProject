package info.androidhive.retrofit.util;

/**
 * Created by Pushadon on 2017/3/2.
 */

public class QueryUrl {

    public static String getStockRevenueUrl(int stockNum, int startMonth, int endMonth) {
        String revenueUrl = "http://data.xq.com.tw/jds/46/1/"+stockNum+"/TW/GetTAData4Unit.jdxml?SID="+stockNum+".TW&ST=1&a=10&b=10&c=0&f=0";
        return revenueUrl;
    }

    public static String getStockNetIncomeUrl(int stockNum, int startMonth, int endMonth) {
        String netIncomeUrl = "http://data.xq.com.tw/jds/46/1/"+stockNum+"/TW/GetTAData4Unit.jdxml?SID="+stockNum+".TW&ST=1&a=49&b=14&c="+startMonth+"&f="+endMonth;
        return netIncomeUrl;
    }

    public static String getStockPriceContentUrl(int stockNum, int startDate, int endDate) {
        String priceUrl = "http://data.xq.com.tw/jds/46/1/"+stockNum+"/TW/gethistdata2B.jdxml?SID="+stockNum+".TW&ST=1&a=13&b="+startDate+"&d="+endDate;
        return priceUrl;
    }

    public static String getStockCapital(int stockNum, int startDate, int endDate) {
        String capitalUrl = "http://data.xq.com.tw/jds/46/1/"+stockNum+"/TW/GetTAData4Unit.jdxml?SID="+stockNum+".TW&ST=1&a=44&b=14&c=0&f=0";
        return capitalUrl;
    }

    public static String getStockFinanicalRatio(int stockNum, int startDate, int endDate) {
        String finanicalRatioUrl = "http://data.xq.com.tw/Z/XQWEB2011/DATA/JVO2.xdjjs?A=46&B=1&C=05205&P="+stockNum+".TW|Q&Lang=TW";
        return finanicalRatioUrl;
    }
    //現金流量
    public static String getStockCashFlowInfoUrl(int stockNum, int startDate, int endDate) {
        String cashFlowInfoUrl = "http://data.xq.com.tw/Z/XQWEB2011/DATA/JVO2.xdjjs?A=46&B=1&C=04809&P="+stockNum+".TW|Q&Lang=TW";
        return cashFlowInfoUrl;
    }
    //損益表
    public static String getStockIncomeStatementInfoUrl(int stockNum, int startDate, int endDate) {
        String incomeStatementInfoUrl = "http://data.xq.com.tw/Z/XQWEB2011/DATA/JVO2.xdjjs?A=46&B=1&C=04709&P="+stockNum+".TW|Q&Lang=TW";
        return incomeStatementInfoUrl;
    }
    //資產負債
    public static String getStockBalanceSheetUrl(int stockNum, int startDate, int endDate) {
        String balanceSheetUrl = "http://data.xq.com.tw/Z/XQWEB2011/DATA/JVO2.xdjjs?A=46&B=1&C=04609&P="+stockNum+".TW|Q&FT=KV&Lang=TW";
        return balanceSheetUrl;
    }
}
