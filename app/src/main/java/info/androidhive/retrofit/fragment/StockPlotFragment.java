package info.androidhive.retrofit.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.activity.StockInfoActivity;
import info.androidhive.retrofit.event.StockBalanceSheetInfoEvent;
import info.androidhive.retrofit.event.StockCashFlowInfoEvent;
import info.androidhive.retrofit.event.StockIncomeStatementInfoEvent;
import info.androidhive.retrofit.util.BalanceSheetInfoFactory;
import info.androidhive.retrofit.util.CashFlowInfoFactory;
import info.androidhive.retrofit.util.IncomeStatementInfoFactory;

import static android.R.attr.entries;
import static android.R.attr.lines;


// https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/com/xxmassdeveloper/mpchartexample/CubicLineChartActivity.java


public class StockPlotFragment extends Fragment{

    private BarChart mCashFlowChart;

    private LineChart mLineChart;

    private String TAG = StockPlotFragment.class.toString();

    private String mStockNum;

    private  List<Double> mQuarmQuarInventories= new ArrayList<>();  //存貨

    private  List<Double> mQuarmQuarReceivables= new ArrayList<>();  //應收帳款

    private  List<Double> mQuarOperatingRevenue= new ArrayList<>(); // 營業收入


    private int dataCnt;


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
        dataCnt = 0;
        mStockNum = StockInfoActivity.getStockNum();
        IncomeStatementInfoFactory.getIncomeStatementQueryResult(Integer.parseInt(mStockNum));
        CashFlowInfoFactory.getCashFlowInfoQueryResult(Integer.parseInt(mStockNum));
        BalanceSheetInfoFactory.getBalanceSheetQueryResult(Integer.parseInt(mStockNum));
        mCashFlowChart = (BarChart) getView().findViewById(R.id.cashFlowChart);
        mLineChart = (LineChart) getView().findViewById(R.id.lineChart);

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

    private void setLineChartData(List<Double> inventories, List<Double> receivables, List<Double> operatingRevenue) {


        ArrayList<Entry> dataset1 = new ArrayList<>(); //存貨營收比
        ArrayList<Entry> dataset2 = new ArrayList<>(); //收帳帳營收比

        ArrayList<String> labels = new ArrayList<String>();

        for (int i = 0; i < mQuarOperatingRevenue.size(); i++) {
            dataset1.add(new BarEntry(Float.valueOf(String.valueOf(mQuarmQuarInventories.get(i)/mQuarOperatingRevenue.get(i))),i));
            dataset2.add(new BarEntry(Float.valueOf(String.valueOf(mQuarmQuarReceivables.get(i)/mQuarOperatingRevenue.get(i))),i));
            labels.add("前"+(i+1)+"季");
        }
        ArrayList<LineDataSet> lines = new ArrayList<LineDataSet> ();

        LineDataSet lDataSet1 = new LineDataSet(dataset1, "存貨營收比");
        LineDataSet lDataSet2 = new LineDataSet(dataset2, "收帳營收比");
        lDataSet1.setColor(Color.BLUE);
        lDataSet2.setColor(Color.GREEN);
        lines.add(lDataSet1);
        lines.add(lDataSet2);

        mLineChart.getAxisRight().setDrawLabels(false);

        mLineChart.setDescription("");
        mLineChart.setData(new LineData(labels, lines));

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StockCashFlowInfoEvent event) {
        Log.e(TAG,"get stockCAshFlowEvent");
        setCashFlowPlotData(event.getMyQuarCashFlowOfYear());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StockBalanceSheetInfoEvent event) {
        Log.e(TAG,"get StockBalanceSheetInfoEvent");
        dataCnt ++;
        mQuarmQuarInventories = event.getmQuarmQuarInventories();
        mQuarmQuarReceivables = event.getmQuarmQuarReceivables();
        if(dataCnt == 2) {
            setLineChartData(mQuarmQuarInventories,mQuarmQuarReceivables,mQuarmQuarReceivables);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StockIncomeStatementInfoEvent event) {
        Log.e(TAG,"get StockIncomeStatementInfoEvent");
        dataCnt ++;
        mQuarOperatingRevenue = event.getMyQuarOperatingRevenue();
        if(dataCnt == 2) {
            setLineChartData(mQuarmQuarInventories,mQuarmQuarReceivables,mQuarmQuarReceivables);
        }
    }
}
