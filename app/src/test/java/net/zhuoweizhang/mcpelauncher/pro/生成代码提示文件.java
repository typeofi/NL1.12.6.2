package net.zhuoweizhang.mcpelauncher.pro;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class 生成代码提示文件 {
    static String outPath = "C:\\Users\\CC\\Desktop\\NL1.12.6.2\\脚本\\android\\";


    @Test
    public void ttt() {
        new File(outPath).delete();
        new File(outPath).mkdirs();
        String[] filter = new String[]{"java.", "android.app.", "android.widget"};

//        Thread
        String jarPath = "C:\\Users\\CC\\Desktop\\NL1.12.6.2\\app\\src\\test\\java\\net\\zhuoweizhang\\mcpelauncher\\pro\\android.jar"; // 请将此路径替换为你的JAR文件路径
        List<String> strings = listClassesInJarFile(jarPath);
//        Class<?> cla = null;
//        for (String claz : strings) {
//            for (String string : filter) {
//                if (claz.startsWith(string)) {
//                    try {
//                        cla = Class.forName(claz);
//                    } catch (ClassNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                    if (cla != null) {
//                        appendApiMethods(cla);
//                    }
//
//                }
//            }
//
//        }
        appendApiMethods(Thread.class);
//        appendApiMethods(android.graphics.BitmapFactory.class);
//        appendApiMethods(android.os.Process.class);
//
//        appendApiMethods(Color.class);
//
//        appendApiMethods(Runnable.class);
//        appendApiMethods(View.class);
//        appendApiMethods(View.OnClickListener.class);
//        appendApiMethods(LinearLayout.class);
//        appendApiMethods(ImageView.class);
//        appendApiMethods(PopupWindow.class);
//        appendApiMethods(AlertDialog.Builder.class);
//        appendApiMethods(Bitmap.class);
//        appendApiMethods(BitmapDrawable.class);
//        appendApiMethods(Canvas.class);
//        appendApiMethods(Paint.class);
//        appendApiMethods(Color.class);
    }

    public static List<String> listClassesInJarFile(String jarPath) {
        List<String> list = new ArrayList<>();
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(jarPath);
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();
                if (entryName.endsWith(".class")) {
                    // 从路径中去除"/"并将"."替换为"//"，以便得到类名
                    String className = entryName.replace("/", ".").replace(".class", "");
                    list.add(className.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }


    private static void appendApiMethods(Class<?> clazz) {
        StringBuilder builder = new StringBuilder();
        List<String> cla = new ArrayList<>();
        for (String s : clazz.getName().split("\\.")) {
            if (s.contains("$")) {
                String[] a = s.split("\\$");
                cla.addAll(Arrays.asList(a));
                continue;
            }
            cla.add(s);
        }

        builder.append("var ");
        builder.append(cla.get(0));
        builder.append(" = {\n");

        for (int i = 1; i < cla.size(); i++) {
            for (int j = 0; j < i; j++) {
                builder.append("    ");
            }

            if (i < cla.size() - 1) {
                builder.append(cla.get(i));
                builder.append(": {\n");
            }
            if (i == cla.size() - 1) {
                builder.append(cla.get(i));
                builder.append(": class {\n");
            }
        }

        try {
            for (Method met : clazz.getMethods()) {

                for (int j = 0; j < cla.size(); j++) {
                    builder.append("    ");
                }

                if (Modifier.isStatic(met.getModifiers())) {
                    builder.append("static ");
                }
                builder.append(met.getName());
                builder.append(" = function (");

                Class<?>[] params = met.getParameterTypes();

                Parameter[] p = met.getParameters();
                for (int i = 0; i < met.getParameters().length; i++) {
                    Parameter par = p[i];
                    builder.append(par.getName());
                    builder.append("/*").append(params[i].getSimpleName().replaceAll("Native", ""));
                    builder.append("*/");
                    if (i < p.length - 1) {
                        builder.append(", ");
                    }
                }
                builder.append("){}\n");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }


        for (int i = 0; i < cla.size(); i++) {
            for (int j = cla.size() - i; j > 1; j--) {
                builder.append("    ");
            }
            builder.append("}\n");
        }


        try (FileWriter writer = new FileWriter(outPath + clazz + ".js")) {
            writer.write(builder.toString());
            System.out.println("BlockLauncher:" + clazz + "  文件已成功保存");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
