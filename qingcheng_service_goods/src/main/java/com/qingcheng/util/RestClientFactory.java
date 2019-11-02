package com.qingcheng.util;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

public class RestClientFactory {
    public static RestHighLevelClient getHighLevelClient(String hostname,int port){
        HttpHost http=new HttpHost(hostname,port,"http");
        RestClientBuilder builder = RestClient.builder(http);
        return new RestHighLevelClient(builder);
    }
}
