package com.example.tiamo.sumproject.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tiamo.sumproject.MyApp;
import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.UrlApis;
import com.example.tiamo.sumproject.Validator;
import com.example.tiamo.sumproject.activity.myfragment_activity.AddressActivity;
import com.example.tiamo.sumproject.activity.myfragment_activity.CircleActivity;
import com.example.tiamo.sumproject.activity.myfragment_activity.FootActivity;
import com.example.tiamo.sumproject.activity.myfragment_activity.MoneyActivity;
import com.example.tiamo.sumproject.activity.myfragment_activity.PersonActivity;
import com.example.tiamo.sumproject.bean.myfragmentbean.MyBean;
import com.example.tiamo.sumproject.bean.myfragmentbean.MyPhoneBean;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class MyFragment extends Fragment implements IView {

    private Unbinder bind;
    @BindView(R.id.grzl_text)
    TextView textPersonData;
    @BindView(R.id.wdqz_text)
    TextView textMyCircle;
    @BindView(R.id.zj_text)
    TextView textFoot;
    @BindView(R.id.wdqb_text)
    TextView textMoney;
    @BindView(R.id.shdz_text)
    TextView textAddress;
    @BindView(R.id.my_head_img)
    ImageView imgMyHead;
    @BindView(R.id.my_head_name)
    TextView textHeadName;
    IPersenterImpl iPersenter;
    private AlertDialog avatarBuild;
    private final int REQUEST_PICK = 200;
    private final int REQUEST_CAMEAR = 100;
    private final int REQUEST_PICTRUE = 300;
    private final String PATH_FILE=Environment.getExternalStorageDirectory()+"/file.png";
    private final String path =Environment.getExternalStorageDirectory()+ "/image.png";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myfragment,null);
        bind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iPersenter = new IPersenterImpl(this);
        iPersenter.showRequestData(UrlApis.SELECT_USER,MyBean.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        iPersenter.showRequestData(UrlApis.SELECT_USER,MyBean.class);
    }

    @OnClick({R.id.grzl_text,R.id.wdqz_text,R.id.zj_text,R.id.wdqb_text,R.id.shdz_text,R.id.my_head_img})
    public void setTextClick(View v){
        switch (v.getId()){
            case R.id.grzl_text:
                //个人资料
                Intent intent = new Intent(getActivity(),PersonActivity.class);
                startActivity(intent);
                break;
            case R.id.wdqz_text:
                //我的圈子
                Intent intentCir = new Intent(getActivity(),CircleActivity.class);
                startActivity(intentCir);
                break;
            case R.id.zj_text:
                //足迹
                Intent intentFoot = new Intent(getActivity(),FootActivity.class);
                startActivity(intentFoot);
                break;
            case R.id.wdqb_text:
                //我的钱包
                Intent intentMoney = new Intent(getActivity(),MoneyActivity.class);
                startActivity(intentMoney);
                break;
            case R.id.shdz_text:
                //收货地址
                Intent intent1 = new Intent(getActivity(),AddressActivity.class);
                startActivity(intent1);
                break;
            case R.id.my_head_img:
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                    String[] mStatenetwork = new String[]{
                            //写的权限
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            //读的权限
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            //入网权限
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            //WIFI权限
                            Manifest.permission.ACCESS_WIFI_STATE,
                            //读手机权限
                            Manifest.permission.READ_PHONE_STATE,
                            //网络权限
                            Manifest.permission.INTERNET,
                            //相机
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_APN_SETTINGS,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                    };
                    ActivityCompat.requestPermissions(getActivity(), mStatenetwork, 100);
                }

                //点击头像更换头像
                getPopupWindow();
                break;
                default:
                    break;
        }
    }

    private void getPopupWindow() {
        //弹出Alert
        View view = View.inflate(getActivity(), R.layout.myfragment_popwindow, null);
        avatarBuild = new AlertDialog.Builder(getContext()).setView(view).show();
        TextView pick = view.findViewById(R.id.pick);
        TextView camera = view.findViewById(R.id.camera);
        //相册
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_PICK);
                avatarBuild.dismiss();
            }
        });
        //相机
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
                startActivityForResult(intent, REQUEST_CAMEAR);
                avatarBuild.dismiss();
            }
        });
    }

    //上传头像
    private void getUserAvatar(Map<String,String> map){
        iPersenter.postImgShowRequestData(UrlApis.USER_IMAGE,map,MyPhoneBean.class);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMEAR && resultCode == RESULT_OK) {
            //打开裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            //将图片设置给裁剪
            intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
            //设置是否支持裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置输出后图片大小
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
            //返回到data
            intent.putExtra("return-data", true);
            //启动
            startActivityForResult(intent, REQUEST_PICTRUE);

        }
        if (requestCode == REQUEST_PICK && resultCode == RESULT_OK) {
            //打开裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            Uri uri = data.getData();
            //将图片设置给裁剪
            intent.setDataAndType(uri, "image/*");
            //设置是否可裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置输出
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
            //返回data
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_PICTRUE);
        }
        //获取剪切完的图片数据 bitmap
        if (requestCode == REQUEST_PICTRUE && resultCode == RESULT_OK) {
            Bitmap bitmap =data.getParcelableExtra("data");
            try {
                Validator.saveBitmap(bitmap,PATH_FILE,50);
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("TAG",e.getMessage());
            }
            Map<String,String> map=new HashMap<>();
            map.put("image",PATH_FILE);
            getUserAvatar(map);
        }
    }

    @Override
    public void startRequestData(Object data) {
        if (data instanceof MyBean){
            MyBean myBean = (MyBean) data;
            textHeadName.setText(myBean.getResult().getNickName());
            Glide.with(getActivity()).load(myBean.getResult().getHeadPic()).into(imgMyHead);
        }
        if (data instanceof MyPhoneBean){
            MyPhoneBean bean = (MyPhoneBean) data;
            if (bean.getMessage().equals("上传成功")){
                Uri uri=Uri.parse(bean.getHeadPath());
                Glide.with(getActivity()).load(uri).into(imgMyHead);
                Toast.makeText(getContext(), "上传成功", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "上传失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
