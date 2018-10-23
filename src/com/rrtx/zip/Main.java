package com.rrtx.zip;
/**
 * Created by 14641 on 2018/7/19.
 */
import java.io.*;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        InputStream in = Object.class.getResourceAsStream("/config.properties");
        prop.load(in);
        String ZipPath=(String)prop.get("zipPath");
        String WebPath=(String)prop.get("webPath");
        String foldersOrFiles=(String)prop.get("foldersOrFiles");
        if("0".equals(foldersOrFiles)){
            BufferedOutputStream bos = null;
            File file = new File(WebPath);
            File[] listFiles = file.listFiles();
            for (File file2 : listFiles) {
                ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                        ZipPath + file2.getName() + ".zip"));
                    bos = new BufferedOutputStream(out);
                    File sourceFile = new File(file2.getPath());
                    compress(out, bos, sourceFile, sourceFile.getName());
                out.close();
                System.out.println("压缩完成");
            }
        }else if("1".equals(foldersOrFiles)){
            BufferedOutputStream bos = null;
            File file = new File(WebPath);
            File[] listFiles = file.listFiles();
            for (File file2 : listFiles) {
                File[] listFiles2 = file2.listFiles();
                ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                        ZipPath + file2.getName() + ".zip"));
                for (File file3 : listFiles2) {
                    bos = new BufferedOutputStream(out);
                    File sourceFile = new File(file3.getPath());
                    compress(out, bos, sourceFile, sourceFile.getName());
                }
                out.close();
                System.out.println("压缩完成");
            }
        }
    }
    public static void compress(ZipOutputStream out, BufferedOutputStream bos,
                                File sourceFile, String base) throws Exception {
        if (sourceFile.isDirectory()) {
            File[] flist = sourceFile.listFiles();
            if (flist.length == 0) {
                System.out.println(base + "/");
                out.putNextEntry(new ZipEntry(base + "/"));
            } else {
                for (int i = 0; i < flist.length; i++) {
                    compress(out, bos, flist[i],
                            base + "/" + flist[i].getName());
                }
            }
        } else {
            out.putNextEntry(new ZipEntry(base));
            FileInputStream fos = new FileInputStream(sourceFile);
            BufferedInputStream bis = new BufferedInputStream(fos);
            int tag;
            System.out.println(base);
            while ((tag = bis.read()) != -1) {
                bos.write(tag);
            }
            bos.flush();
            bis.close();
            fos.close();

        }
    }
}
