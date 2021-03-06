package com.newsblur.network;

import java.util.ArrayList;

import android.content.Context;
import android.content.AsyncTaskLoader;

import com.newsblur.domain.FeedResult;

public class SearchAsyncTaskLoader extends AsyncTaskLoader<SearchLoaderResponse> {

	public static final String SEARCH_TERM = "searchTerm";
	
	private String searchTerm;
	private APIManager apiManager;

	public SearchAsyncTaskLoader(Context context, String searchTerm) {
		super(context);
		this.searchTerm = searchTerm;
		apiManager = new APIManager(context);
	}

	@Override
	public SearchLoaderResponse loadInBackground() {
		SearchLoaderResponse response;
		try {
			ArrayList<FeedResult> list = new ArrayList<FeedResult>();
            FeedResult[] results = apiManager.searchForFeed(searchTerm);
            if (results != null) {
                for (FeedResult result : results) {
                    list.add(result);
                }
            }
			response = new SearchLoaderResponse(list);
		} catch (ServerErrorException ex) {
			response = new SearchLoaderResponse(ex.getMessage());
		}
		return response;
	}

}
