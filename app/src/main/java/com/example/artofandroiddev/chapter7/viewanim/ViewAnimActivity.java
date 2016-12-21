package com.example.artofandroiddev.chapter7.viewanim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.artofandroiddev.R;

import butterknife.BindView;

/**
 * 本Activity用于演示View动画
 * 效果是点击之后会飞出再飞回来的手里剑
 */
public class ViewAnimActivity extends AppCompatActivity {
    private Animation mShurikenAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_anim);

        init();
    }

    private void init()
    {
        mShurikenAnim = AnimationUtils.loadAnimation(this,R.anim.shuriken);
    }

    public void throwShuriken(View view) {
        Animation animation = view.getAnimation();
        if (animation == null || animation.hasEnded())
            view.startAnimation(mShurikenAnim);
    }
}
