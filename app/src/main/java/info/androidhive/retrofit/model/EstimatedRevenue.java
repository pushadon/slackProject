package info.androidhive.retrofit.model;

import android.util.Log;

import java.util.List;

/**
 * Created by Pushadon on 2017/2/28.
 */

public class EstimatedRevenue {
    private String TAG = EstimatedRevenue.class.toString();
    public Double getCurrentPrice() {
        return currentPrice;
    }

    private Double currentPrice;
    private Double estimateRevenue;


    public Double getRevenueYoy() {
        return revenueYoy;
    }

    private Double revenueYoy;
    private Double yearReveneue;

    public Double getIncomeRatioAverage() {
        return incomeRatioAverage;
    }

    private Double incomeRatioAverage;
    private Double stockCapitalContent;
    private List<Double> stockYearEps;
    private List<Double[]> stockYearPriceBond; // [0]date [1]high [2] low
    private Double[] stockYearPEAverage;

    public Double getEstimatePEG() {
        Double currentPE = getCurrentPrice()/getStockYearEps().get(0);
        Double tmpEPSGrowthRate = ((estimateEPS/getStockYearEps().get(0))-1)*100;
        return currentPE/tmpEPSGrowthRate;
    }

    private Double estimatePEG;
    private Double stockYearPEHigh = 0.0;
    private Double stockYearPELow = 0.0;


    public void setStockYearPEHigh(Double stockYearPEHigh) {
        this.stockYearPEHigh = stockYearPEHigh;
    }

    public void setStockYearPELow(Double stockYearPELow) {
        this.stockYearPELow = stockYearPELow;
    }
    public Double getStockYearPEHigh() {
        Log.e(TAG,"getStockYearPEHigh:"+stockYearPEHigh);
        if(stockYearPEHigh == 0.0)
            getStockYearPEAverage();
        return stockYearPEHigh;
    }

    public Double getStockYearPELow() {
        if(stockYearPELow == 0.0)
            getStockYearPEAverage();
        return stockYearPELow;
    }


    public void setStockYoyList(List<Double> stockYoyList) {
        this.stockYoyList = stockYoyList;
    }

    private List<Double> stockYoyList;

    public Double[] getStockYearPEAverage() {
        stockYearPEAverage = new Double[] {0.0,0.0};
        if(stockYearEps.size() == stockYearPriceBond.size()) {
            double totalPEhigh = 0.0;
            double totalPElow = 0.0;
            int peHighCount = 0;
            int peLowCount = 0;
            for(int i=0; i<stockYearEps.size();i++) {
                if(stockYearPriceBond.get(i)[1]/stockYearEps.get(i) >0) {
                    peHighCount++;
                    totalPEhigh += stockYearPriceBond.get(i)[1]/stockYearEps.get(i);
                }
                if(stockYearPriceBond.get(i)[2]/stockYearEps.get(i) >0) {
                    peLowCount++;
                    totalPElow += stockYearPriceBond.get(i)[2]/stockYearEps.get(i);
                }
                Log.e("estimate PE date:",""+stockYearPriceBond.get(i)[0]);

                Log.e("estimate PE high:",""+stockYearPriceBond.get(i)[1]/stockYearEps.get(i));
                Log.e("estimate PE low",""+stockYearPriceBond.get(i)[2]/stockYearEps.get(i));
            }
            stockYearPEAverage = new Double[] { totalPEhigh/peHighCount,totalPElow/peLowCount};
            setStockYearPEHigh(stockYearPEAverage[0]);
            setStockYearPELow(stockYearPEAverage[1]);
        }
        return stockYearPEAverage;
    }

    public String getDetailContent() {
        String yearPEHigh = "PE high :";

        String yearPELow =  "PE low  :";
        String yearEPS =    "Year EPS:";
        String incomeRatio ="Income Ratio:";
        String yearPriceHigh = "Year Price H:";
        String yearPriceLow =  "Year Price L :";
        String revenueYoyList = "Month YOY:";
        Log.e("crashSize:","stockYearEps:"+stockYearEps.size());
        Log.e("crashSize:","stockYearPriceBond:"+stockYearPriceBond.size());

        for(int i=0; i<stockYearEps.size();i++) {
            if(i<stockYearEps.size() && i<stockYearPriceBond.size()) {
                yearPEHigh += "  "+String.format( "%.2f", stockYearPriceBond.get(i)[1]/stockYearEps.get(i) );
                yearPELow  += "  "+String.format( "%.2f", stockYearPriceBond.get(i)[2]/stockYearEps.get(i) );
                yearEPS += "  "+String.format( "%.2f", stockYearEps.get(i));
                yearPriceHigh += "  "+String.format( "%.2f", stockYearPriceBond.get(i)[1]);
                yearPriceLow += "  "+String.format( "%.2f", stockYearPriceBond.get(i)[2]);
            }

        }
        for (int i=0; i<stockYoyList.size(); i++){
            revenueYoyList += "  "+String.format( "%.2f", stockYoyList.get(i));
        }
        for (int i=0; i<incomeRatioList.size();i++) {
            incomeRatio += "  "+String.format( "%.2f", incomeRatioList.get(i));
        }

        return "\nDetail \n yearRevenue:"+yearReveneue+"\n revenueYOY:"+revenueYoy/100+"\n " +
                "incomeRatioAverage:"+incomeRatioAverage/100+"\n " +
                "stockCapitalContent:"+stockCapitalContent + "\n"+
                yearPEHigh+'\n'+yearPELow+"\n"
                +yearEPS+"\n"+yearPriceHigh+"\n"+yearPriceLow+"\n"
                +incomeRatio+"\n"+revenueYoyList;
    }

    public List<Double[]> getStockYearPriceBond() {
        return stockYearPriceBond;
    }

    public void setStockYearPriceBond(List<Double[]> stockYearPriceBond) {
        this.stockYearPriceBond = stockYearPriceBond;
    }


    public List<Double> getStockYearEps() {
        return stockYearEps;
    }

    public void setStockYearEps(List<Double> stockYearEps) {
        this.stockYearEps = stockYearEps;
    }

    public Double getEstimateEPS() {
        return estimateEPS;
    }

    private Double estimateEPS = 0.0;

    private Double estimateHighestPrice = 0.0;

    public Double getEstimateLowestPrice() {
        return estimateLowestPrice;
    }

    private Double estimateLowestPrice = 0.0;

    public Double getEstimateRiskRatio() {
        return estimateRiskRatio;
    }

    private Double estimateRiskRatio = 0.0;

    public Double getHistoryPEHigh() {
        return historyPEHigh;
    }

    private Double historyPEHigh = 0.0;

    public List<Double> getIncomeRatioList() {
        return incomeRatioList;
    }

    public void setIncomeRatioList(List<Double> incomeRatioList) {
        this.incomeRatioList = incomeRatioList;
    }

    private List<Double> incomeRatioList;

    public Double getHistoryPELow() {
        return historyPELow;
    }

    private Double historyPELow = 0.0;

    private String stockName;

    public EstimatedRevenue() { }

    public void setCurrentPrice(Double currentPrice) {
        Log.d("EstimatedRevenue","setCurrentPrice:"+currentPrice);

        this.currentPrice = currentPrice;
    }

    public void setEstimateRevenue(Double estimateRevenue) {
        Log.d("EstimatedRevenue","estimateRevenue:"+estimateRevenue);

        this.estimateRevenue = estimateRevenue;
    }

    public void setRevenueYoy(Double revenueYoy) {
        Log.d("EstimatedRevenue","setRevenueYoy:"+revenueYoy);
        this.revenueYoy = revenueYoy;
    }

    public void setYearReveneue(Double yearReveneue) {
        Log.d("EstimatedRevenue","setYearReveneue:"+yearReveneue);
        this.yearReveneue = yearReveneue;
    }

    public void setIncomeRatioAverage(Double incomeRatioAverage) {
        Log.d("EstimatedRevenue","setIncomeRatioAverage:"+incomeRatioAverage);
        this.incomeRatioAverage = incomeRatioAverage;
    }

    public void setStockCapital(Double stockCleanValue) {
        Log.d("EstimatedRevenue","setStockCapital:"+stockCleanValue);
        this.stockCapitalContent = stockCleanValue;
    }

    public void setEstimateHighestPrice(Double estimateHighestPrice) {
        Log.d("EstimatedRevenue","setEstimateHighestPrice:"+estimateHighestPrice);

        this.estimateHighestPrice = estimateHighestPrice;
    }

    public void setEstimateLowestPrice(Double estimateLowestPrice) {
        Log.d("EstimatedRevenue","setEstimateLowestPrice:"+estimateLowestPrice);

        this.estimateLowestPrice = estimateLowestPrice;
    }
    public void setHistoryPEHigh(Double historyPEHigh) {
        Log.d("EstimatedRevenue","setHistoryPEHigh:"+historyPEHigh);

        this.historyPEHigh = historyPEHigh;
    }

    public void setHistoryPELow(Double historyPELow) {
        Log.d("EstimatedRevenue","setHistoryPELow:"+historyPELow);

        this.historyPELow = historyPELow;
    }

    public void setStockName(String stockName) {
        Log.d("EstimatedRevenue","setStockName:"+stockName);

        this.stockName = stockName;
    }

    public Double getEstimateHighest() {
        Log.e("EstimateHighest", "currentPrice:"+currentPrice);
        //Log.e("EstimateHighest", "historyPEHigh:"+getStockYearPEAverage()[0]);
        Log.e("EstimateHighest", "incomeRatioAverage:"+incomeRatioAverage);
        Log.e("EstimateHighest", "stockCapitalContent:"+ stockCapitalContent);
        Log.e("EstimateHighest", "yearReveneue:"+yearReveneue);
        Log.e("EstimateHighest", "revenueYoy:"+revenueYoy);


        estimateEPS = yearReveneue*incomeRatioAverage/100*(1+revenueYoy/100)*10/ stockCapitalContent;
        estimateHighestPrice = getStockYearPEHigh()*estimateEPS;
        Log.d("EstimatedRevenue","getEstimateHighest:"+estimateHighestPrice);

        return estimateHighestPrice;
    }
    public Double getEstimateLowest() {
        Log.e("EstimateLowest", "currentPrice:"+currentPrice);
        //Log.e("EstimateLowest", "historyPELow:"+getStockYearPEAverage()[1]);
        Log.e("EstimateLowest", "incomeRatioAverage:"+incomeRatioAverage/100);
        Log.e("EstimateLowest", "stockCapitalContent:"+ stockCapitalContent);
        Log.e("EstimateLowest", "yearReveneue:"+yearReveneue);
        Log.e("EstimateLowest", "revenueYoy:"+(1+revenueYoy/100));
        estimateLowestPrice = getStockYearPELow()*estimateEPS;
        Log.d("EstimatedRevenue","getEstimateLowest:"+estimateLowestPrice);

        estimateRiskRatio = (estimateHighestPrice-currentPrice)/(currentPrice-estimateLowestPrice);
        if(estimateHighestPrice-currentPrice <0)
            estimateRiskRatio = 0.0;
        if(estimateHighestPrice-currentPrice >0 && estimateLowestPrice -currentPrice >0)
            estimateRiskRatio = 1000.0;
        return estimateLowestPrice;
    }
    public Double getRiskRatio(){return estimateRiskRatio;}
    public String getStockName() {
        return stockName;
    }



}
