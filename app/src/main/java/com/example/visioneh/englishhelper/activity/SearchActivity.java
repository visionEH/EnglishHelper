package com.example.visioneh.englishhelper.activity;

import android.app.Dialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visioneh.englishhelper.R;
import com.example.visioneh.englishhelper.bean.Constant;
import com.example.visioneh.englishhelper.translation.TransApi;
import com.example.visioneh.englishhelper.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText text_input;
    private ImageView search;
    private TransApi transApi;
    private CardView word_card;
    private LinearLayout show;

    private TextView name;
    private TextView tran;
    private TextView keep;
    private Handler handler=new Handler();
    private String _name;
    private String _tran;
    private Dialog dialog;
    private int tag=1;
    private static String  APP_ID="20170910000081909";
    private static String SECURITY_KEY="TItuOp2hRDaDMlP1HMMx";
    private int uid=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();


        InitWidge();
        tag= getIntent().getExtras().getInt("tag");
        uid=getIntent().getExtras().getInt("uid");
    }

    private void InitWidge(){
        text_input=(EditText)findViewById(R.id.edit_text_search);
        search=(ImageView)findViewById(R.id.iv_bottom_search);
        word_card=(CardView)findViewById(R.id.word_card);
        show=(LinearLayout)findViewById(R.id.show);
        name=(TextView)findViewById(R.id.name);
        tran=(TextView)findViewById(R.id.tran);
        search.setOnClickListener(this);
        keep=(TextView)findViewById(R.id.keep);

        keep.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.iv_bottom_search){
            String value=text_input.getText().toString();
            if(value==null||value.equals("")){
                Toast.makeText(getApplicationContext(),"翻译内容不能为空",Toast.LENGTH_LONG).show();
                return;
            }
            Translation(tag,value);
        }else if(id==R.id.keep){
           // dialog=  StyledDialog.buildProgress("正在存储至云端",false).show();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            Date date=new Date();
            final String _date=(simpleDateFormat.format(date)).toString();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String args="name="+_name+"&tran="+_tran+"&date="+_date+"&method=add"
                            +"&uid="+uid;
                    final String ans= HttpUtil.post(Constant.WORD_URL,args);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if("success".equals(ans)){
                                Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_SHORT).show();
                            }else if("error".equals(ans)){
                                Toast.makeText(getApplicationContext(),"添加失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }).start();
        }
    }
    /**
     * 利用百度翻译sdk翻译指定字符串
     * @param tag  1表示音译汉，2表示汉译英
     * @param value
     */
    private void Translation(final int tag, final String value){
        transApi=new TransApi(APP_ID,SECURITY_KEY);
        final String[] ans = {""};
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(tag==1)
                    ans[0] =transApi.getTransResult(value,"en","zh");//我已经封装好的访问百度翻译API的方法
                else
                    ans[0] =transApi.getTransResult(value,"zh","en");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                         parseJSON(ans[0]);
                         show.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }
    /**
     * 解析json字符串
     * @param jsonDate 
     */
    private void parseJSON(String jsonDate)  {
        try {
            JSONObject jsonObject=new JSONObject(jsonDate);
            Log.d("json",jsonDate);
            JSONArray jsonArray1= jsonObject.getJSONArray("trans_result");
            JSONObject jsonObject1=jsonArray1.getJSONObject(0);
            String _name=jsonObject1.getString("src");
            String _tran=jsonObject1.getString("dst");
            if(tag==1){
                        tran.setText("翻译："+_tran);
                        name.setText("英文："+_name);
                        this._name=_name;
                        this._tran=_tran;
            }else{
                       tran.setText("翻译："+_name);
                       name.setText("英文："+_tran);
                       this._name=_tran;
                       this._tran=_name;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
