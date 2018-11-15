package hu.bme.aut.shoppinglist.adapter;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hu.bme.aut.shoppinglist.R;
import hu.bme.aut.shoppinglist.data.ShoppingItem;

public class ShoppingAdapter
        extends RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder> {

    private final List<ShoppingItem> items;

    private ShoppingItemClickListener listener;

    public ShoppingAdapter(ShoppingItemClickListener listener) {
        this.listener = listener;
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public ShoppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_shopping_list, parent, false);
        return new ShoppingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingViewHolder holder, int position) {
        ShoppingItem item = items.get(position);
        holder.nameTextView.setText(item.name);
        holder.descriptionTextView.setText(item.description);
        holder.categoryTextView.setText(item.category.name());
        holder.priceTextView.setText(item.estimatedPrice + " Ft");
        holder.iconImageView.setImageResource(getImageResource(item.category));
        holder.isBoughtCheckBox.setChecked(item.isBought);

        holder.item = item;
    }

    public void addItem(ShoppingItem item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    private void deleteItemAndNotify(ShoppingItem item){
        listener.onItemDeleted(item);
        deleteItem(item);
    }

    private void deleteItem(ShoppingItem item){
        int index = items.indexOf(item);
        notifyItemRemoved(index);
        items.remove(item);
    }

    public void deleteAllItems(){
        for(Iterator<ShoppingItem> i = items.iterator(); i.hasNext();){
            notifyItemRemoved(items.indexOf(i.next()));
            i.remove();
        }
    }

    private void editItem(@NonNull ShoppingItem item){
        listener.editItem(item);
    }

    public void update(List<ShoppingItem> shoppingItems) {
        items.clear();
        items.addAll(shoppingItems);
        notifyDataSetChanged();
    }

    public void onItemUpdated(ShoppingItem shoppingItem){
        for (int i = 0; i < items.size(); ++i)
        {
            if (items.get(i).id.equals(shoppingItem.id))
            {
                Log.d("item updated", "item updated");
                items.set(i, shoppingItem);
            }
        }
        notifyDataSetChanged();
    }

    private @DrawableRes
    int getImageResource(ShoppingItem.Category category) {
        @DrawableRes int ret;
        switch (category) {
            case BOOK:
                ret = R.drawable.open_book;
                break;
            case ELECTRONIC:
                ret = R.drawable.lightning;
                break;
            case FOOD:
                ret = R.drawable.groceries;
                break;
            default:
                ret = 0;
        }
        return ret;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface ShoppingItemClickListener{
        void onItemChanged(ShoppingItem item);
        void onItemDeleted(ShoppingItem item);
        void editItem(ShoppingItem item);
    }

    class ShoppingViewHolder extends RecyclerView.ViewHolder {

        ImageView iconImageView;
        TextView nameTextView;
        TextView descriptionTextView;
        TextView categoryTextView;
        TextView priceTextView;
        CheckBox isBoughtCheckBox;
        ImageButton removeButton;
        Button editButton;

        ShoppingItem item;

        ShoppingViewHolder(View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.ShoppingItemIconImageView);
            nameTextView = itemView.findViewById(R.id.ShoppingItemNameTextView);
            descriptionTextView = itemView.findViewById(R.id.ShoppingItemDescriptionTextView);
            categoryTextView = itemView.findViewById(R.id.ShoppingItemCategoryTextView);
            priceTextView = itemView.findViewById(R.id.ShoppingItemPriceTextView);
            isBoughtCheckBox = itemView.findViewById(R.id.ShoppingItemIsBoughtCheckBox);
            removeButton = itemView.findViewById(R.id.ShoppingItemRemoveButton);
            editButton = itemView.findViewById(R.id.ShoppingItemEditButton);

            isBoughtCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                    if(item != null){
                        item.isBought = isChecked;
                        listener.onItemChanged(item);
                    }
                }
            });

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(item != null){
                        deleteItemAndNotify(item);
                        item = null;
                    }
                }
            });

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(item != null){
                        editItem(item);
                    }
                }
            });
        }
    }
}
