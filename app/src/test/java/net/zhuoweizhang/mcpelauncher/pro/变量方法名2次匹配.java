package net.zhuoweizhang.mcpelauncher.pro;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class 变量方法名2次匹配 {


    @Test
    public void test(){
        String pathToFile = "C:\\Users\\CC\\Desktop\\NL1.12.6.2\\脚本\\list.txt1111"; // 替换为你的文件路径
        String pathToFile2 = "C:\\Users\\CC\\Desktop\\NL1.12.6.2\\脚本\\var2.txt"; // 替换为你的文件路径


        HashSet<String> find = new HashSet<>();


        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
              if (line.contains(" ")){
                  String[] sp = line.split(" ");
                  if (sp.length == 2){
                      find.add(line);
                  }
              }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile2))) {
            String line;
            while ((line = br.readLine()) != null) {
                for (String s : find) {
                    String[] sp = s.split(" ");
                    if (Objects.equals(sp[0], line)){
                        result.append(line).append(" ").append(sp[1]).append("\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter("C:\\Users\\CC\\Desktop\\" +
                "NL1.12.6.2\\脚本\\" + "var2.ok")) {
            writer.write(result.toString());
            System.out.println("BlockLauncher: " + "文件已成功保存");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
