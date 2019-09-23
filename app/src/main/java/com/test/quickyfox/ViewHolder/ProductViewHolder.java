package com.test.quickyfox.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.test.quickyfox.Interface.ItemClickListener;
import com.test.quickyfox.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView txtProductName, txtProductDescription, txtProductPrice, txtProductSeller,txtProductCategory;
    public ImageView imageView;
    public  ItemClickListener listener;


    public ProductViewHolder(@NonNull View itemView)
    {
        super(itemView);

        imageView = itemView.findViewById(R.id.product_image);
        txtProductName = itemView.findViewById(R.id.product_name);
        txtProductDescription =itemView.findViewById(R.id.product_description);
        txtProductPrice = itemView.findViewById(R.id.product_price);
        txtProductSeller = itemView.findViewById(R.id.product_seller_details);
        txtProductCategory=itemView.findViewById(R.id.product_category_details);

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
