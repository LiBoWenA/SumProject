package com.example.tiamo.sumproject.view.ShopCustomView;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.adapter.ShopAdapter;
import com.example.tiamo.sumproject.bean.shopfragmentbean.SelectShopBean;

import java.util.List;

public class ShopCustomView extends LinearLayout implements View.OnClickListener {
    private EditText etNum;
    private Context context;
    private List<SelectShopBean.ResultBean> list;
    private int position;
    private ShopAdapter shopAdapter;
    public ShopCustomView(Context context) {
        super(context);
        init(context);
    }

    public ShopCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShopCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        View view = View.inflate(context, R.layout.shopcustomview,null);
        view.findViewById(R.id.shop_addnum).setOnClickListener(this);
        view.findViewById(R.id.shop_deletenum).setOnClickListener(this);
        etNum = view.findViewById(R.id.shop_ednum);
        addView(view);

        etNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    num = Integer.parseInt(String.valueOf(s));
                    list.get(position).setCount(num);
                }catch (Exception e){
                    list.get(position).setCount(1);
                }
                if (backListener != null){
                    backListener.callBack();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private int num;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shop_addnum:
                num++;
                etNum.setText(num+"");
                break;
            case R.id.shop_deletenum:
                if (num>1){
                    num--;
                }else{
                    Toast.makeText(context,"宝贝不能为0哦~",Toast.LENGTH_SHORT).show();
                }
                etNum.setText(num+"");
                break;
            default:
                break;
        }
    }

    CallBackListener backListener;

    public void setOnCallBack(CallBackListener onCallBack){
        backListener = onCallBack;
    }

    public interface CallBackListener{
        void callBack();
    }

    public void setData(ShopAdapter shopAdapter, List<SelectShopBean.ResultBean> list, int i){
        this.list = list;
        this.shopAdapter = shopAdapter;
        position = i;
        num = list.get(i).getCount();
        etNum.setText(num+"");
    }
}
