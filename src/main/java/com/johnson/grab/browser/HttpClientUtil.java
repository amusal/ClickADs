/*
 * Copyright 2013 Future TV, Inc.
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */
package com.johnson.grab.browser;

import com.johnson.grab.utils.StringUtil;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2013/10/18
 * Time: 15:02
 */
public class HttpClientUtil {

    private static final int CONNECTION_REQUEST_TIMEOUT = 5 * 60 * 1000;
    private static final int SOCKECT_TIMEOUT = 10 * 60 * 1000;

    private HttpClient client;

    public void setClient(HttpClient client) {
        this.client = client;
    }

    /**
     * Get content by url as string
     * @param url
     *      original url
     * @return
     *      page content
     * @throws java.io.IOException
     */
    public static String getContent(String url) throws CrawlException, IOException {
        // construct request
        HttpGet request = new HttpGet(url);
        request.setConfig(RequestConfig.custom()
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .setSocketTimeout(SOCKECT_TIMEOUT)
                .build());
        // construct response handler
        ResponseHandler<String> handler = new ResponseHandler<String>() {
            @Override
            public String handleResponse(final HttpResponse response) throws IOException {
                StatusLine status = response.getStatusLine();
                // status
                if (status.getStatusCode() != HttpStatus.SC_OK) {
                    throw new HttpResponseException(status.getStatusCode(), status.getReasonPhrase());
                }
                // get encoding in header
                String encoding = getPageEncoding(response);
                boolean encodingFounded = true;
                if (StringUtil.isEmpty(encoding)) {
                    encodingFounded = false;
                    encoding = UniversalConstants.Encoding.ISO_8859_1;
                }
                // get content and find real encoding
                HttpEntity entity = response.getEntity();
                if (entity == null) {
                    return null;
                }
                // get content
                byte[] contentBytes = EntityUtils.toByteArray(entity);
                if (contentBytes == null) {
                    return null;
                }
                // found encoding
                if (encodingFounded) {
                    return new String(contentBytes, encoding);
                }
                // attempt to discover encoding
                String rawContent = new String(contentBytes, UniversalConstants.Encoding.DEFAULT);
                Matcher matcher = PATTERN_HTML_CHARSET.matcher(rawContent);
                if (matcher.find()) {
                    String realEncoding = matcher.group(1);
                    if (!encoding.equalsIgnoreCase(realEncoding)) {
                        // bad luck :(
                        return new String(rawContent.getBytes(encoding), realEncoding);
                    }
                }
                // not found right encoding :)
                return rawContent;
            }
        };
        // execute
        CloseableHttpClient client = HttpClientHolder.getClient();
        return client.execute(request, handler);
    }


    public static Response<String> request(String url) throws CrawlException, IOException {
        // construct request
        HttpGet request = new HttpGet(url);
        request.setConfig(RequestConfig.custom()
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .setSocketTimeout(SOCKECT_TIMEOUT)
                .build());
        // construct response handler
        ResponseHandler<Response<String>> handler = new ResponseHandler<Response<String>>() {
            @Override
            public Response<String> handleResponse(final HttpResponse response) throws IOException {
                Response result = new Response();
                StatusLine status = response.getStatusLine();
//                // status
//                if (status.getStatusCode() != HttpStatus.SC_OK) {
//                    throw new HttpResponseException(status.getStatusCode(), status.getReasonPhrase());
//                }
                result.setStatusCode(status.getStatusCode());

                // get encoding in header
                String encoding = getPageEncoding(response);
                boolean encodingFounded = true;
                if (StringUtil.isEmpty(encoding)) {
                    encodingFounded = false;
                    encoding = UniversalConstants.Encoding.ISO_8859_1;
                }
                // get content and find real encoding
                HttpEntity entity = response.getEntity();
                if (entity == null) {
                    return null;
                }
                // get content
                byte[] contentBytes = EntityUtils.toByteArray(entity);
                if (contentBytes == null) {
                    return null;
                }

                String rawContent = new String(contentBytes, UniversalConstants.Encoding.DEFAULT);
                result.setData(rawContent);
                // found encoding
                if (encodingFounded) {
                    result.setData(new String(contentBytes, encoding));
                } else {
                    // attempt to discover encoding
                    Matcher matcher = PATTERN_HTML_CHARSET.matcher(rawContent);
                    if (matcher.find()) {
                        String realEncoding = matcher.group(1);
                        if (!encoding.equalsIgnoreCase(realEncoding)) {
                            // bad luck :(
                           result.setData(new String(rawContent.getBytes(encoding), realEncoding));
                        }
                    }
                }
                // header
                return result;
            }
        };
        // execute
        CloseableHttpClient client = HttpClientHolder.getClient();
        return client.execute(request, handler);
    }

    private static final Pattern PATTERN_HTML_CHARSET = Pattern.compile("(?i)\\w+/\\w+;\\s*charset=([a-zA-Z0-9\\-]+)");
    private static String getPageEncoding(HttpResponse response) {
        Header contentType = response.getEntity().getContentType();
        if (contentType == null || StringUtil.isEmpty(contentType.getValue())) {
            return null;
        }
        Matcher matcher = PATTERN_HTML_CHARSET.matcher(contentType.getValue());
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }


    public static void main(String[] args) throws IOException, CrawlException {
//        final String url = "http://192.168.24.248:8080/HbaseDb/youku/";
//        String url = "http://business.sohu.com/20131021/n388557348.shtml?pvid=tc_business&a=&b=%E6%A5%BC%E5%B8%82%E6%B3%A1%E6%B2%AB%E7%A0%B4%E7%81%AD%E5%B0%86%E5%9C%A82015%E5%B9%B4%E5%BA%95%E4%B9%8B%E5%89%8D";
        final String url = "http://www.sohu.com";
        final int threadNum = 20;
        final int loop = 100;
        Thread[] threads = new Thread[threadNum];
        final List<Integer> times = new ArrayList<Integer>();
        final long s = System.currentTimeMillis();
        for (int i=0; i<threads.length; i++) {
            threads[i] = new Thread() {
                public void run() {
                    for (int i=0; i<loop; i++) {
                        try {
                            getContent(url);
                        } catch (CrawlException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                        long e = System.currentTimeMillis();
                        times.add((int) (e - s));
                    }
                }
            };
            threads[i].start();
        }
        while (times.size() < threadNum * loop) {
            int current = times.size();
            System.out.println("total: " + threadNum * loop + ", current: " + current + ", left: " + (threadNum * loop - current));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long e = System.currentTimeMillis();
        int totalTime = 0;
        for (Integer time : times) {
            totalTime += time;
        }
        System.out.println("------------------------------------------------------");
        System.out.println("thread num: " + threadNum + ", loop: " + loop);
        System.out.println("totalTime: " + totalTime + ", averTime: " + totalTime / (threadNum * loop));
        System.out.println("finalTime: " + (e - s) + ", throughput: " + (e - s) / (threadNum * loop));
    }
}
