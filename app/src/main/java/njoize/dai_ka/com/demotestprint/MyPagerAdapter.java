package njoize.dai_ka.com.demotestprint;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private FragmentManager fragmentManager;
    private int anInt;

    public MyPagerAdapter(FragmentManager fragmentManager, int anInt) {
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
        this.anInt = anInt;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                BillFragment billFragment = new BillFragment();
                return billFragment;
            case 1:
                DeskFragment deskFragment = new DeskFragment();
                return deskFragment;
            case 2:
                FoodFragment foodFragment = FoodFragment.foodInstante("", true,"","", "");
                return foodFragment;
            case 3:
                ArrayList<String> stringArrayList = new ArrayList<>();
                MemberFragment memberFragment = MemberFragment.memberInstance(true, stringArrayList);
                return memberFragment;
//                NotificationFragment notificationFragment = new NotificationFragment();
//                return notificationFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return anInt;
    }
} // Main Class