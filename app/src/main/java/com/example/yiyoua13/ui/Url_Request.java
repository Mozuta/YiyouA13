package com.example.yiyoua13.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Url_Request {
    private String url_login="http://192.168.79.206:8081/user/login";
    private static String url_head="http://192.168.79.206:8081";
    public static String getUrl_head() {
        return url_head;
    }
    String url_user_code="http://192.168.79.206:8081/user/code";
    String url_userme="http://192.168.79.206:8081/user/me";
    String url_user_logout="http://192.168.79.206:8081/user/logout";

    public String getUrl_login() {
        return url_login;
    }
    public String getUrl_userme() {
        return url_userme;
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
    public class Me{
        private Long id;
        private String nickName;
        private String icon;
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getNickname() {
            return nickName;
        }
        public void setNickname(String nickname) {
            this.nickName = nickname;
        }
        public String getIcon() {
            return icon;
        }
        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
    public class User_info{
        private String user_id;
        private String introduce;
        private String fans;
        private String followee;
        private String gender;
        private String birthday;
        private String creat_time;
        private String update_time;
        public String getUser_id() {
            return user_id;
        }
        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
        public String getIntroduce() {
            return introduce;
        }
        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }
        public String getFans() {
            return fans;
        }
        public void setFans(String fans) {
            this.fans = fans;
        }
        public String getFollowee() {
            return followee;
        }
        public void setFollowee(String followee) {
            this.followee = followee;
        }
        public String getGender(){
            return gender;
        }

    }
    private static String parseUserMe(String responseData) {
        Gson gson = new Gson();
        User user = gson.fromJson(responseData, User.class);
        String success=user.getSuccess().toString();
        String errormsg=user.getErrormsg();
        JsonElement data= gson.toJsonTree(user.getData());
        if (data.isJsonObject()) {
            JsonObject jsonObject = data.getAsJsonObject();
            String id = jsonObject.get("id").getAsString();
            String nickname = jsonObject.get("nickName").getAsString();
            String icon = jsonObject.get("icon").getAsString();
            Log.d("UserMe", "parseJSONWithGSON: "+id);
            Log.d("UserMe", "parseJSONWithGSON: "+nickname);
            Log.d("UserMe", "parseJSONWithGSON: "+icon);
            Log.d("UserMe", "onResponse: "+responseData);
            Log.d("UserMe", "parseJSONWithGSON: "+success);
            Log.d("UserMe", "parseJSONWithGSON: "+errormsg);
            return icon;
        }
        else {
            Log.d("UserMe", "parseJSONWithGSON: "+success);
            Log.d("UserMe", "parseJSONWithGSON: "+errormsg);
            return "https://i.postimg.cc/NMQjFFVv/cat.jpg";
        }





    }
    public static void sendRequestLogout(String url,String value) {//典型post
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                JsonObject jsonObject = new JsonObject();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                FormBody formBody = new FormBody.Builder().build();
                final Request request = new Request.Builder()
                        .url(url)
                        .header("Authorization", value)
                        .post(body)
                        .build();//创建Request 对象
                Call call = client.newCall(request);
                call.enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, java.io.IOException e) {
                        Log.d("TAG", "onFailure: ");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws java.io.IOException {
                        String responseData = response.body().string();
                        parseUsual(responseData);
                        Log.d("Logout", "onResponse: " + responseData);

                    }
                });
            }
        }).start();
    }

    public static void sendRequestinfo_id(String url,String value){//get
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    //String phone=et.getText().toString();
                    FormBody formBody = new FormBody.Builder().build();
                    final Request request = new Request.Builder()
                            .url(url)
                            .header("Authorization", value)
                            //.post(formBody)
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
                            parseUserMe(responseData);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();



    }
    private static void parseUsual(String jsonData) {
        Gson gson = new Gson();
        User user = gson.fromJson(jsonData, User.class);
        String success=user.getSuccess().toString();
        String errormsg=user.getErrormsg();
        /*String data=user.getData().toString();
        String total=user.getTotal().toString();*/
        Log.d("Usual", "Usual: "+success);
        Log.d("Usual", "Usual: "+errormsg);


    }

    public static void loadImageFromUrl(Activity context, String url, ImageView imageView) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 创建 URL 对象
                            URL imageUrl = new URL(url);
                            // 打开 URL 连接
                            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                            // 设置连接超时时间
                            conn.setConnectTimeout(10000);
                            // 设置读取超时时间
                            conn.setReadTimeout(10000);
                            // 设置请求方式
                            conn.setRequestMethod("GET");
                            // 执行请求并获取返回的输入流
                            InputStream inputStream = conn.getInputStream();
                            // 将输入流解码为 Bitmap 对象
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            // 将 Bitmap 对象设置到 ImageView 控件中显示
                            imageView.setImageBitmap(bitmap);
                            // 关闭输入流
                            inputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                    }
                });

        // 启动线程
        thread.start();
    }


    public static void sendRequestUserMe(String url, String value,final OnIconResponseListener listener) {//典型get
        //final String[] icona = {"https://i.postimg.cc/k4wXb936/flower.jpg"};
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    //String phone=et.getText().toString();
                    FormBody formBody = new FormBody.Builder().build();
                    final Request request = new Request.Builder()
                            .url(url)
                            .header("Authorization", value)
                            //.post(formBody)
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
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            JsonElement data= gson.toJsonTree(user.getData());
                            if (data.isJsonObject()) {
                                JsonObject jsonObject = data.getAsJsonObject();
                                String id = jsonObject.get("id").getAsString();
                                String nickname = jsonObject.get("nickName").getAsString();
                                String icon = jsonObject.get("icon").getAsString();
                                Log.d("UserMe", "parseJSONWithGSON: "+id);
                                Log.d("UserMe", "parseJSONWithGSON: "+nickname);
                                Log.d("UserMe", "parseJSONWithGSON: "+icon);
                                Log.d("UserMe", "onResponse: "+responseData);
                                Log.d("UserMe", "parseJSONWithGSON: "+success);
                                Log.d("UserMe", "parseJSONWithGSON: "+errormsg);
                                //icona[0] = String.valueOf(icon);
                                if (listener != null) {
                                    //listener.onIconResponse();
                                    listener.onIconResponse(icon);
                                    listener.onUserNameResponse(nickname);
                                }

                            }
                            else {
                                Log.d("UserMe", "parseJSONWithGSON: "+success);
                                Log.d("UserMe", "parseJSONWithGSON: "+errormsg);
                                //icona[0]= "https://i.postimg.cc/NMQjFFVv/cat.jpg";
                            }


                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //Log.d("UserMe", "icoN: "+icona[0]);
        //String icon1=icona[0];

        //Log.d("UserMe", "parseJSONWithGSON: "+icon1);
        //return icon1;
    }
    public interface OnIconResponseListener {
        void onIconResponse(String icon);
        void onUserNameResponse(String name);

    }

}
