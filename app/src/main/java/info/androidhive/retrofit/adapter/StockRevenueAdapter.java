package info.androidhive.retrofit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.model.EstimatedRevenue;

/**
 * Created by Pushadon on 2017/2/28.
 */


public class StockRevenueAdapter extends RecyclerView.Adapter<StockRevenueAdapter.StockRevenueHolder> {

    private List<EstimatedRevenue> stockItem;
    private int rowLayout;
    private Context context;


    public static class StockRevenueHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView stockNumber;
        TextView stockNextMonthRevenue;
        TextView currentYoy;




        public StockRevenueHolder(View v) {
            super(v);
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

    @Override
    public StockRevenueAdapter.StockRevenueHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new StockRevenueHolder(view);
    }


    @Override
    public void onBindViewHolder(StockRevenueHolder holder, final int position) {
        holder.stockNumber.setText(stockItem.get(position).getStockName());
        holder.stockNextMonthRevenue.setText("Next month estimate revenue YOY "+stockItem.get(position).getEstimateRevenue());
        holder.currentYoy.setText("Current month revenue YOY "+stockItem.get(position).getCurrentYoy());

    }

    @Override
    public int getItemCount() {
        return stockItem.size();
    }
}