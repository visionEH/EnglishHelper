package com.example.visioneh.englishhelper.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.visioneh.englishhelper.bean.Constant;
import com.example.visioneh.englishhelper.R;
import com.example.visioneh.englishhelper.util.HttpUtil;
import com.hss01248.dialog.MyActyManager;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText usname;
    private EditText psd;
    private TextView login;
    private TextView register;
    private Handler handler=new Handler();
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        usname=(EditText)findViewById(R.id.edit_username);
        psd=(EditText)findViewById(R.id.edit_psd);
        login=(TextView)findViewById(R.id.login);
        register=(TextView)findViewById(R.id.regeister);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        StyledDialog.init(getApplicationContext());
        MyActyManager.getInstance().setCurrentActivity(this);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.regeister){
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            return ;
        }else if (id==R.id.login){
            final String _user=usname.getText().toString();
            final String _psd=psd.getText().toString();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final String args="username="+_user+"&password="+_psd+"&method=loginin";
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                       // dialog= StyledDialog.buildProgress("正在登录，请稍后",false).show();
                            StyledDialog.buildLoading("正在登录，请稍后").show();
                        }
                    });
                    final int ans= Integer.parseInt(HttpUtil.post(Constant.ADMIN_URL,args));
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                          //  StyledDialog.dismiss(dialog);
                            StyledDialog.dismissLoading();
                            if(ans==-1){
                                StyledDialog.buildIosAlert("登录失败", "您的账号或密码填写错误", new MyDialogListener() {
                                    @Override
                                    public void onFirst() {

                                    }

                                    @Override
                                    public void onSecond() {

                                    }
                                }).setBtnText("确定",null).show();
                            }else{
                                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                Bundle bundle=new Bundle();
                                bundle.putInt("uid",ans);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                }
            }).start();
        }


    }

}
