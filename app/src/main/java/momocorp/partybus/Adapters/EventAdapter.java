package momocorp.partybus.Adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by Pablo on 12/15/2016.
 */
public class EventAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
