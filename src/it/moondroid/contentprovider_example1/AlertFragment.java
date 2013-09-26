package it.moondroid.contentprovider_example1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class AlertFragment extends DialogFragment implements OnClickListener {

	public interface AlertDialogListener {
        
		void onDeleteWord(long id);
    }
	
	public AlertFragment(){
		// Empty constructor required for DialogFragment
	}
	
	private long mId;
	
	public static AlertFragment newInstance(long id) {

		AlertFragment f = new AlertFragment();
		Bundle args = new Bundle();		
		args.putLong("id", id);
		f.setArguments(args);
		return f;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		Context mContext = getActivity();
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle("Delete Word");
        alertDialogBuilder.setMessage("Are you sure?");
        //null should be your on click listener
        alertDialogBuilder.setPositiveButton("OK", this);
        alertDialogBuilder.setNegativeButton("Cancel", this);

        Bundle b = getArguments();		
		if (b != null) {
			mId = getArguments().getLong("id", 0);
		}
		
        return alertDialogBuilder.create();
		
	}

	@Override
	public void onClick(DialogInterface dialog, int button) {
		
		if(button==DialogInterface.BUTTON_POSITIVE){
			AlertDialogListener activity = (AlertDialogListener)getActivity();
			activity.onDeleteWord(mId);
		}
				
		dialog.dismiss();
	}
	
	
	
}
