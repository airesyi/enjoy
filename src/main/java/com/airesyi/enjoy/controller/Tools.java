package com.airesyi.enjoy.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

@RestController
public class Tools {
    public static final String GOPATH = System.getenv("GOPATH");

    @PostMapping("/ansSql")
    public String ansSql(String sql) {
        StringBuffer result = new StringBuffer();
        try {
            Runtime rt = Runtime.getRuntime();
            //执行命令, 最后一个参数，可以使用new File("path")指定运行的命令的位置
            String[] cmd = new String[]{"./soar", "-query", sql};

            Process proc = rt.exec(cmd, null, new File(GOPATH+"/src/github.com/XiaoMi/soar"));

            InputStream stderr = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(stderr, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line = "";

            while ((line = br.readLine()) != null) { // 打印出命令执行的结果
                System.out.println(line);
                result.append(line).append("\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
