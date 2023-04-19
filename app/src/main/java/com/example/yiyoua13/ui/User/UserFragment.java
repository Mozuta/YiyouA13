package com.example.yiyoua13.ui.User;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.yiyoua13.FragAct;
import com.example.yiyoua13.PersonAdapter;
import com.example.yiyoua13.R;
import com.example.yiyoua13.WaterFallAdapter;
import com.example.yiyoua13.databinding.FragmentUserBinding;
import com.example.yiyoua13.ui.FansFollowActivity;
import com.example.yiyoua13.ui.FavoriteActivity;
import com.example.yiyoua13.ui.InfoActivity;
import com.example.yiyoua13.ui.MineActivity;
import com.example.yiyoua13.ui.Url_Request;
import com.example.yiyoua13.variousclass.fans;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserFragment extends Fragment implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private ImageButton bt_info;
    private ImageButton bt_fav;
    private ImageButton bt_mine;
    private int pagenum = 1;
    private int flag = 0;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager2;
    private PersonAdapter mAdapter;
    private WaterFallAdapter mAdapter2;
    private LinearLayout myinfo;

    private TextView myfollow;
    private TextView myfans;
    private TextView mycontent;

    private FragmentUserBinding binding;
    private CircleImageView touxiang;
    private Bitmap head;// 头像Bitmap
    private SharedPreferences sp,sp_note;
    private String user_token,user_id;
    @SuppressLint("SdCardPath")
    private static String path = "/data/data/com.example.yiyoua13/";// sd路径
    private void initPhotoError(){
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        UserViewModel userViewModel =
                new ViewModelProvider(this).get(UserViewModel.class);



        initPhotoError();

        binding = FragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        init();//大的在这呢！！！！！！！！！！！！


        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");// 从SD卡中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            touxiang.setImageDrawable(drawable);
        } else {
            /**
             * 如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
        }
        return root;
    }
    private void showTypeDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog dialog = builder.create();
        View view = View.inflate(getActivity(), R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                //打开文件
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                startActivityForResult(intent2, 2);// 采用ForResult打开
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }
    private void init(){
        sp = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        //user_token=TOKEN
        user_token=sp.getString("TOKEN","");
        user_id=sp.getString("USER_ID","");
        bt_info = binding.settings;
        myinfo= binding.space;
        touxiang=binding.tx;
        mRecyclerView = binding.recyclerView;
        mycontent = binding.myArticle;
        myfans = binding.myFans;
        myfollow = binding.myFollow;
        bt_fav = binding.favourites;
        bt_mine = binding.mine;
        bt_fav.setOnClickListener(this);
        bt_mine.setOnClickListener(this);
        bt_info.setOnClickListener(this);
        mycontent.setOnClickListener(this);
        myfans.setOnClickListener(this);
        myfollow.setOnClickListener(this);
        myinfo.setOnClickListener(this);
        if (!user_token.equals("")){
            Url_Request.sendRequestBasicinfo_id(Url_Request.getUrl_head() + "/user/"+user_id, user_token,  new Url_Request.OnIconResponseListener() {
                @Override
                public void onBeanResponse(Object bean) {
                    Url_Request.User_info user_basicinfo = (Url_Request.User_info) bean;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Glide.with(getActivity()).load(user_basicinfo.getIcon()).into(touxiang);
                        }
                    });
                }
            });
            Url_Request.sendRequestinfo_id(Url_Request.getUrl_head() + "/user/info", user_token, user_id, new Url_Request.OnIconResponseListener() {
                @Override
                public void onBeanResponse(Object bean) {
                    Url_Request.User_info user_info = (Url_Request.User_info) bean;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.name.setText(user_info.getUserName());
                            binding.fansnum.setText(user_info.getFans());
                            binding.follownum.setText(user_info.getFollowee());
                        }
                    });
                }
            });
        }

        //binding.refreshLayout.setEnableLoadMore(false);
        //下拉刷新
        SmartRefreshLayout refresh = binding.refreshLayout;
        refresh.setRefreshHeader(new ClassicsHeader(getActivity()));
        refresh.setRefreshFooter(new ClassicsFooter(getActivity()));
        //refresh.setRefreshHeader(new DeliveryHeader(getActivity()));
        refresh.setOnLoadMoreListener(refreshLayout -> {



            loadData();
            refreshLayout.finishLoadMore(/*,false*/);//传入false表示加载失败
        });
        refresh.setOnRefreshListener(refreshLayout ->{
            //刷新数据

            pagenum=mAdapter2.clearData();
            FreshData();




            refreshLayout.finishRefresh();//传入false表示刷新失败
        });

        //mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        //mAdapter = new PersonAdapter(getActivity(), buildDatafans());
        mLayoutManager2 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        if (!user_token.equals("")){
            mAdapter2 = new WaterFallAdapter(getActivity(), buildData());
            mRecyclerView.setAdapter(mAdapter2);
        }


        mRecyclerView.setLayoutManager(mLayoutManager2);
        mRecyclerView.setAdapter(mAdapter2);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.space:
                if (user_token.equals("")) {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    Intent intent0 = new Intent(getActivity(), FragAct.class);
                    startActivity(intent0);
                }else {
                    Intent intent = new Intent(getActivity(), FansFollowActivity.class);
                    intent.putExtra("name",binding.name.getText().toString());
                    intent.putExtra("fans_num",binding.fansnum.getText().toString());
                    intent.putExtra("follow_num",binding.follownum.getText().toString());
                    startActivity(intent);
                }

                //showTypeDialog();
                break;
            case R.id.settings:
                if (user_token.equals("")) {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    Intent intent0 = new Intent(getActivity(), FragAct.class);
                    startActivity(intent0);
                }else {
                    Intent intent = new Intent(getActivity(), InfoActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.favourites:
                if (user_token.equals("")) {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    Intent intent0 = new Intent(getActivity(), FragAct.class);
                    startActivity(intent0);
                }else {
                    Intent intent = new Intent(getActivity(), FavoriteActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mine:
                if (user_token.equals("")) {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    Intent intent0 = new Intent(getActivity(), FragAct.class);
                    startActivity(intent0);
                }else {
                    Intent intent = new Intent(getActivity(), MineActivity.class);
                    startActivity(intent);
                }
                break;

        }
    }
    private void FreshData(){
        Url_Request.sendRequestBlogOfFollow(Url_Request.getUrl_head()+"/blog/of/follow", user_token,String.valueOf(System.currentTimeMillis()),"0",new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                List<WaterFallAdapter.PersonCard> list = new ArrayList<>();
                Url_Request.NoteList noteLists = (Url_Request.NoteList) bean;
                String minTime = noteLists.getMintime().toString();
                String offset = noteLists.getOffset().toString();
                //存入sharedpreference
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("note", MODE_PRIVATE).edit();
                editor.putString("minTime", minTime);
                editor.putString("offset", offset);
                editor.apply();
                List<Url_Request.Note> blogHots = noteLists.getList();
                for (Url_Request.Note note : blogHots) {
                    WaterFallAdapter.PersonCard p = new WaterFallAdapter.PersonCard();
                    p.name = note.getName();
                    p.content = note.getTitle();
                    p.like = note.getLiked().toString();
                    p.avatarUrl = note.getImageslist().get(0).toString();
                    p.headurl = note.getIcon();
                    p.islike = note.getIslike();
                    p.imgHeight = 500;
                    p.id = note.getId().toString();
                    list.add(p);
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter2.updateData(list);
                    }
                });
            }
        });
        pagenum=2;
    }

    private List<WaterFallAdapter.PersonCard> buildData() {
        List<WaterFallAdapter.PersonCard> list = new ArrayList<>();
        Url_Request.sendRequestBlogOfFollow(Url_Request.getUrl_head()+"/blog/of/follow", user_token,String.valueOf(System.currentTimeMillis()),"0",new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                Url_Request.NoteList noteLists = (Url_Request.NoteList) bean;
                String minTime = noteLists.getMintime().toString();
                String offset = noteLists.getOffset().toString();
                //存入sharedpreference
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("note", MODE_PRIVATE).edit();
                editor.putString("minTime", minTime);
                editor.putString("offset", offset);
                editor.apply();
                List<Url_Request.Note> blogHots = noteLists.getList();
                for (Url_Request.Note note : blogHots) {
                    WaterFallAdapter.PersonCard p = new WaterFallAdapter.PersonCard();
                    p.name = note.getName();
                    p.content = note.getTitle();
                    p.like = note.getLiked().toString();
                    p.avatarUrl = note.getImageslist().get(0).toString();
                    p.headurl = note.getIcon();
                    p.islike = note.getIslike();
                    p.imgHeight = 500;
                    p.id = note.getId().toString();
                    list.add(p);
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter2.notifyDataSetChanged();
                    }
                });
            }
        });

        pagenum=2;


        return list;
    }
    private void loadData(){
        sp_note = getActivity().getSharedPreferences("note", MODE_PRIVATE);
        String mT = sp_note.getString("minTime", "");
        String os = sp_note.getString("offset", "");
        Url_Request.sendRequestBlogOfFollow(Url_Request.getUrl_head()+"/blog/of/follow", user_token,mT,os,new Url_Request.OnIconResponseListener() {
            @Override
            public void onBeanResponse(Object bean) {
                List<WaterFallAdapter.PersonCard> list = new ArrayList<>();
                Url_Request.NoteList noteLists = (Url_Request.NoteList) bean;
                String minTime = noteLists.getMintime().toString();
                String offset = noteLists.getOffset().toString();
                //存入sharedpreference
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("note", MODE_PRIVATE).edit();
                editor.putString("minTime", minTime);
                editor.putString("offset", offset);
                editor.apply();
                List<Url_Request.Note> blogHots = noteLists.getList();
                for (Url_Request.Note note : blogHots) {
                    WaterFallAdapter.PersonCard p = new WaterFallAdapter.PersonCard();
                    p.name = note.getName();
                    p.content = note.getTitle();
                    p.like = note.getLiked().toString();
                    p.avatarUrl = note.getImageslist().get(0).toString();
                    p.headurl = note.getIcon();
                    p.islike = note.getIslike();
                    p.imgHeight = 500;
                    p.id = note.getId().toString();
                    list.add(p);
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter2.updateData(list);
                    }
                });
            }
        });
        pagenum++;
    }

    private List<fans> buildDatafans() {
        List<fans> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            fans fans = new fans();
            fans.setFans_name("粉丝" + i);
            fans.setFans_headpic("https://i.postimg.cc/x1HHh4C7/1.png");
            //fans.setFans_id(String.valueOf(i));
            fans.setFans_num(String.valueOf(i));
            fans.setContent_num(String.valueOf(i*2));
            list.add(fans);
        }
        return list;
    }
    private List<fans> buildDatafollow() {
        List<fans> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            fans fans = new fans();
            fans.setFans_name("关注" + i);
            fans.setFans_headpic("https://i.postimg.cc/x1HHh4C7/1.png");
            //fans.setFans_id(String.valueOf(i));
            fans.setFans_num(String.valueOf(i));
            fans.setContent_num(String.valueOf(i*2));
            list.add(fans);
        }
        return list;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d(TAG, "onActivityResult: "+requestCode);

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
                        setPicToView(head);// 保存在SD卡中
                        Log.e(TAG,head.toString());
                        touxiang.setImageBitmap(head);// 用ImageButton显示出来
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
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


    private void setPicToView(Bitmap mBitmap) {
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
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}