package it.moondroid.contentprovider_example1;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleCursorAdapter;

public class WordsListFragment extends ListFragment implements OnItemClickListener {

	private SimpleCursorAdapter mCursorAdapter;
	
	public interface WordsListListener {
        void onWordClick(int position);
    }
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void loadWordsList(Cursor cursor) {
		
		
		// Defines a list of columns to retrieve from the Cursor and load into an output row
		String[] mWordListColumns =
		{
		    UserDictionary.Words.WORD,   // Contract class constant containing the word column name
		    UserDictionary.Words.LOCALE  // Contract class constant containing the locale column name
		};

		// Defines a list of View IDs that will receive the Cursor columns for each row
		int[] mWordListItems = { R.id.dictWord, R.id.locale};

		// Creates a new SimpleCursorAdapter
		mCursorAdapter = new SimpleCursorAdapter(
		    getActivity().getApplicationContext(),               // The application's Context object
		    R.layout.wordlistrow,                  // A layout in XML for one row in the ListView
		    cursor,                               // The result from the query
		    mWordListColumns,                      // A string array of column names in the cursor
		    mWordListItems,                        // An integer array of view IDs in the row layout
		    0);                                    // Flags (usually none are needed)

		// Sets the adapter for the ListView
		setListAdapter(mCursorAdapter);
		
		getListView().setOnItemClickListener(this);
	}
	
	public void updateWordsList(Cursor cursor){
		
		if (mCursorAdapter!=null){
			mCursorAdapter.changeCursor(cursor);
			mCursorAdapter.notifyDataSetChanged();
		}
		
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	
		WordsListListener activity = (WordsListListener)getActivity();
		activity.onWordClick(position);
	}

}
