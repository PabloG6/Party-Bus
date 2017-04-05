package momocorp.partybus.Fragments;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TimePicker;
import momocorp.partybus.Adapters.AddFragmentPagerAdapter;
import momocorp.partybus.CustomObjects.EventInformation;
import momocorp.partybus.R;
import momocorp.partybus.misc.ID;

public class TimeFragment extends Fragment {

    private FrameLayout frameLayout;
    EventInformation.Interface eventInfoInterface;
    private int position;
    ViewGroup container;
    public TimeFragment() {
        // Required empty public constructor
    }


    public static TimeFragment newInstance(EventInformation eventInformation, AddFragmentPagerAdapter addFragPagerAapter) {
        TimeFragment fragment = new TimeFragment();
        Bundle args = new Bundle();
        args.putParcelable(ID.addFragmentAdapter.name(), addFragPagerAapter);
        args.putParcelable(ID.eventInfo.name(), eventInformation);
        fragment.setArguments(args);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_time, container, false);
        final CardView cardView = (CardView) view.findViewById(R.id.card_view_holder);


//        tabLayout = (TabLayout) view.findViewById(R.id.add_tab_layout);
//        TabLayout.Tab dateTab = tabLayout.newTab().setIcon(R.drawable.ic_date_range_white_24dp);
//        TabLayout.Tab timeTab = tabLayout.newTab().setIcon(R.drawable.ic_access_time_white_24dp);
//        frameLayout.removeAllViews();
//        DatePicker datePicker = new DatePicker(getActivity());
//        tabLayout.addTab(dateTab, true);
//        tabLayout.addTab(timeTab);
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                switch (tab.getPosition()) {
//                    case 0:
//                        frameLayout.removeAllViews();
//                        DatePicker datePicker = new DatePicker(getActivity());
//                        frameLayout.addView(datePicker);
//                        Snackbar.make(view, "Choose a date", Snackbar.LENGTH_SHORT).show();
//                        break;
//                    case 1:
//                        frameLayout.removeAllViews();
//                        TimePicker timePicker = new TimePicker(getActivity());
//                        timePicker.setLayoutParams(new FrameLayout.
//                                LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
//                                FrameLayout.LayoutParams.MATCH_PARENT));
//                        frameLayout.addView(timePicker);
//                        Snackbar.make(view, "Choose a time", Snackbar.LENGTH_SHORT).show();
//                        break;
//                    case 2:
//                        frameLayout.removeAllViews();
//                        //instantiate new layout android
//                        NumberPicker numberPicker = new NumberPicker(getActivity());
//                        numberPicker.
//                                setLayoutParams(new FrameLayout.
//                                                LayoutParams
//                                        (FrameLayout.LayoutParams.MATCH_PARENT,
//                                        FrameLayout.LayoutParams.MATCH_PARENT));
//
//                        break;
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

        switch (position) {
            case 0:
                cardView.removeAllViews();
                View calendar_date_view = LayoutInflater.from(getActivity()).inflate(R.layout.calendar_date_layout, container, false);
                cardView.addView(calendar_date_view);
                Snackbar.make(view, "Choose a date", Snackbar.LENGTH_SHORT).show();
                break;
            case 1:
                cardView.removeAllViews();
                View time_date_view = LayoutInflater.from(getActivity()).inflate(R.layout.time_date_layout, container, false);
                cardView.addView(time_date_view);
                Snackbar.make(view, "Choose a time", Snackbar.LENGTH_SHORT).show();
                break;
            case 2:
                break;
            default:
                break;
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    public void setPosition() {
//        int i = tabLayout.getSelectedTabPosition();
//        TabLayout.Tab tab = tabLayout.getTabAt(i + 1);
//        if (tab != null)
//            tab.select();






    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int uri);
    }
}
