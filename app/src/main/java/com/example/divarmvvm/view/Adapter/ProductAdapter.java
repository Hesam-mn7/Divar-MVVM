package com.example.divarmvvm.view.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.divarmvvm.R;
import com.example.divarmvvm.databinding.ListItemsBinding;
import com.example.divarmvvm.model.room.entity.Product;
import com.example.divarmvvm.view.DetailActivity;
import com.example.divarmvvm.viewModel.ProductViewModel;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> implements Filterable {

    private ProductViewModel viewModel;
    private List<Product> list;

    private List<Product> temp;
    Context context;


    public ProductAdapter(ProductViewModel productViewModel , Context context) {
        this.list = new ArrayList<>();
        this.viewModel = productViewModel;
        this.context = context;

        this.temp = new ArrayList<>();
    }

    public void setList(List<Product> list) {
        this.list = list;
        this.temp = list;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemsBinding binding = ListItemsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(ProductAdapter.MyViewHolder holder, int position) {

        Product product = this.list.get(position);

        int id = product.getId();
        String name = product.getName();
        String value = product.getValue();
        String time = product.getTime();
        String imgId = product.getImgId();
        String numberPhone = product.getNumberPhone();
        String details = product.getDetails();

        holder.binding.tvname.setText(name);
        holder.binding.tvvalue.setText(value);
        holder.binding.tvtime.setText(time);

        Uri uri = Uri.parse(imgId);
        Picasso.get()
                .load(uri)
                .into(holder.binding.imgProduct);

        holder.binding.listItemm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);

                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("value", value);
                intent.putExtra("time", time);
                intent.putExtra("imgId", imgId);
                intent.putExtra("numberPhone", numberPhone);
                intent.putExtra("details", details);

                context.startActivity(intent);
            }
        });
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ListItemsBinding binding;

        public MyViewHolder(ListItemsBinding itemView) {
            super(itemView.getRoot());

            binding = itemView;

        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence fiterRequest) {
                FilterResults filterResults = new FilterResults();

                List<Product> filterlist = new ArrayList<>();
                for (Product item : temp) {
                    if (item.getName().contains(fiterRequest)) {
                        filterlist.add(item);
                    }

                }

                filterResults.values = filterlist;
                filterResults.count = filterlist.size();

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                list = (List<Product>) results.values;
                notifyDataSetChanged();


            }
        };
    }
}


