package com.example.practicals2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class TabScience extends Fragment {

    PageAdapterGridView imagesAdapter;
    GridView myGridView;
    List<PostClass> storedPosts;
    String tabName = "Science";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //state which layout file to pull up and apply
        View view = inflater.inflate(R.layout.frag_science, container, false);

        //return all records that have the same section as the name of the tab
        storedPosts = PostClass.getAllPosts(tabName);

        //setting up the connection of ImageViews of the returned records in a GridView
        myGridView = (GridView)view.findViewById(R.id.gridviewscience);
        imagesAdapter = new PageAdapterGridView(getContext(), storedPosts);

        //joining and connecting the ImageView and GridView
        myGridView.setAdapter(imagesAdapter);

        return view;
    }
}
