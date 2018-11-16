package hu.bme.aut.shoppinglist.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import hu.bme.aut.shoppinglist.R;

public class NewTranslationDialogFragment extends DialogFragment {

    public class Translation{
        public String hungarianWord;
        public String italianWord;
    }

    public interface NewTranslationDialogListener {
        void onTranslationCreated(Translation translation);
    }

    private NewTranslationDialogListener listener;

    private EditText hungarianWordEditText;
    private EditText italianWordEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity instanceof NewTranslationDialogListener) {
            listener = (NewTranslationDialogListener) activity;
        } else {
            throw new RuntimeException("Activity must implement the NewShoppingItemDialogListener interface!");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.creating_new_translation)
                .setView(getContentView())
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (true) { //TODO check if content is valid
                            listener.onTranslationCreated(getTranslation());
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
    }

    private View getContentView() {
        View contentView = View.inflate(getContext(),R.layout.dialog_new_translation,null);
        italianWordEditText = contentView.findViewById(R.id.NewTranslationDialogItalianWord);
        hungarianWordEditText = contentView.findViewById(R.id.NewTranslationDialogHungarianWord);
        return contentView;
    }

    private Translation getTranslation() {
        Translation newTranslation = new Translation();

        newTranslation.hungarianWord = hungarianWordEditText.getText().toString();
        newTranslation.italianWord = italianWordEditText.getText().toString();

        return newTranslation;
    }
}