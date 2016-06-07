package com.example.desy.friends;

import android.content.ContentResolver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.example.desy.friends.model.Friend;

import java.util.List;

/**
 * Created by desy on 6/5/16.
 */
public class FriendsListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Friend>> {

    private static final String LOG_TAG = FriendsListFragment.class.getSimpleName();
    private FriendsCustomAdapter mFriendsCustomAdapter;
    private static final int LOADER_ID = 1;
    private ContentResolver mContentResolver;
    private List<Friend> mFriends;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        mContentResolver = getActivity().getContentResolver();
        mFriendsCustomAdapter = new FriendsCustomAdapter(getActivity(), getChildFragmentManager());
        setEmptyText("No friends");
        setListAdapter(mFriendsCustomAdapter);
        setListShown(false);

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }



    @Override
    public Loader<List<Friend>> onCreateLoader(int id, Bundle args) {
        mContentResolver = getActivity().getContentResolver();
        return new FriendsListLoader(getActivity(), FriendsContract.URI_TABLE, mContentResolver);
    }

    @Override
    public void onLoadFinished(Loader<List<Friend>> loader, List<Friend> friends) {
        mFriendsCustomAdapter.setData(friends);
        mFriends = friends;
        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Friend>> loader) {
        mFriendsCustomAdapter.setData(null);
    }
}
