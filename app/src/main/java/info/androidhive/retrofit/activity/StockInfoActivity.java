package info.androidhive.retrofit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import info.androidhive.retrofit.R;
import info.androidhive.retrofit.adapter.PagerAdapter;


public class StockInfoActivity extends AppCompatActivity {


    private TabLayout mTab;
    private static String mStockNum;

    public static String getStockNum() {
        return mStockNum;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_info);
        Intent intent = getIntent();
        mStockNum = intent.getStringExtra("STOCK_NUM");
        mTab = (TabLayout) findViewById(R.id.tab_layout);
        mTab.addTab(mTab.newTab().setText("Stock Value"));
        mTab.addTab(mTab.newTab().setText("Plot"));
        mTab.addTab(mTab.newTab().setText("Bonddealer"));
        mTab.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), mTab.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTab));
        mTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



}