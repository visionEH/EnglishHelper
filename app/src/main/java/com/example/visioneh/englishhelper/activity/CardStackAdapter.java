package com.example.visioneh.englishhelper.activity;

import android.content.Context;
import android.view.ViewGroup;

import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;

/**
 * Created by 62588 on 2018/1/8.
 */

public class CardStackAdapter  extends StackAdapter<String>{


    public CardStackAdapter(Context context) {
        super(context);
    }
    @Override
    public void bindView(String data, int position, CardStackView.ViewHolder holder) {

    }

    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        return null;
    }


}
