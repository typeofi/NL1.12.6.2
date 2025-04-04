package com.ipaulpro.afilechooser.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.ipaulpro.afilechooser.FileChooserActivity;

import net.zhuoweizhang.mcpelauncher.api.modpe.EnchantType;
import net.zhuoweizhang.mcpelauncher.pro.R;

import java.io.File;
import java.io.FileFilter;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileUtils {
    private static final boolean DEBUG = false;
    public static final String EXTRA_MIME_TYPES = "net.zhuoweizhang.afilechooser.extra.MIME_TYPES";
    public static final String EXTRA_SORT_METHOD = "net.zhuoweizhang.afilechooser.extra.SORT_METHOD";
    private static final String HIDDEN_PREFIX = ".";
    public static final String MIME_TYPE_APP = "application/*";
    public static final String MIME_TYPE_AUDIO = "audio/*";
    public static final String MIME_TYPE_IMAGE = "image/*";
    public static final String MIME_TYPE_TEXT = "text/*";
    public static final String MIME_TYPE_VIDEO = "video/*";
    public static final String SORT_LAST_MODIFIED = "net.zhuoweizhang.afilechooser.extra.SORT_LAST_MODIFIED";
    static final String TAG = "FileUtils";
    private static final Comparator<File> mComparator = new Comparator<File>() {
        @Override
        public int compare(File f1, File f2) {
            return f1.getName().toLowerCase().compareTo(f2.getName().toLowerCase());
        }
    };
    private static final FileFilter mFileFilter = new FileFilter() {
        @Override // java.io.FileFilter
        public boolean accept(File file) {
            String fileName = file.getName();
            if (!file.isFile() || fileName.startsWith(FileUtils.HIDDEN_PREFIX)) {
                return FileUtils.DEBUG;
            }
            return true;
        }
    };
    private static final FileFilter mDirFilter = new FileFilter() {
        @Override // java.io.FileFilter
        public boolean accept(File file) {
            String fileName = file.getName();
            if (!file.isDirectory() || fileName.startsWith(FileUtils.HIDDEN_PREFIX)) {
                return FileUtils.DEBUG;
            }
            return true;
        }
    };

    public static boolean isLocal(String uri) {
        if (uri == null || uri.startsWith("http://")) {
            return DEBUG;
        }
        return true;
    }

    public static String getExtension(String uri) {
        if (uri == null) {
            return null;
        }
        int dot = uri.lastIndexOf(HIDDEN_PREFIX);
        if (dot >= 0) {
            return uri.substring(dot);
        }
        return "";
    }

    public static boolean isMediaUri(Uri uri) {
        String uriString = uri.toString();
        if (uriString.startsWith(MediaStore.Audio.Media.INTERNAL_CONTENT_URI.toString()) || uriString.startsWith(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString()) || uriString.startsWith(MediaStore.Video.Media.INTERNAL_CONTENT_URI.toString()) || uriString.startsWith(MediaStore.Video.Media.EXTERNAL_CONTENT_URI.toString())) {
            return true;
        }
        return DEBUG;
    }

    public static Uri getUri(File file) {
        if (file != null) {
            return Uri.fromFile(file);
        }
        return null;
    }

    public static File getFile(Uri uri) {
        String filepath;
        if (uri == null || (filepath = uri.getPath()) == null) {
            return null;
        }
        return new File(filepath);
    }

    public static File getPathWithoutFilename(File file) {
        if (file != null) {
            if (!file.isDirectory()) {
                String filename = file.getName();
                String filepath = file.getAbsolutePath();
                String pathwithoutname = filepath.substring(0, filepath.length() - filename.length());
                if (pathwithoutname.endsWith("/")) {
                    pathwithoutname = pathwithoutname.substring(0, pathwithoutname.length() - 1);
                }
                return new File(pathwithoutname);
            }
            return file;
        }
        return null;
    }

    public static File getFile(String curdir, String file) {
        String separator = "/";
        if (curdir.endsWith("/")) {
            separator = "";
        }
        return new File(curdir + separator + file);
    }

    public static File getFile(File curdir, String file) {
        return getFile(curdir.getAbsolutePath(), file);
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            try {
                Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static String getReadableFileSize(int size) {
        DecimalFormat dec = new DecimalFormat("###.#");
        float fileSize = 0.0f;
        String suffix = " KB";
        if (size > 1024) {
            fileSize = (float) size / EnchantType.pickaxe;
            if (fileSize > 1024.0f) {
                fileSize /= 1024.0f;
                if (fileSize > 1024.0f) {
                    fileSize /= 1024.0f;
                    suffix = " GB";
                } else {
                    suffix = " MB";
                }
            }
        }
        return dec.format(fileSize) + suffix;
    }

    private static MimeTypes getMimeTypes(Context context) {
        MimeTypeParser mtp = new MimeTypeParser();
        XmlResourceParser in = context.getResources().getXml(R.xml.mimetypes);
        try {
            return mtp.fromXmlResource(in);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getMimeType(Context context, File file) {
        MimeTypes mimeTypes = getMimeTypes(context);
        if (file == null) {
            return null;
        }
        return mimeTypes.getMimeType(file.getName());
    }

    public static Bitmap getThumbnail(Context context, File file) {
        return getThumbnail(context, getUri(file), getMimeType(context, file));
    }

    public static Bitmap getThumbnail(Context context, Uri uri) {
        return getThumbnail(context, uri, getMimeType(context, getFile(uri)));
    }

    public static Bitmap getThumbnail(Context context, Uri uri, String mimeType) {
        Bitmap bm = null;
        if (isMediaUri(uri)) {
            Log.e(TAG, "You can only retrieve thumbnails for images and videos.");
        } else {
            ContentResolver resolver = context.getContentResolver();
            Cursor cursor = null;
            try {
                Cursor cursor2 = resolver.query(uri, null, null, null, null);
                if (cursor2.moveToFirst()) {
                    int id = cursor2.getInt(0);
                    if (mimeType.contains("video")) {
                        bm = MediaStore.Video.Thumbnails.getThumbnail(resolver, id, 1, null);
                    } else if (mimeType.contains(MIME_TYPE_IMAGE)) {
                        bm = MediaStore.Images.Thumbnails.getThumbnail(resolver, id, 1, null);
                    }
                }
                cursor2.close();
            } catch (Throwable th) {
                throw th;
            }
        }
        return bm;
    }

    public static List<File> getFileList(String path) {
        ArrayList<File> list = new ArrayList<>();
        File pathDir = new File(path);
        File[] dirs = pathDir.listFiles(mDirFilter);
        if (dirs != null) {
            Arrays.sort(dirs, mComparator);
            Collections.addAll(list, dirs);
        }
        File[] files = pathDir.listFiles(mFileFilter);
        if (files != null) {
            Arrays.sort(files, mComparator);
            Collections.addAll(list, files);
        }
        return list;
    }

    public static Intent createGetContentIntent() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType(FileChooserActivity.MIME_TYPE_ALL);
        intent.addCategory("android.intent.category.OPENABLE");
        return intent;
    }
}
