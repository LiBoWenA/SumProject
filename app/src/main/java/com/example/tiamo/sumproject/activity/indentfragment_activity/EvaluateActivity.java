package com.example.tiamo.sumproject.activity.indentfragment_activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tiamo.sumproject.R;
import com.example.tiamo.sumproject.UrlApis;
import com.example.tiamo.sumproject.Validator;
import com.example.tiamo.sumproject.adapter.RecycleImageAdpter;
import com.example.tiamo.sumproject.bean.EventBean;
import com.example.tiamo.sumproject.bean.homefragmentbean.HomePageDetailsBean;
import com.example.tiamo.sumproject.bean.indentfragmentbean.IndentBean;
import com.example.tiamo.sumproject.persenter.IPersenterImpl;
import com.example.tiamo.sumproject.view.IView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EvaluateActivity extends AppCompatActivity implements IView {

    @BindView(R.id.pl_img)
    ImageView plImg;
    @BindView(R.id.pl_title)
    TextView plTitle;
    @BindView(R.id.pl_price)
    TextView plPrice;
    @BindView(R.id.ed_pl)
    EditText edPl;
    @BindView(R.id.radio_pl)
    RadioButton radioPl;
    @BindView(R.id.sucess_pl)
    Button sucessPl;
    @BindView(R.id.issue_image_recycle)
    RecyclerView imgaeRecycle;
    private IPersenterImpl iPersenter;
    private int id;
    private int num = 0;
    private AlertDialog avatarBuild;
    private final int REQUEST_PICK = 200;
    private final int REQUEST_CAMEAR = 100;
    private final int REQUEST_PICTRUE = 300;
    private final String PATH_FILE=Environment.getExternalStorageDirectory()+"/file.png";
    private String path=Environment.getExternalStorageDirectory()+"/header_image.png";
    private String imageFiel;
    private String orid;
    private File file;
    private RecycleImageAdpter imageAdpter;
    private List<File> file_list=new ArrayList<>();
    private boolean checked;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        ButterKnife.bind(this);
        iPersenter = new IPersenterImpl(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        orid = intent.getStringExtra("orid");
        iPersenter.showRequestData(String.format(UrlApis.HOMEPAGE_DETAILS, id), HomePageDetailsBean.class);
        initImageAdpter();
        getUserAvatar();
        oneImageClick();
    }

    //设置图片的list集合和适配器
    private void  initImageAdpter(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        imgaeRecycle.setLayoutManager(gridLayoutManager);
        imageAdpter = new RecycleImageAdpter(this);
        imgaeRecycle.setAdapter(imageAdpter);
    }
    //imageAdpter的条目点击事件
    public void  oneImageClick(){
        imageAdpter.getImage(new RecycleImageAdpter.ImageClick() {
            @Override
            public void getdata() {
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
                    ActivityCompat.requestPermissions(EvaluateActivity.this, mStatenetwork, 100);
                }
                getPopupWindow();
            }
        });
    }

    private void getPopupWindow() {
        //弹出Alert
        View view = View.inflate(this, R.layout.myfragment_popwindow, null);
        avatarBuild = new AlertDialog.Builder(this).setView(view).show();
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
                //intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION |Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(intent, REQUEST_CAMEAR);
                avatarBuild.dismiss();
            }
        });
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
            if (data!=null)
            {
                Bitmap bitmap = data.getParcelableExtra("data");
                //将bitmap转换成uri
                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,null,null));
                try {
                    Validator.saveBitmap(bitmap,PATH_FILE,50);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                List<Object>  list = new ArrayList<>();
                list.add(bitmap);
                imageAdpter.setmList(list);
                file = new File(PATH_FILE);
                file_list.add(file);
            }
        }
    }

    //上传头像
    private void getUserAvatar() {
        //发表
        sucessPl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = edPl.getText().toString();
                checked = radioPl.isChecked();
                if (checked){
                    Map<String,String> maps=new HashMap<>();
                    maps.put("commodityId",id+"");
                    maps.put("content",content);
                    iPersenter.postImagesCon(UrlApis.PUT_CIECLE,maps,file_list,IndentBean.class);
                }else{
                    Map<String, String> map = new HashMap<>();
                    map.put("commodityId", id + "");
                    map.put("orderId", orid);
                    map.put("content", content);
                    iPersenter.postImagesCon(UrlApis.PUT_COMMENT,map,file_list,IndentBean.class);
                }
            }
        });
    }

    @Override
    public void startRequestData(Object data) {
        //商品详情
        if (data instanceof HomePageDetailsBean) {
            HomePageDetailsBean bean = (HomePageDetailsBean) data;
            //商品详细信息
            String[] split = bean.getResult().getPicture().split("\\,");
            Glide.with(this).load(split[0]).into(plImg);
            plPrice.setText("¥：" + bean.getResult().getPrice());
            plTitle.setText(bean.getResult().getCategoryName());
        } else if (data instanceof IndentBean) {
            IndentBean bean = (IndentBean) data;
            if (bean.getStatus().equals("0000")) {
                EventBus.getDefault().post(new EventBean(77));
                Toast.makeText(this, bean.getMessage().toString(), Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this,bean.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
