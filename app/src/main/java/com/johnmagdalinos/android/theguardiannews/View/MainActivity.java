package com.johnmagdalinos.android.theguardiannews.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.johnmagdalinos.android.theguardiannews.Presenter.NewsPresenter;
import com.johnmagdalinos.android.theguardiannews.R;

public class MainActivity extends AppCompatActivity {

    /** Member variables */
    private DrawerLayout mDrawerLayout;
    private NewsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new NewsPresenter();

        // Setup the navigation drawer
        NavigationView navigationView = findViewById(R.id.nv_main_drawer);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                loadFragment(item.getItemId());
                return true;
            }
        });
        mDrawerLayout = findViewById(R.id.dl_main_drawer);
    }

    private void loadFragment(int id) {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

        String section;
        switch (id) {
            case R.id.menu_business:
                section = getString(R.string.menu_business);
                break;
            case R.id.menu_culture:
                section = getString(R.string.menu_culture);
                break;
            case R.id.menu_environment:
                section = getString(R.string.menu_environment);
                break;
            case R.id.menu_lifeandstyle:
                section = getString(R.string.menu_life_style);
                break;
            case R.id.menu_politics:
                section = getString(R.string.menu_politics);
                break;
            case R.id.menu_science:
                section = getString(R.string.menu_science);
                break;
            case R.id.menu_sport:
                section = getString(R.string.menu_sport);
                break;
            case R.id.menu_travel:
                section = getString(R.string.menu_travel);
                break;
            default:
                section = getString(R.string.menu_travel);
                break;
        }

        NewsFragment fragment = NewsFragment.newInstance(section);
        fm.beginTransaction()
                .replace(R.id.fl_main_content, fragment)
                .commit();

        fragment.setPresenter(mPresenter);

        mDrawerLayout.closeDrawer(GravityCompat.START);
    }
}
