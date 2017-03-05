package info.androidhive.retrofit.model;

/**
 * Created by Pushadon on 2017/2/28.
 */

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "Item",strict = false)
public class StockPriceItem {

    @Attribute(name = "D")
    public String myData;

    @Attribute(name = "V")
    public String stockVolume;

    @Attribute(name = "O")
    public String openPrice;

    @Attribute(name = "H")
    public String hightPrice;

    @Attribute(name = "L")
    public String lowPrice;

    @Attribute(name = "C")
    public String closePrice;


    public String getStockVolume() {
        return stockVolume;
    }

    public String getDate() {
        return myData;
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public String getHightPrice() {
        return hightPrice;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public String getClosePrice() {
        return closePrice;
    }


}