package com.example.yiyoua13.ui;

import static android.content.ContentValues.TAG;
import static com.amap.api.maps.model.BitmapDescriptorFactory.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yiyoua13.FragAct;
import com.example.yiyoua13.R;
import com.example.yiyoua13.utils.StatusBarUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.EasyPermissions;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "InfoActivity";
    private Button logout;

    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    private Bitmap head;// 头像Bitmap
    private ConstraintLayout mLayout1;
    private ConstraintLayout mLayout2;
    private ConstraintLayout mLayout3;
    private ConstraintLayout mLayout4;
    private ConstraintLayout mLayout5;
    private ConstraintLayout mLayout6;
    private ConstraintLayout mLayout7;
    private CircleImageView mImage1;

    private SharedPreferences sp;

    private TextView mText1;

    String user_id,user_token;
    private TextView mText2;
    private TextView mText3;
    private TextView mText4;
    private TextView mText5;
    private TextView mText6;
    private ImageView back_btn;
    Calendar calendar = Calendar.getInstance(Locale.CHINA);
    public class DestroyActivityUtil {
        public List<Activity> activityList = new LinkedList();       //将要销毁的Activity事先存在这个集合中
        public void exit()              //  启动退出程序的按钮时，调用该方法，遍历一遍集合，销毁所有的Activity
        {
            for(Activity act:activityList)
            {
                Log.d("TAGS",act.toString());
                act.finish();
            }
            System.exit(0);
        }
    }
    public static void showDatePickerDialog(Activity activity, int themeResId, final TextView tv, final Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                tv.setText("" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + "");

            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }
        init();
    }
    public void init(){
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Url_Request.sendRequestLogout(Url_Request.getUrl_head()+"/user/logout",user_token);
                sp = getSharedPreferences("Login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(InfoActivity.this, FragAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);		//将DengLuActivity至于栈顶
                startActivity(intent);
                DestroyActivityUtil destroyActivityUtil = new DestroyActivityUtil();
                destroyActivityUtil.exit();
            }
        });
        back_btn = findViewById(R.id.back_info);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mLayout1 = findViewById(R.id.one);
        mLayout2 = findViewById(R.id.two);
        mLayout3 = findViewById(R.id.three);
        mLayout4 = findViewById(R.id.four);
        mLayout5 = findViewById(R.id.five);
        mLayout6 = findViewById(R.id.six);
        mLayout7 = findViewById(R.id.seven);
        mLayout1.setOnClickListener(this);
        mLayout2.setOnClickListener(this);
        mLayout3.setOnClickListener(this);
        mLayout4.setOnClickListener(this);
        mLayout5.setOnClickListener(this);
        mLayout6.setOnClickListener(this);
        mLayout7.setOnClickListener(this);
        mImage1 = findViewById(R.id.head_image);
        mText1 = findViewById(R.id.name_det);
        mText2 = findViewById(R.id.sex_det);
        mText3 = findViewById(R.id.birth_det);
        mText4 = findViewById(R.id.info_det);
        mText5 = findViewById(R.id.permission_det);
        mText6 = findViewById(R.id.feedback_det);

        sp = this.getSharedPreferences("Login", MODE_PRIVATE);
        //user_token=TOKEN
        user_token=sp.getString("TOKEN","");
        user_id=sp.getString("USER_ID","");
        Url_Request.sendRequestUserMe(Url_Request.getUrl_head()+"/user/me",user_token,InfoActivity.this, new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                final Url_Request.User_info user_me = (Url_Request.User_info) bean;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        user_id=user_me.getUser_id();
                        Glide.with(InfoActivity.this).load(user_me.getIcon()).into(mImage1);
                    }
                });
            }
        });
        Url_Request.sendRequestinfo_id(Url_Request.getUrl_head()+"/user/info", user_token, user_id, new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                final Url_Request.User_info infoBean = (Url_Request.User_info) bean;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mText1.setText(infoBean.getUserName());
                        if (infoBean.getGender().equals("0"))
                            mText2.setText("男");
                        else
                            mText2.setText("女");
                        mText3.setText(infoBean.getBirthday());
                        mText4.setText(infoBean.getIntroduce());
                    }
                });
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("circleCrop", true);
        intent.putExtra("return-data", true);
        //裁剪成圆形

        startActivityForResult(intent, 3);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {

                    Log.d(TAG,"！！！！！！！！！！！");

                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {

                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        Log.e(TAG,head.toString());
                        /**
                         * 上传服务器代码
                         */
                        File file = new File("/storage/emulated/0/Android/data/com.example.yiyoua13/files/Pictures/head.jpg");
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(file);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        head.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        try {
                            fos.flush();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            fos.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Url_Request.sendRequestUpdateIcon(Url_Request.getUrl_head()+"/user/updateIcon", user_token, file);
                        //setPicToView(head);// 保存在SD卡中
                        Log.e(TAG,head.toString());
                        mImage1.setImageBitmap(head);// 用ImageButton显示出来
                    }
                }
                break;
            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    /*private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            Log.e(TAG,"！！！！！！！！！！！");
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();// 创建文件夹
        }

        //file.mkdirs();// 创建文件夹
        String fileName = path + "head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);

            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流

                if (b != null) {
                    b.flush();
                    b.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    //返回键
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Url_Request.User_info user_info=new Url_Request.User_info();
        user_info.setUser_id(user_id);
        user_info.setUserName(mText1.getText().toString());
        if (mText2.getText().toString().equals("男"))
            user_info.setGender("0");
        else
            user_info.setGender("1");
        String[] dateArr = mText3.getText().toString().split("-");
        String year = dateArr[0];
        String month = String.format("%02d", Integer.parseInt(dateArr[1]));
        String day = String.format("%02d", Integer.parseInt(dateArr[2]));
        String newDateStr = year + "-" + month + "-" + day;
        user_info.setBirthday(newDateStr);
        user_info.setIntroduce(mText4.getText().toString());


        Url_Request.sendRequestSetInfo(Url_Request.getUrl_head()+"/user/setInfo", user_token, user_info);

        finish();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.one:
                new XPopup.Builder(this)
                        //.maxWidth(600)
                        .asCenterList("请选择一项", new String[]{"从相册中选择", "拍照"},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        //从相册中选择
                                        if (position == 0) {
                                            if (ContextCompat.checkSelfPermission(InfoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                                    != PackageManager.PERMISSION_GRANTED) {
                                                ActivityCompat.requestPermissions(InfoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                                        READ_EXTERNAL_STORAGE_REQUEST_CODE);
                                            }

                                            //申请权限
                                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                            startActivityForResult(intent, 1);
                                            //返回图片加载到imageview


                                        }
                                        //拍照
                                        else {
                                            if (ContextCompat.checkSelfPermission(InfoActivity.this, Manifest.permission.CAMERA)
                                                    != PackageManager.PERMISSION_GRANTED) {
                                                ActivityCompat.requestPermissions(InfoActivity.this, new String[]{Manifest.permission.CAMERA},
                                                        CAMERA_REQUEST_CODE);

                                            }
                                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                                            startActivityForResult(intent, 2);


                                        }
                                    }
                                })
                        .show();
                break;
            case R.id.two:
                //只能单行输入

                new XPopup.Builder(this).asInputConfirm("请选择您的昵称", "昵称不超过十个字",
                                new OnInputConfirmListener() {

                                    @Override
                                    public void onConfirm(String text) {
                                        mText1.setText(text);
                                    }
                                })
                        .show();


                break;
            case R.id.three:
                new XPopup.Builder(this)
                        //.maxWidth(600)
                        .asCenterList("请选择一项", new String[]{"男", "女"},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        mText2.setText(text);
                                    }
                                })
                        .show();
                break;
            case R.id.four:
                //选择日期
                showDatePickerDialog(this, 0, mText3, calendar);

                break;
            case R.id.five:
                new XPopup.Builder(this).asInputConfirm("介绍一下你自己吧~", "不超过50字哦",
                                new OnInputConfirmListener() {

                                    @Override
                                    public void onConfirm(String text) {
                                        mText4.setText(text);
                                    }
                                })
                        .show();
                break;
            case R.id.six:
                new XPopup.Builder(this)
                        //.maxWidth(600)
                        .asCenterList("请选择一项", new String[]{"启用景点播报", "关闭景点播报"},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        if (position == 0){
                                            mText5.setText("已开启");
                                            SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                                            editor.putBoolean("isPlay", true);
                                            editor.apply();

                                        }
                                        else{
                                            mText5.setText("已关闭");
                                            SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                                            editor.putBoolean("isPlay", false);
                                            editor.apply();
                                        }

                                    }
                                })
                        .show();
                break;
            case R.id.seven:
                new XPopup.Builder(this).asInputConfirm("敬请反馈您的需求和我们的不足！", "我们会努力改进哒！",
                                new OnInputConfirmListener() {

                                    @Override
                                    public void onConfirm(String text) {
                                        mText6.setText("已反馈")  ;
                                        //直接发送到服务器
                                        Url_Request.sendRequestFeedBackSend(Url_Request.getUrl_head()+"/feedBack/send", user_token, text.toString());
                                    }
                                })
                        .show();
                break;
        }
    }
}