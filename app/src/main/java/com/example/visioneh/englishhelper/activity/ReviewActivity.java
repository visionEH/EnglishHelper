package com.example.visioneh.englishhelper.activity;

import android.app.Dialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.visioneh.englishhelper.R;
import com.example.visioneh.englishhelper.bean.Constant;
import com.example.visioneh.englishhelper.bean.Word;
import com.example.visioneh.englishhelper.frag.Line_Decoration;
import com.example.visioneh.englishhelper.util.HttpUtil;
import com.hss01248.dialog.MyActyManager;
import com.hss01248.dialog.StyledDialog;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ReviewAdapter adapter;
    private List<Word> mdates;
    private Handler handler=new Handler();
    private Dialog dialog;
    private int uid=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        getSupportActionBar().hide();
        recyclerView=(RecyclerView)findViewById(R.id.rv);
        uid=getIntent().getExtras().getInt("uid");
        StyledDialog.init(getApplicationContext());
        MyActyManager.getInstance().setCurrentActivity(ReviewActivity.this);
      //  dialog= StyledDialog.buildProgress("正在读取数据",false).show();
        StyledDialog.buildLoading("正在读取数据").show();
        InitDate();
       // StyledDialog.dismiss(dialog);
        StyledDialog.dismissLoading();
    }
    private void InitDate(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String args="uid="+uid+"&method=select";
                final String result= HttpUtil.post(Constant.WORD_URL,args);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mdates=new ArrayList<Word>();
                        String[] wordlist=result.split("&");
                        if(wordlist==null||wordlist.equals("")){

                        }else{
                            int count=wordlist.length-1;
                            for(int i=0;i<count;i++){
                                String[] words=wordlist[i].split("@");
                                mdates.add(new Word(words[0],words[1],words[2],uid));
                            }
                        }
                        adapter=new ReviewAdapter(getApplicationContext(),mdates);
                        adapter.setOnItemClickListener(new ReviewAdapter.onItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                     Word word=mdates.get(position);
                                     AddReview(word.getName(),position);
                            }
                        });
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.addItemDecoration(new Line_Decoration(getApplicationContext()));
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }
  private  void AddReview(final String name, final int pos){
      new Thread(new Runnable() {
          @Override
          public void run() {
              String args="name="+name+"&method=addreview"+"&uid="+uid;
              final String ans=HttpUtil.post(Constant.WORD_URL,args);
              handler.post(new Runnable() {
                  @Override
                  public void run() {
                      if("success".equals(ans)){
                          mdates.remove(pos);
                          adapter.notifyDataSetChanged();
                      }else{
                          Toast.makeText(getApplicationContext(),"操作失败，请重试",Toast.LENGTH_LONG).show();
                      }
                  }
              });
          }
      }).start();
  }
}

