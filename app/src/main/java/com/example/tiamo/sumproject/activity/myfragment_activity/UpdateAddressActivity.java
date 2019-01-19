package com.example.tiamo.sumproject.activity.myfragment_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.UrlApis;
import com.example.tiamo.sumproject.bean.indentfragmentbean.IndentBean;
import com.example.tiamo.sumproject.bean.indentfragmentbean.JsonBean;
import com.example.tiamo.sumproject.bean.myfragmentbean.SelectAddressBean;
import com.example.tiamo.sumproject.persenter.IPersenter;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;
import com.google.gson.Gson;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UpdateAddressActivity extends AppCompatActivity implements IView {

    private IPersenterImpl iPersenter;
    private Unbinder bind;
    @BindView(R.id.update_name)
    EditText edName;
    @BindView(R.id.update_phone)
    EditText edPhone;
    @BindView(R.id.insert_address_region)
    TextView textRegion;
    @BindView(R.id.update_address)
    EditText edAddress;
    @BindView(R.id.update_code)
    EditText edCode;
    @BindView(R.id.update_btn)
    Button updataBtn;
    @BindView(R.id.but_city)
    Button btnCity;
    private int id;
    private String name;
    private String phone;
    private String address;
    private String code;
    private ArrayList<JsonBean> options1Items = new ArrayList<>(); //省
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//区
    private CityPickerView mPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);
        bind = ButterKnife.bind(this);
        iPersenter = new IPersenterImpl(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        iPersenter.showRequestData(UrlApis.SELECT_ADDRESS,SelectAddressBean.class);
        init();
    }

    private void init() {

        updataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edName.getText().toString().trim();
                phone = edPhone.getText().toString().trim();
                address = textRegion.getText().toString()+" "+edAddress.getText().toString();
                code = edCode.getText().toString().trim();
                if (name != null && phone != null && address != null && code != null) {
                    Map<String, String> map = new HashMap<>();
                    map.put("id", id + "");
                    map.put("realName", name);
                    map.put("phone", phone);
                    map.put("address", address);
                    map.put("zipCode", code);
                    iPersenter.putShowRequestData(UrlApis.UPDATE_ADDRESS, map, IndentBean.class);
                }else{
                    Toast.makeText(UpdateAddressActivity.this,"不能为空哦",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //点击显示三级联动
        //点击按钮显示地址
        btnCity .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showPickerView();
                //初始化地区pop
                mPicker =new CityPickerView();
                mPicker.init(UpdateAddressActivity.this);
                CityConfig cityConfig = new CityConfig.Builder().build();
                mPicker.setConfig(cityConfig);
                mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        super.onSelected(province, city, district);
                        //显示自由发挥
                        //省份
                        if (province != null) {
//                            ToastUtils.showToast(InsertAddressActivity.this,province.getName());
                        }
                        //城市
                        if (city != null) {
//                            ToastUtils.showToast(InsertAddressActivity.this,city.getName());
                        }

                        //地区
                        if (district != null) {
//                            ToastUtils.showToast(InsertAddressActivity.this,district.getName());
                        }
                        textRegion.setText(province.getName()+" "+city.getName()+" "+district.getName()+" ");
                    }

                    @Override
                    public void onCancel() {
                        super.onCancel();
//                        ToastUtils.showToast(InsertAddressActivity.this,"您取消了选择！！！");
                    }
                });
                //显示
                mPicker.showCityPicker( );
            }
        });
    }

    private void initJsonData() {//解析数据 （省市区三级联动）
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三级）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    @Override
    public void startRequestData(Object data) {
        if (data instanceof IndentBean){
            IndentBean bean = (IndentBean) data;
            if (bean.getStatus().equals("0000")) {
                Toast.makeText(UpdateAddressActivity.this, bean.getMessage(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateAddressActivity.this,AddressActivity.class);
                startActivity(intent);
                finish();
            }
        }if (data instanceof SelectAddressBean){
            SelectAddressBean bean = (SelectAddressBean) data;
            for (int i = 0; i < bean.getResult().size(); i++) {
                if (id == bean.getResult().get(i).getId()){
                    String address = bean.getResult().get(i).getAddress();
                    String[] split = address.split("\\ ");
                    edName.setText(bean.getResult().get(i).getRealName());
                    edPhone.setText(bean.getResult().get(i).getPhone()+"");
                    textRegion.setText(split[0]+" "+split[1]+" "+split[2]);
                    edAddress.setText(split[3]);
                    edCode.setText(bean.getResult().get(i).getZipCode()+"");
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iPersenter != null) {
            iPersenter.onDestory();
        }
        bind.unbind();
    }
}
