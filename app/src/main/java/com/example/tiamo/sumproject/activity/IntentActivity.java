package com.example.tiamo.sumproject.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;

import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.fragment.CircleFragment;
import com.example.tiamo.sumproject.fragment.HomePageFragment;
import com.example.tiamo.sumproject.fragment.IndentFragment;
import com.example.tiamo.sumproject.fragment.MyFragment;
import com.example.tiamo.sumproject.fragment.ShopFragment;

import java.util.ArrayList;
import java.util.List;

public class IntentActivity extends AppCompatActivity {

    ViewPager pager;
    List<Fragment> list;
    RadioGroup group;
    private HomePageFragment homePageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        init();

    }
    private void init() {
        //获取资源ID
        pager = findViewById(R.id.pager);
        group = findViewById(R.id.intent_group);
        //添加fragment
        list= new ArrayList<>();
        homePageFragment = new HomePageFragment();
        list.add(homePageFragment);
        list.add(new CircleFragment());
        list.add(new ShopFragment());
        list.add(new IndentFragment());
        list.add(new MyFragment());
        //添加适配器
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.intent_home:
                        pager.setCurrentItem(0);
                        break;
                    case R.id.intent_circle:
                        pager.setCurrentItem(1);
                        break;
                    case R.id.intent_shopping:
                        pager.setCurrentItem(2);
                        break;
                    case R.id.intent_indent:
                        pager.setCurrentItem(3);
                        break;
                    case R.id.intent_my:
                        pager.setCurrentItem(4);
                        break;
                        default:
                            break;
                }
            }
        });
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        group.check(R.id.intent_home);
                        break;
                    case 1:
                        group.check(R.id.intent_circle);
                        break;
                    case 2:
                        group.check(R.id.intent_shopping);
                        break;
                    case 3:
                        group.check(R.id.intent_indent);
                        break;
                    case 4:
                        group.check(R.id.intent_my);
                        break;
                        default:
                            break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ){
            homePageFragment.getBackData(true);
        }
        return false;
    }
}
