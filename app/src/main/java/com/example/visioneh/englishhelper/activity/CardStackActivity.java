package com.example.visioneh.englishhelper.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.visioneh.englishhelper.R;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;

import java.sql.Array;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;

public class CardStackActivity extends AppCompatActivity {

    private Integer[] list_item=new Integer[]{R.color.team1,R.color.team2,R.color.team3,R.color.team4,
                       R.color.team5,R.color.team6,R.color.team7,R.color.team8
    };
    private CardStackView stackView;
    private TestStackAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_stack);
        stackView=(CardStackView)findViewById(R.id.list_content);
        adapter=new TestStackAdapter(getApplicationContext());
        stackView.setAdapter(adapter);
        adapter.updateData(Arrays.asList(list_item));
    }
}
