package com.example.qrtagbarcode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterPDF extends ArrayAdapter<PdfReportList> {

    Context context;
    List<PdfReportList> arrayListDetail;

    public MyAdapterPDF(@NonNull Context context, ArrayList<PdfReportList> arrayListEmployee) {
        super(context, R.layout.pdf_list_item, arrayListEmployee);

        this.context = context;
        this.arrayListDetail = arrayListEmployee;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_list_item, null, true);

        TextView PDF_Text = view.findViewById(R.id.txt_PDF);

        PDF_Text.setText(arrayListDetail.get(position).PDFReport());

        return view;
    }
}
