package it.moondroid.contentprovider_example1;

import it.moondroid.contentprovider_example1.AddWordDialogFragment.AddWordDialogListener;
import it.moondroid.contentprovider_example1.AlertFragment.AlertDialogListener;
import it.moondroid.contentprovider_example1.EditWordDialogFragment.EditWordDialogListener;
import it.moondroid.contentprovider_example1.WordsListFragment.WordsListListener;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends FragmentActivity 
implements OnClickListener, 
	AddWordDialogListener, EditWordDialogListener, AlertDialogListener, WordsListListener {

	private WordsListFragment mWordList;
	private Cursor mCursor;
	private DictionaryController mDictionary;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				
		ImageButton btnAdd = (ImageButton)findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(this);
		
		FragmentManager fm = getSupportFragmentManager();
		mWordList = (WordsListFragment) fm.findFragmentById(R.id.frWordsList);
		
		mDictionary = DictionaryController.newInstance(this);
		
		//When your activity is created again, call getLastNonConfigurationInstance() to recover your object.
		mCursor = (Cursor) getLastCustomNonConfigurationInstance();
		if (mCursor==null){
			
			
			mCursor = mDictionary.getWords();
			
			if (mCursor!=null){
				Toast.makeText(this, "Words found: "+mCursor.getCount(), Toast.LENGTH_SHORT).show();
				mWordList.loadWordsList(mCursor);
			}else{
				Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
			}
			
		}else{
			mWordList.loadWordsList(mCursor);
		}
	
	}

	//Override the onRetainNonConfigurationInstance() method to return the object you would like to retain.
	//When the Android system shuts down your activity due to a configuration change, it calls onRetainNonConfigurationInstance()
	@Override
	public Object onRetainCustomNonConfigurationInstance() {
		return mCursor;
	}

		
	
	private void showAddDialog() {
		FragmentManager fm = getSupportFragmentManager();
		AddWordDialogFragment dialog = new AddWordDialogFragment();
		dialog.show(fm, "addwordfragment");
	}
	
	private void showEditDialog(long id, String word) {
		FragmentManager fm = getSupportFragmentManager();
		EditWordDialogFragment dialog = EditWordDialogFragment.newInstance(id, word);
		dialog.show(fm, "editwordfragment");
	}
	
	private void showAlertDialog(long id){
		FragmentManager fm = getSupportFragmentManager();
		AlertFragment dialog = AlertFragment.newInstance(id);
		dialog.show(fm, "alertfragment");
	}
	
	private void updateList(){
		mCursor = mDictionary.getWords();
		mWordList.updateWordsList(mCursor);
	}
	
	
	@Override
	public void onClick(View view) {
		
		switch(view.getId()){
			
		case R.id.btnAdd:
			showAddDialog();
			break;
		
		}
		
	}

			
	@Override
	public void onWordClick(int position) {
		
		boolean moved = mCursor.moveToPosition(position);
		if (moved){
			String word = mCursor.getString(mCursor.getColumnIndex(UserDictionary.Words.WORD));
			long wordId = mCursor.getLong(mCursor.getColumnIndex(UserDictionary.Words._ID));
			
			showEditDialog(wordId, word);
		}
	}
	
	@Override
	public void onAddWord(String inputText) {
				
		mDictionary.addWord(inputText);		
		Toast.makeText(this, "Added: " + inputText, Toast.LENGTH_SHORT).show();
		
		updateList();
		
	}

	@Override
	public void onEditWord(long id, String inputText) {
				
		mDictionary.updateWord(id, inputText);		
		Toast.makeText(this, "Modified to: " + inputText, Toast.LENGTH_SHORT).show();
		
		updateList();
				
	}

	@Override
	public void onDeleteClick(long id) {
		
		showAlertDialog(id);
		
	}

	@Override
	public void onDeleteWord(long id) {
	
		mDictionary.deleteWord(id);			
		Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
			
		updateList();

	}


	

	

}
