package teamtwo.event.com.events;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by Primož Pesjak on 19.3.2015.
 */
public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private static int NUM_ITEMS = 3;
        public FragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    FragmentA a = new FragmentA();
                    return a;
                case 1:
                    FragmentB b = new FragmentB();

                    return b;
                case 2:
                    FragmentC c = new FragmentC();
                    return c;
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }
    }
