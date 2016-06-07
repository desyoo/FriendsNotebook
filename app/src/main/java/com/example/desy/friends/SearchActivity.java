package com.example.desy.friends;

import android.content.ContentResolver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.desy.friends.model.Friend;

import java.util.List;

/**
 * Created by desy on 6/6/16.
 */
public class SearchActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<List<Friend>> {
    private static final String LOG_TAG = SearchActivity.class.getSimpleName();
    private FriendsCustomAdapter mFriendsCustomAdapter;
    private static int LOADER_ID = 2;
    private ContentResolver mContentResolver;
    private List<Friend> friendsRetrieved;
    private ListView mListView;
    private EditText mSearchEditText;
    private Button mSearchFriendButton;
    private String matchText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        mListView = (ListView) findViewById(R.id.searchResultsList);
        mSearchEditText = (EditText) findViewById(R.id.searchName);
        mSearchFriendButton = (Button) findViewById(R.id.searchButton);
        mContentResolver = getContentResolver();
        mFriendsCustomAdapter = new FriendsCustomAdapter(SearchActivity.this, getSupportFragmentManager());
        mListView.setAdapter(mFriendsCustomAdapter);

        mSearchFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchText = mSearchEditText.getText().toString();
                getSupportLoaderManager().initLoader(LOADER_ID++, null, SearchActivity.this);
            }
        });
    }

    @Override
    public Loader<List<Friend>> onCreateLoader(int id, Bundle args) {
        return new FriendsSearchListLoader(SearchActivity.this, FriendsContract.URI_TABLE, this.mContentResolver, matchText);
    }

    @Override
    public void onLoadFinished(Loader<List<Friend>> loader, List<Friend> friends) {
        mFriendsCustomAdapter.setData(friends);
        this.friendsRetrieved = friends;
    }

    @Override
    public void onLoaderReset(Loader<List<Friend>> loader) {
        mFriendsCustomAdapter.setData(null);
    }
}
