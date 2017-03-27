package info.androidhive.retrofit.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.model.EstimatedRevenue;

/**
 * Created by Pushadon on 2017/2/28.
 */


public class StockRevenueAdapter {

    private List<EstimatedRevenue> stockItem;
    private int rowLayout;
    private Context context;


    public static class StockRevenueHolder  {
        LinearLayout moviesLayout;
        TextView stockNumber;
        TextView stockNextMonthRevenue;
        TextView currentYoy;




        public StockRevenueHolder(View v) {
            moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            stockNumber = (TextView) v.findViewById(R.id.title);
            stockNextMonthRevenue = (TextView) v.findViewById(R.id.subtitle);
            currentYoy = (TextView) v.findViewById(R.id.currentYoy);


        }
    }

    public StockRevenueAdapter(List<EstimatedRevenue> stockItem, int rowLayout, Context context) {
        this.stockItem = stockItem;
        this.rowLayout = rowLayout;
        this.context = context;
    }


}