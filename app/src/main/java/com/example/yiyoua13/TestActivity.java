package com.example.yiyoua13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yiyoua13.bean.CommentBean;
import com.example.yiyoua13.bean.CommentDetailBean;
import com.example.yiyoua13.bean.ReplyDetailBean;
import com.example.yiyoua13.databinding.ActivityTestBinding;
import com.example.yiyoua13.task.RatingBarLoaderTask;
import com.example.yiyoua13.task.TextLoaderTask;
import com.example.yiyoua13.ui.PCenter_Activity;
import com.example.yiyoua13.ui.SpotActivity;
import com.example.yiyoua13.ui.Url_Request;
import com.example.yiyoua13.utils.StatusBarUtil;
import com.example.yiyoua13.view.CommentExpandableListView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    //private Integer[] list = {R.drawable.hog, R.drawable.lwr, R.drawable.light, R.drawable.ic_launcher_background, R.drawable.hog};
    private static final String TAG = "MainActivity";
    private androidx.appcompat.widget.Toolbar toolbar;
    private List<String> list2 = new ArrayList<String>();
    private LikeButton likeButton;
    private int pagenum = 1;
    private SharedPreferences sp,sp2;
    private String id;
    private String user_token,user_id,note_id,spot_id,user_names_tring,user_logo_url;
    private CardView itemView;
    private ImageView spot_img;
    private TextView spot_name,spot_rating,spot_price,spot_kind,spot_area,spot_distance,spot_discription,user_name,create_time,note_title,note_content,like_num,comment_num,spot_tag,like_two,like_down;
    private MaterialRatingBar spot_ratingBar;
    private LinearLayout user;
    private CircleImageView avatar;
    private TextView bt_comment;
    private CommentExpandableListView expandableListView;
    private CommentExpandAdapter adapter;
    private CommentBean commentBean;
    private MZBannerView<String> mMZBanner;
    private List<CommentDetailBean> commentsList;
    private BottomSheetDialog dialog;
    private String testJson = "{\n" +
            "\t\"code\": 1000,\n" +
            "\t\"message\": \"查看评论成功\",\n" +
            "\t\"data\": {\n" +
            "\t\t\"total\": 3,\n" +
            "\t\t\"list\": [{\n" +
            "\t\t\t\t\"id\": 42,\n" +
            "\t\t\t\t\"nickName\": \"程序猿\",\n" +
            "\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\"content\": \"时间是一切财富中最宝贵的财富。\",\n" +
            "\t\t\t\t\"createDate\": \"三分钟前\",\n" +
            "\t\t\t\t\"replyList\": [{\n" +
            "\t\t\t\t\t\"nickName\": \"沐風\",\n" +
            "\t\t\t\t\t\"content\": \"时间总是在不经意中擦肩而过,不留一点痕迹.\",\n" +
            "\t\t\t\t\t\"createDate\": \"一个小时前\"\n" +
            "\t\t\t\t}]\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": 41,\n" +
            "\t\t\t\t\"nickName\": \"设计狗\",\n" +
            "\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\"content\": \"这世界要是没有爱情，它在我们心中还会有什么意义！这就如一盏没有亮光的走马灯。\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 1,\n" +
            "\t\t\t\t\"createDate\": \"一天前\",\n" +
            "\t\t\t\t\"replyList\": [{\n" +
            "\t\t\t\t\t\"nickName\": \"沐風\",\n" +
            "\t\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\t\"commentId\": \"\",\n" +
            "\t\t\t\t\t\"content\": \"时间总是在不经意中擦肩而过,不留一点痕迹.\",\n" +
            "\t\t\t\t\t\"status\": \"01\",\n" +
            "\t\t\t\t\t\"createDate\": \"三小时前\"\n" +
            "\t\t\t\t}]\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"id\": 40,\n" +
            "\t\t\t\t\"nickName\": \"产品喵\",\n" +
            "\t\t\t\t\"userLogo\": \"http://ucardstorevideo.b0.upaiyun.com/userLogo/9fa13ec6-dddd-46cb-9df0-4bbb32d83fc1.png\",\n" +
            "\t\t\t\t\"content\": \"笨蛋自以为聪明，聪明人才知道自己是笨蛋。\",\n" +
            "\t\t\t\t\"imgId\": \"xcclsscrt0tev11ok364\",\n" +
            "\t\t\t\t\"replyTotal\": 0,\n" +
            "\t\t\t\t\"createDate\": \"三天前\",\n" +
            "\t\t\t\t\"replyList\": []\n" +
            "\t\t\t}\n" +
            "\t\t]\n" +
            "\t}\n" +
            "}";
    public static class BannerViewHolder implements MZViewHolder<String> {
        private ImageView mImageView;

        @SuppressLint("MissingInflatedId")
        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_lidt, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        public void onBind(Context context, int position, String data){
            Glide.with(context).load(data).into(mImageView);
        }

        /*@Override
        public void onBind(Context context, int position, String data) {
            // 数据绑定
            mImageView.setImageURI(data);
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }
        initView();
    }

    private void initView() {
        //sp2 = TestActivity.this.getSharedPreferences("Login", MODE_PRIVATE);
        note_id = getIntent().getStringExtra("person_data");
        sp = TestActivity.this.getSharedPreferences("Login", MODE_PRIVATE);
        //user_token=TOKEN
        user_token=sp.getString("TOKEN","");
        user_id=sp.getString("USER_ID","");
        avatar= (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.detail_page_userLogo);
        spot_name = (TextView) findViewById(R.id.gt_name);
        itemView = (CardView)findViewById(R.id.card_spot);
        itemView.setOnClickListener(this);
        create_time=(TextView)findViewById(R.id.detail_page_time);
        user_name = (TextView) findViewById(R.id.detail_page_userName);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        expandableListView = (CommentExpandableListView) findViewById(R.id.detail_page_lv_comment);
        bt_comment = (TextView) findViewById(R.id.detail_page_do_comment);
        bt_comment.setOnClickListener(this);
        user=(LinearLayout)findViewById(R.id.detail_page_title_container);


        note_title=(TextView)findViewById(R.id.detail_page_title);
        note_content=(TextView)findViewById(R.id.detail_page_story);
        spot_img=(ImageView)findViewById(R.id.gt_icon);
        spot_area=(TextView)findViewById(R.id.gt_area);
        spot_ratingBar=(MaterialRatingBar) findViewById(R.id.gt_materialRatingBar);
        spot_rating=(TextView)findViewById(R.id.gt_rating);
        spot_price=(TextView)findViewById(R.id.gt_price);
        spot_kind=(TextView)findViewById(R.id.gt_kind);
        spot_distance=(TextView)findViewById(R.id.gt_distance);
        spot_discription=(TextView)findViewById(R.id.intro_gt);
        spot_tag=(TextView)findViewById(R.id.gt_tag);
        like_num=(TextView)findViewById(R.id.zanle_num);
        like_two=(TextView) findViewById(R.id.xxxzanle);
        like_down=(TextView)findViewById(R.id.like_num2);
        likeButton=(LikeButton)findViewById(R.id.detail_page_like);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_token.equals("")){
                    Toast.makeText(TestActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(TestActivity.this,FragAct.class);
                    startActivity(intent);
                }
                else {
                    Intent intent=new Intent(TestActivity.this, PCenter_Activity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
            }
        });
        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if (user_token.equals("")){
                    Toast.makeText(TestActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(TestActivity.this,FragAct.class);
                    startActivity(intent);
                    likeButton.setLiked(false);
                }
                else {
                    Url_Request.sendRequestBlogLike(Url_Request.getUrl_head()+"/blog/like/"+note_id, user_token);//点赞
                    if(like_down.getText().equals("99+")){
                    }
                    else{
                        int likenum1=Integer.parseInt(like_down.getText().toString());
                        likenum1++;
                        like_down.setText(String.valueOf(likenum1));
                    }
                }

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if(user_token.equals("")){
                    Toast.makeText(TestActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(TestActivity.this,FragAct.class);
                    startActivity(intent);
                    likeButton.setLiked(true);
                }
                else{
                    Url_Request.sendRequestBlogLike(Url_Request.getUrl_head()+"/blog/like/"+note_id, user_token);//点赞
                    if(like_down.getText().equals("99+")){
                    }
                    else{
                        int likenum1=Integer.parseInt(like_down.getText().toString());
                        likenum1--;
                        like_down.setText(String.valueOf(likenum1));
                    }
                }


            }
        });
        Log.e("12121",testJson);

        //设置toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        commentsList = generateTestData();
        //Log.e("<<<0>>>", "initView: " + commentsList.get(1).getContent());
        initExpandableListView(commentsList);

        Url_Request.sendRequestBlogSearch(Url_Request.getUrl_head() + "/blog/search/" + note_id, user_token, new Url_Request.OnIconResponseListener() {
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

                TestActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        collapsingToolbar.setTitle(" ");
                        //字体变小

                        Glide.with(TestActivity.this).load(note.getIcon()).error(R.drawable.sqw).into(avatar);
                        new TextLoaderTask(user_name, note.getName()).execute();
                        String str = note.getCreatetime();
                        str = str.replace("T", " ");
                        new TextLoaderTask(create_time, str).execute();
                        new TextLoaderTask(note_title, note.getTitle()).execute();
                        new TextLoaderTask(note_content, note.getContent()).execute();
                        new TextLoaderTask(spot_name, note.getAttraction().getName()).execute();
                        Glide.with(TestActivity.this).load(note.getAttraction().getImagesList().get(0)).error(R.drawable.sqw).into(spot_img);
                        new TextLoaderTask(spot_area, note.getAttraction().getArea()).execute();
                        if (note.getAttraction().getScore() == 0){
                            new TextLoaderTask(spot_rating,"暂无评分").execute();
                            new RatingBarLoaderTask(spot_ratingBar,"0").execute();
                        } else{
                            new TextLoaderTask(spot_rating,String.format("%.1f", Double.parseDouble(String.valueOf(note.getAttraction().getScore())) / 10)).execute();
                            new RatingBarLoaderTask(spot_ratingBar,String.format("%.1f", Double.parseDouble(String.valueOf(note.getAttraction().getScore())) / 10)).execute();
                        }
                        new TextLoaderTask(spot_price, note.getAttraction().getPrice().toString()+"元起").execute();
                        new TextLoaderTask(spot_kind, note.getAttraction().getType()).execute();
                        spot_id = note.getAttraction().getId().toString();
                        user_logo_url = note.getIcon();
                        /*if (note.getAttraction().getDistance()<1000)
                            new TextLoaderTask(spot_distance, note.getAttraction().getDistance()+"m").execute();
                        else
                            new TextLoaderTask(spot_distance, note.getAttraction().getDistance()/1000+"km").execute();*/
                        new TextLoaderTask(spot_distance, "时间："+note.getAttraction().getOpenhours()).execute();
                        new TextLoaderTask(spot_discription, note.getAttraction().getIntroduction()).execute();
                        new TextLoaderTask(spot_tag, note.getAttraction().getType()).execute();
                        new TextLoaderTask(like_down, note.getLiked().toString()).execute();
                        id = note.getUserid().toString();
                        user_names_tring=note.getName();
                        if (note.getIslike()!=null) {
                            likeButton.setLiked(note.getIslike());
                        } else {
                            likeButton.setLiked(false);
                        }
                        if (note.getLiked()<1){
                            new TextLoaderTask(like_num, "等你来赞~").execute();
                        }
                        else{
                            new TextLoaderTask(like_num, "等"+note.getLiked().toString()+"人赞了").execute();
                        }

                        mMZBanner = findViewById(R.id.detail_page_image);
                        mMZBanner.setCanLoop(true);
                        for (String s : note.getImageslist()) {
                            list2.add(s);
                        }
                        mMZBanner.setPages((list2), new MZHolderCreator<TestActivity.BannerViewHolder>() {
                            @Override
                            public TestActivity.BannerViewHolder createViewHolder() {
                                return new TestActivity.BannerViewHolder();
                            }
                        });
                    }
                });
            }
        });
        Url_Request.sendRequestBlogUserLike(Url_Request.getUrl_head() + "/blog/likes/" + note_id, user_token, new Url_Request.OnIconResponseListener() {
                    @Override
                    public void onBeanResponse(Object bean) {
                        List<Url_Request.Me> meList = (List<Url_Request.Me>) bean;
                        TestActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String str = "";
                                for (Url_Request.Me me : meList) {
                                    Log.d("FragAct", "onBeanResponse: " + me.getId());
                                    Log.d("FragAct", "onBeanResponse: " + me.getIcon());
                                    Log.d("FragAct", "onBeanResponse: " + me.getNickname());
                                    str += me.getNickname() + "、";

                                }
                                if (str.length() > 0)
                                    str = str.substring(0, str.length() - 1);
                                new TextLoaderTask(like_two, str).execute();
                            }
                        });


                    }
                });
    }

    /**
     * 初始化评论和回复列表
     */
    private void initExpandableListView(final List<CommentDetailBean> commentList){
        expandableListView.setGroupIndicator(null);
        //默认展开所有回复
        adapter = new CommentExpandAdapter(this, commentList);
        expandableListView.setAdapter(adapter);
        for(int i = 0; i<commentList.size(); i++){
            expandableListView.expandGroup(i);

        }
        for(int i = 0; i < adapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i);
            for(int j = 0; j < adapter.getChildrenCount(i); j++) {
                expandableListView.expandGroup(i);
            }
        }
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
                Log.e(TAG, "onGroupClick: 当前的评论id>>>"+commentList.get(groupPosition).getId());
//                if(isExpanded){
//                    expandableListView.collapseGroup(groupPosition);
//                }else {
//                    expandableListView.expandGroup(groupPosition, true);
//                }
                showReplyDialog(groupPosition,groupPosition,false);
                return true;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                boolean isExpanded = expandableListView.isGroupExpanded(childPosition);
                showReplyDialog(groupPosition,childPosition,true);
                return true;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //toast("展开第"+groupPosition+"个分组");

            }
        });

    }

    /**
     * by moos on 2018/04/20
     * func:生成测试数据
     * @return 评论数据
     */
    private List<CommentDetailBean> generateTestData(){
        List<CommentDetailBean> listlist = new ArrayList<>();
        Url_Request.sendRequestBlogCommentsId(Url_Request.getUrl_head()+"/blog-comments/"+note_id, user_token, new Url_Request.OnIconResponseListener() {
                    @Override
                    public void onBeanResponse(Object bean) {
                        List<Url_Request.Comment> commentList = (List<Url_Request.Comment>) bean;
                        for (int i = 0; i < commentList.size(); i++) {
                            CommentDetailBean commentDetailBean = new CommentDetailBean("1","1","1");
                            commentDetailBean.setId(Integer.parseInt(String.valueOf(commentList.get(i).getId())));
                            commentDetailBean.setUserId(commentList.get(i).getUserid().toString());
                            commentDetailBean.setBlogId(commentList.get(i).getBlogid().toString());
                            commentDetailBean.setLiked(commentList.get(i).getLiked());
                            String str = commentList.get(i).getCreatetime();
                            str = str.replace("T"," ");
                            commentDetailBean.setCreateDate(str);
                            commentDetailBean.setContent(commentList.get(i).getContent());
                            commentDetailBean.setNickName(commentList.get(i).getName());
                            commentDetailBean.setUserLogo(commentList.get(i).getUserIcon());
                            //commentDetailBean.setReplyTotal(commentList.size());

                            List<ReplyDetailBean> replyList = new ArrayList<>();


                            for (int j = 0; j < commentList.get(i).getChildcomments().size(); j++) {
                                ReplyDetailBean replyDetailBean = new ReplyDetailBean("1","1");
                                replyDetailBean.setId(Integer.parseInt(String.valueOf(commentList.get(i).getChildcomments().get(j).getId())));
                                replyDetailBean.setUserId(commentList.get(i).getChildcomments().get(j).getUserid().toString());
                                replyDetailBean.setBlogId(commentList.get(i).getChildcomments().get(j).getBlogid().toString());
                                replyDetailBean.setParentId(commentList.get(i).getChildcomments().get(j).getParentid().toString());
                                replyDetailBean.setAnswerId(commentList.get(i).getChildcomments().get(j).getAnswerid().toString());
                                replyDetailBean.setCreateDate("一个小时前");
                                replyDetailBean.setContent(commentList.get(i).getChildcomments().get(j).getContent());
                                replyDetailBean.setNickName(commentList.get(i).getChildcomments().get(j).getName());
                                replyDetailBean.setUserLogo("0");
                                replyDetailBean.setStatus("01");

                                replyList.add(replyDetailBean);


                            }
                            commentDetailBean.setReplyList(replyList);
                            listlist.add(commentDetailBean);
                            for (int k = 0; k < commentDetailBean.getReplyList().size(); k++) {
                                Log.e("FragAct", "onBeanResponse: " + listlist.get(0).getReplyList().get(0).getContent());
//                                Log.d("FragAct", "onBeanResponse: " + commentDetailBean.getReplyList().get(k).getUserId());
//                                Log.d("FragAct", "onBeanResponse: " + commentDetailBean.getReplyList().get(k).getBlogId());
//                                Log.d("FragAct", "onBeanResponse: " + commentDetailBean.getReplyList().get(k).getParentId());
//                                Log.d("FragAct", "onBeanResponse: " + commentDetailBean.getReplyList().get(k).getAnswerId());
//                                Log.d("FragAct", "onBeanResponse: " + commentDetailBean.getReplyList().get(k).getContent());
//                                Log.d("FragAct", "onBeanResponse: " + commentDetailBean.getReplyList().get(k).getCreateDate());
//                                Log.d("FragAct", "onBeanResponse: " + commentDetailBean.getReplyList().get(k).getNickName());
//                                Log.d("FragAct", "onBeanResponse: " + commentDetailBean.getReplyList().get(k).getUserLogo());
//                                Log.d("FragAct", "onBeanResponse: " + commentDetailBean.getReplyList().get(k).getStatus());
                            }
                           // Log.d("FragAct", "onBeanResponse: " + commentDetailBean.getReplyList());
                        }

                        TestActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                });//获取评论列表


        return listlist;
    }
    /*private List<CommentDetailBean> generateTestData(){
        Gson gson = new Gson();
        commentBean = gson.fromJson(testJson, CommentBean.class);
        List<CommentDetailBean> commentList = commentBean.getData().getList();
        for (int k = 0; k < commentBean.getData().getList().size(); k++) {
            Log.d("FragAct", "onBeanResponse: " + commentBean.getData().getList().get(k).getReplyTotal());

            for (int j = 0; j < commentBean.getData().getList().get(k).getReplyList().size(); j++) {
                Log.d("FragAct", "？？？: " + commentBean.getData().getList().get(k).getReplyList().get(j).getId());
                Log.d("FragAct", "？？？: " + commentBean.getData().getList().get(k).getReplyList().get(j).getCommentId());
                Log.d("FragAct", "？？？: " + commentBean.getData().getList().get(k).getReplyList().get(j).getUserId());
                Log.d("FragAct", "？？？: " + commentBean.getData().getList().get(k).getReplyList().get(j).getBlogId());
                Log.d("FragAct", "？？？: " + commentBean.getData().getList().get(k).getReplyList().get(j).getParentId());
                Log.d("FragAct", "？？？: " + commentBean.getData().getList().get(k).getReplyList().get(j).getAnswerId());
                Log.d("FragAct", "？？？: " + commentBean.getData().getList().get(k).getReplyList().get(j).getContent());
                Log.d("FragAct", "？？？: " + commentBean.getData().getList().get(k).getReplyList().get(j).getCreateDate());
                Log.d("FragAct", "？？？: " + commentBean.getData().getList().get(k).getReplyList().get(j).getNickName());
                Log.d("FragAct", "？？？: " + commentBean.getData().getList().get(k).getReplyList().get(j).getUserLogo());
                Log.d("FragAct", "？？？: " + commentBean.getData().getList().get(k).getReplyList().get(j).getStatus());
            }
            //Log.d("FragAct", "onBeanResponse: " + commentBean.getData().getList().get(k).getStatus());
        }
        return commentList;
    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.detail_page_do_comment){
            if (user_token.equals("")){
                Toast.makeText(TestActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TestActivity.this, FragAct.class);
                startActivity(intent);
            }else {
                showCommentDialog();
            }

        }
        if (view.getId() == R.id.card_spot){
            Intent intent = new Intent(TestActivity.this, SpotActivity.class);
            intent.putExtra("id", spot_id);
            startActivity(intent);
            finish();
        }
    }

    /**
     * by moos on 2018/04/20
     * func:弹出评论框
     */
    private void showCommentDialog(){
        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        /**
         * 解决bsd显示不全的情况
         */
        View parent = (View) commentView.getParent();
        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0,0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if(!TextUtils.isEmpty(commentContent)){

                    //commentOnWork(commentContent);
                    dialog.dismiss();
                    CommentDetailBean detailBean = new CommentDetailBean(user_names_tring, commentContent,"刚刚");
                    detailBean.setUserLogo(user_logo_url);
                    adapter.addTheCommentData(detailBean);
                    //在这加网络请求
                    Url_Request.sendRequestBlogCommentsC(Url_Request.getUrl_head()+"/blog-comments/comments",user_token,user_id,note_id,"0","0",commentContent,"0","0");
                    Toast.makeText(TestActivity.this,"评论成功",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(TestActivity.this,"评论内容不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

    /**
     * by moos on 2018/04/20
     * func:弹出回复框
     */
    private void showReplyDialog(final int position,int childp,boolean isChild){

        dialog = new BottomSheetDialog(this);
        View commentView = LayoutInflater.from(this).inflate(R.layout.comment_dialog_layout,null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final Button bt_comment = (Button) commentView.findViewById(R.id.dialog_comment_bt);
        if (!isChild) {
            commentText.setHint("回复 " + commentsList.get(position).getNickName() + " 的评论:");

        }else {
            //回复当前子评论\
            commentText.setText("@" + commentsList.get(position).getReplyList().get(childp).getNickName() + " ");
        }
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user_token.equals("")){
                    Toast.makeText(TestActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TestActivity.this, FragAct.class);
                    startActivity(intent);
                }else {
                    String replyContent = commentText.getText().toString().trim();
                    if(!TextUtils.isEmpty(replyContent)){

                        dialog.dismiss();
                        ReplyDetailBean detailBean = new ReplyDetailBean(user_names_tring,replyContent);
                        adapter.addTheReplyData(detailBean, position);
                        expandableListView.expandGroup(position);
                        Toast.makeText(TestActivity.this,"回复成功",Toast.LENGTH_SHORT).show();
                        if (isChild) {
                            //回复当前子评论\
                            Url_Request.sendRequestBlogCommentsC(Url_Request.getUrl_head() + "/blog-comments/comments", user_token, user_id, note_id, commentsList.get(position).getReplyList().get(childp).getParentId(), String.valueOf(commentsList.get(position).getId()), replyContent, "0", "0");
                        }else {
                            //回复当前评论\
                            Url_Request.sendRequestBlogCommentsC(Url_Request.getUrl_head() + "/blog-comments/comments", user_token, user_id, note_id, String.valueOf(commentsList.get(position).getId()),String.valueOf(commentsList.get(position).getId()) , replyContent, "0", "0");
                        }
                    }else {
                        Toast.makeText(TestActivity.this,"回复内容不能为空",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence) && charSequence.length()>2){
                    bt_comment.setBackgroundColor(Color.parseColor("#FFB568"));
                }else {
                    bt_comment.setBackgroundColor(Color.parseColor("#D8D8D8"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        dialog.show();
    }

}
