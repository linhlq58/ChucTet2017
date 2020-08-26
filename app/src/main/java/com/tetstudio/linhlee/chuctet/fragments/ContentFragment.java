package com.tetstudio.linhlee.chuctet.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tetstudio.linhlee.chuctet.R;
import com.tetstudio.linhlee.chuctet.adapter.ListContentAdapter;
import com.tetstudio.linhlee.chuctet.models.ContentText;

import java.util.ArrayList;

/**
 * Created by lequy on 1/17/2017.
 */

public class ContentFragment extends BaseFragment {
    private static String LIST_KEY = "list_content";

    private ListView listContentView;
    private ListContentAdapter listContentAdapter;
    private ArrayList<ContentText> listContent;

    public static ContentFragment newInstance(ArrayList<ContentText> listContent) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(LIST_KEY, listContent);

        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listContent = getArguments().getParcelableArrayList(LIST_KEY);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_content;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {
        listContentView = (ListView) rootView.findViewById(R.id.list_content);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        listContentAdapter = new ListContentAdapter(getActivity(), listContent);
        listContentView.setAdapter(listContentAdapter);
        listContentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("message", Html.fromHtml(listContent.get(position).getContent()).toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getActivity(), "Đã copy vào clipboard", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
