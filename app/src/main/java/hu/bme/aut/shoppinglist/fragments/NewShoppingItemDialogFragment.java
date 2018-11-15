package hu.bme.aut.shoppinglist.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import hu.bme.aut.shoppinglist.R;
import hu.bme.aut.shoppinglist.data.ShoppingItem;

public class NewShoppingItemDialogFragment extends DialogFragment {

    public static final String TAG = "NewShoppingItemDialogFragment";

    public interface NewShoppingItemDialogListener {
        void onShoppingItemCreated(ShoppingItem newItem);
        void onShoppingItemChanged(ShoppingItem item);
    }

    public void setItemToShow(ShoppingItem itemToShow){
        editing = true;
        this.itemToShow = itemToShow;
    }

    private NewShoppingItemDialogListener listener;

    private EditText nameEditText;
    private EditText descriptionEditText;
    private EditText estimatedPriceEditText;
    private Spinner categorySpinner;
    private CheckBox alreadyPurchasedCheckBox;
    private ArrayAdapter<String> adapter;
    private boolean editing;
    ShoppingItem itemToShow;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentActivity activity = getActivity();
        if (activity instanceof NewShoppingItemDialogListener) {
            listener = (NewShoppingItemDialogListener) activity;
        } else {
            throw new RuntimeException("Activity must implement the NewShoppingItemDialogListener interface!");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setTitle(editing ? R.string.editing_shopping_item : R.string.new_shopping_item)
                .setView(getContentView())
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (isValid()) {
                            if(editing)
                                listener.onShoppingItemChanged(getShoppingItem());
                            else
                                listener.onShoppingItemCreated(getShoppingItem());
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();
    }

    private View getContentView() {
        adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.category_items));
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_new_shopping_item, null);
        nameEditText = contentView.findViewById(R.id.ShoppingItemNameEditText);
        descriptionEditText = contentView.findViewById(R.id.ShoppingItemDescriptionEditText);
        estimatedPriceEditText = contentView.findViewById(R.id.ShoppingItemEstimatedPriceEditText);
        categorySpinner = contentView.findViewById(R.id.ShoppingItemCategorySpinner);
        categorySpinner.setAdapter(adapter);
        alreadyPurchasedCheckBox = contentView.findViewById(R.id.ShoppingItemIsPurchasedCheckBox);
        if(editing)
            initializeDisplayedData();
        return contentView;
    }

    private void initializeDisplayedData(){
        nameEditText.setText(itemToShow.name);
        descriptionEditText.setText(itemToShow.description);
        estimatedPriceEditText.setText(String.valueOf(itemToShow.estimatedPrice));
        categorySpinner.setSelection(adapter.getPosition(itemToShow.category.name()));
        alreadyPurchasedCheckBox.setChecked(itemToShow.isBought);
    }

    private boolean isValid() {
        return nameEditText.getText().length() > 0;
    }

    private ShoppingItem getShoppingItem() {
        ShoppingItem shoppingItem = new ShoppingItem();
        if(editing)
            shoppingItem.id = itemToShow.id;
        shoppingItem.name = nameEditText.getText().toString();
        shoppingItem.description = descriptionEditText.getText().toString();
        try {
            shoppingItem.estimatedPrice = Integer.parseInt(estimatedPriceEditText.getText().toString());
        } catch (NumberFormatException e) {
            shoppingItem.estimatedPrice = 0;
        }
        shoppingItem.category = ShoppingItem.Category.getByOrdinal(categorySpinner.getSelectedItemPosition());
        shoppingItem.isBought = alreadyPurchasedCheckBox.isChecked();
        return shoppingItem;
    }
}