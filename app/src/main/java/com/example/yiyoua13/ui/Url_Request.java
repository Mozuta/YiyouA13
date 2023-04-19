package com.example.yiyoua13.ui;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
        private Long status;
        private String introduction;
        public Long getStatus() {
            return status;
        }
        public void setStatus(Long status) {
            this.status = status;
        }
        public String getIntroduction() {
            return introduction;
        }
        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }
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
    public class NoteList{
        private List<Note> list;
        Long minTime;
        Long offset;
        public List<Note> getList() {
            return list;
        }
        public void setList(List<Note> list) {
            this.list = list;
        }
        public Long getMintime() {
            return minTime;
        }
        public void setMintime(Long mintime) {
            this.minTime = mintime;
        }
        public Long getOffset() {
            return offset;
        }
        public void setOffset(Long offset) {
            this.offset = offset;
        }
    }
    public class Note{
        Long id;
        Long selfScore;
        Long attractionId;
        Attraction attraction;
        Long userId;
        String icon;
        String name;
        Boolean isLike;
        String title;
        List<String> imagesList;
        String content;
        Long liked;
        Long comments;
        String createTime;
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public Long getSelfscore() {
            return selfScore;
        }
        public void setSelfscore(Long selfscore) {
            this.selfScore = selfscore;
        }
        public Long getAttractionid() {
            return attractionId;
        }
        public void setAttractionid(Long attractionid) {
            this.attractionId = attractionid;
        }
        public Attraction getAttraction() {
            return attraction;
        }
        public void setAttraction(Attraction attraction) {
            this.attraction = attraction;
        }
        public Long getUserid() {
            return userId;
        }
        public void setUserid(Long userid) {
            this.userId = userid;
        }
        public String getIcon() {
            return icon;
        }
        public void setIcon(String icon) {
            this.icon = icon;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Boolean getIslike() {
            return isLike;
        }
        public void setIslike(Boolean islike) {
            this.isLike = islike;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public List<String> getImageslist() {
            return imagesList;
        }
        public void setImageslist(List<String> imageslist) {
            this.imagesList = imageslist;
        }
        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
        public Long getLiked() {
            return liked;
        }
        public void setLiked(Long liked) {
            this.liked = liked;
        }
        public Long getComments() {
            return comments;
        }
        public void setComments(Long comments) {
            this.comments = comments;
        }
        public String getCreatetime() {
            return createTime;
        }
        public void setCreatetime(String createtime) {
            this.createTime = createtime;
        }


    }
    public class SKind{
        private Long id;
        private String name;
        private Long parentId;
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Long getParentid() {
            return parentId;
        }
        public void setParentid(Long parentid) {
            this.parentId = parentid;
        }

    }
    public class Model{
        private Long userId;
        private List<Attraction> attractionList;
        private List<Route> routes;
        public Long getUserid() {
            return userId;
        }
        public void setUserid(Long userid) {
            this.userId = userid;
        }
        public List<Attraction> getAttractionlist() {
            return attractionList;
        }
        public void setAttractionlist(List<Attraction> attractionlist) {
            this.attractionList = attractionlist;
        }
        public List<Route> getRoutelist() {
            return routes;
        }
        public void setRoutelist(List<Route> routelist) {
            this.routes = routelist;
        }
    }
    public class Route{
        private Long id;
        private String routeName;
        private String describe;
        private List<Attraction> routes;
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getRoutename() {
            return routeName;
        }
        public void setRoutename(String routename) {
            this.routeName = routename;
        }
        public String getDescribe() {
            return describe;
        }
        public void setDescribe(String describe) {
            this.describe = describe;
        }
        public List<Attraction> getAttractionlist() {
            return routes;
        }
        public void setAttractionlist(List<Attraction> attractionlist) {
            this.routes = attractionlist;
        }

    }
    public static class Attraction{
        private Long id;
        private String name;
        private Long typeId;
        private String type;//景点类型
        private List<String> imagesList;
        private String introduction;
        private Long radius;

        private String area;
        private String address;
        private String x;
        private String y;
        private Long price;
        private Long score;
        private String openHours;
        private int distance;
        private Long numberGraders;
        private Long capacity;

        private Long blogs;
        public Long getCapacity() {
            return capacity;
        }
        public void setCapacity(Long capacity) {
            this.capacity = capacity;
        }
        public String getIntroduction() {
            return introduction;
        }
        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }
        public Long getRadius() {
            return radius;
        }
        public void setRadius(Long radius) {
            this.radius = radius;
        }
        public int getDistance() {
            return distance;
        }
        public void setDistance(int distance) {
            this.distance = distance;
        }
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public List<String> getImagesList() {
            return imagesList;
        }
        public void setImagesList(List<String> imagesList) {
            this.imagesList = imagesList;
        }
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Long getTypeid() {
            return typeId;
        }
        public void setTypeid(Long typeid) {
            this.typeId = typeid;
        }
        public String getArea() {
            return area;
        }
        public void setArea(String area) {
            this.area = area;
        }
        public String getAddress() {
            return address;
        }
        public void setAddress(String address) {
            this.address = address;
        }
        public String getX() {
            return x;
        }
        public void setX(String x) {
            this.x = x;
        }
        public String getY() {
            return y;
        }
        public void setY(String y) {
            this.y = y;
        }
        public Long getPrice() {
            return price;
        }
        public void setPrice(Long price) {
            this.price = price;
        }
        public Long getScore() {
            return score;
        }
        public void setScore(Long score) {
            this.score = score;
        }
        public String getOpenhours() {
            return openHours;
        }
        public void setOpenhours(String openhours) {
            this.openHours = openhours;
        }
        public Long getNumbergraders() {
            return numberGraders;
        }
        public void setNumbergraders(Long numbergraders) {
            this.numberGraders = numbergraders;
        }
        public Long getBlogs() {
            return blogs;
        }
        public void setBlogs(Long blogs) {
            this.blogs = blogs;
        }


    }
    public static class Collection{
        private Long id;
        private Long userId;
        private Long attractionId;
        private Attraction attraction;
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public Long getUserid() {
            return userId;
        }
        public void setUserid(Long userid) {
            this.userId = userid;
        }
        public Long getAttractionid() {
            return attractionId;
        }
        public void setAttractionid(Long attractionid) {
            this.attractionId = attractionid;
        }
        public Attraction getAttraction() {
            return attraction;
        }
        public void setAttraction(Attraction attraction) {
            this.attraction = attraction;
        }

    }
    public static class User_info{
        private String user_id;
        private String introduce;
        private String userName;
        private String fans;
        private String followee;
        private String gender;
        private String birthday;
        private String icon;
        public String getIcon() {
            return icon;
        }
        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getUserName() {
            return userName;
        }
        public void setUserName(String userName) {
            this.userName = userName;
        }
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
        public void setGender(String gender){
            this.gender=gender;
        }
        public String getBirthday(){
            return birthday;
        }
        public void setBirthday(String birthday){
            this.birthday=birthday;
        }



    }
    public static class AttractionList{
        private Long id;
        private String name;
        private Long parentId;
        private String icon;
        private Long sort;
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Long getParentid() {
            return parentId;
        }
        public void setParentid(Long parentid) {
            this.parentId = parentid;
        }
        public String getIcon() {
            return icon;
        }
        public void setIcon(String icon) {
            this.icon = icon;
        }
        public Long getSort() {
            return sort;
        }
        public void setSort(Long sort) {
            this.sort = sort;
        }


    }
    public static class Comment{
        private Long id;
        private Long userId;
        private String name;
        private Long blogId;
        private Long parentId;
        private Long answerId;
        private String content;
        private Long liked;
        private String icon;
        private List<ChildComment> childComments;
        private String status;
        private String createTime;
        public String getUserIcon() {
            return icon;
        }
        public void setUserIcon(String userIcon) {
            this.icon = userIcon;
        }
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public Long getUserid() {
            return userId;
        }
        public void setUserid(Long userid) {
            this.userId = userid;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Long getBlogid() {
            return blogId;
        }
        public void setBlogid(Long blogid) {
            this.blogId = blogid;
        }
        public Long getParentid() {
            return parentId;
        }
        public void setParentid(Long parentid) {
            this.parentId = parentid;
        }
        public Long getAnswerid() {
            return answerId;
        }
        public void setAnswerid(Long answerid) {
            this.answerId = answerid;
        }
        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
        public Long getLiked() {
            return liked;
        }
        public void setLiked(Long liked) {
            this.liked = liked;
        }
        public List<ChildComment> getChildcomments() {
            return childComments;
        }
        public void setChildcomments(List<ChildComment> childcomments) {
            this.childComments = childcomments;
        }
        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
        public String getCreatetime() {
            return createTime;
        }
        public void setCreatetime(String createtime) {
            this.createTime = createtime;
        }

    }
    public static class ChildComment{
        private Long id;
        private Long userId;
        private String name;
        private String respond_name;
        private Long blogId;
        private Long parentId;
        private Long answerId;
        private String content;
        private Long liked;
        private String status;
        private String createTime;
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public Long getUserid() {
            return userId;
        }
        public void setUserid(Long userid) {
            this.userId = userid;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getRespond_name() {
            return respond_name;
        }
        public void setRespond_name(String respond_name) {
            this.respond_name = respond_name;
        }
        public Long getBlogid() {
            return blogId;
        }
        public void setBlogid(Long blogid) {
            this.blogId = blogid;
        }
        public Long getParentid() {
            return parentId;
        }
        public void setParentid(Long parentid) {
            this.parentId = parentid;
        }
        public Long getAnswerid() {
            return answerId;
        }
        public void setAnswerid(Long answerid) {
            this.answerId = answerid;
        }
        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
        public Long getLiked() {
            return liked;
        }
        public void setLiked(Long liked) {
            this.liked = liked;
        }
        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
        public String getCreatetime() {
            return createTime;
        }
        public void setCreatetime(String createtime) {
            this.createTime = createtime;
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
    public static void sendRequestUserSetPreference(String url,String value, String preferences){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                FormBody body = new FormBody.Builder()
                        .add("preferences", preferences)
                        .build();
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
                        Log.d("TAG", "onResponse: " + responseData);
                        Gson gson = new Gson();
                        User user = gson.fromJson(responseData, User.class);
                        String success = user.getSuccess().toString();
                        String errormsg = user.getErrormsg();
                        Log.d("TAG", "onResponse: " + success);
                        Log.d("TAG", "onResponse: " + errormsg);

                    }
                });
            }
        }).start();
    }
    public static void sendRequestAttraction_TypeSmall_List(String url,String value, final OnIconResponseListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                    String finalUrl = urlBuilder.build().toString();

                    final Request request = new Request.Builder()
                            .url(finalUrl)
                            .addHeader("Authorization", value)
                            .get()
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
                            Log.d("BlogSearch", "onResponse: " + responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("BlogSearch", "parseJSONWithGSON: "+success);
                            Log.d("BlogSearch", "parseJSONWithGSON: "+errormsg);
                            JsonElement data= gson.toJsonTree(user.getData());
                            if (data.isJsonArray()) {
                                JsonArray jsonArray = data.getAsJsonArray();
                                List<SKind> attraction_typeSmalls = new ArrayList<>();
                                for (JsonElement attraction_typeSmall : jsonArray) {
                                    SKind attraction_typeSmall1 = gson.fromJson(attraction_typeSmall, SKind.class);
                                    attraction_typeSmalls.add(attraction_typeSmall1);
                                }
                                listener.onBeanResponse(attraction_typeSmalls);
                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void sendRequestSetInfo(String url,String value,User_info user_info){//post请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                JsonObject jsonObject = new JsonObject();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                jsonObject.addProperty("userId",user_info.getUser_id());
                jsonObject.addProperty("userName",user_info.getUserName());
                jsonObject.addProperty("introduce",user_info.getIntroduce());
                jsonObject.addProperty("gender",user_info.getGender());
                jsonObject.addProperty("birthday",user_info.getBirthday());
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                //加入参数

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
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        Log.d("TAG", "onResponse: "+responseData);
                    }
                });
            }
        }).start();
    }
    public static void sendGaoDeRequest(String url,String key){//post请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                JsonObject jsonObject = new JsonObject();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                body = new FormBody.Builder()
                        .add("key",key)
                        .add("name","Service_1")
                        .build();
                final Request request = new Request.Builder()
                        .url(url)
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
                        Log.d("GaoDe", "onResponse: " + responseData);

                    }
                });
            }
        }).start();
    }
    public static void sendRequestBlogSaveBlog(String url,String value,String selfScore,String attractionId,String userId,String title,String content,List<File> fileList){//post/blog/saveBlog
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                //加入参数
                MultipartBody.Builder builder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM);
                for (int i = 0; i < fileList.size(); i++) {
                    File file = fileList.get(i);
                    builder.addFormDataPart("files", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
                }
                builder.addFormDataPart("selfScore", selfScore);
                builder.addFormDataPart("attractionId", attractionId);
                builder.addFormDataPart("userId", userId);
                builder.addFormDataPart("title", title);
                builder.addFormDataPart("content", content);
                MultipartBody requestBody = builder.build();
                final Request request = new Request.Builder()
                        .url(url)
                        .header("Authorization", value)
                        .post(requestBody)
                        .build();//创建Request 对象
                Call call = client.newCall(request);
                call.enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, java.io.IOException e) {
                        Log.d("TAG", "onFailure: ");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        Log.d("TAG", "onResponse: "+responseData);
                        Gson gson = new Gson();
                        User user = gson.fromJson(responseData, User.class);

                    }
                });
            }
        }).start();

    }
    public static void sendRequestLogout(String url,String value) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                JsonObject jsonObject = new JsonObject();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
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
    }//典型post，value是头id
    public static void sendRequestBlogOfMe(String url,String value, String current ,String pageSize,final OnIconResponseListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                FormBody body = new FormBody.Builder()
                        .add("current", current)
                        .add("pageSize", pageSize)
                        .build();
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
                        Log.d("TAG", "onResponse: "+responseData);
                        Gson gson = new Gson();
                        User user = gson.fromJson(responseData, User.class);
                        String success=user.getSuccess().toString();
                        String errormsg=user.getErrormsg();
                        Log.d("TAG", "onResponse: "+success);
                        Log.d("TAG", "onResponse: "+errormsg);
                        JsonElement data = gson.toJsonTree(user.getData());
                        if (data.isJsonArray()) {
                            JsonArray array = data.getAsJsonArray();
                            List<Note> blog = new ArrayList<>();
                            for (JsonElement jsonElement : array) {
                                Note note = gson.fromJson(jsonElement, Note.class);
                                blog.add(note);

                            }
                            listener.onBeanResponse(blog);
                        }else {
                            Log.d("TAG", "onResponse: "+errormsg);
                        }


                    }
                });
            }
        }).start();
    }
    public static void sendRequestBlogHot(String url ,String value, String current,final OnIconResponseListener listener) {//post/blog/hot
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                FormBody body = new FormBody.Builder()
                        .add("current", current)
                        .build();
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
                        Log.d("TAG", "onResponse: " + responseData);
                        Gson gson = new Gson();
                        User user = gson.fromJson(responseData, User.class);
                        String success = user.getSuccess().toString();
                        String errormsg = user.getErrormsg();
                        Log.d("TAG", "onResponse: " + success);
                        Log.d("TAG", "onResponse: " + errormsg);
                        JsonElement data = gson.toJsonTree(user.getData());
                        if (data.isJsonArray()) {
                            JsonArray array = data.getAsJsonArray();
                            List<Note> blog = new ArrayList<>();
                            for (JsonElement jsonElement : array) {
                                Note note = gson.fromJson(jsonElement, Note.class);
                                blog.add(note);

                            }
                            listener.onBeanResponse(blog);
                        } else {
                            Log.d("TAG", "onResponse: " + errormsg);
                        }
                    }
                });
            }
        }).start();

    }
    public static void sendRequestBlogSearch(String url, String value,final OnIconResponseListener listener){//get/blog/search/26
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                    String finalUrl = urlBuilder.build().toString();

                    final Request request = new Request.Builder()
                            .url(finalUrl)
                            .addHeader("Authorization", value)
                            .get()
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
                            Log.d("BlogSearch", "onResponse: " + responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("BlogSearch", "parseJSONWithGSON: "+success);
                            Log.d("BlogSearch", "parseJSONWithGSON: "+errormsg);
                            JsonElement data= gson.toJsonTree(user.getData());
                            if (data.isJsonObject()){
                                JsonObject jsonObject = data.getAsJsonObject();
                                Note note = gson.fromJson(jsonObject, Note.class);

                                if (listener != null) {
                                    listener.onBeanResponse(note);
                                }
                            }
                            else {
                                Log.d("BlogSearch", "onResponse: "+"data不是jsonobject");

                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public static void sendRequestBlogUserLike(String url,String value,final OnIconResponseListener listener){//get/blog/likes/26
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                    String finalUrl = urlBuilder.build().toString();

                    final Request request = new Request.Builder()
                            .url(finalUrl)
                            .addHeader("Authorization", value)
                            .get()
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
                            Log.d("BlogUserLike", "onResponse: " + responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("BlogUserLike", "parseJSONWithGSON: "+success);
                            Log.d("BlogUserLike", "parseJSONWithGSON: "+errormsg);
                            JsonElement data= gson.toJsonTree(user.getData());
                            if (data.isJsonArray()){
                                JsonArray array = data.getAsJsonArray();
                                List<Me> mes = new ArrayList<>();
                                for (JsonElement jsonElement : array) {
                                    Me me = gson.fromJson(jsonElement, Me.class);
                                    mes.add(me);

                                }
                                listener.onBeanResponse(mes);
                            }
                            else {
                                Log.d("BlogUserLike", "onResponse: "+"data不是jsonobject");

                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public static void sendRequestUserCollected(String url,String value,String attractionId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                    urlBuilder.addQueryParameter("attractionId",attractionId);
                    String finalUrl = urlBuilder.build().toString();

                    final Request request = new Request.Builder()
                            .url(finalUrl)
                            .addHeader("Authorization", value)
                            .get()
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
                            Log.d("UserCollected", "onResponse: " + responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("UserCollected", "parseJSONWithGSON: "+success);
                            Log.d("UserCollected", "parseJSONWithGSON: "+errormsg);


                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public static void sendRequestUserIsCollection(String url,String value,String attraction_Id,final OnIconResponseListener listener){//post
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                FormBody body = new FormBody.Builder()
                        .add("attraction_id", attraction_Id)
                        .build();
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
                        Log.d("TAG", "onResponse: " + responseData);
                        Gson gson = new Gson();
                        User user = gson.fromJson(responseData, User.class);
                        String success = user.getSuccess().toString();
                        String errormsg = user.getErrormsg();
                        Log.d("TAG", "onResponse: " + success);
                        Log.d("TAG", "onResponse: " + errormsg);

                        listener.onBeanResponse(user.getData());

                    }
                });
            }
        }).start();
    }
    public static void sendRequestBlogOfUser(String url,String value,String current,String pageSize,String id,final OnIconResponseListener listener){//get/blog/of/user/
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                    urlBuilder.addQueryParameter("current",current);
                    urlBuilder.addQueryParameter("pageSize",pageSize);
                    urlBuilder.addQueryParameter("id",id);
                    String finalUrl = urlBuilder.build().toString();

                    final Request request = new Request.Builder()
                            .url(finalUrl)
                            .addHeader("Authorization", value)
                            .get()
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
                            Log.d("BlogOfUser", "onResponse: " + responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("BlogOfUser", "parseJSONWithGSON: "+success);
                            Log.d("BlogOfUser", "parseJSONWithGSON: "+errormsg);
                            JsonElement data= gson.toJsonTree(user.getData());
                            if (data.isJsonArray()) {
                                JsonArray array = data.getAsJsonArray();
                                List<Note> blog = new ArrayList<>();
                                for (JsonElement jsonElement : array) {
                                    Note note = gson.fromJson(jsonElement, Note.class);
                                    blog.add(note);

                                }
                                listener.onBeanResponse(blog);
                            }
                            else {
                                Log.d("BlogOfUser", "onResponse: "+"data不是jsonobject");

                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public static void sendRequestModelRunDistinguish(String url,String value ,final OnIconResponseListener listener ){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader("Authorization", value)
                        .get()
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("ModelRunDistinguish", "onResponse: " + responseData);
                    Gson gson = new Gson();
                    User user = gson.fromJson(responseData, User.class);
                    String success=user.getSuccess().toString();
                    String errormsg=user.getErrormsg();
                    Log.d("ModelRunDistinguish", "parseJSONWithGSON: "+success);
                    Log.d("ModelRunDistinguish", "parseJSONWithGSON: "+errormsg);
                    JsonElement data= gson.toJsonTree(user.getData());
                    if (data.isJsonObject()){
                        JsonObject jsonObject = data.getAsJsonObject();
                        Model model = gson.fromJson(jsonObject, Model.class);
                        if (listener != null) {
                            listener.onBeanResponse(model);
                        }
                    }
                    else {
                        Log.d("ModelRunDistinguish", "onResponse: "+"data不是jsonobject");

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static void sendRequestUserGetXY(String url,JsonArray jsonArray){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(JSON, jsonArray.toString());
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("UserGetXY", "onResponse: " + responseData);
                    Gson gson = new Gson();
                    User user = gson.fromJson(responseData, User.class);
                    String success=user.getSuccess().toString();
                    String errormsg=user.getErrormsg();
                    Log.d("UserGetXY", "parseJSONWithGSON: "+success);
                    Log.d("UserGetXY", "parseJSONWithGSON: "+errormsg);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static void sendRequestUserSendLocation(String url,String value,String x,String y){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                    urlBuilder.addQueryParameter("x",x);
                    urlBuilder.addQueryParameter("y",y);
                    String finalUrl = urlBuilder.build().toString();

                    final Request request = new Request.Builder()
                            .url(finalUrl)
                            .addHeader("Authorization", value)
                            .get()
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
                            Log.d("UserSendLocation", "onResponse: " + responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("UserSendLocation", "parseJSONWithGSON: "+success);
                            Log.d("UserSendLocation", "parseJSONWithGSON: "+errormsg);


                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public static void sendRequestBlogDelete(String url,String value){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    final Request request = new Request.Builder()
                            .url(url)
                            .addHeader("Authorization", value)
                            .get()
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
                            Log.d("BlogDelete", "onResponse: " + responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success = user.getSuccess().toString();
                            String errormsg = user.getErrormsg();
                            Log.d("BlogDelete", "parseJSONWithGSON: " + success);
                            Log.d("BlogDelete", "parseJSONWithGSON: " + errormsg);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public static void sendRequestBlogAllBlog(String url,String value,String current,String pageSize,final OnIconResponseListener listener) {//get/
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                    urlBuilder.addQueryParameter("current",current);
                    urlBuilder.addQueryParameter("pageSize",pageSize);
                    String finalUrl = urlBuilder.build().toString();

                    final Request request = new Request.Builder()
                            .url(finalUrl)
                            .addHeader("Authorization", value)
                            .get()
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
                            Log.d("BlogAllBlog", "onResponse: " + responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("BlogAllBlog", "parseJSONWithGSON: "+success);
                            Log.d("BlogAllBlog", "parseJSONWithGSON: "+errormsg);
                            JsonElement data= gson.toJsonTree(user.getData());
                            if (data.isJsonArray()) {
                                JsonArray array = data.getAsJsonArray();
                                List<Note> blog = new ArrayList<>();
                                for (JsonElement jsonElement : array) {
                                    Note note = gson.fromJson(jsonElement, Note.class);
                                    blog.add(note);

                                }
                                listener.onBeanResponse(blog);
                            }
                            else {
                                Log.d("BlogAllBlog", "onResponse: "+"data不是jsonobject");

                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public static void sendRequestBlogOfFollow(String url,String value,String lastId,String offset,final OnIconResponseListener listener){//get/blog/follow
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                    urlBuilder.addQueryParameter("lastId",lastId);
                    urlBuilder.addQueryParameter("offset",offset);
                    String finalUrl = urlBuilder.build().toString();

                    final Request request = new Request.Builder()
                            .url(finalUrl)
                            .addHeader("Authorization", value)
                            .get()
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
                            Log.d("BlogOfFollow", "onResponse: " + responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("BlogOfFollow", "parseJSONWithGSON: "+success);
                            Log.d("BlogOfFollow", "parseJSONWithGSON: "+errormsg);
                            JsonElement data= gson.toJsonTree(user.getData());
                            if (data.isJsonObject()){
                                JsonObject jsonObject = data.getAsJsonObject();
                                NoteList noteList = gson.fromJson(jsonObject, NoteList.class);

                                if (listener != null) {
                                    listener.onBeanResponse(noteList);
                                }
                            }
                            else {

                                Log.d("BlogOfFollow", "onResponse: "+"data不是jsonobject");

                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public static void sendRequestFeedBackSend(String url,String value,String content){//post/feedBack/send
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                FormBody body = new FormBody.Builder()
                        .add("content", content)
                        .build();
                final Request request = new Request.Builder()
                        .url(url)
                        .header("Authorization", value)
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
                        Log.d("FeedBackSend", "onResponse: " + responseData);
                        Gson gson = new Gson();
                        User user = gson.fromJson(responseData, User.class);
                        String success=user.getSuccess().toString();
                        String errormsg=user.getErrormsg();
                        Log.d("FeedBackSend", "parseJSONWithGSON: "+success);
                        Log.d("FeedBackSend", "parseJSONWithGSON: "+errormsg);


                    }
                });
            }
        }).start();


    }
    public static void sendRequestBlogCommentsC(String url,String value,String userId,String blogId,String parentId,String answerId,String content,String like,String status){
new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                JsonObject jsonObject = new JsonObject();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                jsonObject.addProperty("userId",userId);
                jsonObject.addProperty("blogId",blogId);
                jsonObject.addProperty("parentId",parentId);
                jsonObject.addProperty("answerId",answerId);
                jsonObject.addProperty("content",content);
                jsonObject.addProperty("like",like);
                jsonObject.addProperty("status",status);
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
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
                        Log.d("BlogCommentsC", "onResponse: " + responseData);

                    }
                });
            }
        }).start();

    }
    public static void sendRequestBlogCommentsId(String url,String value,final OnIconResponseListener listener){//get/blog-comments/{id}
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    final Request request = new Request.Builder()
                            .url(url)
                            .header("Authorization", value)
                            .get()
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
                            Log.d("TAG", "onResponse: "+responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success=user.getSuccess().toString();
                            //String message=user.getErrormsg().toString();
                            Log.d("TAG", "onResponse: "+success);
                            //Log.d("TAG", "onResponse: "+message);
                            JsonElement data = gson.toJsonTree(user.getData());
                            if (data.isJsonArray()) {
                                JsonArray jsonArray = data.getAsJsonArray();
                                List<Comment> commentList = new ArrayList<>();
                                for (JsonElement comment : jsonArray) {
                                    Comment comment1 = gson.fromJson(comment, Comment.class);
                                    commentList.add(comment1);



                                }
                                if (listener != null) {
                                    listener.onBeanResponse(commentList);
                                }
                            } else {
                                Log.d("Collection", "onResponse: " + "data不是jsonarray");

                            }


                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public static void sendRequestAtrractionType(String url , final OnIconResponseListener listener){//get/attraction-type/list
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    final Request request = new Request.Builder()
                            .url(url)
                            .get()
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
                            Log.d("TAG", "onResponse: "+responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("Basicinfo", "parseJSONWithGSON: "+success);
                            Log.d("Basicinfo", "parseJSONWithGSON: "+errormsg);

                            JsonElement jsonElement = gson.toJsonTree(user.getData());
                            if (jsonElement.isJsonArray()){
                                JsonArray jsonArray = jsonElement.getAsJsonArray();
                                List<AttractionList> attractionLists = new ArrayList<>();
                                for (JsonElement element : jsonArray){
                                    AttractionList attractionType = new Gson().fromJson(element, AttractionList.class);
                                    attractionLists.add(attractionType);
                                    Log.d("TAG", "onResponse: "+attractionType.id) ;
                                    Log.d("TAG", "onResponse: "+attractionType.name) ;
                                    Log.d("TAG", "onResponse: "+attractionType.icon) ;

                                }
                                listener.onBeanResponse(attractionLists);
                            }
                            else{
                                Log.d("TAG", "onResponse: "+"不是数组");
                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void sendRequestBlogLike(String url,String value){//get/blog/like/26

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    final Request request = new Request.Builder()
                            .url(url)
                            .header("Authorization", value)
                            .get()
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
                            Log.d("TAG", "onResponse: "+responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("Basicinfo", "parseJSONWithGSON: "+success);
                            Log.d("Basicinfo", "parseJSONWithGSON: "+errormsg);


                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public static void sendRequestFollowUsers(String url,String value,final OnIconResponseListener listener){//get/follow/users
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    final Request request = new Request.Builder()
                            .url(url)
                            .header("Authorization", value)
                            .get()
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
                            Log.d("TAG", "onResponse: "+responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("Basicinfo", "parseJSONWithGSON: "+success);
                            Log.d("Basicinfo", "parseJSONWithGSON: "+errormsg);
                            JsonElement data = gson.toJsonTree(user.getData());
                            if(data.isJsonArray()){
                                JsonArray jsonArray = data.getAsJsonArray();
                                List<Me> followUsers = new ArrayList<>();
                                for (JsonElement element : jsonArray){
                                    Me followUser = new Gson().fromJson(element, Me.class);
                                    followUsers.add(followUser);

                                }
                                listener.onBeanResponse(followUsers);
                            }
                            else{
                                Log.d("TAG", "onResponse: "+"不是数组");
                            }


                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static void sendRequestFollowFans(String url,String value,final OnIconResponseListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    final Request request = new Request.Builder()
                            .url(url)
                            .header("Authorization", value)
                            .get()
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
                            Log.d("TAG", "onResponse: "+responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("Basicinfo", "parseJSONWithGSON: "+success);
                            Log.d("Basicinfo", "parseJSONWithGSON: "+errormsg);
                            JsonElement data = gson.toJsonTree(user.getData());
                            if(data.isJsonArray()){
                                JsonArray jsonArray = data.getAsJsonArray();
                                List<Me> followUsers = new ArrayList<>();
                                for (JsonElement element : jsonArray){
                                    Me followUser = new Gson().fromJson(element, Me.class);
                                    followUsers.add(followUser);

                                }
                                listener.onBeanResponse(followUsers);
                            }
                            else{
                                Log.d("TAG", "onResponse: "+"不是数组");
                            }


                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
    public static void sendRequestFollowOrNot(String url,String value,final OnIconResponseListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    final Request request = new Request.Builder()
                            .url(url)
                            .header("Authorization", value)
                            .get()
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
                            Log.d("TAG", "onResponse: "+responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("Basicinfo", "parseJSONWithGSON: "+success);
                            Log.d("Basicinfo", "parseJSONWithGSON: "+errormsg);

                            listener.onBeanResponse(user.getData());



                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static void sendRequestFollowFollowed(String url, String value){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    final Request request = new Request.Builder()
                            .url(url)
                            .header("Authorization", value)
                            .get()
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
                            Log.d("TAG", "onResponse: "+responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("Basicinfo", "parseJSONWithGSON: "+success);
                            Log.d("Basicinfo", "parseJSONWithGSON: "+errormsg);



                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void sendRequestBasicinfo_id(String url,String value,final OnIconResponseListener listener){//get/user/{id}
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    /*SharedPreferences pref = context.getSharedPreferences("user", MODE_PRIVATE);
                    String id = pref.getString("userid", "");//获取id*/

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                    String finalUrl = urlBuilder.build().toString();

                    final Request request = new Request.Builder()
                            .url(finalUrl)
                            .addHeader("Authorization", value)
                            .get()
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
                            Log.d("Basicinfo", "onResponse: " + responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("Basicinfo", "parseJSONWithGSON: "+success);
                            Log.d("Basicinfo", "parseJSONWithGSON: "+errormsg);
                            JsonElement data= gson.toJsonTree(user.getData());
                            if (data.isJsonObject()){
                                JsonObject jsonObject = data.getAsJsonObject();
                                int i=jsonObject.get("id").getAsInt();
                                User_info user_info=new User_info();
                                user_info.setUser_id(String.valueOf(i));
                                user_info.setUserName(jsonObject.get("nickName").getAsString());
                                user_info.setIcon(jsonObject.get("icon").getAsString());
                                if (listener != null) {
                                    listener.onBeanResponse(user_info);
                                }
                            }
                            else {
                                Log.d("Basicinfo", "onResponse: "+"data不是jsonobject");

                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }//典型get，value是头id
    public static void sendRequestCollection(String url,String value,final OnIconResponseListener listener){//get/user/collection
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                    String finalUrl = urlBuilder.build().toString();

                    final Request request = new Request.Builder()
                            .url(finalUrl)
                            .addHeader("Authorization", value)
                            .get()
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
                            Log.d("Collection", "onResponse: " + responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("Collection", "parseJSONWithGSON: "+success);
                            Log.d("Collection", "parseJSONWithGSON: "+errormsg);
                            JsonElement data= gson.toJsonTree(user.getData());
                            if (data.isJsonArray()){
                                JsonArray jsonArray = data.getAsJsonArray();
                                List<Collection> collectionList=new ArrayList<>();
                                for (JsonElement collection : jsonArray) {
                                    Collection collection1 = gson.fromJson(collection, Collection.class);
                                    collectionList.add(collection1);
                                }
                                List<Attraction> attractionList=new ArrayList<>();
                                for (Collection collection:collectionList){
                                    Attraction attraction=new Attraction();
                                    attraction.setId(collection.getAttractionid());
                                    attraction.setName(collection.getAttraction().getName());
                                    attraction.setTypeid(collection.getAttraction().getTypeid());
                                    attraction.setPrice(collection.getAttraction().getPrice());
                                    attraction.setScore(collection.getAttraction().getScore());
                                    attraction.setArea(collection.getAttraction().getArea());
                                    attraction.setBlogs(collection.getAttraction().getBlogs());
                                    attraction.setAddress(collection.getAttraction().getAddress());
                                    attraction.setImagesList(collection.getAttraction().getImagesList());
                                    attraction.setType(collection.getAttraction().getType());
                                    attraction.setDistance(collection.getAttraction().getDistance());
                                    attractionList.add(attraction);
                                }
                                if (listener != null) {
                                    listener.onBeanResponse(attractionList);
                                }
                            }
                            else {
                                Log.d("Collection", "onResponse: "+"data不是jsonarray");

                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }//典型get，value是头id

    public static void sendRequestinfo_id(String url,String value,String id,final OnIconResponseListener listener){//get/user/info/{id}
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    /*SharedPreferences pref = context.getSharedPreferences("user", MODE_PRIVATE);
                    String id = pref.getString("userid", "");//获取id*/

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                    urlBuilder.addQueryParameter("id", id);
                    String finalUrl = urlBuilder.build().toString();

                    final Request request = new Request.Builder()
                            .url(finalUrl)
                            .addHeader("Authorization", value)
                            .get()
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
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            Log.d("info_id", "onResponse: "+responseData);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            JsonElement data= gson.toJsonTree(user.getData());
                            if (data.isJsonObject()) {
                                JsonObject jsonObject = data.getAsJsonObject();

                                int i=jsonObject.get("userId").getAsInt();
                                String id = String.valueOf(i);
                                String introduce = jsonObject.get("introduce").getAsString();
                                int j=jsonObject.get("fans").getAsInt();
                                String fans = String.valueOf(j);
                                int k=jsonObject.get("followee").getAsInt();
                                String followee = String.valueOf(k);
                                String userName = jsonObject.get("userName").getAsString();
                                i = jsonObject.get("gender").getAsInt();
                                String gender = String.valueOf(i);
                                String birthday = jsonObject.get("birthday").getAsString();
                                Log.d("info_id", "parseJSONWithGSON: "+id);///id
                                Log.d("info_id", "parseJSONWithGSON: "+userName);///name
                                Log.d("info_id", "parseJSONWithGSON: "+introduce);///大问题
                                Log.d("info_id", "parseJSONWithGSON: "+fans);
                                Log.d("info_id", "parseJSONWithGSON: "+followee);
                                Log.d("info_id","parseJSONWithGSON"+gender);
                                Log.d("info_id","parseJSONWithGSON"+birthday);

                                User_info user_info = new User_info();
                                user_info.user_id=id;
                                user_info.gender=gender;
                                user_info.birthday=birthday;
                                user_info.introduce=introduce;
                                user_info.fans=fans;
                                user_info.followee=followee;
                                user_info.userName=userName;

                                if (listener != null) {
                                    //listener.onIconResponse();
                                    /*listener.onIconResponse(icon);
                                    listener.onUserNameResponse(nickname);*/
                                    listener.onBeanResponse(user_info);
                                }
                            }
                            else {
                                Log.d("info_id", "parseJSONWithGSON: "+success);
                                Log.d("info_id", "parseJSONWithGSON: "+errormsg);
                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();




    }//get
    public static void sendRequestRecommendSelectAttraction(String url,String value,String attractionId){//post
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                    urlBuilder.addQueryParameter("attractionId", attractionId);
                    String finalUrl = urlBuilder.build().toString();
                    final Request request = new Request.Builder()
                            .url(finalUrl)
                            .addHeader("Authorization", value)
                            .get()
                            .build();//创建Request 对象
                    Call call=client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("TAG", "onFailure: ");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {



                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
    public static void sendRequestRecommendSelectRoute(String url,String value,String id){//get
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    /*SharedPreferences pref = context.getSharedPreferences("user", MODE_PRIVATE);
                    String id = pref.getString("userid", "");//获取id*/

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                    urlBuilder.addQueryParameter("id", id);
                    String finalUrl = urlBuilder.build().toString();

                    final Request request = new Request.Builder()
                            .url(finalUrl)
                            .addHeader("Authorization", value)
                            .get()
                            .build();//创建Request 对象
                    Call call=client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("TAG", "onFailure: ");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {


                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public static void sendRequestAttractionOfName(String url,String value,String name,String current,String pageSize,String x,String y,final OnIconResponseListener listener){//get/attraction/name/{name}
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    /*SharedPreferences pref = context.getSharedPreferences("user", MODE_PRIVATE);
                    String id = pref.getString("userid", "");//获取id*/

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                    urlBuilder.addQueryParameter("name", name);
                    urlBuilder.addQueryParameter("current", current);
                    urlBuilder.addQueryParameter("pageSize", pageSize);
                    urlBuilder.addQueryParameter("x", x);
                    urlBuilder.addQueryParameter("y", y);
                    String finalUrl = urlBuilder.build().toString();

                    final Request request = new Request.Builder()
                            .url(finalUrl)
                            .addHeader("Authorization", value)
                            .get()
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
                            Log.d("AttractionOfName", "onResponse: " + responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("AttractionOfName", "parseJSONWithGSON: "+success);
                            Log.d("AttractionOfName", "parseJSONWithGSON: "+errormsg);
                            JsonElement data= gson.toJsonTree(user.getData());
                            if (data.isJsonArray()){
                                JsonArray jsonArray = data.getAsJsonArray();
                                List<Attraction> attractionList=new ArrayList<>();
                                for (JsonElement attraction : jsonArray) {
                                    Attraction attraction1 = gson.fromJson(attraction, Attraction.class);
                                    attractionList.add(attraction1);
                                }
                                if (listener != null) {
                                    listener.onBeanResponse(attractionList);
                                }
                            }
                            else {
                                Log.d("AttractionOfName", "onResponse: "+"data不是jsonarray");

                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }//get

    public static void sendRequestCancel_Collection(String url,String value){//get/user/cancel_collection/{ATid}
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    /*SharedPreferences pref = context.getSharedPreferences("user", MODE_PRIVATE);
                    String id = pref.getString("userid", "");//获取id*/

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                    //urlBuilder.addQueryParameter("id", id);
                    String finalUrl = urlBuilder.build().toString();

                    final Request request = new Request.Builder()
                            .url(finalUrl)
                            .addHeader("Authorization", value)
                            .get()
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
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            Log.d("Cancel_Collection", "onResponse: "+responseData);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("Cancel_Collection", "parseJSONWithGSON: "+success);
                            Log.d("Cancel_Collection", "parseJSONWithGSON: "+errormsg);

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }//取消收藏
    public static void sendRequestUpdateIcon(String url, String value, File icon){//post/user/updateIcon
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), icon);
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("file", icon.getName(), fileBody)
                            .build();
                    final Request request = new Request.Builder()
                            .url(url)
                            .addHeader("Authorization", value)
                            .post(requestBody)
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
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            Log.d("UpdateIcon", "onResponse: "+responseData);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("UpdateIcon", "parseJSONWithGSON: "+success);
                            Log.d("UpdateIcon", "parseJSONWithGSON: "+errormsg);

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }//更新头像
    public static void sendRequestSign_In(String url, String value, String id){//post/user/sign_in
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("id", id)
                            .build();
                    final Request request = new Request.Builder()
                            .url(url)
                            .addHeader("Authorization", value)
                            .post(requestBody)
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
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            Log.d("Sign_In", "onResponse: "+responseData);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("Sign_In", "parseJSONWithGSON: "+success);
                            Log.d("Sign_In", "parseJSONWithGSON: "+errormsg);

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public static void sendRequestQueryById(String url,String value,String id,final OnIconResponseListener listener) {//get/attraction/queryById/{id}
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    /*SharedPreferences pref = context.getSharedPreferences("user", MODE_PRIVATE);
                    String id = pref.getString("userid", "");//获取id*/

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                    urlBuilder.addQueryParameter("id", id);
                    String finalUrl = urlBuilder.build().toString();

                    final Request request = new Request.Builder()
                            .url(finalUrl)
                            .addHeader("Authorization", value)
                            .get()
                            .build();//创建Request 对象
                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("TAG", "onFailure: ");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            String responseData = response.body().string();
                            Log.d("QueryById", "onResponse: " + responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("QueryById", "parseJSONWithGSON: "+success);
                            Log.d("QueryById", "parseJSONWithGSON: "+errormsg);
                            JsonElement data= gson.toJsonTree(user.getData());
                            if (data.isJsonObject()){
                                JsonObject jsonObject=data.getAsJsonObject();
                                Attraction attraction = gson.fromJson(jsonObject, Attraction.class);

                                if (listener != null) {
                                    listener.onBeanResponse(attraction);
                                }
                            }
                            else {
                                Log.d("Collection", "onResponse: "+"data不是jsonarray");

                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }//根据id查询用户信息get
    public static void sendRequestTOBD(String url, String value, String id,String page,String number,String x,String y,final OnIconResponseListener listener){//get/attraction/of/typeOrderByDistance
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    /*SharedPreferences pref = context.getSharedPreferences("user", MODE_PRIVATE);
                    String id = pref.getString("userid", "");//获取id*/

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                    urlBuilder.addQueryParameter("typeId", id);
                    urlBuilder.addQueryParameter("current", page);
                    urlBuilder.addQueryParameter("pageSize", number);
                    urlBuilder.addQueryParameter("x", x);
                    urlBuilder.addQueryParameter("y", y);
                    String finalUrl = urlBuilder.build().toString();

                    final Request request = new Request.Builder()
                            .url(finalUrl)
                            .addHeader("Authorization", value)
                            .get()
                            .build();//创建Request 对象
                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("TAG", "onFailure: ");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            String responseData = response.body().string();
                            Log.d("TOBD", "onResponse: " + responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success=user.getSuccess().toString();
                            String errormsg=user.getErrormsg();
                            Log.d("TOBD", "parseJSONWithGSON: "+success);
                            Log.d("TOBD", "parseJSONWithGSON: "+errormsg);
                            JsonElement data= gson.toJsonTree(user.getData());
                            if (data.isJsonArray()){
                                JsonArray jsonArray=data.getAsJsonArray();
                                List<Attraction> attractionList = new ArrayList<>();
                                for (JsonElement attraction : jsonArray) {
                                    Attraction attraction1 = gson.fromJson(attraction, Attraction.class);
                                    attractionList.add(attraction1);
                                }
                                if (listener != null) {
                                    listener.onBeanResponse(attractionList);
                                }
                            }
                            else {
                                Log.d("Collection", "onResponse: "+"data不是jsonarray");

                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }//根据id查询用户信息get
    public static void sendRequestTOBS(String url, String value, String id,String page,String number,String x,String y,final OnIconResponseListener listener) {//get/attraction/of/typeOrderScore
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    /*SharedPreferences pref = context.getSharedPreferences("user", MODE_PRIVATE);
                    String id = pref.getString("userid", "");//获取id*/

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                    urlBuilder.addQueryParameter("typeId", id);
                    urlBuilder.addQueryParameter("current", page);
                    urlBuilder.addQueryParameter("pageSize", number);
                    urlBuilder.addQueryParameter("x", x);
                    urlBuilder.addQueryParameter("y", y);
                    String finalUrl = urlBuilder.build().toString();

                    final Request request = new Request.Builder()
                            .url(finalUrl)
                            .addHeader("Authorization", value)
                            .get()
                            .build();//创建Request 对象
                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("TAG", "onFailure: ");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            String responseData = response.body().string();
                            Log.d("TOBS", "onResponse: " + responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success = user.getSuccess().toString();
                            String errormsg = user.getErrormsg();
                            Log.d("TOBS", "parseJSONWithGSON: " + success);
                            Log.d("TOBS", "parseJSONWithGSON: " + errormsg);
                            JsonElement data = gson.toJsonTree(user.getData());
                            if (data.isJsonArray()) {
                                JsonArray jsonArray = data.getAsJsonArray();
                                List<Attraction> attractionList = new ArrayList<>();
                                for (JsonElement attraction : jsonArray) {
                                    Attraction attraction1 = gson.fromJson(attraction, Attraction.class);
                                    attractionList.add(attraction1);
                                }
                                if (listener != null) {
                                    listener.onBeanResponse(attractionList);
                                }
                            } else {
                                Log.d("Collection", "onResponse: " + "data不是jsonarray");

                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void sendRequestTOBA(String url, String value, String id,String page,String number,String x,String y,final OnIconResponseListener listener) {//get/attraction/of/typeOrderScore
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    /*SharedPreferences pref = context.getSharedPreferences("user", MODE_PRIVATE);
                    String id = pref.getString("userid", "");//获取id*/

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                    urlBuilder.addQueryParameter("typeId", id);
                    urlBuilder.addQueryParameter("current", page);
                    urlBuilder.addQueryParameter("pageSize", number);
                    urlBuilder.addQueryParameter("x", x);
                    urlBuilder.addQueryParameter("y", y);
                    String finalUrl = urlBuilder.build().toString();

                    final Request request = new Request.Builder()
                            .url(finalUrl)
                            .addHeader("Authorization", value)
                            .get()
                            .build();//创建Request 对象
                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("TAG", "onFailure: ");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            String responseData = response.body().string();
                            Log.d("TOBS", "onResponse: " + responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success = user.getSuccess().toString();
                            String errormsg = user.getErrormsg();
                            Log.d("TOBS", "parseJSONWithGSON: " + success);
                            Log.d("TOBS", "parseJSONWithGSON: " + errormsg);
                            JsonElement data = gson.toJsonTree(user.getData());
                            if (data.isJsonArray()) {
                                JsonArray jsonArray = data.getAsJsonArray();
                                List<Attraction> attractionList = new ArrayList<>();
                                for (JsonElement attraction : jsonArray) {
                                    Attraction attraction1 = gson.fromJson(attraction, Attraction.class);
                                    attractionList.add(attraction1);
                                }
                                if (listener != null) {
                                    listener.onBeanResponse(attractionList);
                                }
                            } else {
                                Log.d("Collection", "onResponse: " + "data不是jsonarray");

                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static void sendRequestTOBN(String url, String value, String name,String page, String number,String x,String y,final OnIconResponseListener listener){//get/attraction/of/name
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    /*SharedPreferences pref = context.getSharedPreferences("user", MODE_PRIVATE);
                    String id = pref.getString("userid", "");//获取id*/

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                    urlBuilder.addQueryParameter("name", name);
                    urlBuilder.addQueryParameter("current", page);
                    urlBuilder.addQueryParameter("pageSize", number);
                    urlBuilder.addQueryParameter("x", x);
                    urlBuilder.addQueryParameter("y", y);
                    String finalUrl = urlBuilder.build().toString();

                    final Request request = new Request.Builder()
                            .url(finalUrl)
                            .addHeader("Authorization", value)
                            .get()
                            .build();//创建Request 对象
                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("TAG", "onFailure: ");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            String responseData = response.body().string();
                            Log.d("TOBN", "onResponse: " + responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success = user.getSuccess().toString();
                            String errormsg = user.getErrormsg();
                            Log.d("TOBN", "parseJSONWithGSON: " + success);
                            Log.d("TOBN", "parseJSONWithGSON: " + errormsg);
                            JsonElement data = gson.toJsonTree(user.getData());
                            if (data.isJsonArray()) {
                                JsonArray jsonArray = data.getAsJsonArray();
                                List<Attraction> attractionList = new ArrayList<>();
                                for (JsonElement attraction : jsonArray) {
                                    Attraction attraction1 = gson.fromJson(attraction, Attraction.class);
                                    attractionList.add(attraction1);
                                }
                                if (listener != null) {
                                    listener.onBeanResponse(attractionList);
                                }
                            } else {
                                Log.d("Collection", "onResponse: " + "data不是jsonarray");

                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static void sendRequestBlogFrom_Attraction(String url,String value,String attraction_id,String current,String pageSize,final OnIconResponseListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    /*SharedPreferences pref = context.getSharedPreferences("user", MODE_PRIVATE);
                    String id = pref.getString("userid", "");//获取id*/

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                    urlBuilder.addQueryParameter("attraction_id", attraction_id);
                    urlBuilder.addQueryParameter("current", current);
                    urlBuilder.addQueryParameter("pageSize", pageSize);
                    String finalUrl = urlBuilder.build().toString();

                    final Request request = new Request.Builder()
                            .url(finalUrl)
                            .addHeader("Authorization", value)
                            .get()
                            .build();//创建Request 对象
                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("TAG", "onFailure: ");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            String responseData = response.body().string();
                            Log.d("TAG", "onResponse: " + responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success = user.getSuccess().toString();
                            String errormsg = user.getErrormsg();
                            Log.d("TAG", "onResponse: " + success);
                            Log.d("TAG", "onResponse: " + errormsg);
                            JsonElement data = gson.toJsonTree(user.getData());
                            if (data.isJsonArray()) {
                                JsonArray array = data.getAsJsonArray();
                                List<Note> blog = new ArrayList<>();
                                for (JsonElement jsonElement : array) {
                                    Note note = gson.fromJson(jsonElement, Note.class);
                                    blog.add(note);

                                }
                                listener.onBeanResponse(blog);
                            } else {
                                Log.d("Collection", "onResponse: " + "data不是jsonarray");

                            }

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


    }//返回信息不重要的时候用，比如退出登录，返回是否成功

    //private static void send


    public static void sendRequestUserCheck(String url,String token,final OnIconResponseListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    /*SharedPreferences pref = context.getSharedPreferences("user", MODE_PRIVATE);
                    String id = pref.getString("userid", "");//获取id*/

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
                    urlBuilder.addQueryParameter("token", token);
                    String finalUrl = urlBuilder.build().toString();

                    final Request request = new Request.Builder()
                            .url(finalUrl)
                            .get()
                            .build();//创建Request 对象
                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("TAG", "onFailure: ");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            String responseData = response.body().string();
                            Log.d("TAG", "onResponse: " + responseData);
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            String success = user.getSuccess().toString();
                            String errormsg = user.getErrormsg();
                            Log.d("TAG", "onResponse: " + success);
                            Log.d("TAG", "onResponse: " + errormsg);

                            listener.onBeanResponse(user.getData());


                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void sendRequestUserMe(String url, String value,Context context,final OnIconResponseListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    final Request request = new Request.Builder()
                            .url(url)
                            .header("Authorization", value)
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
                            Gson gson = new Gson();
                            User user = gson.fromJson(responseData, User.class);
                            User_info user_info = new User_info();
                            String success = user.getSuccess().toString();
                            String errormsg = user.getErrormsg();
                            JsonElement data = gson.toJsonTree(user.getData());
                            if (data.isJsonObject()) {
                                JsonObject jsonObject = data.getAsJsonObject();
                                int i = jsonObject.get("id").getAsInt();
                                String id = String.valueOf(i);
                                String nickname = jsonObject.get("nickName").getAsString();
                                String icon = jsonObject.get("icon").getAsString();
                                Log.d("UserMe", "parseJSONWithGSON: " + id);
                                Log.d("UserMe", "parseJSONWithGSON: " + nickname);
                                Log.d("UserMe", "parseJSONWithGSON: " + icon);
                                Log.d("UserMe", "onResponse: " + responseData);
                                Log.d("UserMe", "parseJSONWithGSON: " + success);
                                Log.d("UserMe", "parseJSONWithGSON: " + errormsg);
                                SharedPreferences preferences = context.getSharedPreferences("Login", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("USER_ID", id);
                                editor.apply();
                                Log.e("UserMe", "onResponse: " + id);
                                user_info.user_id = id;
                                user_info.userName = nickname;
                                user_info.icon = icon;


                                //Log.d("UserMe", "onResponse: "+id);
                                //icona[0] = String.valueOf(icon);
                                if (listener != null) {
                                    //listener.onIconResponse();
                                    listener.onBeanResponse(user_info);
                                }

                            } else {
                                Log.d("UserMe", "parseJSONWithGSON: " + success);
                                Log.d("UserMe", "parseJSONWithGSON: " + errormsg);
                                //icona[0]= "https://i.postimg.cc/NMQjFFVv/cat.jpg";
                            }


                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }//get/user/me
    public interface OnIconResponseListener {

        void onBeanResponse(Object bean);

    }

}
