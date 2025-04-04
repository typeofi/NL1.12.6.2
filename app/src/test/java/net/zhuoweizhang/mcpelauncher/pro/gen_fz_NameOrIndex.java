package net.zhuoweizhang.mcpelauncher.pro;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class gen_fz_NameOrIndex {

    @Test
    public void ttt() {
        list(); // 得到全部的名字列表。按照字符长度排序

//        start(); // 按照名字列表 重新命名 z字符在列表的位置和尾部加上_
    }


    public void list() {
        String pathToFile = "C:\\Users\\CC\\Desktop\\NL1.12.6.2\\脚本\\factorization.build"; // 替换为你的文件路径

        HashSet<String> find = new HashSet<>();
        StringBuilder result = new StringBuilder();
        Pattern pattern = Pattern.compile("_fz_[a-zA-Z0-9]+");

        Pattern var2 = Pattern.compile("^ {4}var .*? = ");
        Pattern fuc1 = Pattern.compile("^function .*\\)\\s*\\{");
        Pattern fuc2 = Pattern.compile("= function \\(.*\\)\\s*\\{");

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("_0x81e9x", "_fz_");
                Matcher lineM = pattern.matcher(line);
                while (lineM.find()) {
                    find.add(lineM.group());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> sortedMatches = new ArrayList<>(find);
        sortedMatches.sort(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s2.length() - s1.length();
            }
        });


        for (String match : sortedMatches) {
            result.append(match).append("\n");
        }

        try (FileWriter writer = new FileWriter("C:\\Users\\CC\\Desktop\\" +
                "NL1.12.6.2\\脚本\\" + "全部的名字列表按照字符长度排序.txt")) {
            writer.write(result.toString());
            System.out.println("BlockLauncher: " + "文件已成功保存");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void start() {
        List<String> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\CC\\Desktop\\NL1.12.6.2\\脚本\\list.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) continue;
                list.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        String pathToFile = "C:\\Users\\CC\\Desktop\\NL1.12.6.2\\脚本\\factorization.build"; // 替换为你的文件路径
        StringBuilder result = new StringBuilder();

        int lineNum = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineNum++;
                System.out.println("BlockLauncher: " + lineNum);
                for (String s : list) {
                    line = line.replaceAll(s, "_zf_" + (list.indexOf(s) + 1) + "\\_");
                }
                result.append(line).append("\n");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String resultString = result.toString();
        try (FileWriter writer = new FileWriter("C:\\Users\\CC\\Desktop\\" +
                "NL1.12.6.2\\脚本\\" + "最终还原变量名.js")) {
            writer.write(resultString);
            System.out.println("BlockLauncher: " + "文件已成功保存");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
