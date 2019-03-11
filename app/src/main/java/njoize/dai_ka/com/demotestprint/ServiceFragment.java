package njoize.dai_ka.com.demotestprint;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyConstant myConstant = new MyConstant();

    private int postionAnInt = 1;
    private String amountString, tidString, tnameString, tznameString;
    private boolean totalABoolean;

    public static ServiceFragment serviceInstant(int positionInt,
                                                 String amountString,
                                                 boolean totalBool,
                                                 String tidString,
                                                 String tnameString,
                                                 String tznameString) {
        ServiceFragment serviceFragment = new ServiceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Position", positionInt);
        bundle.putString("Amount", amountString);
        bundle.putString("Tid", tidString);
        bundle.putString("Tname", tnameString);
        bundle.putString("Tzname", tznameString);
        bundle.putBoolean("Total", totalBool);
        serviceFragment.setArguments(bundle);
        return serviceFragment;
    }


    public ServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getValue();

//        Create TabLayout
        createTabLayout();

//        Create ViewPager
        createViewPager();


    } // Main Medthod

    private void getValue() {
        postionAnInt = getArguments().getInt("Position", 1);
        amountString = getArguments().getString("Amount", "");
        tidString = getArguments().getString("Tid", "");
        tnameString = getArguments().getString("Tname", "");
        tznameString = getArguments().getString("Tzname", "");
        totalABoolean = getArguments().getBoolean("Total", true);

        Log.d("2janV1", "Amount รับ " + amountString);
        Log.d("2janV1", "Tid รับ " + tidString);
        Log.d("2janV1", "Tname รับ " + tnameString);
        Log.d("2janV1", "Tzname รับ " + tznameString);
        Log.d("2janV1", "Total รับ " + totalABoolean);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("OrderFood", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Amount", amountString);
        editor.putString("Tid", tidString);
        editor.putString("Tname", tnameString);
        editor.putString("Tzname", tznameString);
        editor.putBoolean("Total", totalABoolean);
        editor.commit();


    }

    private void createViewPager() {
        viewPager = getView().findViewById(R.id.viewPager);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(myPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.setCurrentItem(postionAnInt);

    }

    private void createTabLayout() {
        tabLayout = getView().findViewById(R.id.tabLayout);
        String[] strings = myConstant.getTitleTabStrings();
        int[] iconInts = myConstant.getIconBillTitleInts();
        for (int i = 0; i < strings.length; i += 1) {
            tabLayout.addTab(tabLayout.newTab().setText(strings[i]).setIcon(iconInts[i]));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_, container, false);
    }

} // Main Class