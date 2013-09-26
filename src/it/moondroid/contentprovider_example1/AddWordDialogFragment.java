package it.moondroid.contentprovider_example1;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class AddWordDialogFragment extends DialogFragment implements
		OnEditorActionListener {

	public interface AddWordDialogListener {
		void onAddWord(String inputText);
	}

	private EditText mEditText;

	public AddWordDialogFragment() {
		// Empty constructor required for DialogFragment
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_add_word, container, false);

		mEditText = (EditText) v.findViewById(R.id.txt_your_word);

		getDialog().setTitle("Type a new word");

		mEditText.setOnEditorActionListener(this);

		return v;
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

		if (actionId == EditorInfo.IME_ACTION_DONE) {
			// Return input text to activity
			AddWordDialogListener activity = (AddWordDialogListener) getActivity();
			activity.onAddWord(mEditText.getText().toString());

			this.dismiss();

			return true;
		}
		return false;

	}

}
