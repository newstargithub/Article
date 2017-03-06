package com.halo.article;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.halo.article.presenter.BookmarksPresenter;
import com.halo.article.presenter.ZhihuDailyPresenter;
import com.halo.article.ui.fragment.BookmarksFragment;
import com.halo.article.ui.fragment.DetailFragment;
import com.halo.article.ui.fragment.ZhihuDailyFragment;
import com.halo.article.util.ActivityUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String ACTION_BOOKMARKS = "com.halo.article.bookmarks";
    private static final String BOOKMARKS_FRAGMENT_TAG = "BOOKMARKS_FRAGMENT_TAG";
    private static final  String ZHIHU_DAILY_FRAGMENT_TAG = "ZHIHU_DAILY_FRAGMENT_TAG";
    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private FloatingActionButton mFab;
    private ZhihuDailyFragment mainFragment;
    private BookmarksFragment bookmarksFragment;
    private ZhihuDailyPresenter mZhihuDailyPresenter;
    private BookmarksPresenter mBookmarksPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        mainFragment =
                (ZhihuDailyFragment) getSupportFragmentManager().findFragmentByTag(ZHIHU_DAILY_FRAGMENT_TAG);
        if (mainFragment == null) {
            mainFragment = ZhihuDailyFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mainFragment, R.id.frame_layout, ZHIHU_DAILY_FRAGMENT_TAG);
        }
        mZhihuDailyPresenter = new ZhihuDailyPresenter(getApplicationContext(), mainFragment);

        bookmarksFragment =
                (BookmarksFragment) getSupportFragmentManager().findFragmentByTag(BOOKMARKS_FRAGMENT_TAG);
        if (bookmarksFragment == null) {
            bookmarksFragment = BookmarksFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    bookmarksFragment, R.id.frame_layout, BOOKMARKS_FRAGMENT_TAG);
        }
        mBookmarksPresenter = new BookmarksPresenter(getApplicationContext(), bookmarksFragment);

        String action = getIntent().getAction();
        if (action.equals(ACTION_BOOKMARKS)) {
            navigationView.setCheckedItem(R.id.nav_bookmarks);
            showBookmarksFragment();
        } else {
            navigationView.setCheckedItem(R.id.nav_home);
            showMainFragment();
        }
    }

    private void showBookmarksFragment() {
        ActivityUtils.showFragmentToActivity(getSupportFragmentManager(), bookmarksFragment, mainFragment);
        toolbar.setTitle(getResources().getString(R.string.nav_bookmarks));
    }

    private void showMainFragment() {
        ActivityUtils.showFragmentToActivity(getSupportFragmentManager(), mainFragment, bookmarksFragment);
        toolbar.setTitle(getResources().getString(R.string.zhihu_daily));
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFab = (FloatingActionButton) findViewById(R.id.fab);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            showMainFragment();
        } else if (id == R.id.nav_bookmarks) {
            showBookmarksFragment();
        } else if (id == R.id.nav_change_theme) {
            changeTheme();
        }
        /*else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeTheme() {
        // change the day/night mode after the drawer closed
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                SharedPreferences sp =  getSharedPreferences("user_settings",MODE_PRIVATE);
                if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                        == Configuration.UI_MODE_NIGHT_YES) {
                    sp.edit().putInt("theme", 0).apply();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    sp.edit().putInt("theme", 1).apply();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                recreate();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }
}
