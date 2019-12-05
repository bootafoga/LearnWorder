package com.bootafoga.learnworderapp;

import android.content.Intent;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DictionaryActivity extends AppCompatActivity {

    private DatabaseConnection connect;

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(this, DictionaryActivity.class);
        intent.putExtra("num_page", 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        getSupportActionBar().setHomeButtonEnabled(true);

        connect = new DatabaseConnection(this);

        int num_page;
        if (getIntent().getExtras() == null ) num_page = 0;
        else num_page = getIntent().getExtras().getInt("num_page");

        SectionsPagerAdapter2 pagerAdapter = new SectionsPagerAdapter2(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(num_page);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

    }

    public void onClickAdd(View view) {
        Intent intent = new Intent(this, AddWordActivity.class);
        startActivity(intent);
    }

    private class SectionsPagerAdapter2 extends FragmentPagerAdapter {
        public SectionsPagerAdapter2(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            DictionaryFragment frag = new DictionaryFragment();
            switch (position) {
                case 0:
                    bundle.putString("type", "not_learned");
                    frag.setArguments(bundle);
                    return frag;
                case 1:
                    frag.setArguments(bundle);
                    bundle.putString("type", "learned");
                    return frag;
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.not_learned) + " (" + connect.getCountNotLearned() + ")";
                case 1:
                    return getResources().getText(R.string.learned) + " (" + connect.getCountLearned() + ")";
            }
            return null;
        }
    }
}