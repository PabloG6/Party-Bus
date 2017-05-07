package momocorp.partybus.Activities;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import momocorp.partybus.Adapters.AddFragmentPagerAdapter;
import momocorp.partybus.CustomObjects.EventInformation;
import momocorp.partybus.Fragments.AddFragment;
import momocorp.partybus.Fragments.EventDetailsFragment;
import momocorp.partybus.Fragments.TimeFragment;
import momocorp.partybus.R;

public class AddActivity extends AppCompatActivity implements TimeFragment.OnFragmentInteractionListener {
    ArrayList<Fragment> fragments;
    private EventInformation eventInformation = new EventInformation();
    TimeFragment timeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.add_viewpager);
        viewPager.setOffscreenPageLimit(4);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.add_tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        fragments = new ArrayList<>();

        final AddFragmentPagerAdapter addFragPagerAapter = new AddFragmentPagerAdapter(getFragmentManager());
        fragments.add(AddFragment.newInstance(eventInformation, addFragPagerAapter));
        fragments.add(EventDetailsFragment.newInstance(eventInformation, addFragPagerAapter));
        timeFragment = TimeFragment.newInstance(eventInformation, addFragPagerAapter);
        fragments.add(timeFragment);

        addFragPagerAapter.setFragments(fragments);
        viewPager.setAdapter(addFragPagerAapter);
        tabLayout.setupWithViewPager(viewPager, true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        FloatingActionButton back_fab = (FloatingActionButton) findViewById(R.id.back_fab);
        back_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (addFragPagerAapter.getEnum() == AddFragmentPagerAdapter.ADD.TIME) {
                    timeFragment.setPosition(false);
                    return;
                }
                if (viewPager.getCurrentItem() > 0) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem()-1, true);
                }


            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (viewPager.getCurrentItem() + 1 < fragments.size()) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                    return;
                }
                timeFragment.setPosition(true);


            }
        });
    }

    @Override
    public void onFragmentInteraction(int position) {


    }
}
