package com.tetstudio.linhlee.chuctet.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mz.A;
import com.mz.ZAndroidSystemDK;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.tetstudio.linhlee.chuctet.MyApplication;
import com.tetstudio.linhlee.chuctet.R;
import com.tetstudio.linhlee.chuctet.adapter.ListMenuAdapter;
import com.tetstudio.linhlee.chuctet.adapter.MyPagerAdapter;
import com.tetstudio.linhlee.chuctet.database.DatabaseAdapter;
import com.tetstudio.linhlee.chuctet.fragments.ContentFragment;
import com.tetstudio.linhlee.chuctet.models.MenuItem;
import com.tetstudio.linhlee.chuctet.utils.Constant;
import com.tetstudio.linhlee.chuctet.utils.NonSwipeableViewPager;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private DatabaseAdapter db;
    private DrawerLayout drawer;
    private View shadowView;
    private ListView listMenuView;
    private ListMenuAdapter listMenuAdapter;
    private ArrayList<MenuItem> listMenu;
    private TextView titleText;
    private ImageView favoriteButton;

    private TabLayout mTabLayout;
    private ViewPager mPager;
    private ArrayList<Fragment> mListFragment;
    private MyPagerAdapter mPagerAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            } else {
                ZAndroidSystemDK.init(this);
                A.f(this);
                A.b(this);
            }
        } else {
            ZAndroidSystemDK.init(this);
            A.f(this);
            A.b(this);
        }

        db = MyApplication.getDatabase();
        shadowView = findViewById(R.id.shadow_view);
        titleText = (TextView) findViewById(R.id.title_text);
        favoriteButton = (ImageView) findViewById(R.id.favorite_button);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mPager = (ViewPager) findViewById(R.id.pager);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        listMenu = new ArrayList<>();
        listMenu.add(new MenuItem(R.mipmap.sms_hay, "SMS Hay"));
        listMenu.add(new MenuItem(R.mipmap.loi_chuc, "Lời Chúc"));
        listMenu.add(new MenuItem(R.mipmap.cau_doi, "Thơ - Câu Đối"));
        listMenu.add(new MenuItem(R.mipmap.sms_english, "SMS Tiếng Anh"));
        listMenu.add(new MenuItem(R.mipmap.sms_hot, "SMS HOT"));
        listMenu.add(new MenuItem(R.mipmap.gia_dinh, "Chúc Gia Đình"));
        listMenu.add(new MenuItem(R.mipmap.ban_be, "Chúc Bạn Bè"));
        listMenu.add(new MenuItem(R.mipmap.dong_nghiep, "Chúc Đồng Nghiệp"));
        listMenu.add(new MenuItem(R.mipmap.nguoi_yeu, "Chúc Người Yêu"));

        createTabLayout();

        shadowView.setOnClickListener(this);
        favoriteButton.setOnClickListener(this);
    }

    private void createDrawerLayout() {
        // Create Navigation Drawer layout
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        listMenuView = (ListView) findViewById(R.id.list_menu);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        listMenuAdapter = new ListMenuAdapter(this, listMenu);
        listMenuView.setAdapter(listMenuAdapter);

        listMenuView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawer.closeDrawers();
                mPager.setCurrentItem(position);
                titleText.setText(listMenu.get(position).getName());
            }
        });
    }

    private void createTabLayout() {
        mListFragment = new ArrayList<>();
        for (int i = 0; i < listMenu.size(); i++) {
            mListFragment.add(ContentFragment.newInstance(db.getContentTextByCategory(i + 1)));
        }

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), mListFragment);

        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(5);
        mPager.setCurrentItem(0);

        mTabLayout.setupWithViewPager(mPager);

        for (int i = 0; i < listMenu.size(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(createTabView(listMenu.get(i).getImgRes()));
        }
        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        mTabLayout.getTabAt(0).getCustomView().setSelected(true);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                titleText.setText(listMenu.get(position).getName());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private View createTabView(int imgRes) {
        View view = LayoutInflater.from(this).inflate(R.layout.tabs_icon, null);

        ImageView image = (ImageView) view.findViewById(R.id.tab_image);
        image.setImageResource(imgRes);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shadow_view:
                shadowView.setVisibility(View.GONE);
                break;
            case R.id.favorite_button:
                startActivity(FavoriteActivity.class);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    ZAndroidSystemDK.init(this);
                    A.f(this);
                    A.b(this);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
