package com.ipaulpro.afilechooser;

import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.ipaulpro.afilechooser.utils.FileUtils;

import net.zhuoweizhang.mcpelauncher.pro.R;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;


public class FileChooserActivity extends ListActivity {
    private static final String BREADCRUMB = "breadcrumb";
    private static final boolean DEBUG = true;
    private static final String HIDDEN_PREFIX = ".";
    public static final String MIME_TYPE_ALL = "*/*";
    private static final String PATH = "path";
    private static final String POSTIION = "position";
    public static final int REQUEST_CODE = 6384;
    private static final String TAG = "ChooserActivity";
    private File mExternalDir;
    private String mPath;
    private ArrayList<String> mBreadcrumb = new ArrayList<>();
    private boolean mExternalStorageAvailable = false;
    private boolean mExternalStorageWriteable = false;
    private final ArrayList<File> mList = new ArrayList<>();
    private final Set<String> extendedMimeTypes = new HashSet<>();
    private final FileFilter mFileFilter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            String fileName = file.getName();
            String mimeType = FileUtils.getMimeType(FileChooserActivity.this, file);
            return file.isFile() && !fileName.startsWith(FileChooserActivity.HIDDEN_PREFIX) && (mimeType.equals(FileChooserActivity.this.getIntent().getType()) || FileChooserActivity.this.extendedMimeTypes.contains(mimeType));
        }
    };
    private final FileFilter mDirFilter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            String fileName = file.getName();
            return file.isDirectory() && !fileName.startsWith(FileChooserActivity.HIDDEN_PREFIX);
        }
    };
    private Comparator<File> mComparator = new Comparator<File>() { 
        @Override
        public int compare(File f1, File f2) {
            return f1.getName().toLowerCase().compareTo(f2.getName().toLowerCase());
        }
    };
    private final Comparator<File> mLastModifiedComparator = new Comparator<File>() {
        @Override
        public int compare(File f1, File f2) {
            long a = f1.lastModified();
            long b = f2.lastModified();
            if (a == b) {
                return 0;
            }
            return a < b ? 1 : -1;
        }
    };
    private final BroadcastReceiver mExternalStorageReceiver = new BroadcastReceiver() { // from class: com.ipaulpro.afilechooser.FileChooserActivity.5
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(FileChooserActivity.TAG, "External storage broadcast recieved: " + intent.getData());
            FileChooserActivity.this.updateExternalStorageState();
        }
    };

    protected boolean isIntentGetContent() {
        Intent intent = getIntent();
        String action = intent.getAction();
        Log.d(TAG, "Intent Action: " + action);
        return "android.intent.action.GET_CONTENT".equals(action);
    }

    protected void showFileChooser(String title, String type) {
        if (TextUtils.isEmpty(title)) {
            title = getString(R.string.select_file);
        }
        if (TextUtils.isEmpty(type)) {
            type = MIME_TYPE_ALL;
        }
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType(type.toLowerCase());
        intent.addCategory("android.intent.category.OPENABLE");
        try {
            startActivityForResult(Intent.createChooser(intent, title), REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            onFileError(e);
        }
    }

    protected void showFileChooser() {
        showFileChooser(null, null);
    }

    private void fillList(int position) {
        Log.d(TAG, "Current path: " + this.mPath);
        setTitle(this.mPath);
        ((FileListAdapter) getListAdapter()).clear();
        File pathDir = new File(this.mPath);
        File[] dirs = pathDir.listFiles(this.mDirFilter);
        if (dirs != null) {
            Arrays.sort(dirs, this.mComparator);
            Collections.addAll(this.mList, dirs);
        }
        File[] files = pathDir.listFiles(this.mFileFilter);
        if (files != null) {
            Arrays.sort(files, this.mComparator);
            Collections.addAll(this.mList, files);
        }
        if (dirs == null && files == null) {
            Log.d(TAG, "Directory is empty");
        }
        ((FileListAdapter) getListAdapter()).setListItems(this.mList);
        ((FileListAdapter) getListAdapter()).notifyDataSetChanged();
        getListView().setSelection(position);
    }

    private void updateBreadcrumb(boolean add) {
        if (add) {
            this.mBreadcrumb.add(this.mPath);
            return;
        }
        if (this.mExternalDir.getAbsolutePath().equals(this.mPath)) {
            onFileSelectCancel();
            finish();
            return;
        }
        int size = this.mBreadcrumb.size();
        if (size > 1) {
            this.mBreadcrumb.remove(size - 1);
            this.mPath = this.mBreadcrumb.get(size - 2);
            fillList(0);
        }
    }
    
    private void updateExternalStorageState() {
        String state = Environment.getExternalStorageState();
        if ("mounted".equals(state)) {
            this.mExternalStorageWriteable = true;
            this.mExternalStorageAvailable = true;
        } else if ("mounted_ro".equals(state)) {
            this.mExternalStorageAvailable = true;
            this.mExternalStorageWriteable = false;
        } else {
            this.mExternalStorageWriteable = false;
            this.mExternalStorageAvailable = false;
        }
        handleExternalStorageState(this.mExternalStorageAvailable, this.mExternalStorageWriteable);
    }

    private void startWatchingExternalStorage() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.MEDIA_MOUNTED");
        filter.addAction("android.intent.action.MEDIA_REMOVED");
        registerReceiver(this.mExternalStorageReceiver, filter);
        if (isIntentGetContent()) {
            updateExternalStorageState();
        }
    }

    private void stopWatchingExternalStorage() {
        unregisterReceiver(this.mExternalStorageReceiver);
    }

    private void handleExternalStorageState(boolean available, boolean writeable) {
        if (!available && isIntentGetContent()) {
            Log.d(TAG, "External Storage was disconnected");
            onFileDisconnect();
            finish();
        }
    }

    protected void onFileSelect(File file) {
        Log.d(TAG, "File selected: " + file.getAbsolutePath());
    }

    protected void onFileError(Exception e) {
        Log.e(TAG, "Error selecting file", e);
    }

    protected void onFileSelectCancel() {
        Log.d(TAG, "File selection canceled");
    }

    protected void onFileDisconnect() {
        Log.d(TAG, "External storage disconnected");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] extraMimeTypes = getIntent().getStringArrayExtra(FileUtils.EXTRA_MIME_TYPES);
        this.extendedMimeTypes.clear();
        if (extraMimeTypes != null) {
            Collections.addAll(this.extendedMimeTypes, extraMimeTypes);
        }
        this.mExternalDir = Environment.getExternalStorageDirectory();
        String sortMethod = getIntent().getStringExtra(FileUtils.EXTRA_SORT_METHOD);
        if (sortMethod != null && sortMethod.equals(FileUtils.SORT_LAST_MODIFIED)) {
            this.mComparator = this.mLastModifiedComparator;
        }
        if (getListAdapter() == null) {
            setListAdapter(new FileListAdapter(this));
        }
        if (savedInstanceState != null) {
            restoreMe(savedInstanceState);
            return;
        }
        this.mPath = this.mExternalDir.getAbsolutePath();
        String startPath = getIntent().getStringExtra("startPath");
        if (startPath != null) {
            this.mPath = startPath;
        }
        updateBreadcrumb(true);
        if (isIntentGetContent()) {
            setContentView(R.layout.explorer);
            fillList(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startWatchingExternalStorage();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopWatchingExternalStorage();
    }

    @Override
    public void onBackPressed() {
        updateBreadcrumb(false);
    }

    @Override // android.app.ListActivity
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        File file = this.mList.get(position);
        this.mPath = file.getAbsolutePath();
        Log.d(TAG, "Selected file: " + this.mPath);
        if (file.isDirectory()) {
            updateBreadcrumb(true);
            fillList(0);
        } else {
            Intent data = new Intent();
            data.setData(Uri.fromFile(file));
            setResult(-1, data);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE /* 6384 */:
                if (resultCode == -1) {
                    try {
                        Uri uri = data.getData();
                        File file = new File(FileUtils.getPath(this, uri));
                        onFileSelect(file);
                        break;
                    } catch (Exception e) {
                        onFileError(e);
                        break;
                    }
                } else if (resultCode == 0) {
                    onFileSelectCancel();
                    break;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PATH, this.mPath);
        outState.putStringArrayList(BREADCRUMB, this.mBreadcrumb);
        outState.putInt(POSTIION, getListView().getFirstVisiblePosition());
    }

    private void restoreMe(Bundle state) {
        this.mPath = state.containsKey(PATH) ? state.getString(PATH) : this.mExternalDir.getAbsolutePath();
        this.mBreadcrumb = state.getStringArrayList(BREADCRUMB);
        fillList(state.getInt(POSTIION));
    }
}
