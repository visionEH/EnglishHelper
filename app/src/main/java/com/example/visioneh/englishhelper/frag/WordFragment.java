package com.example.visioneh.englishhelper.frag;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.transition.Visibility;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visioneh.englishhelper.bean.Constant;
import com.example.visioneh.englishhelper.R;
import com.example.visioneh.englishhelper.bean.Word;
import com.example.visioneh.englishhelper.activity.MainActivity;
import com.example.visioneh.englishhelper.util.HttpUtil;
import com.hss01248.dialog.MyActyManager;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by visionEH on 2017/9/19.
 */

public class WordFragment extends Fragment {

    private RecycleAdapter adapter;
    private RecyclerView recyclerView;
    private List<Word> mdates;
    private TextView update;
    private Handler handler=new Handler();


    private MainActivity activity;
    private int uid=-1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.word_frag,container,false);
        activity=(MainActivity) getActivity();
        uid=activity.getUid();
        recyclerView=(RecyclerView)view.findViewById(R.id.rv);
        update=(TextView)view.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitDate();
            }
        });
        StyledDialog.init(getContext());
        MyActyManager.getInstance().setCurrentActivity(getActivity());
       // dialog= StyledDialog.buildProgress("正在读取数据",false).show();

        InitDate();
     //   dialog.cancel();


        return view;
    }
    private void InitDate(){
        StyledDialog.buildLoading("正在读取数据").show();
          /* WordUtil.Instance(getContext());
           Cursor cursor=WordUtil.SelectAll();
           mdates=new ArrayList<>();
           while (cursor.moveToNext()){
               String name=cursor.getString(cursor.getColumnIndex("name"));
               String tran=cursor.getString(cursor.getColumnIndex("tran"));
               mdates.add(new Word(name,tran));
           }*/
           new Thread(new Runnable() {
               @Override
               public void run() {
                   String args="uid="+ activity.getUid()+"&method=select";
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
                           adapter=new RecycleAdapter(getContext(),mdates);
                           adapter.setOnItemClickListener(new RecycleAdapter.onItemClickListener() {
                               @Override
                               public void onItemClick(View view, int position) {
                                    if(view.getId()==R.id.delete){
                                        Word word=mdates.get(position);
                                        Delete(word.getName(),position);
                                    }
                                    else{
                                        TextView hide=(TextView)view.findViewById(R.id.hide);
                                        TextView tran=(TextView)view.findViewById(R.id.transf);
                                        int vis=hide.getVisibility();
                                        if(vis==View.VISIBLE){
                                            hide.setVisibility(View.INVISIBLE);
                                            tran.setVisibility(View.VISIBLE);
                                        }else{
                                            hide.setVisibility(View.VISIBLE);
                                            tran.setVisibility(View.INVISIBLE);
                                        }
                                    }
                               }
                               @Override
                               public void onLongClick(View view, final int position) {
                                      StyledDialog.buildIosAlertVertical("提示", "确定要删除该单词吗？", new MyDialogListener() {
                                          @Override
                                          public void onFirst() {
                                              Word word=mdates.get(position);
                                              Delete(word.getName(),position);
                                          }
                                          @Override
                                          public void onSecond() {

                                          }
                                      }).setBtnText("确定","再想想").show();
                               }
                           });

                           recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                           recyclerView.addItemDecoration(new Line_Decoration(getContext()));
                           recyclerView.setAdapter(adapter);
                       }
                   });
               }
           }).start();

        StyledDialog.dismissLoading();
    }
    private void Delete(final String name, final int pos){
        StyledDialog.buildIosAlert("警告", "你确定要删除单词" + name + "吗？以后不会再出现了咯？", new MyDialogListener() {
            @Override
            public void onFirst() {
                final String args="name="+name+"&method=delete"+"&uid="+uid;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String ans= HttpUtil.post(Constant.WORD_URL,args);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if("success".equals(ans)){
                                    Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
                                    mdates.remove(pos);
                                    adapter.notifyDataSetChanged();
                                }else{
                                    Toast.makeText(getContext(),"删除失败，请重试",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).start();
            }

            @Override
            public void onSecond() {

            }
        }).setBtnText("删除","再想想")
          .show();

    }
}
