package com.andrew.java.library.utils.download;

import android.os.Build;
import android.os.Process;
import android.text.TextUtils;

import com.andrew.java.library.utils.NetUtil;
import com.orhanobut.logger.Logger;

import org.apache.commons.io.output.FileWriterWithEncoding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

/**
 *
 */
public abstract class Downloader extends Thread {
    private static final String TAG = "Downloader";
    static final long MIN_SPACE = 3 * 1024 * 1024;
    static final int HTTP_OK = 200;
    static final int HTTP_PARTIAL = 206;
    static final int HTTP_MOVED_PERM = 301;
    static final int HTTP_MOVED_TEMP = 302;
    static final int HTTP_SEE_OTHER = 303;
    static final int HTTP_NOT_MODIFIED = 304;
    static final int HTTP_TEMP_REDIRECT = 307;
    static final int DEFAULT_TIMEOUT = 60000;
    /**
     * 最大的重定向次数
     */
    static final int MAX_REDIRECT_COUNT = 10;

    static {
        final StringBuilder builder = new StringBuilder();

        final boolean validRelease = !TextUtils.isEmpty(Build.VERSION.RELEASE);
        final boolean validId = !TextUtils.isEmpty(Build.ID);
        final boolean includeModel = "REL".equals(Build.VERSION.CODENAME)
                && !TextUtils.isEmpty(Build.MODEL);

        builder.append("MultiThreadDownloader");
        if (validRelease) {
            builder.append("/").append(Build.VERSION.RELEASE);
        }
        builder.append(" (Linux; U; Android");
        if (validRelease) {
            builder.append(" ").append(Build.VERSION.RELEASE);
        }
        if (includeModel || validId) {
            builder.append(";");
            if (includeModel) {
                builder.append(" ").append(Build.MODEL);
            }
            if (validId) {
                builder.append(" Build/").append(Build.ID);
            }
        }
        builder.append(")");

        DEFAULT_USER_AGENT = builder.toString();
    }

    public static final String DEFAULT_USER_AGENT;

    private DownloadTask downloadTask;

    public static class DownloadErrorException extends Exception {

        private String errorMsg;

        public DownloadErrorException(String message, String errorMsg1) {
            super(message);
            errorMsg = errorMsg1;
        }

        public String getErrorMsg() {
            return errorMsg;
        }
    }

    public static class DownloadCancelException extends Exception {
        public DownloadCancelException(String message) {
            super(message);
        }
    }

    /**
     * 获取下载任务
     *
     * @return
     */
    public abstract DownloadTask getDownloadTask();

    public abstract void downloadFinish(DownloadTask downloadTask1);

    public abstract void downloadProgress(DownloadTask downloadTask1, int progress);

    public abstract void downloadCache(DownloadTask downloadTask1);

    public abstract void downloadStart(DownloadTask downloadTask1);

    public abstract void downloadError(DownloadTask downloadTask1);

    public abstract void downloadCancel(DownloadTask downloadTask1);

    public abstract FileDownloaderManager.DownloadConf getDownloadConf();

    public abstract void threadStart(String threadName);

    public abstract void threadError(DownloadTask downloadTask1, String threadName);

    public abstract void threadEnd(String threadName);

    void addRequestHeaders(HttpURLConnection httpURLConnection, boolean isKeepAlive) throws Exception {
        httpURLConnection.addRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg," +
                "application/x-shockwave-flash, application/xaml+xml," +
                "application/vnd.ms-xpsdocument, application/x-ms-xbap," +
                "application/x-ms-application, application/vnd.ms-excel," +
                "application/vnd.ms-powerpoint, application/msword, */*");
        httpURLConnection.addRequestProperty("Accept-Ranges", "bytes");
        httpURLConnection.addRequestProperty("Charset", "UTF-8");
        if (isKeepAlive) {
            httpURLConnection.addRequestProperty("Connection", "Keep-Alive");
        } else if (Build.VERSION.SDK_INT > 13) {
            httpURLConnection.setRequestProperty("Connection", "close");
        }
        httpURLConnection.addRequestProperty("Accept-Encoding", "identity");
        File cacheFile = new File(downloadTask.getDestDir(), downloadTask.getCacheFileName());
        if (cacheFile.exists()) {
            String etag = getEtagOrLastModified();
            if (!TextUtils.isEmpty(etag)) {
                int lenIndex = etag.indexOf("||");
                if (lenIndex > 0) {
                    long fileTotalLength = Long.parseLong(etag.substring(lenIndex + 2));
                    if (fileTotalLength > cacheFile.length()) {
                        httpURLConnection.addRequestProperty("If-Range", etag.substring(0, lenIndex));
                        httpURLConnection.addRequestProperty("Range", "bytes=" + cacheFile.length() + "-");
                    }
                }
            }
        }
        if (!httpURLConnection.getRequestProperties().containsKey("User-Agent")) {
            httpURLConnection.addRequestProperty("User-Agent", DEFAULT_USER_AGENT);
        }
    }

    private boolean blockMobile() {
        if (!getDownloadConf().isWorkUnderMobileInternet() && getDownloadConf().getContext() != null) {
            return NetUtil.NetType.WIFI != NetUtil.getNetWorkType(getDownloadConf().getContext());
        }
        return false;
    }

    private void executeTask() throws Exception {
        if (blockMobile()) {
            throw new DownloadCancelException(String.format("Downloading Task %s Cancel", downloadTask.getDownloadUrl()));
        }
        if (getDownloadConf().getContext() != null && !NetUtil.isOnline(getDownloadConf().getContext())) {
            throw new DownloadErrorException("network disconnected", "network error");
        }
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        connect(downloadTask.getDownloadUrl(), 0);
    }

    /**
     * 链接下载
     *
     * @param url           下载的链接地址
     * @param redirectCount 重定向的次数
     * @throws Exception
     */
    private void connect(String url, int redirectCount) throws Exception {
        if (redirectCount >= MAX_REDIRECT_COUNT) {
            throw new IllegalArgumentException("Too Many Redirects");
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setInstanceFollowRedirects(false);
            conn.setConnectTimeout(DEFAULT_TIMEOUT);
            conn.setReadTimeout(DEFAULT_TIMEOUT);

            addRequestHeaders(conn, true);
            if (isInterrupted() || downloadTask.isCancel()) {
                throw new DownloadCancelException(String.format("Downloading Task %s Cancel", downloadTask.getDownloadUrl()));
            }
            taskStart();
            final int code = conn.getResponseCode();
            switch (code) {
                case HTTP_OK:
                case HTTP_PARTIAL:
                    downloadFile(conn);
                    if (renameToOriginalName()) {
                        fileComplete();
                        break;
                    } else {
                        throw new DownloadErrorException(conn.getResponseMessage(), conn.getResponseMessage());
                    }
                case HTTP_MOVED_PERM:
                case HTTP_MOVED_TEMP:
                case HTTP_SEE_OTHER:
                    String location = conn.getHeaderField("Location");
                    if (TextUtils.isEmpty(location)) {
                        location = conn.getHeaderField("location");
                    }
                    location = URLDecoder.decode(location, "UTF-8");
                    if (!location.startsWith("http")) {
                        // Deal with relative URLs
                        URL base = new URL(url);
                        URL next = new URL(base, location);
                        url = next.toExternalForm();
                    } else {
                        url = location;
                    }
                    connect(url, redirectCount + 1);
                    break;
                case HTTP_NOT_MODIFIED:
                case HTTP_TEMP_REDIRECT:
                default:
                    throw new DownloadErrorException(conn.getResponseMessage(), conn.getResponseMessage());
            }
        } finally {
            if (null != conn) conn.disconnect();
        }
    }

    private void taskFinish() {
        downloadFinish(downloadTask);
        if (downloadTask.getDownloadListener() != null) {
            downloadTask.getDownloadListener().onFinish(downloadTask.getTag(), new File(downloadTask.getDestDir(), downloadTask.getFileName()), downloadTask.getDownloadUrl(), downloadTask.getCurrentIndex(), downloadTask.getTotal());
        }
    }

    private void multiTask() throws Exception {
        while (downloadTask.prepareNextUrl()) {
            if (isInterrupted() || downloadTask.isCancel()) {
                throw new DownloadCancelException(String.format("Downloading Task %s Cancel", downloadTask.getDownloadUrl()));
            }
            if (checkSpaceAndFileStatus()) {
                return;
            }
        }
        taskFinish();
    }

    private boolean checkSpaceAndFileStatus() throws Exception {
        if (isSpaceEnough(downloadTask.getDestDir(), MIN_SPACE)) {
            if (getDownloadConf().isCheckLocalFileExist()) {
                if (!new File(downloadTask.getDestDir(), downloadTask.getFileName()).exists()) {
                    if (getDownloadConf().isEnableLocalCheckFirst()) {
                        downloadTask.prepareUrlFail();
                        downloadCache(downloadTask);
                        return true;
                    } else {
                        executeTask();
                    }
                } else {
                    fileComplete();
                }
            } else {
                executeTask();
            }
        } else {
            throw new DownloadErrorException("", "存储空间不足或无文件读写权限");
//            downloadError(downloadTask);
//            if(downloadTask.getDownloadListener() != null){
//                downloadTask.getDownloadListener().onError(downloadTask.getTag(), 0, "存储空间不足或无文件读写权限", null, downloadTask.getDownloadUrl(), downloadTask.getCurrentIndex(), downloadTask.getTotal());
//            }
        }
        return false;
    }

    private void fileComplete() {
        if (downloadTask instanceof MultiDownloadTask) {
            downloadProgress(downloadTask, 100);
            if (downloadTask.getDownloadListener() != null) {
                downloadTask.getDownloadListener().onProgress(downloadTask.getTag(), downloadTask.getDownloadUrl(), 100, downloadTask.getCurrentIndex(), downloadTask.getTotal());
            }
        } else {
            taskFinish();
        }
    }

    private void taskStart() {
        downloadStart(downloadTask);
        if (downloadTask.getDownloadListener() != null) {
            downloadTask.getDownloadListener().onStart(downloadTask.getTag(), downloadTask.getFileName(), downloadTask.getDownloadUrl(), -1, downloadTask.getCurrentIndex(), downloadTask.getTotal());
        }
    }

    private void taskCancel() {
        downloadCancel(downloadTask);
        if (downloadTask.getDownloadListener() != null) {
            downloadTask.getDownloadListener().onCancel(downloadTask.getTag(), downloadTask.getDownloadUrl(), 0, downloadTask.getCurrentIndex(), downloadTask.getTotal());
        }
    }

    @Override
    public void run() {
        threadStart(getName());
        try {
            while (!isInterrupted() && (downloadTask = getDownloadTask()) != null) {
                try {
                    downloadTask.incrementProcessTimes();
                    if (downloadTask instanceof MultiDownloadTask) {
                        multiTask();
                    } else {
                        checkSpaceAndFileStatus();
                    }
                } catch (Exception e) {
                    if (e instanceof DownloadCancelException) {
                        taskCancel();
                    } else {
//                        ToastUtils.getInstance().showShortToast(IeltsApplication.getInstance(), e.getMessage());
                        downloadError(downloadTask);
                        if (downloadTask.getDownloadListener() != null) {
                            if (e instanceof DownloadErrorException) {
                                downloadTask.getDownloadListener().onError(downloadTask.getTag(), 0, ((DownloadErrorException) e).getErrorMsg(), null, downloadTask.getDownloadUrl(), downloadTask.getCurrentIndex(), downloadTask.getTotal());
                            } else {
                                downloadTask.getDownloadListener().onError(downloadTask.getTag(), 0, e.getMessage(), null, downloadTask.getDownloadUrl(), downloadTask.getCurrentIndex(), downloadTask.getTotal());
                            }
                        }
                    }
                    Logger.e(e.getMessage());
                }
            }
            threadEnd(getName());
        } catch (Exception ex) {
            Logger.e(ex.getMessage());
            threadError(downloadTask, getName());
        }
    }

    private boolean renameToOriginalName() {
        new File(downloadTask.getDestDir(), downloadTask.getCacheFileName().replace(".cache", ".info")).delete();
        return new File(downloadTask.getDestDir(), downloadTask.getCacheFileName()).renameTo(new File(downloadTask.getDestDir(), downloadTask.getFileName()));
    }

    static synchronized boolean createFile(String path, String fileName) {
        boolean hasFile = false;
        try {
            File dir = new File(path);
            boolean hasDir = dir.exists() || dir.mkdirs();
            if (hasDir) {
                File file = new File(dir, fileName);
                hasFile = file.exists() || file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hasFile;
    }


    /**
     * 获取下载文件的大小
     *
     * @return
     */
    private long getDownloadFileLength(HttpURLConnection conn) {
        final String transferEncoding = conn.getHeaderField("Transfer-Encoding");
        final String contentRange = conn.getHeaderField("Content-Range");
        if (!TextUtils.isEmpty(contentRange) && contentRange.contains("/")) {
            return Long.parseLong(contentRange.substring(contentRange.indexOf("/") + 1));
        }
        if (TextUtils.isEmpty(transferEncoding)) {
            try {
                return Long.parseLong(conn.getHeaderField("Content-Length"));
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        return -1;
    }

    private boolean isFileExist(long totalBytes) {
        File file = new File(downloadTask.getDestDir(), downloadTask.getFileName());
        if (file.exists()) {
            if (totalBytes == file.length()) {
                return true;
            } else {
                //文件不完整，删除文件后再下载
                file.delete();
                return false;
            }
        } else {
            file = new File(downloadTask.getDestDir(), downloadTask.getCacheFileName());
            if (file.exists() && totalBytes == file.length()) {
                return true;
            } else {
                if (file.length() > totalBytes) {
                    file.delete();
                }
//                file.delete();
                return false;
            }
        }
    }

    private boolean isSpaceEnough(String dir, long totalLength) {
        File dirFile = new File(dir);
        if (dirFile.exists() || dirFile.mkdirs()) {
            return dirFile.getUsableSpace() > totalLength;
        }
        return false;
    }

    /**
     * 保存断点续传标记或者修改时间
     *
     * @param conn
     * @throws Exception
     */
    private void saveEtagOrLastModified(HttpURLConnection conn) throws Exception {
        String etag = conn.getHeaderField("Etag");
        long fileTotalLength = getDownloadFileLength(conn);
        if (TextUtils.isEmpty(etag)) {
            etag = conn.getHeaderField("Last-Modified");
        }
        if (!TextUtils.isEmpty(etag) && fileTotalLength > 0) {
            File file = new File(downloadTask.getDestDir(), downloadTask.getCacheFileName().replace(".cache", ".info"));
            FileWriterWithEncoding fileWriterWithEncoding = new FileWriterWithEncoding(file, "utf-8");
            fileWriterWithEncoding.write(etag);
            fileWriterWithEncoding.write("||");
            fileWriterWithEncoding.write(String.valueOf(fileTotalLength));
            fileWriterWithEncoding.flush();
            fileWriterWithEncoding.close();
        }
    }

    /**
     * 获取断点续传标记或者修改时间与文件总大小
     *
     * @return
     * @throws Exception
     */
    private String getEtagOrLastModified() throws Exception {
        File file = new File(downloadTask.getDestDir(), downloadTask.getCacheFileName().replace(".cache", ".info"));
        if (file.exists()) {
            FileReader fileReader = new FileReader(file);
            char[] buffer = new char[1024];
            int len = 0;
            len = fileReader.read(buffer);
            fileReader.close();
            return new String(buffer, 0, len);
        }
        return null;
    }

    private void downloadFile(HttpURLConnection conn) throws Exception {
        long totalBytes = getDownloadFileLength(conn);
        if (getDownloadConf().isOverrideLocalFileExist() || totalBytes == -1 || !isFileExist(totalBytes)) {
            if (isSpaceEnough(downloadTask.getDestDir(), totalBytes)) {
                createFile(downloadTask.getDestDir(), downloadTask.getCacheFileName());
                InputStream is = conn.getInputStream();
                boolean isRange = conn.getHeaderField("Content-Range") != null;
                if (isRange) {
                    String etag = conn.getHeaderField("Etag");
                    if (TextUtils.isEmpty(etag)) {
                        etag = conn.getHeaderField("Last-Modified");
                    }
                    if (!TextUtils.isEmpty(etag)) {
                        String localEtag = getEtagOrLastModified();
                        if (!TextUtils.isEmpty(localEtag)) {
                            localEtag = localEtag.split("\\|\\|")[0];
                            if (localEtag.equals(etag)) {
                                isRange = true;
                            } else {
                                isRange = false;
                            }
                        } else {
                            isRange = false;
                        }
                    } else {
                        isRange = false;
                    }
                    if (isRange) {
                        isRange = conn.getResponseCode() == 206;
                    }
                }
                saveEtagOrLastModified(conn);
                File cacheFile = new File(downloadTask.getDestDir(), downloadTask.getCacheFileName());
                long totalRec = 0;
                if (isRange && cacheFile.length() > 0) {
                    totalRec = cacheFile.length();
                    Logger.e("断点续传：" + cacheFile.getName());
                    downloadProgress(downloadTask, (int) (totalRec * 100 / totalBytes));
                    if (downloadTask.getDownloadListener() != null) {
                        downloadTask.getDownloadListener().onProgress(downloadTask.getTag(), downloadTask.getDownloadUrl(), (int) (totalRec * 100 / totalBytes), downloadTask.getCurrentIndex(), downloadTask.getTotal());
                    }
                }
                FileOutputStream fos = new FileOutputStream(cacheFile, isRange && cacheFile.length() > 0);
                byte[] b = new byte[4096];
                int len;
                while ((len = is.read(b)) != -1) {
                    if (isInterrupted()) {
                        fos.close();
                        is.close();
                        throw new DownloadCancelException(String.format("Downloading Task %s Cancel", downloadTask.getDownloadUrl()));
                    }
                    if (downloadTask.isCancel()) {
                        fos.close();
                        is.close();
                        throw new DownloadCancelException(String.format("Downloading Task %s Cancel", downloadTask.getDownloadUrl()));
                    }
                    if (blockMobile()) {
                        fos.close();
                        is.close();
                        throw new DownloadCancelException("Downloading Task is not work under mobile network");
                    }
                    fos.write(b, 0, len);
                    totalRec += len;
                    downloadProgress(downloadTask, (int) (totalRec * 100 / totalBytes));
                    if (downloadTask.getDownloadListener() != null) {
                        downloadTask.getDownloadListener().onProgress(downloadTask.getTag(), downloadTask.getDownloadUrl(), (int) (totalRec * 100 / totalBytes), downloadTask.getCurrentIndex(), downloadTask.getTotal());
                    }
                }
                fos.close();
                is.close();
            } else {//存储空间不足
                throw new DownloadErrorException("", "存储空间不足或无文件读写权限");
//                downloadError(downloadTask);
//                if(downloadTask.getDownloadListener() != null){
//                    downloadTask.getDownloadListener().onError(downloadTask.getTag(), 0, "存储空间不足或无文件读写权限", null, downloadTask.getDownloadUrl(), downloadTask.getCurrentIndex(), downloadTask.getTotal());
//                }
            }
        }

    }

}
