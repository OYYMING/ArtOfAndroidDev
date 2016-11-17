package com.example.artofandroiddev.chapter3.diforientationconflict;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.artofandroiddev.R;
import com.example.artofandroiddev.base.DummyContent;
import com.example.artofandroiddev.util.MyViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用于演示Scroller的用法及不同方向上分别有滑动事件所引起的滑动冲突的解决方法
 */
public class HorizontalScrollViewActivity extends AppCompatActivity {

    @BindView(R.id.hsc_container)
    public HorizontalScrollViewEx mHorizontalScrollViewEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_scroll_view);

        ButterKnife.bind(this);
        initViews();
    }

    protected void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < 3; i++) {
            ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.layout_scrollable_content, mHorizontalScrollViewEx, false);
            ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
            layoutParams.width = MyViewUtils.getScreenMetrics(this).widthPixels;
            viewGroup.setBackgroundColor(Color.rgb(50 * i + 100, 50 * i + 100, 50 * i + 40));
            TextView textView = (TextView) viewGroup.findViewById(R.id.tv_content_title);
            textView.setText("page " + i);
            initListView(viewGroup);

            mHorizontalScrollViewEx.addView(viewGroup);
        }
    }

    private void initListView(ViewGroup root) {
        ListView listView = (ListView) root.findViewById(R.id.lv_content);
        SimpleAdapter adapter = new SimpleAdapter(this, DummyContent.ITEM_MAP, android.R.layout.simple_list_item_1, new String[]{"KEY"}, new int[]{android.R.id.text1});
        listView.setAdapter(adapter);
    }
}
