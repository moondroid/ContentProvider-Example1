package it.moondroid.contentprovider_example1;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.UserDictionary;

public class DictionaryController {

	private static DictionaryController _instance = null;
	private static ContentResolver mResolver;

	private DictionaryController() {

	}

	public static synchronized DictionaryController newInstance(Context c) {

		if (_instance == null) {
			_instance = new DictionaryController();
			
			// An application accesses the data from a content provider with a
			// ContentResolver client object
			mResolver = c.getContentResolver();
		}
		return _instance;

	}

	
	public Cursor getWords(){
			
		
		// A "projection" defines the columns that will be returned for each row
		String[] mProjection =
		{
		    UserDictionary.Words._ID,    // Contract class constant for the _ID column name
		    UserDictionary.Words.WORD,   // Contract class constant for the word column name
		    UserDictionary.Words.LOCALE  // Contract class constant for the locale column name
		};

		// Defines a string to contain the selection clause
		String mSelectionClause = null;

		// Initializes an array to contain selection arguments
		String[] mSelectionArgs = null;
		
		
		String mSortOrder = UserDictionary.Words.DEFAULT_SORT_ORDER;
		
		
		// Queries the user dictionary and returns results
		Cursor c = mResolver.query(
		    UserDictionary.Words.CONTENT_URI,   // The content URI of the words table
		    mProjection,                        // The columns to return for each row
		    mSelectionClause,                   // Selection criteria
		    mSelectionArgs,                     // Selection criteria
		    mSortOrder);                        // The sort order for the returned rows
		
		
		return c;
	
		
	}
	
	public long addWord(String word) {

		// Defines a new Uri object that receives the result of the insertion
		Uri mNewUri;

		// Defines an object to contain the new values to insert
		ContentValues mNewValues = new ContentValues();

		/*
		 * Sets the values of each column and inserts the word. The arguments to
		 * the "put" method are "column name" and "value"
		 */
		mNewValues.put(UserDictionary.Words.APP_ID,
				"it.moondroid.contentprovider_example1");
		mNewValues.put(UserDictionary.Words.LOCALE, "it_IT");
		mNewValues.put(UserDictionary.Words.WORD, word);
		mNewValues.put(UserDictionary.Words.FREQUENCY, "1");

		mNewUri = mResolver.insert(UserDictionary.Words.CONTENT_URI, // the user dictionary content URI
				mNewValues // the values to insert
				);

		return ContentUris.parseId(mNewUri);
	}

	public int deleteWord(long id) {

		// Defines selection criteria for the rows you want to update
		String mSelectionClause = UserDictionary.Words._ID + "= ?";
		String[] mSelectionArgs = { Long.toString(id) };

		// Defines a variable to contain the number of updated rows
		int mRowsDeleted = 0;

		mRowsDeleted = mResolver.delete(UserDictionary.Words.CONTENT_URI, // the user dictionary content URI
				mSelectionClause, // the column to select on
				mSelectionArgs // the value to compare to
				);

		return mRowsDeleted;

	}
	
	
	public int updateWord(long id, String newWord){
		
		// Defines an object to contain the updated values
		ContentValues mUpdateValues = new ContentValues();

		// Defines selection criteria for the rows you want to update
		String mSelectionClause = UserDictionary.Words._ID +  "= ?";
		String[] mSelectionArgs = {Long.toString(id)};

		// Defines a variable to contain the number of updated rows
		int mRowsUpdated = 0;

		/*
		 * Sets the updated value and updates the selected words.
		 */
		mUpdateValues.put(UserDictionary.Words.WORD, newWord);

		mRowsUpdated = mResolver.update(
		    UserDictionary.Words.CONTENT_URI,   // the user dictionary content URI
		    mUpdateValues,                       // the columns to update
		    mSelectionClause,                    // the column to select on
		    mSelectionArgs                      // the value to compare to
		);
		
		
		return mRowsUpdated;
	}

}
