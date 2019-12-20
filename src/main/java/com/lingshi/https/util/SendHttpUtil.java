package com.lingshi.https.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: HttpUtil
 * @Create By: chenxihua
 * @Date: 2019/9/22 14:24
 */

@Component
public class SendHttpUtil {



    private static final Logger logger = LoggerFactory.getLogger(SendHttpUtil.class);

    private static final Integer SUCCESS_CODE = 200;

    @Value("${http.socketTimeOut}")
    private int socketTimeout;
    @Value("${http.connectTimeOut}")
    private int connectTimeout;
    @Value("${http.connectionRequestTimeOut}")
    private int connectionRequestTimeout;


    private RequestConfig getConfig(){
        return RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .build();
    }


    /**
     * 获取http 报文回复内容
     * @param response
     * @return
     * @throws Exception
     */
    private String getResponseContent(CloseableHttpResponse response) throws Exception {
        HttpEntity entity = response.getEntity();
        if (entity == null){
            throw new Exception("entity is null...");
        }

        String content = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
        //String content = EntityUtils.toString(entity, "GBK");  就是是用这种GBK，都是没有问题的，转化数据，不会出现乱码
        EntityUtils.consume(entity);
        // 根据网上经验, entity的读取似乎是一个必须完成的步骤, 所以状态码的检测, 放在后面进行
        int code = response.getStatusLine().getStatusCode();
        if (code != SUCCESS_CODE){
            throw new Exception("返回状态码: "+code);
        }
        return content;
    }


    /***
     * 执行 get 请求
     * @param url  服务端 url 路径
     * @return     服务端返回结果, 操作失败时返回null
     */
    public String doGet(String url){
        return doGet(url, null, null);
    }

    /**
     * 执行 get 请求
     * @param url      服务端 url 路径
     * @param params   要传递的参数
     * @return         服务端返回结果, 操作失败时返回null
     */
    public String doGet(String url, Map<String, String> params){
        return doGet(url, null, params);
    }


    public String doGet(String url, Map<String, String> headers, Map<String, String> params){
        if (url == null){
            return null;
        }
        URI uri;
        try {
            URIBuilder builder = new URIBuilder(url);
            if (params!=null){
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.addParameter(entry.getKey(), entry.getValue());
                }
            }
            uri = builder.build();
        } catch (URISyntaxException e) {
            logger.error("解析url{"+url+"}时发生异常, "+e);
            return null;
        }

        HttpGet httpGet = new HttpGet(uri);
        httpGet.setConfig(getConfig());

        if (headers!=null){
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }
        }

        try(CloseableHttpClient client = HttpClients.createDefault()) {
            try(CloseableHttpResponse response = client.execute(httpGet)) {
                return getResponseContent(response);
            }
        } catch (Exception e) {
            logger.error("get url {"+url+"}的过程中发生异常, "+e);
        }
        return null;
    }


    /**
     * 设置默认的请求头
     * @return
     */
    private Map<String, String> getDefaultHeader(){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=utf-8");
        return headers;
    }


    /**
     *   ================     post 请求   方式一： （参数为 Map类型）       ==================
     */


    /**
     * 通过 post 方式发送请求
     * @param url    服务端地址
     * @return       返回结果, 失败时返回null
     */
    public String doPost(String url){
        return doPost(url, getDefaultHeader(), (HttpEntity)null);
    }

    /***
     * 通过 post 方式发送请求
     * @param url      服务端地址
     * @param params   报文参数
     * @return         返回结果, 失败时返回null
     */
    public String doPost(String url, Map<String, String> params){
        return doPost(url, getDefaultHeader(), params);
    }

    /***
     * 通过 post 方式发送数据
     * @param url      服务端地址
     * @param headers  报文头
     * @param params   报文参数
     * @return         返回结果, 失败时返回null
     */
    public String doPost(String url, Map<String, String> headers, Map<String, String> params){
        List<NameValuePair> formParams = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        try {
            HttpEntity entity = new UrlEncodedFormEntity(formParams, "utf-8");
            return doPost(url, headers, entity);
        } catch (UnsupportedEncodingException e) {
            logger.error("post 编码数据时候, 发生异常, "+e);
            return null;
        }
    }


    /**
     *   ================     post 请求   方式二： （参数为 String类型）       ==================
     */


    /**
     * 通过 post 方式发送数据
     * @param url      服务端地址
     * @param message  要发送的数据
     * @return         返回结果, 失败时返回null
     */
    public String doPost(String url, String message){
        HttpEntity entity = null;
        if (message != null){
            entity = new StringEntity(message, "utf-8");
        }
        return doPost(url, getDefaultHeader(), entity);
    }


    /**
     * @param url
     * @param headers
     * @param message
     * @return
     */
    public String doPost(String url, Map<String, String> headers, String message){
        HttpEntity entity = null;
        if (message != null){
            entity = new StringEntity(message, "utf-8");
        }
        return doPost(url, headers, entity);
    }

    public String doPost(String url, Map<String, String> headers, HttpEntity entity){
        if (url == null){
            return null;
        }
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(getConfig());

        if (headers != null){
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        if (entity != null){
            httpPost.setEntity(entity);
        }

        try(CloseableHttpClient client = HttpClients.createDefault()) {
            try(CloseableHttpResponse response = client.execute(httpPost)) {
                return getResponseContent(response);
            }
        } catch (Exception e) {
            logger.error("post url {"+url+"} 过程发生异常, "+e);
        }
        return null;
    }










}










