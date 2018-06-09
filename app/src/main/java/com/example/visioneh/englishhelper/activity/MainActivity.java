package com.example.visioneh.englishhelper.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.visioneh.englishhelper.bean.Constant;
import com.example.visioneh.englishhelper.frag.HomeFragment;
import com.example.visioneh.englishhelper.R;
import com.example.visioneh.englishhelper.frag.WordFragment;

public class MainActivity extends AppCompatActivity {
    private HomeFragment home;
    private WordFragment word;
    private BottomNavigationView bottom;
    private int uid=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        uid=bundle.getInt("uid");
        bottom = (BottomNavigationView) findViewById(R.id.nav_bottom);
        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home) {
                    SetFragment(Constant.Home_Fragment);
                } else if (id == R.id.word) {
                    SetFragment(Constant.Word_Fragment);
                }
                return true;
            }
        });
        getSupportActionBar().hide();
        SetFragment(Constant.Home_Fragment);
    }
    public void SetFragment(int id){
        //开启碎片的管理者,可以对其进行删除，添加等操作
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        //影藏所有fragment
        HideFragment(transaction);
        switch (id){
            case Constant.Home_Fragment:
                if(home==null){
                    home=new HomeFragment();
                    transaction.add(R.id.fl,home);
                }
                else{
                    transaction.show(home);
                }
                break;
            case Constant.Word_Fragment:
                if(word==null){
                    word=new WordFragment();
                    transaction.add(R.id.fl,word);
                }
                else{
                    transaction.show(word);
                }
                break;
        }
        transaction.commit();
    }
    public void HideFragment(FragmentTransaction transaction){
        if(home!=null) {
            transaction.hide(home);
        }
        if(word!=null){
            transaction.hide(word);
        }
    }
    public int getUid() {
        return uid;
    }

}
