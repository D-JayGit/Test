/*
import static org.junit.Assert.assertEquals;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class FilePartUploadClient {
    private final static String USER_AGENT = "Mozilla/5.0";
    private static String FILE_NAME = "/home/mangospring/Videos/SampleVideo-1280x720_10mb.mp4";
    private static final Integer MAX_NO_OF_PARTS = 10000;
    private static final Integer MINIMUM_PART_SIZE = 5 * 1024 * 1024;

    public static void main(String[] args) {
        filePartUpload(FILE_NAME);
    }
   */
/* @Test
    public void test() {
        boolean result = filePartUpload(FILE_NAME);
        assertEquals(result, true);
    }*//*


    public static boolean filePartUpload(String fileName) {
        File inputFile = new File(fileName);
        int contentLength = (int) inputFile.length();
        int partSizeForMax = (int) Math.ceil(contentLength / MAX_NO_OF_PARTS);
        int optimumPartSize =  Math.max(partSizeForMax, MINIMUM_PART_SIZE);

        int optimumNumberOfParts = (int) Math.floor(contentLength
                / optimumPartSize);
        optimumNumberOfParts = contentLength % optimumPartSize == 0 ? optimumNumberOfParts
                : optimumNumberOfParts + 1;

        String chunkStoragePath = System.getProperty("java.io.tmpdir") + "/";

        String actualFileName = fileName
                .substring(fileName.lastIndexOf('/') + 1);

        String url = "http://janus.ms.dev/fpu";
        List<BasicNameValuePair> urlParameters = getInitUrlParams(
                contentLength, optimumNumberOfParts, actualFileName);
        List<String> fileChunkList = null;
        try {
            JSONObject jsonObject;
            Integer fileId = initiateFilePartUpload(url, urlParameters);

            String fileIdHex = Integer.toHexString(fileId);
            urlParameters.add(new BasicNameValuePair("fileId", Integer
                    .toHexString(fileId)));

            fileChunkList = splitFileIntoChunks(actualFileName,
                    chunkStoragePath, optimumPartSize, contentLength, inputFile);
            int partNo = -1;

            // list.parallelStream().forEach(e -> putRequest(urlParameters, e,
            // ++partNo, fileIdHex));
            for (String chunk : fileChunkList) {
                putRequest(urlParameters, chunk, ++partNo, fileIdHex);
            }

            urlParameters = getFinUrlParameters(contentLength,
                    optimumNumberOfParts, actualFileName, fileIdHex);
            jsonObject = postRequest(url, urlParameters, false);

            System.out.println("final Response : " + jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fileChunkList != null) {
                fileChunkList.parallelStream().forEach(
                        file -> deleteChunkFile(Paths.get(file)));
            }
        }
        return true;
    }

    private static List<BasicNameValuePair> getFinUrlParameters(
            int contentLength, int optimumNumberOfParts, String actualFileName,
            String fileIdHex) throws FileNotFoundException {
        List<BasicNameValuePair> urlParameters;
        urlParameters = new ArrayList<BasicNameValuePair>();
        urlParameters.add(new BasicNameValuePair("_felix_session_id",
                "45da23629f885693958827b6b11d8ab9"));
        urlParameters.add(new BasicNameValuePair("filename", actualFileName));
        urlParameters.add(new BasicNameValuePair("size", contentLength + ""));
        urlParameters.add(new BasicNameValuePair("parentId", 123 + ""));
        urlParameters.add(new BasicNameValuePair("totalParts",
                optimumNumberOfParts + ""));
        urlParameters.add(new BasicNameValuePair("action", "fin"));
        urlParameters.add(new BasicNameValuePair("fileId", fileIdHex));
        urlParameters.add(new BasicNameValuePair("checksum", getFileChecksum(
                new FileInputStream(FILE_NAME), actualFileName) + ""));
        return urlParameters;
    }

    private static int initiateFilePartUpload(String url,
                                              List<BasicNameValuePair> urlParameters) throws Exception,
            JSONException {
        JSONObject jsonObject = postRequest(url, urlParameters, true);
        int fileId = jsonObject.getInt("id");
        return fileId;
    }

    private static List<BasicNameValuePair> getInitUrlParams(
            Integer contentLength, Integer optimumNumberOfParts,
            String actualFileName) {
        List<BasicNameValuePair> urlParameters = new ArrayList<BasicNameValuePair>();

        urlParameters.add(new BasicNameValuePair("_felix_session_id",
                "ce8ee96420e8b376841eb17f486cc386"));
        urlParameters.add(new BasicNameValuePair("action", "init"));
        urlParameters.add(new BasicNameValuePair("filename", actualFileName));
        urlParameters.add(new BasicNameValuePair("size", String
                .valueOf(contentLength)));
        urlParameters
                .add(new BasicNameValuePair("parentId", String.valueOf(123)));
        urlParameters.add(new BasicNameValuePair("totalParts", String
                .valueOf(optimumNumberOfParts)));
        return urlParameters;
    }

    private static List<String> splitFileIntoChunks(String fileName,
                                                    String chunkStoragePath, int optimumPartSize, int contentLength,
                                                    File inputFile) {
        if (fileName == null)
            return new ArrayList<>();
        File dir = new File(chunkStoragePath);
        dir.mkdir();
        FileInputStream inputStream;
        String newFileName;
        FileOutputStream filePart;
        int nChunks = 0, readLength = optimumPartSize;
        byte[] byteChunkPart;
        List<String> list = new ArrayList<String>();
        try {
            inputStream = new FileInputStream(inputFile);
            while (contentLength > 0) {
                if (contentLength <= optimumPartSize) {
                    readLength = contentLength;
                }
                byteChunkPart = new byte[readLength];
                optimumPartSize = inputStream
                        .read(byteChunkPart, 0, readLength);
                contentLength -= optimumPartSize;
                nChunks++;
                newFileName = chunkStoragePath + fileName + ".part"
                        + Integer.toString(nChunks - 1);
                filePart = new FileOutputStream(new File(newFileName));
                filePart.write(byteChunkPart);
                list.add(newFileName);
                filePart.flush();
                filePart.close();
                byteChunkPart = null;
                filePart = null;
            }
            inputStream.close();
        } catch (IOException exception) {
            exception.printStackTrace();

        }
        return list;
    }

    private static JSONObject postRequest(String url,
                                          List<BasicNameValuePair> urlParameters, boolean inti)
            throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.setHeader("User-Agent", USER_AGENT);
        HttpEntity httpEntity = new UrlEncodedFormEntity(urlParameters);
        post.setEntity(httpEntity);
        HttpResponse response = client.execute(post);
        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {
            if (header.getValue().equalsIgnoreCase(
                    "A file with same name exists at this location.")) {
                Integer listIndex=0;
                String actualFileName=null;
                for (BasicNameValuePair basicNameValuePair : urlParameters) {
                    if (basicNameValuePair.getName().equals("filename")) {
                        actualFileName = basicNameValuePair.getValue();
                        actualFileName = actualFileName.concat(String.valueOf(new Date()));
                        break;
                    }
                    ++listIndex;
                }
                urlParameters.remove(listIndex);
                urlParameters.add(new BasicNameValuePair("filename", actualFileName));
                httpEntity = new UrlEncodedFormEntity(urlParameters);
                post.setEntity(httpEntity);
                response = client.execute(post);
            }
        }
        System.out.println(response);

        BufferedReader rd = new BufferedReader(new InputStreamReader(response
                .getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result);
        JSONObject jsonObject = null;
        jsonObject = new JSONObject(result.toString());
        return jsonObject;

    }

    private static void putRequest(List<BasicNameValuePair> urlParameters,
                                   String fileName, int partNo, String fileIdHex) throws Exception {
        String acutalFilename = fileName
                .substring(fileName.lastIndexOf('/') + 1);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        URIBuilder builder = new URIBuilder();
        long checkSum = getFileChecksum(new FileInputStream(fileName),
                acutalFilename);
        builder.setScheme("http")
                .setHost("mangospring.ms.dev")
                .setPath("/fpu")
                .setParameter("_felix_session_id",
                        "a6199c76d858f33e772d41a8ffdd74fc")
                .setParameter("fileId", fileIdHex)
                .setParameter("folder_id", "91")
                .setParameter("filename", acutalFilename)
                .setParameter("checksum", checkSum + "")
                .setParameter("partNo", partNo + "");
        URI uri = builder.build();
        HttpPut put = new HttpPut(uri);
        MultipartEntityBuilder mpEntity = MultipartEntityBuilder.create();
        mpEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        FileBody fileBody = new FileBody(new File(fileName));
        mpEntity.addPart("fileName", fileBody);
        final HttpEntity entity = mpEntity.build();
        put.setEntity(entity);
        HttpResponse response = httpClient.execute(put);

        System.out.println(response);

        httpClient.close();
    }

    private static boolean deleteChunkFile(Path path) {
        try {
            Files.delete(path);
            return true;
        } catch (Exception x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        }
        return false;
    }

    private static Long getFileChecksum(InputStream inputStream, String filename) {
        Checksum checksum = new Adler32();
        byte[] tempArray = new byte[8192];
        CheckedInputStream cis = null;
        try {
            cis = new CheckedInputStream(inputStream, checksum);
            while (cis.read(tempArray) >= 0) {
            }
        } catch (IOException e) {
        } finally {
            if (cis != null)
                try {
                    cis.close();
                } catch (IOException e) {
                }
            tempArray = null;
        }
        return cis.getChecksum().getValue();
    }
}
*/
