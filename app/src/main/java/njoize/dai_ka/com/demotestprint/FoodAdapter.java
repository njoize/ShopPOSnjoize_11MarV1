package njoize.dai_ka.com.demotestprint;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{

    private Context context;
    private ArrayList<String> foodStringArrayList, priceStringArrayList;
    private OnClickItem onClickItem;
    private LayoutInflater layoutInflater;

    public FoodAdapter(Context context, ArrayList<String> foodStringArrayList, ArrayList<String> priceStringArrayList, OnClickItem onClickItem) {
        this.layoutInflater = LayoutInflater.from(context);
        this.foodStringArrayList = foodStringArrayList;
        this.priceStringArrayList = priceStringArrayList;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = layoutInflater.inflate(R.layout.recycler_food, viewGroup, false);
        FoodViewHolder foodViewHolder = new FoodViewHolder(view);

        return foodViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodViewHolder foodViewHolder, int i) {

        String foodString = foodStringArrayList.get(i);
        String priceString = priceStringArrayList.get(i);

        foodViewHolder.foodTextView.setText(foodString);
        foodViewHolder.priceTextView.setText(priceString);

        foodViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClickItem(v, foodViewHolder.getAdapterPosition());
            }
        });


    }

    @Override
    public int getItemCount() {
        return foodStringArrayList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {

        private TextView foodTextView, priceTextView;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            foodTextView = itemView.findViewById(R.id.txtFood);
            priceTextView = itemView.findViewById(R.id.txtPrice);

        }
    }
}
