package net.zhuoweizhang.mcpelauncher.pro;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class 去掉多余括号字符 {

    @Test
    public void main1() {


        // 清理掉全部的类似的Math["min"] 多余括号字符
        String inputFile = "C:\\Users\\CC\\Desktop\\NL1.12.6.2\\脚本\\factorization.build"; // 替换为你的文件路径
        // 输出文件路径
        String outputFile = "C:\\Users\\CC\\Desktop\\NL1.12.6.2\\脚本\\factorization.build.format.js";

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             FileWriter fw = new FileWriter(outputFile)) {

            String line;
            Pattern pattern = Pattern.compile("\\[\"([^\"]+)\"]"); // 匹配 ["任意字符串"]

            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                StringBuilder modifiedLine = new StringBuilder();
                int lastEnd = 0;

                while (matcher.find()) {
                    if (matcher.group().contains(" ")) continue;
                    modifiedLine.append(line, lastEnd, matcher.start());
                    modifiedLine.append('.').append(matcher.group(1));
                    lastEnd = matcher.end();
                }

                modifiedLine.append(line.substring(lastEnd));
                fw.write(modifiedLine.toString() + System.lineSeparator());
            }

           System.out.println("BlockLauncher:  替换完成，结果已保存到 " + outputFile);

        } catch (IOException e) {
            System.err.println("读取或写入文件时发生错误: " + e.getMessage());
        }
    }
}