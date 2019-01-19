package com.example.tiamo.sumproject.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;

import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.activity.shopfragmentactivity.ShopActivity;
import com.example.tiamo.sumproject.fragment.CircleFragment;
import com.example.tiamo.sumproject.fragment.HomePageFragment;
import com.example.tiamo.sumproject.fragment.IndentFragment;
import com.example.tiamo.sumproject.fragment.MyFragment;
import com.example.tiamo.sumproject.fragment.ShopFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomePageActivity extends AppCompatActivity {

    @BindView(R.id.intent_home)
    RadioButton intentHome;
    @BindView(R.id.intent_circle)
    RadioButton intentCircle;
    @BindView(R.id.intent_shopping)
    RadioButton intentShopping;
    @BindView(R.id.intent_indent)
    RadioButton intentIndent;
    @BindView(R.id.intent_my)
    RadioButton intentMy;
    private FragmentTransaction transaction;
    private HomePageFragment homePageFragment;
    private Unbinder bind;
    private ShopActivity shopActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        bind = ButterKnife.bind(this);
        init();

    }

    private void init() {
        homePageFragment = new HomePageFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_sum_pager, homePageFragment).commit();
    }

    @OnClick({R.id.intent_home, R.id.intent_circle, R.id.intent_shopping, R.id.intent_indent, R.id.intent_my})
    public void onViewClicked(View view) {
        transaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()){
            case R.id.intent_home:
                transaction.replace(R.id.home_sum_pager,homePageFragment).commit();
                break;
            case R.id.intent_circle:
                transaction.replace(R.id.home_sum_pager,new CircleFragment()).commit();
                break;
            case R.id.intent_shopping:
                transaction.replace(R.id.home_sum_pager,new ShopFragment()).commit();
                break;
            case R.id.intent_indent:
                transaction.replace(R.id.home_sum_pager,new IndentFragment()).commit();
                break;
            case R.id.intent_my:
                transaction.replace(R.id.home_sum_pager,new MyFragment()).commit();
                break;
            default:
                break;
        }
        }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ){
            homePageFragment.getBackData(true);
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}




