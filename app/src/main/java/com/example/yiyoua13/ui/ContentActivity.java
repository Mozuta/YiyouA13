package com.example.yiyoua13.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.example.yiyoua13.R;
import com.example.yiyoua13.adapter.ImageAdapter;
import com.example.yiyoua13.utils.StatusBarUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class ContentActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout ll_tag;
    private TextView tag_text;
    private String id;
    private MaterialRatingBar ratingBar;
    private EditText et_content,et_title;

    private static final int REQUEST_CODE = 0x00000011;
    private static final int PERMISSION_WRITE_EXTERNAL_REQUEST_CODE = 0x00000012;

    private RecyclerView rvImage;
    private SharedPreferences sp;
    private String user_token,user_id;
    private ImageAdapter mAdapter;
    private Button submit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contentex);
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }
        sp = this.getSharedPreferences("Login", MODE_PRIVATE);
        //user_token=TOKEN
        user_token=sp.getString("TOKEN","");
        user_id=sp.getString("USER_ID","");
        ratingBar = findViewById(R.id.ex_rb);
        et_content = findViewById(R.id.et_content);
        et_title = findViewById(R.id.et_title);

        submit = findViewById(R.id.btn_submit);
        submit.setOnClickListener(this);
        ll_tag = findViewById(R.id.ll_tag);
        tag_text = (TextView) findViewById(R.id.text_tag);
        rvImage = findViewById(R.id.rv_image);
        rvImage.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new ImageAdapter(this);
        rvImage.setAdapter(mAdapter);

        findViewById(R.id.ll_tag).setOnClickListener(this);
        findViewById(R.id.btn_single).setOnClickListener(this);
        findViewById(R.id.btn_limit).setOnClickListener(this);
        findViewById(R.id.btn_unlimited).setOnClickListener(this);
        findViewById(R.id.btn_clip).setOnClickListener(this);
        findViewById(R.id.btn_only_take).setOnClickListener(this);
        findViewById(R.id.btn_take_and_clip).setOnClickListener(this);

        int hasWriteExternalPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteExternalPermission == PackageManager.PERMISSION_GRANTED) {
            //预加载手机图片。加载图片前，请确保app有读取储存卡权限
            com.donkingliang.imageselector.utils.ImageSelector.preload(this);
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            ArrayList<String> images = data.getStringArrayListExtra(com.donkingliang.imageselector.utils.ImageSelector.SELECT_RESULT);
            boolean isCameraImage = data.getBooleanExtra(com.donkingliang.imageselector.utils.ImageSelector.IS_CAMERA_IMAGE, false);
//            Log.d("ImageSelector", "是否是拍照图片：" + isCameraImage);
            mAdapter.refresh(images);
        }


        //接受返回的数据
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String tag = data.getStringExtra("tag");
            id = data.getStringExtra("id");

            tag_text.setText(tag);
        }
    }

    /**
     * 处理权限申请的回调。
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_WRITE_EXTERNAL_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //预加载手机图片
                ImageSelector.preload(this);
            } else {
                //拒绝权限。
            }
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_single:
                //单选
                com.example.yiyoua13.utils.ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(true)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .start(this, REQUEST_CODE); // 打开相册
                break;

            case R.id.btn_limit://现在有用的
                //多选(最多9张)
                com.example.yiyoua13.utils.ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
                        .start(this, REQUEST_CODE); // 打开相册
                break;

            case R.id.btn_unlimited:
                //多选(不限数量)
                com.example.yiyoua13.utils.ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .setMaxSelectCount(0) // 图片的最大选择数量，小于等于0时，不限数量。
                        .start(this, REQUEST_CODE); // 打开相册
                break;

            case R.id.btn_clip:
                //单选并剪裁
                com.example.yiyoua13.utils.ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setCrop(true)  // 设置是否使用图片剪切功能。
                        .setCropRatio(1.0f) // 图片剪切的宽高比,默认1.0f。宽固定为手机屏幕的宽。
                        .setSingle(true)  //设置是否单选
                        .canPreview(true) //是否点击放大图片查看,，默认为true
                        .start(this, REQUEST_CODE); // 打开相册
                break;

            case R.id.btn_only_take:
                //仅拍照
                com.example.yiyoua13.utils.ImageSelector.builder()
                        .onlyTakePhoto(true)  // 仅拍照，不打开相册
                        .start(this, REQUEST_CODE);
                break;

            case R.id.btn_take_and_clip:
                //拍照并剪裁
                com.example.yiyoua13.utils.ImageSelector.builder()
                        .setCrop(true) // 设置是否使用图片剪切功能。
                        .setCropRatio(1.0f) // 图片剪切的宽高比,默认1.0f。宽固定为手机屏幕的宽。
                        .onlyTakePhoto(true)  // 仅拍照，不打开相册
                        .start(this, REQUEST_CODE);
                break;

            case R.id.ll_tag:
                //跳转到标签选择页面
                Intent intent = new Intent(this, TagActivity.class);
                startActivityForResult(intent, 1);

                break;
            case R.id.btn_submit:

                    //发布
                    if (tag_text.getText().toString().equals("请选择标签")){
                        Toast.makeText(this, "请选择标签", Toast.LENGTH_SHORT).show();
                    } else if (et_content.getText().toString().equals("")) {
                        Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();

                    } else if (et_title.getText().toString().equals("")) {
                        Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
                    } else if (et_title.getText().toString().length() > 20) {
                        Toast.makeText(this, "标题不能超过20字", Toast.LENGTH_SHORT).show();
                    } else if (et_content.getText().toString().length() > 200) {
                        Toast.makeText(this, "内容不能超过200字", Toast.LENGTH_SHORT).show();

                    } else if (et_content.getText().toString().length() < 10) {
                        Toast.makeText(this, "内容不能少于10字", Toast.LENGTH_SHORT).show();
                    } else if (et_title.getText().toString().length() < 5) {
                        Toast.makeText(this, "标题不能少于5字", Toast.LENGTH_SHORT).show();
                    } else if (ratingBar.getRating() == 0) {
                        Toast.makeText(this, "请给出评分", Toast.LENGTH_SHORT).show();

                    } else {
                        if (mAdapter.getItemCount() == 0){
                            Toast.makeText(this, "请选择图片", Toast.LENGTH_SHORT).show();
                        }else {
                            List<File> files = new ArrayList<>();
                            //上传图片
                            for (int i = 0; i < mAdapter.getItemCount(); i++) {
                                File file = new File(mAdapter.getImages().get(i));
                                files.add(file);
                            }
                            int rating = (int) (ratingBar.getRating() * 10);
                            Url_Request.sendRequestBlogSaveBlog(Url_Request.getUrl_head()+"/blog/saveBlog", user_token,String.valueOf(rating),id,user_id,et_title.getText().toString(),et_content.getText().toString(),files);
                            Toast.makeText(this, "上传成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    break;
        }
    }

}