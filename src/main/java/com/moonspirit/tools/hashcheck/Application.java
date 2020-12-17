package com.moonspirit.tools.hashcheck;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.print("Please input your target directory: ");
        String dir = input.nextLine();
        File file = new File(dir);
        generateChecksum(file);
    }

    private static void generateChecksum(File dir) throws IOException {
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File MD5CHECKSUM = new File(dir, "MD5CHECKSUM");
        File SHA1CHECKSUM = new File(dir, "SHA1CHECKSUM");
        File SHA256CHECKSUM = new File(dir, "SHA256CHECKSUM");
        PrintWriter pwMD5 = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(MD5CHECKSUM), "UTF-8"), true);
        PrintWriter pwSHA1 = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(SHA1CHECKSUM), "UTF-8"), true);
        PrintWriter pwSHA256 = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(SHA256CHECKSUM), "UTF-8"), true);

        String[] files = dir.list();
        if (files != null) {
            for (String fileName : files) {
                File file = new File(dir, fileName);
                if (file.isFile() && !fileName.equals("MD5CHECKSUM") && !fileName.equals("SHA1CHECKSUM") && !fileName.equals("SHA256CHECKSUM")) {
                    InputStream inputStream = new FileInputStream(file);
                    pwMD5.println(DigestUtils.md5Hex(new FileInputStream(file)) + "  " + fileName);
                    pwSHA1.println(DigestUtils.sha1Hex(new FileInputStream(file)) + "  " + fileName);
                    pwSHA256.println(DigestUtils.sha256Hex(new FileInputStream(file)) + "  " + fileName);
                } else {
                    generateChecksum(file);
                }
            }
        }
        pwMD5.close();
        pwSHA1.close();
        pwSHA256.close();
    }

}
