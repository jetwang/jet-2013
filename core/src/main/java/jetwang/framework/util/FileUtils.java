package jetwang.framework.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    public static void copy(String sourceFilePath, String targetFilePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(sourceFilePath);
            copy(fileInputStream, targetFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copy(File inputFile, String targetFilePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(inputFile);
            copy(fileInputStream, targetFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copy(byte[] bytes, String targetChildFilePath) {
        try {
            File outputFile = generateEmptyFile(targetChildFilePath);
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(outputFile));
            output.write(bytes);
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copy(InputStream inputStream, String targetChildFilePath) {
        try {
            BufferedInputStream input = new BufferedInputStream(inputStream);
            File outputFile = generateEmptyFile(targetChildFilePath);
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(outputFile));
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = input.read(b)) != -1) {
                output.write(b, 0, len);
            }
            output.flush();
            output.close();
            input.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File generateEmptyFile(String targetChildFilePath) {
        File outputFile = new File(targetChildFilePath);
        if (outputFile.exists()) {
            outputFile.delete();
        }
        File parentFile = outputFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        return outputFile;
    }

    public static String getFileType(File file) {
        String fileName = file.getName();
        return getFileType(fileName);
    }

    public static String getFileType(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex > -1) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        return "";
    }

    public static void close(InputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                //ignore
            }
        }
    }

    public static void deleteFile(String file) {
        new File(file).delete();
    }


    public static void deleteFileUnderDirectory(File dir) {
        if (dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    FileUtils.deleteFileUnderDirectory(file);
                }
                file.delete();
            }
        }
    }

    public static String readFileStream(String file, String encode) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), encode));
        try {
            String temp = null;
            while ((temp = br.readLine()) != null) {
                sb.append(temp).append("\n");
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
        return sb.toString();
    }

    public static void write(String content, File file, String encoding) {
        try {
            file.getParentFile().mkdirs();
            OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(file), encoding);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getEncodeName(String unpackEncode, String targetName) {
        return targetName + "_" + unpackEncode;
    }

    public static String getFileName(String fullPath) {
        if (StringUtils.isEmpty(fullPath) || !fullPath.contains("/")) {
            return fullPath;
        }
        return fullPath.substring(fullPath.lastIndexOf("/"));
    }
    
    public static List<File> getAllFiles(File fileDirectory) {
        List<File> fileList = new ArrayList<File>();
        List<File> pathList = new ArrayList<File>();
        FileFilter pathFileter = new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        };
        FileFilter filter = new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isFile();
            }

        };
        if (fileDirectory.isDirectory()) {
            pathList.addAll(Arrays.asList(fileDirectory.listFiles(pathFileter)));
            fileList.addAll(Arrays.asList(fileDirectory.listFiles(filter)));
            while (!pathList.isEmpty()) {
                pathList.addAll(Arrays.asList(pathList.get(0).listFiles(pathFileter)));
                fileList.addAll(Arrays.asList(pathList.get(0).listFiles(filter)));
                pathList.remove(0);
            }
        } else {
            if (filter.accept(fileDirectory)) {
                fileList.add(fileDirectory);
            }
        }
        return fileList;
    }
}
