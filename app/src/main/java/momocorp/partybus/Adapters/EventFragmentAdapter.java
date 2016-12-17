package momocorp.partybus.Adapters;


import android.app.Fragment;
import android.support.v13.app.FragmentPagerAdapter;
import android.app.FragmentManager;
import java.util.ArrayList;

import momocorp.partybus.Fragments.Eventsfragments.EventListFragment;
import momocorp.partybus.Fragments.Eventsfragments.EventLocationFragment;

/**
 * Created by Pablo on 12/15/2016.
 */
public class EventFragmentAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments;
    public EventFragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
