package njoize.dai_ka.com.demotestprint;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private Context context;
    private ArrayList<String> categoryStringArrayList;
    private OnClickItem onClickItem;
    private LayoutInflater layoutInflater;

    public CategoryAdapter(Context context, ArrayList<String> categoryStringArrayList, OnClickItem onClickItem) {
        this.layoutInflater = LayoutInflater.from(context);
        this.categoryStringArrayList = categoryStringArrayList;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = layoutInflater.inflate(R.layout.recycler_category, viewGroup, false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);

        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder categoryViewHolder, int i) {

        String categoryString = categoryStringArrayList.get(i);

        categoryViewHolder.textView.setText(categoryString);
        categoryViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClickItem(v, categoryViewHolder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryStringArrayList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.txtcategory);

        }
    }
}
