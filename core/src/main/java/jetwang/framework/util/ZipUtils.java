package jetwang.framework.util;

import org.apache.commons.io.IOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    public static void zip(String zipFileName, String inputFile) throws IOException {
        zip(zipFileName, new File(inputFile));
    }

    public static void zip(String zipFileName, File inputFile) throws IOException {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        try {
            zip(out, inputFile, "");
        } finally {
            IOUtils.closeQuietly(out);
        }
    }


    public static void unzip(String zipFileName, String outputDirectory) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFileName));
        try {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                if (zipEntry.isDirectory()) {
                    String name = zipEntry.getName();
                    name = name.substring(0, name.length() - 1);
                    File f = new File(outputDirectory + File.separator + name);
                    f.mkdir();
                } else {
                    FileOutputStream out = null;
                    try {
                        File file = new File(outputDirectory + File.separator + zipEntry.getName());
                        file.createNewFile();
                        out = new FileOutputStream(file);
                        IOUtils.copy(zipInputStream, out);
                    } finally {
                        IOUtils.closeQuietly(out);
                    }
                }
            }
        } finally {
            IOUtils.closeQuietly(zipInputStream);
        }
    }


    public static void zip(ZipOutputStream out, File inputFile, String base) throws IOException {
        if (inputFile.isDirectory()) {
            File[] files = inputFile.listFiles();
            out.putNextEntry(new ZipEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";
            for (File file : files) {
                zip(out, file, base + file.getName());
            }
        } else {
            out.putNextEntry(new ZipEntry(base));
            FileInputStream in = null;
            try {
                in = new FileInputStream(inputFile);
                IOUtils.copy(in, out);
            } finally {
                IOUtils.closeQuietly(in);
            }
        }
    }
}
