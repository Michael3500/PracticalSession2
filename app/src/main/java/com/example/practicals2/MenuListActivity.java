package com.example.practicals2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuListActivity extends ListActivity {

    String[] menu = {"Portal","Settings", "Help","About"};
    ListView l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        //String[] menu = getResources().getStringArray(R.array.menu);
        l = getListView();

        //connecting the string array with the List of items
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, menu);
        l.setAdapter(myAdapter);
    }

    //accessed whenever one of the list items is clicked on
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        TextView temp = (TextView) v;
        Toast.makeText(this, ""+temp.getText()+" "+position,Toast.LENGTH_SHORT).show();
        switch(position)
        {
            //redirect to the main/home activity if "Portal" is selected
            case 0: Intent postintent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(postintent);
        }

    }

}
