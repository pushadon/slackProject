package info.androidhive.retrofit.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.activity.StockInfoActivity;
import info.androidhive.retrofit.event.StockCashFlowInfoEvent;
import info.androidhive.retrofit.util.CashFlowInfoFactory;


// https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/com/xxmassdeveloper/mpchartexample/CubicLineChartActivity.java


public class StockPlotFragment extends Fragment{

    private BarChart mCashFlowChart;

    private String TAG = StockPlotFragment.class.toString();

    private String mStockNum;


    public StockPlotFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stock_plot, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mStockNum = StockInfoActivity.getStockNum();

        CashFlowInfoFactory.getCashFlowInfoQueryResult(Integer.parseInt(mStockNum));
        mCashFlowChart = (BarChart) getView().findViewById(R.id.cashFlowChart);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void setCashFlowPlotData(List<Double> cashFlowArray) {


        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();

        for (int i = 0; i < cashFlowArray.size(); i++) {
            entries.add(new BarEntry(Float.valueOf(String.valueOf(cashFlowArray.get(i))),i));
            labels.add("前"+(i+1)+"季");
        }

        BarDataSet dataset = new BarDataSet(entries, "過去四季現金流量");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData data = new BarData(labels, dataset);
        mCashFlowChart.getAxisRight().setDrawLabels(false);

        mCashFlowChart.setDescription("");
        mCashFlowChart.setData(data);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StockCashFlowInfoEvent event) {
        Log.e(TAG,"get stockCAshFlowEvent");
        setCashFlowPlotData(event.getMyQuarCashFlowOfYear());
    }
}
