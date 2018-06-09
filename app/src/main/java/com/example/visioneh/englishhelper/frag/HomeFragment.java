package com.example.visioneh.englishhelper.frag;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visioneh.englishhelper.activity.ReviewActivity;
import com.example.visioneh.englishhelper.bean.Constant;
import com.example.visioneh.englishhelper.R;
import com.example.visioneh.englishhelper.activity.MainActivity;
import com.example.visioneh.englishhelper.activity.SearchActivity;
import com.example.visioneh.englishhelper.util.HttpUtil;
import com.hss01248.dialog.MyActyManager;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.bottomsheet.BottomSheetBean;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.hss01248.dialog.interfaces.MyItemDialogListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by visionEH on 2017/9/13.
 */

public class HomeFragment extends Fragment implements View.OnClickListener{

    private TextView add;
    private TextView review;
    private TextView look_for;
    private int uid=-1;
    private Handler handler=new Handler();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.home_frag,container,false);

        uid=((MainActivity)getActivity()).getUid();
        InitView(view);
        return  view;
    }
    public void InitView(View view){
        add=(TextView)view.findViewById(R.id.add);
        review=(TextView)view.findViewById(R.id.review);
        look_for=(TextView)view.findViewById(R.id.lookfor);
        add.setOnClickListener(this);
        review.setOnClickListener(this);
        look_for.setOnClickListener(this);
        StyledDialog.init(getContext());
        MyActyManager.getInstance().setCurrentActivity(getActivity());
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.add:
                Add();
                break;
            case R.id.lookfor:
                List<BottomSheetBean> mdates=new ArrayList<>();
                mdates.add(new BottomSheetBean(R.drawable.english_icon,"英译汉"));
                mdates.add(new BottomSheetBean(R.drawable.english_icon,"汉译英"));
                StyledDialog.buildBottomSheetLv("翻译形式", mdates, null, new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence charSequence, int i) {
                        Intent intent=new Intent(getActivity(),SearchActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("tag",i+1);
                        bundle.putInt("uid",uid);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }).show();
                break;
            case R.id.review:
                Intent intent=new Intent(getActivity(), ReviewActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("uid",uid);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }
    private void Add(){
      Dialog dialog= StyledDialog.buildNormalInput("添加", "单词", "翻译", "确定", "取消", new MyDialogListener() {
                    @Override
                    public void onFirst() {

                    }

                    @Override
                    public void onSecond() {

                    }

                    @Override
                    public void onGetInput(final CharSequence input1, final CharSequence input2) {
                      super.onGetInput(input1, input2);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                        Date date=new Date();
                        final String _date=(simpleDateFormat.format(date)).toString();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String args="name="+input1.toString()+"&tran="+input2.toString()+"&date="+_date+"&method=add"
                                        +"&uid="+uid;
                                final String ans=HttpUtil.post(Constant.WORD_URL,args);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if("success".equals(ans)){
                                            Toast.makeText(getContext(),"添加成功",Toast.LENGTH_SHORT).show();
                                        }else if("error".equals(ans)){
                                            Toast.makeText(getContext(),"添加失败",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        }).start();
                    }
              }
        ).show();
    }
}
