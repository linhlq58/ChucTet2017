package com.tetstudio.linhlee.chuctet.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mz.A;
import com.tetstudio.linhlee.chuctet.MyApplication;
import com.tetstudio.linhlee.chuctet.R;
import com.tetstudio.linhlee.chuctet.adapter.ListContentAdapter;
import com.tetstudio.linhlee.chuctet.database.DatabaseAdapter;
import com.tetstudio.linhlee.chuctet.models.ContentText;
import com.tetstudio.linhlee.chuctet.utils.Constant;

import java.util.ArrayList;

/**
 * Created by lequy on 1/19/2017.
 */

public class FavoriteActivity extends BaseActivity {
    private DatabaseAdapter db;
    private ListView listFavoriteView;
    private ListContentAdapter adapter;
    private ArrayList<ContentText> listFavorite;
    private ImageView backButton;
    private TextView emptyText;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_favorite;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        A.f(this);
        A.b(this);

        db = MyApplication.getDatabase();
        listFavoriteView = (ListView) findViewById(R.id.list_favorite);
        backButton = (ImageView) findViewById(R.id.back_button);
        emptyText = (TextView) findViewById(R.id.empty_text);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        listFavorite = db.getAllFavorite();

        adapter = new ListContentAdapter(this, listFavorite);
        listFavoriteView.setAdapter(adapter);

        if (listFavorite.size() > 0) {
            emptyText.setVisibility(View.GONE);
        } else {
            emptyText.setVisibility(View.VISIBLE);
        }

        Constant.increaseHitArea(backButton);

        listFavoriteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("message", Html.fromHtml(listFavorite.get(position).getContent()).toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(FavoriteActivity.this, "Đã copy vào clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
