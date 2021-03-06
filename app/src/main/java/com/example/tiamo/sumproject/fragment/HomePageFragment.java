package com.example.tiamo.sumproject.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TabWidget;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.UrlApis;
import com.example.tiamo.sumproject.activity.HomePageActivity;
import com.example.tiamo.sumproject.adapter.FashionAdapter;
import com.example.tiamo.sumproject.adapter.HomeSerchAdapter;
import com.example.tiamo.sumproject.adapter.HomeShopAdapter;
import com.example.tiamo.sumproject.adapter.LifeAdapter;
import com.example.tiamo.sumproject.adapter.NewDesignAdapter;
import com.example.tiamo.sumproject.adapter.OneLevelAdapter;
import com.example.tiamo.sumproject.adapter.TwoLevelAdapter;
import com.example.tiamo.sumproject.adapter.TwoLevelSerchAdapter;
import com.example.tiamo.sumproject.bean.BannerBean;
import com.example.tiamo.sumproject.bean.HomeBean;
import com.example.tiamo.sumproject.bean.HomeShopBean;
import com.example.tiamo.sumproject.bean.HomeserchBean;
import com.example.tiamo.sumproject.bean.OneLevelBean;
import com.example.tiamo.sumproject.bean.TwoLevelBean;
import com.example.tiamo.sumproject.bean.TwoLevelSerchBean;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stx.xhb.xbanner.XBanner;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomePageFragment extends Fragment implements IView {
    @BindView(R.id.banner_pager)
    XBanner pager;
    @BindView(R.id.new_design_num)
    RecyclerView recyclerViewDesign;
    @BindView(R.id.fashion_num)
    RecyclerView recyclerViewFashion;
    @BindView(R.id.life_num)
    RecyclerView recyclerViewLife;
    @BindView(R.id.scroll)
    ScrollView scrollView;
    @BindView(R.id.home_new_design_show)
    LinearLayout linearLayoutNewDesign;
    @BindView(R.id.new_design_xr)
    XRecyclerView xRecyclerViewNew;
    @BindView(R.id.text_new_show)
    TextView textNewShow;
    @BindView(R.id.text_fashion_show)
    TextView textFashionShow;
    @BindView(R.id.text_life_show)
    TextView textLifeShow;
    @BindView(R.id.serch_image)
    ImageView serchImage;
    @BindView(R.id.serch_btn)
    Button serchBtn;
    @BindView(R.id.serch_et)
    EditText edSerch;
    @BindView(R.id.homepage_serch)
    XRecyclerView xRecyclerView;
    @BindView(R.id.three_level)
    ImageView imgLevel;
    @BindView(R.id.no_img)
    RelativeLayout relativeLayoutNo;
    private RecyclerView recyclerViewOne;
    private RecyclerView recyclerViewtwo;
    private IPersenterImpl iPersenter;
    private NewDesignAdapter designAdapter;
    private FashionAdapter fashionAdapter;
    private LifeAdapter lifeAdapter;
    private int page = 1;
    private int rxxpid;
    private HomeShopAdapter homeShopAdapter;
    private HomeSerchAdapter homeSerchAdapter;
    private int mlssId;
    private int pzshId;
    private OneLevelAdapter oneLevelAdapter;
    private boolean isShow = true;
    private TwoLevelAdapter twoLevelAdapter;
    private TwoLevelSerchAdapter twoLevelSerchAdapter;
    private Unbinder bind;
    private PopupWindow popupWindow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homepagerfragment,container,false);
        bind = ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iPersenter = new IPersenterImpl(this);
        iPersenter.showRequestData(UrlApis.BANNER_URI,BannerBean.class);
        init();
    }

    private void init() {
        iPersenter.showRequestData(UrlApis.HOME_NEW_DESIGN_URI,HomeBean.class);
        //热销新品
        newDesign();
        //魔力时尚
        fashion();
        //品质生活
        life();

    }
    private void life() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(),2);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewLife.setLayoutManager(manager);
        lifeAdapter = new LifeAdapter(getActivity());
        recyclerViewLife.setAdapter(lifeAdapter);
    }
    //魔力时尚
    private void fashion() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewFashion.setLayoutManager(manager);
        fashionAdapter = new FashionAdapter(getActivity());
        recyclerViewFashion.setAdapter(fashionAdapter);
    }
    //热销新品
    private void newDesign() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewDesign.setLayoutManager(manager);
        designAdapter = new NewDesignAdapter(getActivity());
        recyclerViewDesign.setAdapter(designAdapter);
    }
    //布局管理
    public void gridLayout(XRecyclerView re){
        GridLayoutManager manager = new GridLayoutManager(getActivity(),2);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        re.setLayoutManager(manager);
        re.setLoadingMoreEnabled(true);
        re.setPullRefreshEnabled(true);
    }
    public void linearLayou(RecyclerView re){
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        re.setLayoutManager(manager);
    }

    //点击点点点隐藏了scroll显示xrecycle
    public void setDatas(final int id){
        gridLayout(xRecyclerViewNew);
        homeShopAdapter = new HomeShopAdapter(getActivity());
        xRecyclerViewNew.setAdapter(homeShopAdapter);
        xRecyclerViewNew.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                iPersenter.showRequestData(String.format(UrlApis.SHOP_URI,id+"",page),HomeShopBean.class);
            }
            @Override
            public void onLoadMore() {
                iPersenter.showRequestData(String.format(UrlApis.SHOP_URI,id+"",page),HomeShopBean.class);
            }
        });
    }
    //点击事件
    @OnClick({R.id.new_design_image,R.id.fashion_image,R.id.life_image,R.id.serch_image,R.id.serch_btn,R.id.three_level})
    public void imageClick(View v){
        switch (v.getId()){
            case R.id.new_design_image:
                page = 1;
                scrollView.setVisibility(View.GONE);
                serchBtn.setVisibility(View.VISIBLE);
                edSerch.setVisibility(View.VISIBLE);
                serchImage.setVisibility(View.GONE);
                linearLayoutNewDesign.setVisibility(View.VISIBLE);
                textNewShow.setVisibility(View.VISIBLE);
                textFashionShow.setVisibility(View.GONE);
                textLifeShow.setVisibility(View.GONE);
                iPersenter.showRequestData(String.format(UrlApis.SHOP_URI,rxxpid+"",page),HomeShopBean.class);
                setDatas(rxxpid);
                break;
            case R.id.fashion_image:
                page = 1;
                scrollView.setVisibility(View.GONE);
                serchBtn.setVisibility(View.VISIBLE);
                serchImage.setVisibility(View.GONE);
                edSerch.setVisibility(View.VISIBLE);
                linearLayoutNewDesign.setVisibility(View.VISIBLE);
                textNewShow.setVisibility(View.GONE);
                textFashionShow.setVisibility(View.VISIBLE);
                textLifeShow.setVisibility(View.GONE);
                iPersenter.showRequestData(String.format(UrlApis.SHOP_URI,rxxpid+"",page),HomeShopBean.class);
                setDatas(mlssId);
                break;
            case R.id.life_image:
                page = 1;
                scrollView.setVisibility(View.GONE);
                serchBtn.setVisibility(View.VISIBLE);
                serchImage.setVisibility(View.GONE);
                edSerch.setVisibility(View.VISIBLE);
                linearLayoutNewDesign.setVisibility(View.VISIBLE);
                textNewShow.setVisibility(View.GONE);
                textFashionShow.setVisibility(View.GONE);
                textLifeShow.setVisibility(View.VISIBLE);
                iPersenter.showRequestData(String.format(UrlApis.SHOP_URI,rxxpid+"",page),HomeShopBean.class);
                setDatas(pzshId);
                break;
            case R.id.serch_image:
                serchBtn.setVisibility(View.VISIBLE);
                edSerch.setVisibility(View.VISIBLE);
                serchImage.setVisibility(View.GONE);
                break;
            case R.id.serch_btn:
                page = 1;
                String s = edSerch.getText().toString();
                if (s == null||s.equals("")||s.isEmpty()){
                    relativeLayoutNo.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.GONE);
                    xRecyclerView.setVisibility(View.GONE);
                    linearLayoutNewDesign.setVisibility(View.GONE);
                }else {
                    scrollView.setVisibility(View.GONE);
                    xRecyclerView.setVisibility(View.VISIBLE);
                    linearLayoutNewDesign.setVisibility(View.GONE);
                    iPersenter.showRequestData(String.format(UrlApis.HOME_SERCH, s, page), HomeserchBean.class);
                    setxRecyclerView(s);
                }
                break;
            case R.id.three_level:
                /*if (isShow) {
                    relativeLayoutOne.setVisibility(View.VISIBLE);
                    relativeLayoutTwo.setVisibility(View.VISIBLE);
                    linearLayou(recyclerViewOne);
                    oneLevelAdapter = new OneLevelAdapter(getActivity());
                    twoLevelAdapter = new TwoLevelAdapter(getActivity());
                    recyclerViewOne.setAdapter(oneLevelAdapter);

                    iPersenter.showRequestData(UrlApis.SHOP_ONE_LEVEL, OneLevelBean.class);
                    linearLayou(recyclerViewtwo);
                    recyclerViewtwo.setAdapter(twoLevelAdapter);
                    twoLevelClick();
                    iPersenter.showRequestData(String.format(UrlApis.SHOP_TWO_LEVEL,"1001002"),TwoLevelBean.class);
                }*/
                toastPop();
                popupWindow.showAsDropDown(imgLevel,0,25);
                iPersenter.showRequestData(UrlApis.SHOP_ONE_LEVEL, OneLevelBean.class);
                iPersenter.showRequestData(String.format(UrlApis.SHOP_TWO_LEVEL,"1001002"),TwoLevelBean.class);
                break;
                default:
                    break;
        }
    }
    //pop弹框
    private void toastPop() {

        // 用于PopupWindow的View
        View contentView=LayoutInflater.from(getActivity()).inflate(R.layout.homefragment_pop, null, false);
        recyclerViewOne = contentView.findViewById(R.id.one_level_rv);
        recyclerViewtwo = contentView.findViewById(R.id.two_level_rv);

        oneLevelAdapter = new OneLevelAdapter(getActivity());
        twoLevelAdapter = new TwoLevelAdapter(getActivity());
        linearLayou(recyclerViewOne);
        recyclerViewOne.setAdapter(oneLevelAdapter);

        linearLayou(recyclerViewtwo);
        recyclerViewtwo.setAdapter(twoLevelAdapter);

        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        // 设置PopupWindow是否能响应外部点击事件
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置此参数获得焦点，否则无法点击，即：事件拦截消费
        popupWindow.setFocusable(true);
        oneLevelAdapter.setOnClick(new OneLevelAdapter.OnClick() {
            @Override
            public void Click(String commodityId) {
                iPersenter.showRequestData(String.format(UrlApis.SHOP_TWO_LEVEL,commodityId),TwoLevelBean.class);
            }
        });

        twoLevelClick();
        popupWindow.dismiss();
    }

    //点击二级列表条目
    private void twoLevelClick() {
        twoLevelAdapter.setOnClick(new TwoLevelAdapter.OnClick() {
            @Override
            public void Click(final String id) {
                page = 1;
                gridLayout(xRecyclerView);
                scrollView.setVisibility(View.GONE);
                linearLayoutNewDesign.setVisibility(View.GONE);
                xRecyclerView.setVisibility(View.VISIBLE);
                twoLevelSerchAdapter = new TwoLevelSerchAdapter(getActivity());
                xRecyclerView.setAdapter(twoLevelSerchAdapter);
                xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                    @Override
                    public void onRefresh() {
                        page = 1;
                        iPersenter.showRequestData(String.format(UrlApis.SHOP_TWO_LEVEL_SERCH,id,page),TwoLevelSerchBean.class);
                        xRecyclerView.refreshComplete();
                    }
                    @Override
                    public void onLoadMore() {
                        iPersenter.showRequestData(String.format(UrlApis.SHOP_TWO_LEVEL_SERCH,id,page),TwoLevelSerchBean.class);
                        xRecyclerView.loadMoreComplete();
                    }
                });
                iPersenter.showRequestData(String.format(UrlApis.SHOP_TWO_LEVEL_SERCH,id,page),TwoLevelSerchBean.class);
                popupWindow.dismiss();
            }
        });
    }
    //搜索
    public void setxRecyclerView(final String s){
        gridLayout(xRecyclerView);
        homeSerchAdapter = new HomeSerchAdapter(getActivity());
        xRecyclerView.setAdapter(homeSerchAdapter);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                iPersenter.showRequestData(String.format(UrlApis.HOME_SERCH,s,page),HomeserchBean.class);
                xRecyclerView.refreshComplete();
            }
            @Override
            public void onLoadMore() {
                iPersenter.showRequestData(String.format(UrlApis.HOME_SERCH,s,page),HomeserchBean.class);
                xRecyclerView.loadMoreComplete();
            }
        });
    }
    //监听返回键
    public void getBackData(boolean back){
        if(back){
            linearLayoutNewDesign.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            xRecyclerView.setVisibility(View.GONE);
            edSerch.setVisibility(View.GONE);
            serchBtn.setVisibility(View.GONE);
            serchImage.setVisibility(View.VISIBLE);
            relativeLayoutNo.setVisibility(View.GONE);
        }
    }

    @Override
    public void startRequestData(Object data) {
        if (data instanceof BannerBean){
            BannerBean bannerBean = (BannerBean) data;
            pager.setData(bannerBean.getResult(),null);
            pager.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    BannerBean.ResultBean resultBean = (BannerBean.ResultBean) model;
                    Glide.with(getActivity()).load(resultBean.getImageUrl()).into((ImageView) view);
                }
            });
        }
        //热销新品
        if (data instanceof HomeBean){
            HomeBean bean = (HomeBean) data;
            List<HomeBean.ResultBean.RxxpBean.CommodityListBean> designlist = bean.getResult().getRxxp().get(0).getCommodityList();
            rxxpid = bean.getResult().getRxxp().get(0).getId();
            mlssId = bean.getResult().getMlss().get(0).getId();
            pzshId = bean.getResult().getPzsh().get(0).getId();
            designAdapter.setData(designlist);
            List<HomeBean.ResultBean.MlssBean.CommodityListBeanXX> fashionlist = bean.getResult().getMlss().get(0).getCommodityList();
            fashionAdapter.setData(fashionlist);
            List<HomeBean.ResultBean.PzshBean.CommodityListBeanX> lifelist = bean.getResult().getPzsh().get(0).getCommodityList();
            lifeAdapter.setData(lifelist);
        }
        if (data instanceof HomeShopBean){
            HomeShopBean shopBean = (HomeShopBean) data;

            if (page == 1) {
                homeShopAdapter.setData(shopBean.getResult());
            }else{
                homeShopAdapter.addData(shopBean.getResult());
            }
            page++;
            xRecyclerViewNew.loadMoreComplete();
            xRecyclerViewNew.refreshComplete();
        }//点击搜索
        if (data instanceof HomeserchBean){
            edSerch.setText("");
            HomeserchBean bean = (HomeserchBean) data;
            if (page == 1) {
                if (bean.getResult().size() == 0){
                    relativeLayoutNo.setVisibility(View.VISIBLE);
                }else{
                    relativeLayoutNo.setVisibility(View.GONE);
                }
                homeSerchAdapter.setData(bean.getResult());
            }else{
                homeSerchAdapter.addData(bean.getResult());
            }
            page++;
        }//一级列表
        if (data instanceof OneLevelBean){
            OneLevelBean bean = (OneLevelBean) data;
            oneLevelAdapter.setDate(bean.getResult());
        }//二级列表
        if (data instanceof TwoLevelBean){
            TwoLevelBean bean = (TwoLevelBean) data;
            twoLevelAdapter.setDate(bean.getResult());
        }//二级列表条目具体搜索
        if (data instanceof TwoLevelSerchBean){
            TwoLevelSerchBean bean = (TwoLevelSerchBean) data;
            if (page == 1) {
                twoLevelSerchAdapter.setData(bean.getResult());
            }else{
                twoLevelSerchAdapter.addData(bean.getResult());
            }
            page++;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iPersenter.onDestory();
    }
}
