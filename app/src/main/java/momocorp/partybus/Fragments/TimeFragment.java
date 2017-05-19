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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TimePicker;

import java.util.Calendar;

import momocorp.partybus.Adapters.AddFragmentPagerAdapter;
import momocorp.partybus.CustomObjects.EventInformation;
import momocorp.partybus.Fragments.Eventsfragments.FragmentInterfaces.EventInterface;
import momocorp.partybus.R;
import momocorp.partybus.misc.ID;

public class TimeFragment extends Fragment {

    EventInformation.Interface eventInfoInterface;
    private int position;
    TabLayout tabLayout;
    ViewGroup container;
    EventInformation eventInformation;

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
            eventInformation = getArguments().getParcelable(ID.eventInfo.name());
            eventInfoInterface = (EventInformation.Interface) getArguments().getParcelable(ID.addFragmentAdapter.name());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_time, container, false);
        final CardView cardView = (CardView) view.findViewById(R.id.card_view_holder);


        tabLayout = (TabLayout) view.findViewById(R.id.add_tab_layout);
        TabLayout.Tab dateTab = tabLayout.newTab().setIcon(R.drawable.ic_date_range_white_24dp);
        TabLayout.Tab timeTab = tabLayout.newTab().setIcon(R.drawable.ic_access_time_white_24dp);
        tabLayout.addTab(dateTab, true);
        tabLayout.addTab(timeTab);
        cardView.removeAllViews();
        View calendar_date_view = LayoutInflater.from(getActivity()).inflate(R.layout.calendar_date_layout, container, false);
        cardView.addView(calendar_date_view);
        Snackbar.make(view, "Choose a date", Snackbar.LENGTH_SHORT).show();
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                final Calendar c = Calendar.getInstance();
//                switch (tab.getPosition()) {
//                    case 0:
//                        cardView.removeAllViews();
//                        View calendar_date_view = LayoutInflater.from(getActivity()).inflate(R.layout.calendar_date_layout, container, false);
//                        cardView.addView(calendar_date_view);
//                        final DatePicker datePicker = (DatePicker) calendar_date_view.findViewById(R.id.calendar_date);
//                        //get current date
//
////                        Button submit = (Button) calendar_date_view.findViewById(R.id.submit_date);
////                        Button reset = (Button) calendar_date_view.findViewById(R.id.reset_date);
//                        datePicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
//
//                        Snackbar.make(view, "Choose a date", Snackbar.LENGTH_SHORT).show();
//                        submit.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                int day = datePicker.getDayOfMonth();
//                                int month = datePicker.getMonth();
//                                int year = datePicker.getYear();
//                                eventInformation.setDate(new EventInformation.Date(month, day, year));
//                                eventInfoInterface.setEventInformation(eventInformation);
//                                cardView.removeAllViews();
//                                View time_date_view = LayoutInflater.from(getActivity()).inflate(R.layout.time_date_layout, container, false);
//                                cardView.addView(time_date_view);
//                                Snackbar.make(view, "Choose a time", Snackbar.LENGTH_SHORT).show();
//
//                            }
//                        });
//
//                        reset.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                //todo make this reset to current time
//                                datePicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
//                            }
//                        });
//                        break;
//
//                    case 1:
//                        cardView.removeAllViews();
//                        View time_date_view = LayoutInflater.from(getActivity()).inflate(R.layout.time_date_layout, container, false);
//                        cardView.addView(time_date_view);
//                        final TimePicker timePicker = (TimePicker) time_date_view.findViewById(R.id.choose_time);
//                        Button submit_time = (Button) time_date_view.findViewById(R.id.submit_time);
//                        Button reset_time = (Button) time_date_view.findViewById(R.id.reset_time);
//                        timePicker.setCurrentHour(c.get(Calendar.HOUR));
//                        timePicker.setCurrentMinute(c.get(Calendar.MINUTE));
//
//                        Snackbar.make(view, "Choose a date", Snackbar.LENGTH_SHORT).show();
//                        submit_time.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                int hour = timePicker.getCurrentHour();
//                                int minute = timePicker.getCurrentMinute();
//
//                                eventInformation.seTime(new EventInformation.Time(hour, minute));
//                                eventInfoInterface.setEventInformation(eventInformation);
//                                cardView.removeAllViews();
//                                View time_date_view = LayoutInflater.from(getActivity()).inflate(R.layout.time_date_layout, container, false);
//                                cardView.addView(time_date_view);
//
//                            }
//                        });
//
//                        reset_time.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                //todo make this reset to current time
//
//
//                            }
//                        });
//
//
//                        break;
//
//                    default:
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


    public void setPosition(boolean value) {
        if(value) {
            int i = tabLayout.getSelectedTabPosition();
            TabLayout.Tab tab = tabLayout.getTabAt(i + 1);
            AddFragmentPagerAdapter.PAGE++;
            if (tab != null)
                tab.select();
            return;
        }

        int i = tabLayout.getSelectedTabPosition();
        TabLayout.Tab tab = tabLayout.getTabAt(i-1);
        if(tab!=null)
            tab.select();
        AddFragmentPagerAdapter.PAGE--;

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int uri);
    }
}
