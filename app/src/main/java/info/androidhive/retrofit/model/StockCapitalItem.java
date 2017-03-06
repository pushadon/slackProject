package info.androidhive.retrofit.model;

/**
 * Created by Pushadon on 2017/2/28.
 */

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "Item",strict = false)
public class StockCapitalItem {

    @Attribute(name = "D")
    public String myData;

    @Attribute(name = "V1")
    public String value1;

    public String getDate() {
        return myData;
    }
    public String getValue1() {
        return value1;
    }

}