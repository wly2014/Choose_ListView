package com.wly.library_listview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wly on 2015/3/23.
 */
public class Choose_ListView extends RelativeLayout implements View.OnClickListener {

    private ListView listView;
    private Choose_Adapter adapter;
    private ViewGroup bottom_linear;

    private Button reverse_button;
    private Button all_button;
    private Button delete_button;
    private OnDeleteListener onDeleteListener;

    public Choose_ListView(Context context) {
        this(context, null);
    }

    public Choose_ListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Choose_ListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.choose_listview, this);//注意最后是this，不是null！

        listView = (ListView) findViewById(R.id.listView);
        bottom_linear = (ViewGroup) findViewById(R.id.bottom_linear);

        reverse_button = (Button) findViewById(R.id.reverse);
        all_button = (Button) findViewById(R.id.all);
        delete_button = (Button) findViewById(R.id.delete);

        reverse_button.setOnClickListener(this);
        all_button.setOnClickListener(this);
        delete_button.setOnClickListener(this);

    }


    public Choose_Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Choose_Adapter adapter) {
        this.adapter = adapter;
        Log.i("listView", listView + "");
        listView.setAdapter(adapter);

        adapter.setOnCheckChangedListener(new Choose_Adapter.OnCheckChangedListener() {
            @Override
            public void onCheck(boolean isCheck) {
                if (isCheck) {
                    bottom_linear.setVisibility(VISIBLE);
                    Animation animation = new TranslateAnimation(0, 0, bottom_linear.getHeight(), 0);
                    animation.setDuration(500);
                    animation.setFillAfter(true);
                    bottom_linear.setAnimation(animation);
                } else {
                    Animation animation = new TranslateAnimation(0, 0, 0, bottom_linear.getHeight());
                    animation.setDuration(500);
                    animation.setFillAfter(true);
                    bottom_linear.setAnimation(animation);
                    bottom_linear.setVisibility(INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        HashMap<Integer, Boolean> isCheckMap = adapter.getIsCheckMap();
        if (i == R.id.reverse) {
            Log.i("onClick","reverse");
            for (int x = 0; x < isCheckMap.size(); x++) {
                isCheckMap.put(x, !isCheckMap.get(x));
            }
            adapter.setIsCheckMap(isCheckMap);

        } else if (i == R.id.all) {
            Log.i("onClick","all");
            for (int x = 0; x < isCheckMap.size(); x++) {
                isCheckMap.put(x, true);
            }
            adapter.setIsCheckMap(isCheckMap);

        } else if (i == R.id.delete) {
            Log.i("onDeleteListener-->", "onDelete");
            if (onDeleteListener != null) {
                onDeleteListener.onDelete(adapter.getList(),adapter.getIsCheckMap());
            }
        }

    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public interface OnDeleteListener {
        public void onDelete(List<Map<String, Object>> list, HashMap<Integer, Boolean> isCheckMap);
    }
}
