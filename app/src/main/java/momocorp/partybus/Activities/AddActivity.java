package momocorp.partybus.Activities;

import android.app.Fragment;
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
import momocorp.partybus.R;

public class AddActivity extends AppCompatActivity {

    private EventInformation eventInformation = new EventInformation();

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
        ArrayList<Fragment> fragments = new ArrayList<>();

        AddFragmentPagerAdapter addFragPagerAapter = new AddFragmentPagerAdapter(getFragmentManager());
        fragments.add(AddFragment.newInstance(eventInformation, addFragPagerAapter));
        fragments.add(EventDetailsFragment.newInstance(eventInformation, addFragPagerAapter));

        addFragPagerAapter.setFragments(fragments);
        viewPager.setAdapter(addFragPagerAapter);
        tabLayout.setupWithViewPager(viewPager, true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);



            }
        });
    }

}
