package com.example.yiyoua13;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yiyoua13.ui.InterestActivity;
import com.example.yiyoua13.variousclass.Login;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

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
    private Button bt;
    private Button test;
    private SharedPreferences sp;
    private String edit_userNameValue,edit_passwordValue,user_id;
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
                            //判断是否为空
                            if(data!=null){
                                Log.d("jieshou", "parseJSONWithGSON: "+data);

                            }


                            if(success.equals("true")){
                                Intent intent=new Intent(FragAct.this, InterestActivity.class);
                                startActivity(intent);
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

        sp = this.getSharedPreferences("Login", MODE_PRIVATE);

        remember = (CheckBox) findViewById(R.id.cb_1);

        et_2=(EditText)findViewById(R.id.et_2);
        et=(EditText)findViewById(R.id.et_1);
        ask_code=(Button)findViewById(R.id.check_code);
        bt=(Button)findViewById(R.id.loginbt);
        if(sp.getBoolean("ISCHECK",false)){
            remember.setChecked(true);
            et.setText(sp.getString("USER_NAME",""));
            et_2.setText(sp.getString("PASSWORD",""));
            user_id=sp.getString("USER_ID","");
        }
        Log.d("jieshou", "user_id: "+user_id);
        ask_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取输入的手机号
                //sendRequestWithOkHttp(url_code);
            }
        });


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_id=et.getText().toString();
                edit_userNameValue = et.getText().toString();
                edit_passwordValue = et_2.getText().toString();
                //获取输入的手机号
               //sendRequestWithOkHttp_login(url_login);
                //Gson
                if (remember.isChecked()){
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("USER_NAME",edit_userNameValue);
                    editor.putString("PASSWORD",edit_passwordValue);
                    editor.putString("USER_ID",user_id+"user_id");
                    editor.commit();
                }
                Intent intent = new Intent(FragAct.this, InterestActivity.class);
                FragAct.this.startActivity(intent);
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