package info.androidhive.retrofit.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import info.androidhive.retrofit.fragment.StockBonddealerIndexFragment;
import info.androidhive.retrofit.fragment.StockPlotFragment;
import info.androidhive.retrofit.fragment.StockValueFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                StockValueFragment tab1 = new StockValueFragment();
                return tab1;
            case 1:
                StockPlotFragment tab2 = new StockPlotFragment();
                return tab2;
            case 2:
                StockBonddealerIndexFragment tab3 = new StockBonddealerIndexFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}