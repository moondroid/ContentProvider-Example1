package it.moondroid.contentprovider_example1;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class EditWordDialogFragment extends DialogFragment implements OnEditorActionListener, OnClickListener {

	private String mOldWord = "";
	private long mId;
	
	public interface EditWordDialogListener {
        void onEditWord(long id, String inputText);
        void onDeleteClick(long id);
    }

    private EditText mEditText;
    
	public EditWordDialogFragment() {
		// Empty constructor required for DialogFragment
	}

		
	public static EditWordDialogFragment newInstance(long id, String oldWord) {

		EditWordDialogFragment f = new EditWordDialogFragment();
		Bundle args = new Bundle();
		args.putString("oldword", oldWord);
		args.putLong("id", id);
		f.setArguments(args);
		return f;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater
				.inflate(R.layout.fragment_edit_word, container, false);

		mEditText = (EditText) v.findViewById(R.id.txt_your_word);
		 
		getDialog().setTitle("Edit word");
		
		Bundle b = getArguments();		
		if (b != null) {
			mOldWord = getArguments().getString("oldword");
			mId = getArguments().getLong("id", 0);
		}
		mEditText.setText(mOldWord);
		
        mEditText.setOnEditorActionListener(this);
		
        
        ImageButton btnDelete = (ImageButton)v.findViewById(R.id.ibtnDelete);
        btnDelete.setOnClickListener(this);
        
		return v;
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		
		if (actionId == EditorInfo.IME_ACTION_DONE) {
            // Return input text to activity
            EditWordDialogListener activity = (EditWordDialogListener) getActivity();
            activity.onEditWord(mId, mEditText.getText().toString());
            this.dismiss();
            return true;
        }
        return false;
        
	}


	@Override
	public void onClick(View v) {
		
		// Return input text to activity
        EditWordDialogListener activity = (EditWordDialogListener) getActivity();
        activity.onDeleteClick(mId);
        this.dismiss();
	}

}
