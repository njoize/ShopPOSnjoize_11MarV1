package njoize.dai_ka.com.demotestprint;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MyPagerAdapterBill extends FragmentStatePagerAdapter {

    private FragmentManager fragmentManager;
    private int anInt;

    public MyPagerAdapterBill(FragmentManager fragmentManager, int anInt) {
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
                BillFinishFragment billFinishFragment = new BillFinishFragment();
                return billFinishFragment;
            case 2:
                BillCancleFragment billCancleFragment = new BillCancleFragment();
                return billCancleFragment;
            case 3:
                BillOnlineFragment billOnlineFragment = new BillOnlineFragment();
                return billOnlineFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return anInt;
    }
} // Main Class
