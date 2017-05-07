package momocorp.partybus.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CompoundButton;
import android.widget.EditText;

import android.widget.LinearLayout;
import android.widget.Switch;;

import momocorp.partybus.Adapters.AddFragmentPagerAdapter;
import momocorp.partybus.CustomObjects.EventInformation;
import momocorp.partybus.R;
import momocorp.partybus.misc.ID;
import momocorp.partybus.misc.NumberTextWatcher;

public class EventDetailsFragment extends Fragment {
    EventInformation eventInformation;
    EventInformation.Interface eventInfoInterface;
    private String current = "";
    private OnFragmentInteractionListener mListener;

    public EventDetailsFragment() {
        // Required empty public constructor
    }

    public static EventDetailsFragment newInstance(EventInformation eventInformation, AddFragmentPagerAdapter addFragmentPagerAdapter) {
        EventDetailsFragment fragment = new EventDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ID.addFragmentAdapter.name(), addFragmentPagerAdapter);
        args.putParcelable(ID.eventInfo.name(), eventInformation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventInfoInterface = (EventInformation.Interface) getArguments().get(ID.addFragmentAdapter.name());
            eventInformation = (EventInformation) getArguments().get(ID.eventInfo.name());

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_details, container, false);
        final Switch isFree = (Switch) view.findViewById(R.id.money_switch);
        final Switch isDrinks = (Switch) view.findViewById(R.id.drink_switch);

        isFree.setChecked(true);
        isFree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                //set edittext values
                final EditText input = new EditText(getActivity());
                input.setInputType(Configuration.KEYBOARD_12KEY);
                input.addTextChangedListener(new NumberTextWatcher(input, "#,###"));

                //set layout params
                LinearLayout.LayoutParams lp = new
                        LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                input.setLayoutParams(lp);

                //set parameters of dialog builder
                builder.setView(input);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        isFree.setChecked(false);
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.setTitle("Price");
                builder.setIcon(R.drawable.ic_local_atm_blue_400_24dp);


                Dialog dialog = builder.create();

                if (!b) {

                    dialog.show();
                } else {
                    dialog.dismiss();
                }

            }
        });
        isDrinks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            eventInformation.isDrinks(b);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        eventInfoInterface.setEventInformation(eventInformation);


    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
