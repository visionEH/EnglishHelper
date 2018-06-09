package com.example.visioneh.englishhelper.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visioneh.englishhelper.bean.Constant;
import com.example.visioneh.englishhelper.R;
import com.example.visioneh.englishhelper.util.HttpUtil;
import com.hss01248.dialog.MyActyManager;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText usname;
    private EditText psd;
    private TextView register;
    private Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        usname=(EditText)findViewById(R.id.edit_username);
        psd=(EditText)findViewById(R.id.edit_psd);
        register=(TextView)findViewById(R.id.regeister);


        register.setOnClickListener(this);
        StyledDialog.init(getApplicationContext());
        MyActyManager.getInstance().setCurrentActivity(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.regeister){
            final String _user=usname.getText().toString();
            final String _psd=psd.getText().toString();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final String args="username="+_user+"&password="+_psd+"&method=register";
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            StyledDialog.buildProgress("正在注册，请稍后",false).show();
                        }
                    });
                    final int ans= Integer.parseInt(HttpUtil.post(Constant.ADMIN_URL,args));
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            StyledDialog.dismissLoading();
                            if(ans==-1){
                                StyledDialog.buildIosAlert("注册失败", "请再次提交", new MyDialogListener() {
                                    @Override
                                    public void onFirst() {
                                    }

                                    @Override
                                    public void onSecond() {

                                    }
                                }).setBtnText("确定",null).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });

                }
            }).start();
        }
    }
}
