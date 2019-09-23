package com.test.quickyfox.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.test.quickyfox.Interface.ItemClickListener;
import com.test.quickyfox.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductPrice, txtProductQuantity, txtProductSellerName,txtProductCategory;
    private ItemClickListener itemClickListener;

    public CartViewHolder(View itemView)
    {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtProductPrice = itemView.findViewById(R.id.cart_product_price);
        txtProductCategory = itemView.findViewById(R.id.cart_product_category);
        txtProductSellerName = itemView.findViewById(R.id.cart_product_seller_name);
        /*txtProductQuantity = itemView.findViewById(R.id.cart_product_quantity);*/

    }

    @Override
    public void onClick(View view)
    {
        itemClickListener.onClick(view, getAdapterPosition(), false);

    }

    public  void  setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }
}
