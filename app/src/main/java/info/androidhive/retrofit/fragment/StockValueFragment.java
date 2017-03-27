package info.androidhive.retrofit.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.InputType;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.activity.MainActivity1;
import info.androidhive.retrofit.event.StockCapitalEvent;
import info.androidhive.retrofit.event.StockFinancialRatioEvent;
import info.androidhive.retrofit.event.StockIncomeRatioEvent;
import info.androidhive.retrofit.event.StockPriceContentEvent;
import info.androidhive.retrofit.event.StockRevenueEvent;
import info.androidhive.retrofit.model.EstimatedRevenue;
import info.androidhive.retrofit.util.CapitalStockFactory;
import info.androidhive.retrofit.util.FinancialRatioFactory;
import info.androidhive.retrofit.util.PriceContentFactory;
import info.androidhive.retrofit.util.ReveneueFactory;

import static android.util.Log.d;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StockValueFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StockValueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */



public class StockValueFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = StockValueFragment.class.getSimpleName();


    // TODO - insert your themoviedb.org API KEY here
    private final static String API_KEY = "7e8f60e325cd06e164799af1e317d7a7";
    EditText et_stockNum;
    int modifyValueType = 0;

    Button btn;
    Boolean isYoyOK = false;
    Boolean isIncomeOK = false;
    Boolean isPriceOK = false;
    EstimatedRevenue myEstimate;
    TextView estHighPrice;
    TextView estLowPrice;
    TextView stockPrice;

    TextView tv_detail;
    TextView estYoy;
    TextView estIncomeRatio;

    TextView estEPS;
    TextView riskRatio;
    TextView tvLink;
    TextView tvPEHigh;
    TextView tvPELow;
    TextView tvPECurrent;

    LinearLayout resultLayout;
    int okCount = 0;
    private OnFragmentInteractionListener mListener;

    public StockValueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StockValueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StockValueFragment newInstance(String param1, String param2) {
        StockValueFragment fragment = new StockValueFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myEstimate = new EstimatedRevenue();
        et_stockNum = (EditText)getView().findViewById(R.id.stockNum);


        // show result
        tvPEHigh = (TextView) getView().findViewById(R.id.tv_pe_high);
        estYoy = (TextView) getView().findViewById(R.id.tv_estimate_yoy);
        estIncomeRatio = (TextView) getView().findViewById(R.id.tv_estimate_income_ratio);
        tvPELow = (TextView) getView().findViewById(R.id.tv_pe_low);
        tvPECurrent = (TextView) getView().findViewById(R.id.tv_pe_current);
        estHighPrice = (TextView)getView().findViewById(R.id.tv_est_high_price);
        estLowPrice = (TextView)getView().findViewById(R.id.tv_est_low_price);
        stockPrice = (TextView)getView().findViewById(R.id.stock_price);
        estEPS = (TextView)getView().findViewById(R.id.tv_eps);
        riskRatio = (TextView)getView().findViewById(R.id.tv_risk);
        tv_detail = (TextView)getView().findViewById(R.id.tv_detail);
        tvLink = (TextView) getView().findViewById(R.id.tv_link);
        resultLayout = (LinearLayout)getView().findViewById(R.id.resultLayout);



        btn = (Button) getView().findViewById(R.id.send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okCount = 0;
                resultLayout.setVisibility(View.GONE);
                tvLink.setText(Html.fromHtml("<a href=http://goodinfo.tw/StockInfo/ShowK_ChartFlow.asp?RPT_CAT=PER&STOCK_ID="+et_stockNum.getText().toString()+"&CHT_CAT=MONTH>"+et_stockNum.getText().toString()+"PE interval"));
                tvLink.setMovementMethod(LinkMovementMethod.getInstance());
                //NetIncomeFactory.getNetIncomeQueryResult(Integer.parseInt(et_stockNum.getText().toString()));
                //PriceContentFactory.getPriceContentQueryResult(Integer.parseInt(et_stockNum.getText().toString()));
                ReveneueFactory.getHalfYearAverageYoy(Integer.parseInt(et_stockNum.getText().toString()));
                CapitalStockFactory.getStockCapitalQueryResult(Integer.parseInt(et_stockNum.getText().toString()));
                FinancialRatioFactory.getStockFinancialRatioQueryResult(Integer.parseInt(et_stockNum.getText().toString()));
                myEstimate.setStockName(et_stockNum.getText().toString());

            }
        });

        if (API_KEY.isEmpty()) {
            Toast.makeText(getActivity(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StockRevenueEvent event) {
        d("Main  RevenueYoy",event.getRevenueYoyList().toString());
        d("Main AverageYoy",event.getAverageYoy().toString());
        d("Main Suitableyoy",event.getSuitableYoy().toString());
        myEstimate.setRevenueYoy(event.getSuitableYoy());
        myEstimate.setYearReveneue(event.getYearTotalRevenue());
        myEstimate.setStockYoyList(event.getRevenueYoyList());
        okCount++;

        if(okCount == 4) {
            showResultAndUpdate();
        }
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StockIncomeRatioEvent event) {
        d("Main IncomeRatioList",event.getIncomeRatioList().toString());
        d("Main IncomeAverage",event.getIncomeRationAverage().toString());
        d("Main SuitableIncome",event.getSuitableRatio().toString());
        myEstimate.setIncomeRatioAverage(event.getSuitableRatio());
        myEstimate.setIncomeRatioList(event.getIncomeRatioList());
        okCount++;

        if(okCount == 4) {
            showResultAndUpdate();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StockCapitalEvent event) {
        d("Main totalYear",event.getCapitalContent().toString());
        myEstimate.setStockCapital(event.getCapitalContent());
        okCount++;

        if(okCount == 4) {
            showResultAndUpdate();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StockFinancialRatioEvent event) {
        d("Main totalYear",event.getYearEpsList().toString());
        myEstimate.setStockYearEps(event.getYearEpsList());
        myEstimate.setIncomeRatioAverage(event.getIncomeRatioAverage());
        myEstimate.setIncomeRatioList(event.getMyIncomeRatioList());
        PriceContentFactory.getRelatePriceAndSendEvent(Integer.parseInt(et_stockNum.getText().toString()),event.getEpsBaseYear(),event.getEpsBaseMonth());
        okCount++;
        if(okCount == 4) {
            showResultAndUpdate();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StockPriceContentEvent event) {
        d("Main H price",event.getYearHightestPrice().toString());
        d("Main L price",event.getYearLowestPrice().toString());
        d("Main C price",event.getCurrentPrice().toString());
        myEstimate.setCurrentPrice(event.getCurrentPrice());
        myEstimate.setStockYearPriceBond(event.getYearPriceBond());
        okCount++;

        if(okCount == 4) {
            showResultAndUpdate();
        }
    }

    public void showResultAndUpdate() {
        Log.d("showResultAndUpdate","showResultAndUpdate");

        isIncomeOK = false;
        isYoyOK = false;
        isPriceOK = false;
        final List<EstimatedRevenue> revenuesList = new ArrayList<>();
        myEstimate.getEstimateHighest();
        myEstimate.getEstimateLowest();
        tvPEHigh.setText(String.format("%.2f", (double)myEstimate.getStockYearPEAverage()[0]));
        tvPELow.setText(String.format("%.2f", (double)myEstimate.getStockYearPEAverage()[1]));
        tvPECurrent.setText(String.format("%.2f", (double)myEstimate.getCurrentPrice()/myEstimate.getStockYearEps().get(0)));
        estHighPrice.setText(String.format("%.2f", (double)myEstimate.getEstimateHighest()));
        estLowPrice.setText(String.format("%.2f", (double)myEstimate.getEstimateLowestPrice()));
        stockPrice.setText(String.format("%.2f", (double)myEstimate.getCurrentPrice()));
        estEPS.setText(String.format("%.2f", (double)myEstimate.getEstimateEPS()));
        riskRatio.setText( String.format("%.2f", (double)myEstimate.getRiskRatio()));
        tv_detail.setText(myEstimate.getDetailContent());
        estYoy.setText( String.format("%.2f", (double)myEstimate.getRevenueYoy()));
        estIncomeRatio.setText( String.format("%.2f", (double)myEstimate.getIncomeRatioAverage()));

        estYoy.setOnClickListener(this);
        estIncomeRatio.setOnClickListener(this);
        resultLayout.setVisibility(View.VISIBLE);

//        revenuesList.add(myEstimate);
//        recyclerView.setAdapter(new StockRevenueAdapter(revenuesList, R.layout.list_item_movie, getApplicationContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stock_value, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View v) {
        showEditAlert(v);
    }


    private void showEditAlert(View v) {
        String showString = "";
        switch(v.getId()) {
            case R.id.tv_estimate_yoy :
                showString = "Modify Revenue Yoy (%)";
                break;
            case R.id.tv_estimate_income_ratio :
                modifyValueType =1;
                showString = "Modify Income Ratio(%)";
                break;
        }
        // TODO Auto-generated method stub
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);
        builder.setMessage(showString).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(modifyValueType ==0) {
                            myEstimate.setRevenueYoy(Double.parseDouble(input.getText().toString()));
                        } else {
                            myEstimate.setIncomeRatioAverage(Double.parseDouble(input.getText().toString()));
                        }
                        showResultAndUpdate();
                    }
                });
        AlertDialog about_dialog = builder.create();
        about_dialog.show();
    }
}
