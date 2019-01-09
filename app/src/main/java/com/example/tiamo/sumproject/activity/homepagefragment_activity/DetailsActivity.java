package com.example.tiamo.sumproject.activity.homepagefragment_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tiamo.sumproject.MyApp;
import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.UrlApis;
import com.example.tiamo.sumproject.activity.HomePageActivity;
import com.example.tiamo.sumproject.bean.homefragmentbean.HomePageDetailsBean;
import com.example.tiamo.sumproject.bean.homefragmentbean.ShopDetailsBean;
import com.example.tiamo.sumproject.bean.shopfragmentbean.SelectShopBean;
import com.example.tiamo.sumproject.bean.shopfragmentbean.ShopBean;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements IView {

    IPersenterImpl iPersenter;
    @BindView(R.id.details_banner)
    XBanner detalisBanner;
    @BindView(R.id.details_price)
    TextView tPrice;
    @BindView(R.id.sales_volume)
    TextView tSales;
    @BindView(R.id.details_title)
    TextView tTitle;
    @BindView(R.id.wei_kg)
    TextView tWeight;
    @BindView(R.id.wed_View)
    WebView webDetalis;
    @BindView(R.id.detail_back_btn)
    Button btnBack;
    @BindView(R.id.details_image_add_shop)
    ImageView imgAddShop;
    @BindView(R.id.details_image_buy_shop)
    ImageView imgBuyShop;
    private int commodityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        iPersenter = new IPersenterImpl(this);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        commodityId = intent.getIntExtra("commodityId", 0);
        //点击按钮进行返回
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DetailsActivity.this,HomePageActivity.class);
                startActivity(intent1);
                finish();
            }
        });
        //发送请求
        iPersenter.showRequestData(String.format(UrlApis.HOMEPAGE_DETAILS, commodityId),HomePageDetailsBean.class);
        //点击加入购物车
        imgAddShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先查询购物车判断是否添加过
                iPersenter.showRequestData(UrlApis.SELECT_SHOP,SelectShopBean.class);
            }
        });
        //点击购买

    }

    @Override
    public void startRequestData(Object data) {
        //商品详情
        if (data instanceof HomePageDetailsBean){
            HomePageDetailsBean bean = (HomePageDetailsBean) data;
            //商品详细信息
            tPrice.setText("¥："+bean.getResult().getPrice());
            tSales.setText("销售"+bean.getResult().getSaleNum()+"件");
            tTitle.setText(bean.getResult().getCategoryName());
            tWeight.setText(bean.getResult().getWeight()+"");
            WebSettings settings = webDetalis.getSettings();
            settings.setJavaScriptEnabled(true);//支持JS
            String js = "<script type=\"text/javascript\">"+
                    "var imgs = document.getElementsByTagName('img');" + // 找到img标签
                    "for(var i = 0; i<imgs.length; i++){" +  // 逐个改变
                    "imgs[i].style.width = '100%';" +  // 宽度改为100%
                    "imgs[i].style.height = 'auto';" +
                    "}" +
                    "</script>";

            webDetalis.loadDataWithBaseURL(null, bean.getResult().getDetails()+js, "text/html", "utf-8", null);
            //轮播图
            String picture = bean.getResult().getPicture();
            String[] split = picture.split(",");
            final List<String> list = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                list.add(split[i]);
            }
            if (list.size() > 0) {
                detalisBanner.setData(list, null);
                detalisBanner.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        Glide.with(DetailsActivity.this).load(list.get(position)).into((ImageView) view);
                    }
                });
                //横向移动
                detalisBanner.setPageTransformer(Transformer.Default);
            }

        }else if(data instanceof SelectShopBean){
            //商品查询的bean类
            SelectShopBean bean = (SelectShopBean) data;
            if (bean.getMessage().equals("查询成功")){
                List<ShopBean> list = new ArrayList<>();
                //得到查询结果
                List<SelectShopBean.ResultBean> result = bean.getResult();
                //遍历查询到的结果
                for (SelectShopBean.ResultBean resultBean:result) {
                    list.add(new ShopBean(resultBean.getCommodityId(),resultBean.getCount()));
                }
                //把查询到的数据返回给添加购物车
                String string="[";
                for (int i=0;i<list.size();i++){
                    if(Integer.valueOf(commodityId)==list.get(i).getCommodityId()){
                        int count = list.get(i).getCount();
                        count++;
                        list.get(i).setCount(count);
                        break;
                    }else if(i==list.size()-1){
                        list.add(new ShopBean(Integer.valueOf(commodityId),1));
                        break;
                    }
                }
                for (ShopBean resultBean:list){
                    string+="{\"commodityId\":"+resultBean.getCommodityId()+",\"count\":"+resultBean.getCount()+"},";
                }
                String substring = string.substring(0, string.length() - 1);
                substring+="]";
                Map<String,String> map=new HashMap<>();
                map.put("data",substring);
                //发送请求添加购物车
                iPersenter.putShowRequestData(UrlApis.ADD_SHOPPING,map,ShopDetailsBean.class);

            }else{
                Toast.makeText(DetailsActivity.this,bean.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }else if(data instanceof ShopDetailsBean){
            ShopDetailsBean bean = (ShopDetailsBean) data;
            if (bean.getMessage().equals("同步成功")) {
                Toast.makeText(DetailsActivity.this,"已加入购物车！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
