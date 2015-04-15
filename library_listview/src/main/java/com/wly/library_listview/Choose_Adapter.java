package com.wly.library_listview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wly on 2015/3/23.
 */
public class Choose_Adapter extends BaseAdapter {

    private Context context;

    public List<Map<String, Object>> getList() {
        return list;
    }

    private HashMap<Integer, Boolean> isCheckMap;
    private OnCheckChangedListener onCheckChangedListener;
    private boolean isCheck;

    private List<Map<String, Object>> list;

    public HashMap<Integer, Boolean> getIsCheckMap() {
        return isCheckMap;
    }

    public Choose_Adapter(List<Map<String, Object>> list, Context context) {
        this.list = list;
        this.context = context;

        initCheckMap();
    }

    public void initCheckMap() {
        if (isCheckMap != null) {
            isCheckMap.clear();
        }
        isCheckMap = new HashMap<>();
        Log.i("list", list.size() + "");
        for (int i = 0; i < list.size(); i++) {     //根据list的大小，初始化isCheckMap
            isCheckMap.put(i, false);
            Log.i("", i + ":isCheckMap");
        }
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewGroup layout = null;
        ViewHolder viewHolder = null;
        TextView number;
        TextView name;
        ViewGroup frame;
        final ImageView imageView;
        final CheckBox checkBox;
        /**
         * 进行ListView 的优化
         */
        if (convertView == null) {
            layout = (ViewGroup) LayoutInflater.from(context).inflate(
                    R.layout.choose_adapter_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.number = (TextView) layout.findViewById(R.id.number);
            viewHolder.name = (TextView) layout.findViewById(R.id.name);
            viewHolder.frame = (ViewGroup) layout.findViewById(R.id.frame);

            viewHolder.checkBox = (CheckBox) layout.findViewById(R.id.checkbox);
            viewHolder.imageView = (ImageView) layout.findViewById(R.id.image);

            layout.setTag(viewHolder);

        } else {
            layout = (ViewGroup) convertView;
            viewHolder = (ViewHolder) layout.getTag();
        }

        number = viewHolder.number;
        name = viewHolder.name;
        imageView = viewHolder.imageView;
        checkBox = viewHolder.checkBox;


        number.setText((CharSequence) list.get(position).get("number"));
        name.setText((CharSequence) list.get(position).get("name"));
        if (isCheck) {
            imageView.setVisibility(View.INVISIBLE);
            checkBox.setVisibility(View.VISIBLE);

        } else {
            checkBox.setVisibility(View.INVISIBLE);
            isCheckMap.put(position, false);
            imageView.setVisibility(View.VISIBLE);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick::", "imageView");
                if (!isCheck) {
                    changeCheck(true);
                    isCheckMap.put(position, true);
                }
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheck) {
                    changeCheck(false);
                }
            }
        });

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCheckMap.put(position, !isCheckMap.get(position));
                if (isClose()){
                    changeCheck(false);
                }
            }
        });

        Log.i("checkBox:", checkBox + "");
        Log.i("isCheckMap:", isCheckMap.size() + "");

        checkBox.setChecked(isCheckMap.get(position));

        return layout;
    }

    private boolean isClose() {
        for (int i = 0; i < isCheckMap.size(); i++) {
            if (isCheckMap.get(i)) {     //有一个true
                return false;
            }
        }
        return true;        //全部为false

    }

    private void changeCheck(boolean b) {
        isCheck = b;
        if (onCheckChangedListener != null) {
            onCheckChangedListener.onCheck(b);
        }
        notifyDataSetChanged();
    }

    private class ViewHolder {
        TextView number;
        TextView name;
        ViewGroup frame;
        ImageView imageView;
        CheckBox checkBox;
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
        initCheckMap();
        notifyDataSetChanged();
    }


    public void setIsCheckMap(HashMap<Integer, Boolean> isCheckMap) {
        this.isCheckMap = isCheckMap;
        notifyDataSetChanged();
    }

    public void setOnCheckChangedListener(OnCheckChangedListener onCheckChangedListener) {
        this.onCheckChangedListener = onCheckChangedListener;

    }

    public interface OnCheckChangedListener {
        public void onCheck(boolean isCheck);
    }
}
