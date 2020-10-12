package com.example.qrtagbarcode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class MyAdapter extends ArrayAdapter<DetailListView> {

    Context context;
    List<DetailListView> arrayListDetail;

    public MyAdapter(@NonNull Context context, List<DetailListView> arrayListEmployee) {
        super(context, R.layout.custom_list_item, arrayListEmployee);

        this.context = context;
        this.arrayListDetail = arrayListEmployee;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item, null, true);

//        TextView tvID = view.findViewById(R.id.txt_id);
        TextView tvName = view.findViewById(R.id.txt_name);

//        tvID.setText(arrayListDetail.get(position).getQRDetail_Id());
        tvName.setText(arrayListDetail.get(position).getQRDetail_Code());

        return view;
    }
}
