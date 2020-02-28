package com.example.usuario.needinghelp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDots;
    private SliderAdapter sliderAdapter;
    private Button mNextBtn;
    private Button mPrevBtn;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);
        mNextBtn = (Button) findViewById(R.id.nextBttn);
        mPrevBtn = (Button) findViewById(R.id.prevButton);
        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

    }

    void AbrirMapa(View v){
        Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
        startActivity(intent);
    }

    public void addDotsIndicator(int position){
        mDots = new TextView[3];
        for (int i = 0;i <mDots.length;i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            mDotLayout.addView(mDots[i]);
        }
        if(mDots.length >0){
            mDots[position].setTextColor(0xfff);
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            mCurrentPage = i;
            if(i == mDots.length-1){
                mNextBtn.setEnabled(true);
                mPrevBtn.setEnabled(true);
                mNextBtn.setVisibility(View.VISIBLE);
                mPrevBtn.setVisibility(View.VISIBLE);

            }
            else{
                mNextBtn.setEnabled(false);
                mPrevBtn.setEnabled(false);
                mNextBtn.setVisibility(View.INVISIBLE);
                mPrevBtn.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

}
