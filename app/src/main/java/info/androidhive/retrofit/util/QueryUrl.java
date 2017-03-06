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
        String priceUrl = "http://data.xq.com.tw/jds/46/1/"+stockNum+"/TW/gethistdata2B.jdxml?SID="+stockNum+".TW&ST=1&a=8&b="+startDate+"&d="+endDate;
        return priceUrl;
    }

    public static String getStockCapital(int stockNum, int startDate, int endDate) {
        String capitalUrl = "http://data.xq.com.tw/jds/46/1/"+stockNum+"/TW/GetTAData4Unit.jdxml?SID="+stockNum+".TW&ST=1&a=44&b=14&c=0&f=0";
        return capitalUrl;
    }
}
