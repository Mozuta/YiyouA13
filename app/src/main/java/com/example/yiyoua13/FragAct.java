package com.example.yiyoua13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yiyoua13.task.ButtonLoaderTask;
import com.example.yiyoua13.task.ImageLoaderTask;
import com.example.yiyoua13.task.TextLoaderTask;
import com.example.yiyoua13.ui.InfoActivity;
import com.example.yiyoua13.ui.InterestActivity;
import com.example.yiyoua13.ui.Url_Request;
import com.example.yiyoua13.utils.StatusBarUtil;
import com.example.yiyoua13.variousclass.Login;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FragAct extends Activity {
    String url_code="http://192.168.79.206:8081/user/code";
    String url_login="http://192.168.79.206:8081/user/login";
    String url_userme="http://192.168.79.206:8081/user/me";
    String value="8d3c1513fcb8436ca79039f46847d216";
    private Button bt;
    private Button test;
    private SharedPreferences sp;
    private ImageView imageView;
    private String edit_userNameValue,edit_passwordValue,user_token,user_id,terminal_id;
    private EditText et;
    private EditText et_2;
    private CheckBox remember;
    private Button ask_code;
    private View view;
    private TextView responseText;
    private void sendRequestWithOkHttp(String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String phone=et.getText().toString();
                    FormBody formBody = new FormBody.Builder().add("phone",phone).build();
                    final Request request = new Request.Builder()
                            .url(url)
                            .post(formBody)
                            .build();//创建Request 对象
                    Call call=client.newCall(request);
                    call.enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, java.io.IOException e) {
                            Log.d("TAG", "onFailure: ");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws java.io.IOException {

                            String responseData = response.body().string();
                            parseJSONWithGSON(responseData);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void sendRequestWithOkHttp_login(String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    OkHttpClient client = new OkHttpClient();
                    String phone=et.getText().toString();
                    String code=et_2.getText().toString();
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("phone", phone);
                    jsonObject.addProperty("code", code);
                    jsonObject.addProperty("terminalId",terminal_id);
                    Log.e("TAG", "run: "+jsonObject.toString() );
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                    FormBody formBody = new FormBody.Builder().add("phone",phone).add("code",code).build();
                    final Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();//创建Request 对象
                    Call call=client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("TAG", "onFailure: ");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            String responseData = response.body().string();
                            parseJSONWithGSON_login(responseData);
                        }

                        private void parseJSONWithGSON_login(String responseData) {
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("FragAct", "parseJSONWithGSON: "+success);
                            Log.d("FragAct", "parseJSONWithGSON: "+errormsg);
                            String data=(String) user.getData();
                            //String user_id=user.g();
                            //判断是否为空
                            if(data!=null){
                                Log.d("jieshou", "parseJSONWithGSON: "+data);
                                SharedPreferences.Editor editor=sp.edit();
                                editor.putString("TOKEN",data);
                                //editor.putString("USER_ID",user_id);
                                editor.apply();

                            }


                            if(success.equals("true")){//加入判断登陆状态！！！！！！！！！！！！！！！！！！！
                                SharedPreferences sp2=getSharedPreferences("Login",MODE_PRIVATE);
                                if (sp2.getString("if_tag","").equals("true")){
                                    Intent intent=new Intent(FragAct.this, bottomnavi.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Intent intent=new Intent(FragAct.this, InterestActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(FragAct.this,errormsg,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public class User{
        private Boolean success;
        private String errorMsg="default";
        private Object data;
        private Long total;
        public Boolean getSuccess() {
            return success;
        }
        public void setSuccess(Boolean success) {
            this.success = success;
        }
        public String getErrormsg() {
            return errorMsg;
        }
        public void setErrormsg(String errormsg) {
            this.errorMsg = errormsg;
        }
        public Object getData() {
            return data;
        }
        public void setData(Object data) {
            this.data = data;
        }
        public Long getTotal() {
            return total;
        }
        public void setTotal(Long total) {
            this.total = total;
        }


    }
    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        User user = gson.fromJson(jsonData, User.class);
        String success=user.getSuccess().toString();
        String errormsg=user.getErrormsg();
        /*String data=user.getData().toString();
        String total=user.getTotal().toString();*/
        Log.d("FragAct", "parseJSONWithGSON: "+success);
        Log.d("FragAct", "parseJSONWithGSON: "+errormsg);
        /*Log.d("FragAct", "parseJSONWithGSON: "+data);
        Log.d("FragAct", "parseJSONWithGSON: "+total);*/
        /*List<Result> appList = gson.fromJson(jsonData, new TypeToken<List<Login>>(){}.getType());
        for (Result app : appList) {
            Toast.makeText(FragAct.this, app.success.toString(), Toast.LENGTH_SHORT).show();
        }*/






    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag);
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
       if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
            WindowInsetsController controller = getWindow().getInsetsController();
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars());
                controller.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        // 协调状态栏和应用栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsControllerCompat controller = WindowInsetsControllerCompat
                    .getWindowInsetsController(getWindow().getDecorView());
            controller.setSystemBarsAppearance(0, WindowInsetsControllerCompat.APPEARANCE_LIGHT_STATUS_BARS);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }*/
        imageView=(ImageView)findViewById(R.id.iv_1);


        SharedPreferences sharedPreferences = getSharedPreferences("track", MODE_PRIVATE);
        sp = this.getSharedPreferences("Login", MODE_PRIVATE);
        terminal_id = String.valueOf(sharedPreferences.getLong("terminalId", 0));
        Log.d("terminal_id", "onCreate: "+terminal_id);
        //user_token=TOKEN
        user_token=sp.getString("TOKEN","");
        user_id=sp.getString("USER_ID","");
        //user_id=ID





        remember = (CheckBox) findViewById(R.id.cb_1);

        et_2=(EditText)findViewById(R.id.et_2);
        et=(EditText)findViewById(R.id.et_1);
        ask_code=(Button)findViewById(R.id.check_code);
        bt=(Button)findViewById(R.id.loginbt);
        if(sp.getBoolean("ISCHECK",false)){
            remember.setChecked(true);
            et.setText(sp.getString("USER_NAME",""));
            et_2.setText(sp.getString("PASSWORD",""));
        }
        ask_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取输入的手机号Url_Request.getUrl_head()+"/user/me"
                sendRequestWithOkHttp(url_code);//验证码
                //Url_Request.sendRequestModelRunDistinguish();
                /*Url_Request.sendRequestinfo_id(Url_Request.getUrl_head() + "/user/info", user_token, user_id, new Url_Request.OnIconResponseListener() {


                    @Override
                    public void onBeanResponse(Object bean) {
                        Url_Request.User_info user_info = (Url_Request.User_info) bean;
                        Log.d("FragAct", "onBeanResponse: " + user_info.getUserName());
                        Log.d("FragAct", "onBeanResponse: " + user_info.getBirthday());
                        Log.d("FragAct", "onBeanResponse: " + user_info.getGender());
                        //Log.d("FragAct", "onBeanResponse: " + user_info.getIcon());
                        Log.d("FragAct", "onBeanResponse: " + user_info.getUser_id());
                        Log.d("FragAct", "onBeanResponse: " + user_info.getFans());
                        Log.d("FragAct", "onBeanResponse: " + user_info.getFollowee());
                        Log.d("FragAct", "onBeanResponse: " + user_info.getIntroduce());



                    }
                });//获取用户信息（现在是自己）*/
                //string转换成url
                //Url_Request.sendRequestLogout(Url_Request.getUrl_head()+"/user/logout", user_token);//退出登录(退出之后再退出会g)
                /*Url_Request.sendRequestUserMe(Url_Request.getUrl_head()+"/user/me", user_token, FragAct.this,new Url_Request.OnIconResponseListener() {

                    @Override
                    public void onBeanResponse(Object bean) {
                        Url_Request.User_info user_info = (Url_Request.User_info) bean;
                        Log.d("FragAct", "onBeanResponse: " + user_info.getUser_id());
                        Log.d("FragAct", "onBeanResponse: " + user_info.getIcon());
                        Log.d("FragAct", "onBeanResponse: " + user_info.getUserName());
                    }
                });*/
                /*Url_Request.sendRequestBasicinfo_id(Url_Request.getUrl_head()+"/user/"+user_id, user_token, user_id, new Url_Request.OnIconResponseListener() {
                    @Override
                    public void onBeanResponse(Object bean) {   //获取用户信息（现在是自己）
                        Url_Request.User_info user_info = (Url_Request.User_info) bean;
                        Log.d("FragAct", "onBeanResponse: " + user_info.getUser_id());
                        Log.d("FragAct", "onBeanResponse: " + user_info.getIcon());
                        Log.d("FragAct", "onBeanResponse: " + user_info.getUserName());
                    }
                });//获取用户信息（现在是自己）*/
                /*Url_Request.sendRequestCollection(Url_Request.getUrl_head()+"/user/collection/"+"1", user_token, user_id, new Url_Request.OnIconResponseListener() {
                    @Override
                    public void onBeanResponse(Object bean) {   //获取用户信息（现在是自己）
                        List<Url_Request.Collection> collection = (List<Url_Request.Collection>) bean;
                        Log.d("FragAct", "onBeanResponse: " + (collection.get(0).getId()));
                        Log.d("FragAct", "onBeanResponse: " + collection.get(0).getUserid());
                        Log.d("FragAct", "onBeanResponse: " + collection.get(0).getAttractionid());
                        Log.d("FragAct", "onBeanResponse: " + collection.get(0).getAttraction().getId());
                        Log.d("FragAct", "onBeanResponse: " + collection.get(0).getAttraction().getName());
                        Log.d("FragAct", "onBeanResponse: " + collection.get(0).getAttraction().getTypeid());
                        Log.d("FragAct", "onBeanResponse: " + collection.get(0).getAttraction().getArea());
                        Log.d("FragAct", "onBeanResponse: " + collection.get(0).getAttraction().getAddress());
                        Log.d("FragAct", "onBeanResponse: " + collection.get(0).getAttraction().getX());
                        Log.d("FragAct", "onBeanResponse: " + collection.get(0).getAttraction().getY());
                        Log.d("FragAct", "onBeanResponse: " + collection.get(0).getAttraction().getPrice());
                        Log.d("FragAct", "onBeanResponse: " + collection.get(0).getAttraction().getScore());
                        Log.d("FragAct", "onBeanResponse: " + collection.get(0).getAttraction().getOpenhours());
                        Log.d("FragAct", "onBeanResponse: " + collection.get(0).getAttraction().getNumbergraders());
                        Log.d("FragAct", "onBeanResponse: " + collection.get(0).getAttraction().getBlogs());
                    }
                });//获取收藏，有long*/
                //将imageview的图片转换成file
                /*Url_Request.sendRequestBlogOfMe(Url_Request.getUrl_head() + "/blog/of/me", user_token, "1", "5", new Url_Request.OnIconResponseListener() {
                    @Override
                    public void onBeanResponse(Object bean) {
                        List<Url_Request.Note> blog = (List<Url_Request.Note>) bean;
                        for (Url_Request.Note note : blog) {
                            Log.d("FragAct", "onBeanResponse: " + note.getId());
                            Log.d("FragAct", "onBeanResponse: " + note.getSelfscore());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttractionid());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getId());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getName());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getTypeid());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getArea());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getAddress());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getX());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getY());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getPrice());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getScore());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getOpenhours());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getNumbergraders());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getBlogs());
                            Log.d("FragAct", "onBeanResponse: " + note.getUserid());
                            Log.d("FragAct", "onBeanResponse: " + note.getIcon());
                            Log.d("FragAct", "onBeanResponse: " + note.getName());
                            Log.d("FragAct", "onBeanResponse: " + note.getIslike());
                            Log.d("FragAct", "onBeanResponse: " + note.getTitle());
                            for(String s:note.getImageslist()){
                                Log.d("FragAct", "onBeanResponse: " + s);
                            }
                            Log.d("FragAct", "onBeanResponse: " + note.getContent());
                            Log.d("FragAct", "onBeanResponse: " + note.getLiked());
                            Log.d("FragAct", "onBeanResponse: " + note.getComments());
                            Log.d("FragAct", "onBeanResponse: " + note.getCreatetime());
                        }
                    }
                });*/
                /*Url_Request.sendRequestBlogHot(Url_Request.getUrl_head() + "/blog/hot", user_token, "1", new Url_Request.OnIconResponseListener() {
                    @Override
                    public void onBeanResponse(Object bean) {
                        List<Url_Request.Note> blog = (List<Url_Request.Note>) bean;
                        for (Url_Request.Note note : blog) {
                            Log.d("FragAct", "onBeanResponse: " + note.getId());
                            Log.d("FragAct", "onBeanResponse: " + note.getSelfscore());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttractionid());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getId());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getName());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getTypeid());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getArea());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getAddress());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getX());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getY());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getPrice());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getScore());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getOpenhours());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getNumbergraders());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getBlogs());
                            Log.d("FragAct", "onBeanResponse: " + note.getUserid());
                            Log.d("FragAct", "onBeanResponse: " + note.getIcon());
                            Log.d("FragAct", "onBeanResponse: " + note.getName());
                            Log.d("FragAct", "onBeanResponse: " + note.getIslike());
                            Log.d("FragAct", "onBeanResponse: " + note.getTitle());
                            for(String s:note.getImageslist()){
                                Log.d("FragAct", "onBeanResponse: " + s);
                            }
                            Log.d("FragAct", "onBeanResponse: " + note.getContent());
                            Log.d("FragAct", "onBeanResponse: " + note.getLiked());
                            Log.d("FragAct", "onBeanResponse: " + note.getComments());
                            Log.d("FragAct", "onBeanResponse: " + note.getCreatetime());
                        }
                    }
                });*/
                //Url_Request.sendRequestBlogLike(Url_Request.getUrl_head()+"/blog/like/"+"50", user_token);//点赞
                /*Url_Request.sendRequestBlogUserLike(Url_Request.getUrl_head() + "/blog/likes/" + "50", user_token, new Url_Request.OnIconResponseListener() {
                    @Override
                    public void onBeanResponse(Object bean) {
                        List<Url_Request.Me> meList = (List<Url_Request.Me>) bean;
                        for (Url_Request.Me me : meList) {
                            Log.d("FragAct", "onBeanResponse: " + me.getId());
                            Log.d("FragAct", "onBeanResponse: " + me.getIcon());
                            Log.d("FragAct", "onBeanResponse: " + me.getNickname());
                        }
                    }
                });*/
                /*Url_Request.sendRequestBlogOfUser(Url_Request.getUrl_head() + "/blog/of/user", user_token, "1", "5", "1017", new Url_Request.OnIconResponseListener() {
                    @Override
                    public void onBeanResponse(Object bean) {
                        List<Url_Request.Note> blog = (List<Url_Request.Note>) bean;
                        for (Url_Request.Note note : blog) {
                            Log.d("FragAct", "onBeanResponse: " + note.getId());
                            Log.d("FragAct", "onBeanResponse: " + note.getSelfscore());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttractionid());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getId());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getName());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getTypeid());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getArea());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getAddress());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getX());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getY());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getPrice());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getScore());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getOpenhours());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getNumbergraders());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getBlogs());
                            Log.d("FragAct", "onBeanResponse: " + note.getUserid());
                            Log.d("FragAct", "onBeanResponse: " + note.getIcon());
                            Log.d("FragAct", "onBeanResponse: " + note.getName());
                            Log.d("FragAct", "onBeanResponse: " + note.getIslike());
                            Log.d("FragAct", "onBeanResponse: " + note.getTitle());
                            for (String s : note.getImageslist()) {
                                Log.d("FragAct", "onBeanResponse: " + s);
                            }
                            Log.d("FragAct", "onBeanResponse: " + note.getContent());
                            Log.d("FragAct", "onBeanResponse: " + note.getLiked());
                        }
                    }
                });*/
                /*Url_Request.sendRequestBlogOfFollow(Url_Request.getUrl_head() + "/blog/of/follow", user_token, String.valueOf(System.currentTimeMillis()), "0", new Url_Request.OnIconResponseListener() {
                    @Override
                    public void onBeanResponse(Object bean) {
                        Url_Request.NoteList noteLists = (Url_Request.NoteList) bean;
                        Log.d("FragAct", "onBeanResponse: " + noteLists.getMintime());
                        Log.d("FragAct", "onBeanResponse: " + noteLists.getOffset());
                        List<Url_Request.Note> blog = noteLists.getList();
                        for (Url_Request.Note note : blog) {
                            Log.d("FragAct", "onBeanResponse: " + note.getId());
                            Log.d("FragAct", "onBeanResponse: " + note.getSelfscore());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttractionid());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getId());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getName());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getTypeid());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getArea());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getAddress());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getX());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getY());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getPrice());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getScore());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getOpenhours());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getNumbergraders());
                            Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getBlogs());
                            Log.d("FragAct", "onBeanResponse: " + note.getUserid());
                            Log.d("FragAct", "onBeanResponse: " + note.getIcon());
                            Log.d("FragAct", "onBeanResponse: " + note.getName());
                            Log.d("FragAct", "onBeanResponse: " + note.getIslike());
                            Log.d("FragAct", "onBeanResponse: " + note.getTitle());
                            for (String s : note.getImageslist()) {
                                Log.d("FragAct", "onBeanResponse: " + s);
                            }
                            Log.d("FragAct", "onBeanResponse: " + note.getContent());
                            Log.d("FragAct", "onBeanResponse: " + note.getLiked());
                        }
                    }
                });*/
               //Url_Request.sendRequestFeedBackSend(Url_Request.getUrl_head() + "/feedBack/send",user_token,"刘八刘八！我们的家！");
                //Url_Request.sendRequestUserSendLocation(Url_Request.getUrl_head() + "/user/sendLocation", user_token, "116.404", "39.915");
                //Url_Request.sendRequestBlogDelete(Url_Request.getUrl_head() + "/blog/delete/"+"53", user_token);

                /*Url_Request.sendRequestBlogSearch(Url_Request.getUrl_head() + "/blog/search/" + "50", user_token, new Url_Request.OnIconResponseListener() {
                    @Override
                    public void onBeanResponse(Object bean) {
                        Url_Request.Note note = (Url_Request.Note) bean;
                        Log.d("FragAct", "onBeanResponse: " + note.getId());
                        Log.d("FragAct", "onBeanResponse: " + note.getSelfscore());
                        Log.d("FragAct", "onBeanResponse: " + note.getAttractionid());
                        Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getId());
                        Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getName());
                        Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getTypeid());
                        Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getArea());
                        Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getAddress());
                        Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getX());
                        Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getY());
                        Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getPrice());
                        Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getScore());
                        Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getOpenhours());
                        Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getNumbergraders());
                        Log.d("FragAct", "onBeanResponse: " + note.getAttraction().getBlogs());
                        Log.d("FragAct", "onBeanResponse: " + note.getUserid());
                        Log.d("FragAct", "onBeanResponse: " + note.getIcon());
                        Log.d("FragAct", "onBeanResponse: " + note.getName());
                        Log.d("FragAct", "onBeanResponse: " + note.getIslike());
                        Log.d("FragAct", "onBeanResponse: " + note.getTitle());
                        for (String s : note.getImageslist()) {
                            Log.d("FragAct", "onBeanResponse: " + s);
                        }
                        Log.d("FragAct", "onBeanResponse: " + note.getContent());
                        Log.d("FragAct", "onBeanResponse: " + note.getLiked());
                        Log.d("FragAct", "onBeanResponse: " + note.getComments());
                        Log.d("FragAct", "onBeanResponse: " + note.getCreatetime());
                    }
                });*/
                /*File file = new File("/storage/emulated/0/DCIM/Camera/dog.jpg");
                File file1 = new File("/storage/emulated/0/DCIM/Camera/dog.jpg");
                List<File> files = new ArrayList<>();
                files.add(file);
                files.add(file1);
                Url_Request.sendRequestBlogSaveBlog(Url_Request.getUrl_head()+"/blog/saveBlog", user_token,"40","206","1017","刘八","菜",files);//会返回帖子id*/
                //Url_Request.sendRequestUpdateIcon(Url_Request.getUrl_head()+"/user/updateIcon", user_token,file);//更新头像
                //Url_Request.sendRequestCancel_Collection(Url_Request.getUrl_head()+"/user/cancel_collection/"+"1", user_token);//取消收藏
                /*Url_Request.sendRequestQueryById(String.valueOf(Url_Request.getUrl_head()+"/attraction/queryById"), user_token, "4", new Url_Request.OnIconResponseListener() {
                    @Override
                    public void onBeanResponse(Object bean) {
                        Url_Request.Attraction attraction = (Url_Request.Attraction) bean;
                        Log.d("FragAct", "onBeanResponse: " + attraction.getId());
                        Log.d("FragAct", "onBeanResponse: " + attraction.getName());
                        Log.d("FragAct", "onBeanResponse: " + attraction.getTypeid());
                        Log.d("FragAct", "onBeanResponse: " + attraction.getType());
                        Log.d("FragAct", "onBeanResponse: " + attraction.getImagesList());
                        //获取imagelist里面的图片
                        for(int i=0;i<attraction.getImagesList().size();i++){
                            Log.d("FragAct", "onBeanResponse: " + attraction.getImagesList().get(i));
                        }
                        Log.d("FragAct", "onBeanResponse: " + attraction.getArea());
                        Log.d("FragAct", "onBeanResponse: " + attraction.getAddress());
                        Log.d("FragAct", "onBeanResponse: " + attraction.getX());
                        Log.d("FragAct", "onBeanResponse: " + attraction.getY());
                        Log.d("FragAct", "onBeanResponse: " + attraction.getPrice());
                        Log.d("FragAct", "onBeanResponse: " + attraction.getScore());
                        Log.d("FragAct", "onBeanResponse: " + attraction.getOpenhours());
                        Log.d("FragAct", "onBeanResponse: " + attraction.getNumbergraders());
                        Log.d("FragAct", "onBeanResponse: " + attraction.getBlogs());
                    }
                });//根据id查询景点*/
                /*Url_Request.sendRequestTOBD(Url_Request.getUrl_head()+"/attraction/of/typeOrderByDistance", user_token, "1", "1","5","120.354722","30.313242",new Url_Request.OnIconResponseListener() {
                    @Override
                    public void onBeanResponse(Object bean) {
                        List<Url_Request.Attraction> attraction = (List<Url_Request.Attraction>) bean;
                        for (int i=0;i<attraction.size();i++){
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getId());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getName());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getTypeid());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getType());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getImagesList());
                            //获取imagelist里面的图片
                            for(int j=0;j<attraction.get(i).getImagesList().size();j++){
                                Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getImagesList().get(j));
                            }
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getArea());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getAddress());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getX());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getY());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getPrice());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getScore());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getOpenhours());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getNumbergraders());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getBlogs());
                        }
                    }
                });//根据类型查询景点*/
                /*Url_Request.sendRequestTOBS(Url_Request.getUrl_head()+"/attraction/of/typeOrderScore", user_token, "1", "1","5","120.354722","30.313242",new Url_Request.OnIconResponseListener() {
                    @Override
                    public void onBeanResponse(Object bean) {
                        List<Url_Request.Attraction> attraction = (List<Url_Request.Attraction>) bean;
                        for (int i=0;i<attraction.size();i++){
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getId());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getName());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getTypeid());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getType());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getImagesList());
                            //获取imagelist里面的图片
                            for(int j=0;j<attraction.get(i).getImagesList().size();j++){
                                Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getImagesList().get(j));
                            }
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getArea());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getAddress());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getX());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getY());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getPrice());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getScore());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getOpenhours());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getNumbergraders());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getBlogs());
                        }
                    }
                });//根据类型查询景点*/
                /*Url_Request.sendRequestTOBA(Url_Request.getUrl_head()+"/attraction/of/type", user_token, "1", "1","5","120.354722","30.313242",new Url_Request.OnIconResponseListener() {
                    @Override
                    public void onBeanResponse(Object bean) {
                        List<Url_Request.Attraction> attraction = (List<Url_Request.Attraction>) bean;
                        for (int i=0;i<attraction.size();i++){
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getId());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getName());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getTypeid());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getType());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getImagesList());
                            //获取imagelist里面的图片
                            for(int j=0;j<attraction.get(i).getImagesList().size();j++){
                                Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getImagesList().get(j));
                            }
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getArea());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getAddress());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getX());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getY());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getPrice());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getScore());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getOpenhours());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getNumbergraders());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getBlogs());
                        }
                    }
                });//根据类型查询景点*/

                /*Url_Request.User_info use = new Url_Request.User_info();
                use.setUser_id(user_id);
                use.setUserName("没想到吧！我是刘8！");
                use.setGender("1");
                use.setBirthday("1999-01-01");
                use.setIntroduce("机智的我早已想出了最佳的方法");*/
                //Url_Request.sendRequestSetInfo(Url_Request.getUrl_head()+"/user/setInfo", user_token, use);
                //Url_Request.sendRequestSign_In(Url_Request.getUrl_head()+"/user/sign_in", user_token,"4");
                //Url_Request.sendRequestBlogCommentsC(Url_Request.getUrl_head()+"/blog-comments/comments",user_token,"1016","26","1","2","刘八的春天来了","0","0");
                /*Url_Request.sendRequestTOBN(Url_Request.getUrl_head()+"/attraction/of/name", user_token, "西湖", "1","5","120.354722","30.313242",new Url_Request.OnIconResponseListener() {
                    @Override
                    public void onBeanResponse(Object bean) {
                        List<Url_Request.Attraction> attraction = (List<Url_Request.Attraction>) bean;
                        for (int i=0;i<attraction.size();i++){
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getId());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getName());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getTypeid());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getType());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getImagesList());
                            //获取imagelist里面的图片
                            for(int j=0;j<attraction.get(i).getImagesList().size();j++){
                                Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getImagesList().get(j));
                            }
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getArea());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getAddress());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getX());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getY());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getPrice());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getScore());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getOpenhours());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getNumbergraders());
                            Log.d("FragAct", "onBeanResponse: " + attraction.get(i).getBlogs());
                        }
                    }
                });//根据类型查询景点/*
                /*Url_Request.sendRequestAtrractionType(Url_Request.getUrl_head()+"/attraction-type/list", user_token, new Url_Request.OnIconResponseListener() {
                    @Override
                    public void onBeanResponse(Object bean) {
                        List<Url_Request.AttractionList> attractionType = (List<Url_Request.AttractionList>) bean;
                        for (int i=0;i<attractionType.size();i++){
                            Log.d("FragAct", "onBeanResponse: " + attractionType.get(i).getId());
                            Log.d("FragAct", "onBeanResponse: " + attractionType.get(i).getName());
                            Log.d("FragAct", "onBeanResponse: " + attractionType.get(i).getIcon());
                        }
                    }
                });//获取景点类型*/
                /*Url_Request.sendRequestBlogCommentsId(Url_Request.getUrl_head()+"/blog-comments/"+"26", user_token, new Url_Request.OnIconResponseListener() {
                    @Override
                    public void onBeanResponse(Object bean) {
                        List<Url_Request.Comment> commentList = (List<Url_Request.Comment>) bean;
                        for (int i = 0; i < commentList.size(); i++) {
                            Log.d("FragAct", "onBeanResponse: " + commentList.get(i).getId());
                            Log.d("FragAct", "onBeanResponse: " + commentList.get(i).getUserid());
                            Log.d("FragAct", "onBeanResponse: " + commentList.get(i).getBlogid());
                            Log.d("FragAct", "onBeanResponse: " + commentList.get(i).getParentid());
                            Log.d("FragAct", "onBeanResponse: " + commentList.get(i).getAnswerid());
                            Log.d("FragAct", "onBeanResponse: " + commentList.get(i).getContent());
                            Log.d("FragAct", "onBeanResponse: " + commentList.get(i).getLiked());
                            Log.d("FragAct", "onBeanResponse: " + commentList.get(i).getStatus());
                            Log.d("FragAct", "onBeanResponse: " + commentList.get(i).getCreatetime());
                            Log.d("FragAct", "onBeanResponse: " + commentList.get(i).getChildcomments());
                            for (int j = 0; j < commentList.get(i).getChildcomments().size(); j++) {
                                Log.d("FragAct", "onBeanResponse: " + commentList.get(i).getChildcomments().get(j).getId());
                                Log.d("FragAct", "onBeanResponse: " + commentList.get(i).getChildcomments().get(j).getUserid());
                                Log.d("FragAct", "onBeanResponse: " + commentList.get(i).getChildcomments().get(j).getBlogid());
                                Log.d("FragAct", "onBeanResponse: " + commentList.get(i).getChildcomments().get(j).getParentid());
                                Log.d("FragAct", "onBeanResponse: " + commentList.get(i).getChildcomments().get(j).getAnswerid());
                                Log.d("FragAct", "onBeanResponse: " + commentList.get(i).getChildcomments().get(j).getContent());
                                Log.d("FragAct", "onBeanResponse: " + commentList.get(i).getChildcomments().get(j).getLiked());
                                Log.d("FragAct", "onBeanResponse: " + commentList.get(i).getChildcomments().get(j).getStatus());
                                Log.d("FragmentActivity", "onBeanResponse: " + commentList.get(i).getChildcomments().get(j).getCreatetime());
                            }
                        }
                    }
                });//获取评论列表*/
                /*Url_Request.sendRequestFollowUsers(Url_Request.getUrl_head()+"/follow/users", user_token, new Url_Request.OnIconResponseListener() {
                    @Override
                    public void onBeanResponse(Object bean) {
                        List<Url_Request.Me> followList = (List<Url_Request.Me>) bean;
                        for (int i = 0; i < followList.size(); i++) {
                            Log.d("FragAct", "onBeanResponse: " + followList.get(i).getId());
                            Log.d("FragAct", "onBeanResponse: " + followList.get(i).getNickname());
                            Log.d("FragAct", "onBeanResponse: " + followList.get(i).getIcon());
                            Log.d("FragAct", "onBeanResponse: " + followList.get(i).getIntroduction());
                        }
                    }
                });//获取关注列表*/
                /*Url_Request.sendRequestFollowFans(Url_Request.getUrl_head()+"/follow/fans", user_token, new Url_Request.OnIconResponseListener() {
                    @Override
                    public void onBeanResponse(Object bean) {
                        List<Url_Request.Me> followList = (List<Url_Request.Me>) bean;
                        for (int i = 0; i < followList.size(); i++) {
                            Log.d("FragAct", "onBeanResponse: " + followList.get(i).getId());
                            Log.d("FragAct", "onBeanResponse: " + followList.get(i).getNickname());
                            Log.d("FragAct", "onBeanResponse: " + followList.get(i).getIcon());
                            Log.d("FragAct", "onBeanResponse: " + followList.get(i).getIntroduction());
                        }
                    }
                });//获取粉丝列表*/
                /*Url_Request.sendRequestFollowOrNot(Url_Request.getUrl_head()+"/follow/or/not/"+"1016", user_token, new Url_Request.OnIconResponseListener() {
                    @Override
                    public void onBeanResponse(Object bean) {
                        Boolean followOrNot = (Boolean) bean;
                        Log.d("FragAct", "onBeanResponse: " + followOrNot.toString());
                    }
                });//获取是否关注*/
                //Url_Request.sendRequestFollowFollowed(Url_Request.getUrl_head()+"/follow/followed/"+"1016"+"/"+"1", user_token);//获取是否被关注
            }
        });


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //user_id=et.getText().toString();
                edit_userNameValue = et.getText().toString();
                edit_passwordValue = et_2.getText().toString();
                //获取输入的手机号
               sendRequestWithOkHttp_login(url_login);

                //Gson
                if (remember.isChecked()){
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("USER_NAME",edit_userNameValue);
                    editor.putString("PASSWORD",edit_passwordValue);
                    //editor.putString("TOKEN",user_token);
                    editor.commit();
                }
                /*if (user_token.equals("")){
                    Intent intent = new Intent(FragAct.this, InterestActivity.class);
                    FragAct.this.startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(FragAct.this, bottomnavi.class);
                    FragAct.this.startActivity(intent);
                    finish();
                }*/


            }
        });
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(remember.isChecked()){
                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("ISCHECK",true).commit();
                }
                else{
                    System.out.println("记住密码未选中");
                    sp.edit().putBoolean("ISCHECK",false).commit();
                }
            }
        });
    }
    private void setupWindowAnimations() {
        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
        getWindow().setEnterTransition(fade);
    }
}