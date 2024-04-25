package jp.ac.aut.musicquizgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import jp.ac.aut.musicquizgame.databinding.ActivityTurtorialBinding;


public class TurtorialActivity extends AppCompatActivity {

    class MyAdapter extends FragmentStateAdapter {
        private int[] resources = {R.drawable.start,R.drawable.select,R.drawable.play,R.drawable.score};
        public MyAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment = new Fragment();
            if(Objects.nonNull(position)){
                fragment = ImageFragment.newInstance(resources[position % resources.length]);
            }
            return fragment;
        }

        @Override
        public int getItemCount() {
            return resources.length;
        }
    }

    private ActivityTurtorialBinding binding;
    public int nowPage = 0;
    public Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turtorial);

        binding = ActivityTurtorialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.pager.setAdapter(new MyAdapter(this));
        binding.pager.setPageTransformer(new ViewPager2PageTransformer());
        binding.pager.registerOnPageChangeCallback(new onPageChangeListener());
        binding.skipBtn.setOnClickListener(v ->{
            Intent intent = new Intent(TurtorialActivity.this,MainActivity.class);
            startActivity(intent);
        });



        TimerTask timerTask = new TimerTaskPage();
        timer.schedule(timerTask,5000L,5000L);
    }
    public class onPageChangeListener extends ViewPager2.OnPageChangeCallback{
        @Override
        public void onPageScrolled(int position,float positionOffset, int positionOffsetPixels){
            Log.d("POSTION",Integer.toString(position));
            nowPage = position;
            Log.d("NOWPAGE",Integer.toString(nowPage));
        }
    }
    public class TimerTaskPage extends java.util.TimerTask{
        private Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    binding.pager.setCurrentItem((nowPage+1)%4);
                }
            });
        }
    }

    public class ViewPager2PageTransformer implements ViewPager2.PageTransformer {

        @Override
        public void transformPage(@NonNull View page, float position) {
            if(position < -1){
                page.setAlpha(0.2f);
                page.setScaleX(0.2f);
                page.setScaleY(0.2f);
            }else if(position <= 1){
                page.setAlpha(Math.max(0.2f,1 - Math.abs(position)));
                page.setScaleX(Math.max(0.2f,1 - Math.abs(position)));
                page.setScaleY(Math.max(0.2f,1 - Math.abs(position)));
            }else{
                page.setAlpha(0.2f);
                page.setScaleX(0.2f);
                page.setScaleY(0.2f);
            }
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        timer.cancel();

    }
}