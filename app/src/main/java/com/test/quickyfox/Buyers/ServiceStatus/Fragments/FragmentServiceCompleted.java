package com.test.quickyfox.Buyers.ServiceStatus.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.test.quickyfox.R;

public class FragmentServiceCompleted extends Fragment
{
    View view;

    public FragmentServiceCompleted()
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_service_completed,container,false);
        return view;
    }
}
