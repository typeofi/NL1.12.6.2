package com.ipaulpro.afilechooser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.zhuoweizhang.mcpelauncher.pro.R;

import java.io.File;
import java.util.ArrayList;

public class FileListAdapter extends BaseAdapter {
    private ArrayList<File> mFiles = new ArrayList<>();
    private final LayoutInflater mInflater;
    private static final int ICON_FOLDER = R.drawable.ic_folder;
    private static final int ICON_FILE = R.drawable.ic_file;

    public FileListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setListItems(ArrayList<File> files) {
        this.mFiles = files;
    }

    @Override
    public int getCount() {
        return this.mFiles.size();
    }

    public void add(File file) {
        this.mFiles.add(file);
    }

    public void clear() {
        this.mFiles.clear();
    }

    @Override
    public Object getItem(int position) {
        return this.mFiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View row = convertView;
        if (row == null) {
            row = this.mInflater.inflate(R.layout.file, parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        File file = (File) getItem(position);
        holder.nameView.setText(file.getName());
        holder.iconView.setImageResource(file.isDirectory() ? ICON_FOLDER : ICON_FILE);
        return row;
    }

    static class ViewHolder {
        ImageView iconView;
        TextView nameView;

        ViewHolder(View row) {
            this.nameView = row.findViewById(R.id.file_name);
            this.iconView = row.findViewById(R.id.file_icon);
        }
    }
}
