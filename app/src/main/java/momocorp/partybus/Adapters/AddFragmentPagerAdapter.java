package momocorp.partybus.Adapters;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;


import momocorp.partybus.Fragments.AddFragment;
import momocorp.partybus.Fragments.EventDetailsFragment;

/**
 * Created by Pablo on 3/26/2017.
 */

public class AddFragmentPagerAdapter extends FragmentPagerAdapter {
    public AddFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                AddFragment addFragment = AddFragment.newInstance();
                return addFragment;
            case 1:
                EventDetailsFragment detailsFragment = EventDetailsFragment.newInstance();
                return detailsFragment;



        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}

