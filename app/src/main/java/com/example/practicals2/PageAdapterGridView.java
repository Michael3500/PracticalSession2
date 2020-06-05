package com.example.practicals2;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PageAdapterGridView extends BaseAdapter {

    private Context myContext;
    public List<PostClass> myPosts;

    public PageAdapterGridView(Context context, List<PostClass> posts)
    {
        this.myContext = context;
        this.myPosts = posts;
    }

    @Override
    public int getCount() {
        return myPosts.size();
    }

    @Override
    public Object getItem(int position) {
        return myPosts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View gridView = convertView;

        if(convertView == null)
        {
            //setting up which layout xml file to show
            LayoutInflater inflater = (LayoutInflater)myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.postimageview, null);
        }

        //showing the images of the records in ImageView
        ImageView imageView = (ImageView)gridView.findViewById(R.id.imageimageview);
        imageView.setImageURI(Uri.parse(myPosts.get(position).imageDirectory));

        //showing the title of the records in TextView
        TextView textView = (TextView)gridView.findViewById(R.id.imagetextView);
        textView.setText(myPosts.get(position).title);

        return gridView;
    }

}
