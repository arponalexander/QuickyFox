package com.test.quickyfox.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.quickyfox.Interface.ItemClickListener;
import com.test.quickyfox.R;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView txtProductName, txtProductDescription, txtProductPrice, txtProductSeller,txtProductCategory, txtProductStatus;
    public ImageView imageView;
    public  ItemClickListener listener;


    public ItemViewHolder(@NonNull View itemView)
    {
        super(itemView);

        imageView = itemView.findViewById(R.id.product_seller_image);
        txtProductName = itemView.findViewById(R.id.product_seller_name);
        txtProductStatus = itemView.findViewById(R.id.product_seller_state);
        txtProductPrice = itemView.findViewById(R.id.product_seller_price);

    }

    public void  setItemClickListner(ItemClickListener listner)
    {
        this.listener = listner;
    }

    @Override
    public void onClick(View view)
    {
        listener.onClick(view, getAdapterPosition(), false);
    }
}

