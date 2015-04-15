package teamtwo.event.com.events;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;

import com.astuetz.PagerSlidingTabStrip;

import teamtwo.event.com.events.MainActivity;
/**
 * Created by Primož Pesjak on 19.3.2015.
 */
public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider{


    //spremenil na 2
    private static int NUM_ITEMS = 2;
    private int tabIcons[] = {R.drawable.abc_btn_check_to_on_mtrl_000, R.drawable.abc_btn_check_to_on_mtrl_000}; //dodaj ikono

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
                    FragmentB b = new FragmentB();
                    return b.newInstance(position+1);


                case 1:
                    FragmentA a = new FragmentA();
                    return a.newInstance(position+1);
                /*
                case 2:
                    FragmentC c = new FragmentC();
                    return c.newInstance(position+1);

                    */

                default:
                    return null;
            }
        }

/*        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position)
        {
            return "Page " + position;
        }*/

    @Override
    public int getPageIconResId(int position) {
        return tabIcons[position];
    }
}
