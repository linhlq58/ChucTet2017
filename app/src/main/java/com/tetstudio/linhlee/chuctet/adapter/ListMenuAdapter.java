package com.tetstudio.linhlee.chuctet.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tetstudio.linhlee.chuctet.R;
import com.tetstudio.linhlee.chuctet.models.MenuItem;

import java.util.ArrayList;

/**
 * Created by lequy on 1/17/2017.
 */

public class ListMenuAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<MenuItem> listMenu;

    public ListMenuAdapter(Activity context, ArrayList<MenuItem> listMenu) {
        this.context = context;
        this.listMenu = listMenu;
    }

    @Override
    public int getCount() {
        return listMenu.size();
    }

    @Override
    public Object getItem(int position) {
        return listMenu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = context.getLayoutInflater().inflate(R.layout.item_menu, parent, false);
        }

        ImageView menuImg = (ImageView) convertView.findViewById(R.id.menu_img);
        TextView menuName = (TextView) convertView.findViewById(R.id.menu_name);
        menuImg.setImageResource(listMenu.get(position).getImgRes());
        menuName.setText(listMenu.get(position).getName());

        return convertView;
    }
}
