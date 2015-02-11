package teamtwo.event.com.events;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Primož Pesjak on 16.1.2015.
 */
public class PagerAdapter extends FragmentPagerAdapter{

    final int PAGE_COUNT = 3;
    private String tabtitles[] = new String[] { "Tab1", "Tab2", "Tab3" };
    Context context;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                FragmentA fragmenttaba = new FragmentA();
                return fragmenttaba;
            case 1:
                FragmentB fragmenttab = new FragmentB();

                return fragmenttab;
            case 2:
                FragmentC fragmenttabc = new FragmentC();
                return fragmenttabc;
        }
        return null;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }
}
