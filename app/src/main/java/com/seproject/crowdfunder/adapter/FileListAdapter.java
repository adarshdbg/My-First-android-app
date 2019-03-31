package com.seproject.crowdfunder.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seproject.crowdfunder.R;
import com.seproject.crowdfunder.models.File;
import com.seproject.crowdfunder.ui.DocumentUpload;

import java.io.IOException;
import java.util.ArrayList;

public class FileListAdapter extends ArrayAdapter<File> implements View.OnClickListener{

    private ArrayList<File> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView fileName;
        ImageView image;
    }

    public FileListAdapter(ArrayList<File> data, Context context) {
        super(context, R.layout.document_item_layout, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

//        int position=(Integer) v.getTag();
//        Object object= getItem(position);
//        DataModel dataModel=(DataModel)object;
//
//        switch (v.getId())
//        {
//            case R.id.item_info:
//                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
    }

    private int lastPosition = -1;

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        File file = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag


        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.document_item_layout, parent, false);
            viewHolder.fileName = (TextView) convertView.findViewById(R.id.fileName);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.fileThumbnail);
            viewHolder.fileName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataSet.remove(position);
                    DocumentUpload.files.remove(position);
                    DocumentUpload.adapter.notifyDataSetChanged();

                }
            });


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        assert file != null;
        viewHolder.fileName.setText(file.getFilename());
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), dataSet.get(position).getPath());
            viewHolder.image.setImageBitmap(bitmap);
            viewHolder.fileName.setText(dataSet.get(position).getFilename());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        return convertView;
    }
}