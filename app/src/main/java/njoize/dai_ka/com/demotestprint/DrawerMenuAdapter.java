package njoize.dai_ka.com.demotestprint;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DrawerMenuAdapter extends RecyclerView.Adapter<DrawerMenuAdapter.DrawerMenuViewHolder>{

    private Context context;
    private ArrayList<Integer> integerArrayList = new ArrayList<>();
    private ArrayList<String> stringArrayList = new ArrayList<>();
    private OnClickItem onClickItem;
    private LayoutInflater layoutInflater;

    public DrawerMenuAdapter(Context context, ArrayList<Integer> integerArrayList, ArrayList<String> stringArrayList, OnClickItem onClickItem) {
        this.layoutInflater = LayoutInflater.from(context);
        this.integerArrayList = integerArrayList;
        this.stringArrayList = stringArrayList;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public DrawerMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = layoutInflater.inflate(R.layout.recycler_drawer_menu, viewGroup, false);
        DrawerMenuViewHolder drawerMenuViewHolder = new DrawerMenuViewHolder(view);

        return drawerMenuViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DrawerMenuViewHolder drawerMenuViewHolder, int i) {

        int iconInt = integerArrayList.get(i);
        String titleString = stringArrayList.get(i);

        drawerMenuViewHolder.imageView.setImageResource(iconInt);
        drawerMenuViewHolder.textView.setText(titleString);

        drawerMenuViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClickItem(v, drawerMenuViewHolder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class DrawerMenuViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public DrawerMenuViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imvIcon);
            textView = itemView.findViewById(R.id.txtTitle);

        }
    }

}
