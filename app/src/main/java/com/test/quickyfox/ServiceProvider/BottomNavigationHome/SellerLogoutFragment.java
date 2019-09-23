package com.test.quickyfox.ServiceProvider.BottomNavigationHome;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.quickyfox.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SellerLogoutFragment extends Fragment {


    public SellerLogoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seller_logout, container, false);
    }

}
