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

public class BillRecyclerViewAdapter extends RecyclerView.Adapter<BillRecyclerViewAdapter.BillViewHolder> {

    private Context context;
    private ArrayList<String>
            zoneStringArrayList,
            deskStringArrayList,
            detailLine1StringArrayList,
            detailLine2StringArrayList,
            detailLine3StringArrayList,
            bgColorStringArrayList;
    private LayoutInflater layoutInflater;
    private OnClickItem onClickItem;

    private int[] ints = new int[]{R.drawable.s1, R.drawable.s2, R.drawable.s3};


    public BillRecyclerViewAdapter(Context context,
                                   ArrayList<String> zoneStringArrayList,
                                   ArrayList<String> deskStringArrayList,
                                   ArrayList<String> detailLine1StringArrayList,
                                   ArrayList<String> detailLine2StringArrayList,
                                   ArrayList<String> detailLine3StringArrayList,
                                   ArrayList<String> bgColorStringArrayList,
                                   OnClickItem onClickItem) {
        this.layoutInflater = LayoutInflater.from(context);
        this.zoneStringArrayList = zoneStringArrayList;
        this.deskStringArrayList = deskStringArrayList;
        this.detailLine1StringArrayList = detailLine1StringArrayList;
        this.detailLine2StringArrayList = detailLine2StringArrayList;
        this.detailLine3StringArrayList = detailLine3StringArrayList;
        this.bgColorStringArrayList = bgColorStringArrayList;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = layoutInflater.inflate(R.layout.recycler_view_bill, viewGroup, false);
        BillViewHolder billViewHolder = new BillViewHolder(view);


        return billViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BillViewHolder billViewHolder, int i) {

        String zoneString = zoneStringArrayList.get(i);
        String deskString = deskStringArrayList.get(i);
        String detailLine1String = detailLine1StringArrayList.get(i);
        String detailLine2String = detailLine2StringArrayList.get(i);
        String detailLine3String = detailLine3StringArrayList.get(i);
        String bgString = bgColorStringArrayList.get(i);
        int index = Integer.parseInt(bgString.trim());

        billViewHolder.zoneTextView.setText(zoneString);
        billViewHolder.deskTextView.setText(deskString);
        billViewHolder.detailline1TextView.setText(detailLine1String);
        billViewHolder.detailline2TextView.setText(detailLine2String);
        billViewHolder.detailline3TextView.setText(detailLine3String);
        billViewHolder.imageView.setImageResource(ints[index]);


        billViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClickItem(v, billViewHolder.getAdapterPosition());
            }
        });


    }

    @Override
    public int getItemCount() {
        return detailLine1StringArrayList.size();
    }

    public class BillViewHolder extends RecyclerView.ViewHolder {

        private TextView zoneTextView,
                deskTextView,
                detailline1TextView,
                detailline2TextView,
                detailline3TextView;

        private ImageView imageView;


        public BillViewHolder(@NonNull View itemView) {
            super(itemView);

            zoneTextView = itemView.findViewById(R.id.txtZone);
            deskTextView = itemView.findViewById(R.id.txtDesk);
            detailline1TextView = itemView.findViewById(R.id.txtDetail1);
            detailline2TextView = itemView.findViewById(R.id.txtDetail2);
            detailline3TextView = itemView.findViewById(R.id.txtDetail3);
            imageView = itemView.findViewById(R.id.imvBG);

        }
    } // BillViewHolder Class


} // Main Class
