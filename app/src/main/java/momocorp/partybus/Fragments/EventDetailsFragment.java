package momocorp.partybus.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import momocorp.partybus.Adapters.AddFragmentPagerAdapter;
import momocorp.partybus.CustomObjects.EventInformation;
import momocorp.partybus.R;
import momocorp.partybus.misc.ID;

public class EventDetailsFragment extends Fragment {
    EventInformation eventInformation;
    EventInformation.Interface eventInfoInterface;

    private OnFragmentInteractionListener mListener;

    public EventDetailsFragment() {
        // Required empty public constructor
    }

    public static EventDetailsFragment newInstance(EventInformation eventInformation, AddFragmentPagerAdapter addFragmentPagerAdapter) {
        EventDetailsFragment fragment = new EventDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ID.addFragmentAdapter.name(), eventInformation);
        args.putParcelable(ID.eventInfo.name(), eventInformation);
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
        Switch isFree = (Switch) view.findViewById(R.id.money_switch);
        Switch isDrinks = (Switch) view.findViewById(R.id.drink_switch);


        isFree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.money_dialog);
                    EditText price = (EditText) dialog.findViewById(R.id.price);
                    final Button doneButton = (Button) dialog.findViewById(R.id.done_button);

                    dialog.show();
                    Window window = dialog.getWindow();
                    if (window != null) {
                        window.setGravity(Gravity.CENTER);
                    }
                    price.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            if (editable != null && editable.toString().length() > 0) {
                                doneButton.setEnabled(true);

                            } else {
                                doneButton.setEnabled(false);
                            }
                        }
                    });
                    doneButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.dismiss();
                        }
                    });
                }
            }
        });
        isDrinks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

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
