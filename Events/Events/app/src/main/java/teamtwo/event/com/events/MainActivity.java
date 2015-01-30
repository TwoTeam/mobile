package teamtwo.event.com.events;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;


public class MainActivity extends FragmentActivity {

ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);

    }
}