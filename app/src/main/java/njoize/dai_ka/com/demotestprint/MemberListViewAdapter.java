package njoize.dai_ka.com.demotestprint;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MemberListViewAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<NameMemberModel> nameMemberModelArrayList;
    private List<NameMemberModel> nameMemberModelList;
    private Boolean statusABoolean;

    public MemberListViewAdapter(Context context,
                                 List<NameMemberModel> nameMemberModelList,
                                 boolean statusABoolean) {
        this.context = context;
        this.nameMemberModelList = nameMemberModelList;
        this.layoutInflater = LayoutInflater.from(context);
        this.nameMemberModelArrayList = new ArrayList<NameMemberModel>();
        this.nameMemberModelArrayList.addAll(nameMemberModelList);
        this.statusABoolean = statusABoolean;
    }

    @Override
    public int getCount() {
        return nameMemberModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return nameMemberModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.recycler_member, null);
            holder.name = convertView.findViewById(R.id.txtName);
            holder.imageView = convertView.findViewById(R.id.btnDetail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String idString = nameMemberModelList.get(position).getIdString();
        holder.name.setText(nameMemberModelList.get(position).getNameMemberString());

        if (!statusABoolean) {

            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("28FebV1", "You Click Text id ==> " + idString);


//                    ((DetailActivity) parent.getContext()).getSupportFragmentManager().popBackStack();
//                    ((DetailActivity)parent.getContext()).getSupportFragmentManager().popBackStack();
//                    ((DetailActivity) parent.getContext()).getSupportFragmentManager()
//                            .beginTransaction().add(R.id.contentDetailFragment, BillDetailFragment.billDetailInstance("",
//                            "",
//                            "",
//                            "", "", "", "", "", true))
//                            .commit();

                    Intent intent = new Intent(parent.getContext(), DetailActivity.class);
                    intent.putExtra("Status", true);
                    intent.putExtra("mid", idString);
                    Log.d("28FebV1", "mid adapter ==> " + idString);

                    SharedPreferences sharedPreferences = ((ShowMemberListActivity) parent.getContext())
                            .getSharedPreferences("BillDetail", Context.MODE_PRIVATE);

                    intent.putExtra("idBill", sharedPreferences.getString("idBill", ""));
                    intent.putExtra("Time", sharedPreferences.getString("Time", ""));
                    intent.putExtra("cnum", sharedPreferences.getString("cnum", ""));
                    intent.putExtra("type", sharedPreferences.getString("type", ""));
                    intent.putExtra("name", sharedPreferences.getString("name", ""));
                    intent.putExtra("Zone", sharedPreferences.getString("Zone", ""));
                    intent.putExtra("Desk", sharedPreferences.getString("Desk", ""));
                    intent.putExtra("tid", sharedPreferences.getString("tid", ""));



                    parent.getContext().startActivity(intent);
                    ((ShowMemberListActivity) parent.getContext()).finish();


                }
            });

        }


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("20FebV2", "You Click Arrow id==> " + idString);
                Intent intent = new Intent(parent.getContext(), MemberActivity.class);
                intent.putExtra("id", idString);
                intent.putExtra("Status", false);
                parent.getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView name;
        ImageView imageView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        nameMemberModelList.clear();
        if (charText.length() == 0) {
            nameMemberModelList.addAll(nameMemberModelArrayList);
        } else {
            for (NameMemberModel nameMemberModel : nameMemberModelArrayList) {
                if (nameMemberModel.getNameMemberString().toLowerCase(Locale.getDefault()).contains(charText)) {
                    nameMemberModelList.add(nameMemberModel);
                }
            } // for
        } // if
        notifyDataSetChanged();
    } // filter


}
