package njoize.dai_ka.com.demotestprint;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder>{

    private Context context;
    private ArrayList<String> nameStringArrayList;
    private OnClickItem onClickItem;
    private LayoutInflater layoutInflater;

    public MemberAdapter(Context context, ArrayList<String> nameStringArrayList, OnClickItem onClickItem) {
        this.layoutInflater = LayoutInflater.from(context);
        this.nameStringArrayList = nameStringArrayList;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = layoutInflater.inflate(R.layout.recycler_member, viewGroup, false);
        MemberViewHolder memberViewHolder = new MemberViewHolder(view);

        return memberViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MemberViewHolder memberViewHolder, int i) {

        String name = nameStringArrayList.get(i);

        memberViewHolder.textView.setText(name);
        memberViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClickItem(v,memberViewHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameStringArrayList.size();
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.txtName);

        }
    }

}
