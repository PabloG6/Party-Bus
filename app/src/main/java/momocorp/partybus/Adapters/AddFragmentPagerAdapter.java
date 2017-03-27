package momocorp.partybus.Adapters;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v13.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

import momocorp.partybus.CustomObjects.EventInformation;
import momocorp.partybus.Fragments.AddFragment;
import momocorp.partybus.Fragments.EventDetailsFragment;

/**
 * Created by Pablo on 3/26/2017.
 */

public class AddFragmentPagerAdapter extends FragmentPagerAdapter implements EventInformation.Interface, Parcelable {
    ArrayList<Fragment> fragments;
    EventInformation eventInfo = new EventInformation();

    public AddFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragments = fragments;
    }



    protected AddFragmentPagerAdapter(Parcel in) {
        //lol


        super(new FragmentManager() {
            @Override
            public FragmentTransaction beginTransaction() {
                return null;
            }

            @Override
            public boolean executePendingTransactions() {
                return false;
            }

            @Override
            public Fragment findFragmentById(int i) {
                return null;
            }

            @Override
            public Fragment findFragmentByTag(String s) {
                return null;
            }

            @Override
            public void popBackStack() {

            }

            @Override
            public boolean popBackStackImmediate() {
                return false;
            }

            @Override
            public void popBackStack(String s, int i) {

            }

            @Override
            public boolean popBackStackImmediate(String s, int i) {
                return false;
            }

            @Override
            public void popBackStack(int i, int i1) {

            }

            @Override
            public boolean popBackStackImmediate(int i, int i1) {
                return false;
            }

            @Override
            public int getBackStackEntryCount() {
                return 0;
            }

            @Override
            public BackStackEntry getBackStackEntryAt(int i) {
                return null;
            }

            @Override
            public void addOnBackStackChangedListener(OnBackStackChangedListener onBackStackChangedListener) {

            }

            @Override
            public void removeOnBackStackChangedListener(OnBackStackChangedListener onBackStackChangedListener) {

            }

            @Override
            public void putFragment(Bundle bundle, String s, Fragment fragment) {

            }

            @Override
            public Fragment getFragment(Bundle bundle, String s) {
                return null;
            }

            @Override
            public Fragment.SavedState saveFragmentInstanceState(Fragment fragment) {
                return null;
            }

            @Override
            public boolean isDestroyed() {
                return false;
            }

            @Override
            public void dump(String s, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strings) {

            }
        });
        fragments = in.readParcelable(EventInformation.class.getClassLoader());
        eventInfo = in.readParcelable(EventInformation.class.getClassLoader());
    }

    public static final Creator<AddFragmentPagerAdapter> CREATOR = new Creator<AddFragmentPagerAdapter>() {
        @Override
        public AddFragmentPagerAdapter createFromParcel(Parcel in) {
            return new AddFragmentPagerAdapter(in);
        }

        @Override
        public AddFragmentPagerAdapter[] newArray(int size) {
            return new AddFragmentPagerAdapter[size];
        }
    };

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Log.i("Add Fragment Debug", "instantiate called");
        return super.instantiateItem(container, position);
    }

    @Override
    public void startUpdate(ViewGroup container) {
        Log.i("Add fragment debug", "start update");
        super.startUpdate(container);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);

        Log.i("Add fragment debug", "destroy update");
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);

        Log.i("Add fragment debug", "set primary item");
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);

        Log.i("Add fragment debug", "finish update update");
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        Log.i("Add fragment debug", "view from");
        return super.isViewFromObject(view, object);

    }

    @Override
    public Parcelable saveState() {

        Log.i("Add fragment debug", "save state");
        return super.saveState();
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {

        Log.i("Add fragment debug", "restore state");
        super.restoreState(state, loader);
    }

    @Override
    public long getItemId(int position) {

        Log.i("Add fragment debug", "get item id");
        return super.getItemId(position);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return (AddFragment) fragments.get(0);
            case 1:
                return (EventDetailsFragment) fragments.get(1);


        }
        return null;
    }

    @Override
    public int getCount() {

        Log.i("Add fragment debug", "get count");
        return 2;
    }

    //set updated eventinformation to pass via eventInfor
    public void setEventInformation(EventInformation eventInformation) {
        this.eventInfo = eventInformation;

    }

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(eventInfo, i);
    }

    public void setFragments(ArrayList<Fragment> fragments) {
        this.fragments = fragments;
    }
}

