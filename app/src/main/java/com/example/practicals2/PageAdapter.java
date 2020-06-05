package com.example.practicals2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {

    public int tabcount;

    public PageAdapter(@NonNull FragmentManager fm, int tabcount) {
        super(fm);
        this.tabcount = tabcount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        { //checks which tab is currently selected and shows its corresponding fragment
            case 0: TabWorld tabworld = new TabWorld();
                    return tabworld;
            case 1: TabBusiness tabbusiness = new TabBusiness();
                    return tabbusiness;
            case 2: TabTechnology tabtechnology = new TabTechnology();
                    return tabtechnology;
            case 3: TabScience tabscience = new TabScience();
                    return tabscience;
            case 4: TabSports tabsports = new TabSports();
                    return tabsports;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}