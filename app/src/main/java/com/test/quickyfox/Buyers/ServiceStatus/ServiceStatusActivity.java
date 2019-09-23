package com.test.quickyfox.Buyers.ServiceStatus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.test.quickyfox.Buyers.ServiceStatus.Fragments.FragmentServiceCancelled;
import com.test.quickyfox.Buyers.ServiceStatus.Fragments.FragmentServiceCompleted;
import com.test.quickyfox.Buyers.ServiceStatus.Fragments.FragmentServiceOnItsWay;
import com.test.quickyfox.Buyers.ServiceStatus.Fragments.FragmentServiceProcessing;
import com.test.quickyfox.Buyers.ServiceStatus.Fragments.ViewPagerAdapter;
import com.test.quickyfox.Prevalent.Prevalent;
import com.test.quickyfox.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ServiceStatusActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_status);

        CircleImageView profileImageView = findViewById(R.id.profile_image_service_status);
        Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);

        tabLayout = findViewById(R.id.tablayout_service_status);
        viewPager = findViewById(R.id.viewpager_1);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //add fragments here

        adapter.AddFragment(new FragmentServiceProcessing(),"Processing");
        adapter.AddFragment(new FragmentServiceOnItsWay(),"On its way");
        adapter.AddFragment(new FragmentServiceCompleted(),"Complete");
        adapter.AddFragment(new FragmentServiceCancelled(),"Cancelled");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        /*tabLayout.getTabAt(0).setIcon(R.drawable.source);*/


    }
}
