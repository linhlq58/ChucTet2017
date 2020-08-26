package com.tetstudio.linhlee.chuctet.adapter;

import android.app.Activity;
import android.content.Intent;
import android.provider.Telephony;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tetstudio.linhlee.chuctet.MyApplication;
import com.tetstudio.linhlee.chuctet.R;
import com.tetstudio.linhlee.chuctet.database.DatabaseAdapter;
import com.tetstudio.linhlee.chuctet.models.ContentText;
import com.tetstudio.linhlee.chuctet.utils.Constant;

import java.util.ArrayList;

/**
 * Created by lequy on 1/18/2017.
 */

public class ListContentAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<ContentText> listContent;
    private String shareBody;
    private DatabaseAdapter db;

    public ListContentAdapter(Activity context, ArrayList<ContentText> listContent) {
        this.context = context;
        this.listContent = listContent;
        db = MyApplication.getDatabase();
    }

    @Override
    public int getCount() {
        return listContent.size();
    }

    @Override
    public Object getItem(int position) {
        return listContent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = context.getLayoutInflater().inflate(R.layout.item_content, parent, false);
        }

        TextView content = (TextView) convertView.findViewById(R.id.content_text);
        ImageView shareFb = (ImageView) convertView.findViewById(R.id.share_fb_button);
        ImageView shareMess = (ImageView) convertView.findViewById(R.id.share_mess_button);
        ImageView shareInbox = (ImageView) convertView.findViewById(R.id.share_inbox_button);
        final ImageView favorite = (ImageView) convertView.findViewById(R.id.favorite);

        content.setText(Html.fromHtml(listContent.get(position).getContent()));
        if (db.isFavorite(listContent.get(position).getId())) {
            favorite.setImageResource(R.mipmap.ic_favorite);
        } else {
            favorite.setImageResource(R.mipmap.ic_favorite_empty);
        }

        shareFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareBody = Html.fromHtml(listContent.get(position).getContent()).toString();
                Constant.initShareIntent(context, "com.facebook.katana", shareBody);
            }
        });
        shareMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareBody = Html.fromHtml(listContent.get(position).getContent()).toString();
                Constant.initShareIntent(context, "com.facebook.orca", shareBody);
            }
        });
        shareInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareBody = Html.fromHtml(listContent.get(position).getContent()).toString();
                Constant.initShareIntent(context, Telephony.Sms.getDefaultSmsPackage(context), shareBody);
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.isFavorite(listContent.get(position).getId())) {
                    favorite.setImageResource(R.mipmap.ic_favorite_empty);
                    db.deleteFavorite(listContent.get(position).getId());
                    Toast.makeText(context, "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                } else {
                    favorite.setImageResource(R.mipmap.ic_favorite);
                    db.addFavorite(listContent.get(position));
                    Toast.makeText(context, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }
}
