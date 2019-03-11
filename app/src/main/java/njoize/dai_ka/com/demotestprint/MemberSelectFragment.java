package njoize.dai_ka.com.demotestprint;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class MemberSelectFragment extends Fragment {


    public MemberSelectFragment() {
        // Required empty public constructor
    }

    public static MemberSelectFragment memberSelectInstant(String id) {
        MemberSelectFragment memberSelectFragment = new MemberSelectFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        memberSelectFragment.setArguments(bundle);
        return memberSelectFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Intent intent = new Intent(getActivity(), DetailActivity.class);
//        intent.putExtra("idMember", getArguments().getString("id"));
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_member_select, container, false);
    }

}
