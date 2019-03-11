package njoize.dai_ka.com.demotestprint;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BillDetailAdapter extends RecyclerView.Adapter<BillDetailAdapter.BillDetailViewHolder> {

    private Context context;
    private ArrayList<String> nameStringArrayList,
            detailStringArrayList, amountStringsArrayList, billStringArrayList,
            priceStringArrayList;
    private LayoutInflater layoutInflater;

    public BillDetailAdapter(Context context, ArrayList<String> nameStringArrayList, ArrayList<String> detailStringArrayList, ArrayList<String> amountStringsArrayList, ArrayList<String> billStringArrayList, ArrayList<String> priceStringArrayList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.nameStringArrayList = nameStringArrayList;
        this.detailStringArrayList = detailStringArrayList;
        this.amountStringsArrayList = amountStringsArrayList;
        this.billStringArrayList = billStringArrayList;
        this.priceStringArrayList = priceStringArrayList;
    }

    @NonNull
    @Override
    public BillDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = layoutInflater.inflate(R.layout.recycler_view_bill_detail, viewGroup, false);
        BillDetailViewHolder billDetailViewHolder = new BillDetailViewHolder(view);

        return billDetailViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BillDetailViewHolder billDetailViewHolder, int i) {

        String nameString = nameStringArrayList.get(i);
        String detailString = detailStringArrayList.get(i);
        String amountString = amountStringsArrayList.get(i);
        String billString = billStringArrayList.get(i);
        String priceString = priceStringArrayList.get(i);

        billDetailViewHolder.nameTextView.setText(nameString);
        billDetailViewHolder.detailTextView.setText(detailString);
        billDetailViewHolder.amountTextView.setText(amountString);
        billDetailViewHolder.billTextView.setText(billString);
        billDetailViewHolder.priceTextView.setText(priceString);


    }

    @Override
    public int getItemCount() {
        return nameStringArrayList.size();
    }

    class BillDetailViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView, detailTextView, amountTextView, billTextView, priceTextView;


        public BillDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.txtName);
            detailTextView = itemView.findViewById(R.id.txtDetail);
            amountTextView = itemView.findViewById(R.id.txtAmount);
            billTextView = itemView.findViewById(R.id.txtBill);
            priceTextView = itemView.findViewById(R.id.txtPrice);


        }
    } // BillDetailViewHolder Class

} // Main Class
