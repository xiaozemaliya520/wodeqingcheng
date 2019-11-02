package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qingcheng.dao.SpecMapper;
import com.qingcheng.pojo.goods.Brand;
import com.qingcheng.service.goods.BrandService;
import com.qingcheng.service.goods.SkuSearchService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SkuSearchServiceImpl implements SkuSearchService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private BrandService brandService;
    @Autowired
    private SpecMapper specMapper;

    @Override
    public Map search(Map<String, String> searchMap) {
        SearchRequest searchRequest = new SearchRequest("sku");
        searchRequest.types("doc");

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();


        if (searchMap.get("category") != null) {
            TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("categoryName", searchMap.get("category"));
            boolQueryBuilder.filter(termQueryBuilder);
        }


        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", searchMap.get("keywords"));
        boolQueryBuilder.must(matchQueryBuilder);


        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchRequest.source(searchSourceBuilder);

        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("sku_category").field("categoryName");
        searchSourceBuilder.aggregation(termsAggregationBuilder);

        Map map = new HashMap();
        try {


            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(response.getHits().getTotalHits());


            Aggregations aggregations = response.getAggregations();
            Map<String, Aggregation> asMap1 = aggregations.getAsMap();
            Terms terms = (Terms) asMap1.get("sku_category");
            List<? extends Terms.Bucket> buckets = terms.getBuckets();
            List<String> categoryList = new ArrayList<>();
            for (Terms.Bucket bucket : buckets) {
                categoryList.add(bucket.getKeyAsString());
            }
            map.put("categoryList", categoryList);
            System.out.println(categoryList);
            String categoryName = "";
            if (searchMap.get("brand")==null) {

                if (searchMap.get("category") == null) {
                    if (categoryList.size() > 0) {
                        categoryName = categoryList.get(0);
                    }

                } else {
                    categoryName = searchMap.get("category");
                }
                List<Brand> brandList = brandService.findByCategory(categoryName);
                map.put("brandList", brandList);

            }
            List<Map> specList = specMapper.specList(categoryName);
            for (Map spec : specList) {
                String[] options = ((String) spec.get("options")).split(",");
                spec.put("options",options);
            }
            map.put("specList",specList);


            List<Map<String, Object>> resultList = new ArrayList<>();
            SearchHit[] hits = response.getHits().getHits();
            for (SearchHit hit : hits) {
                Map<String, Object> asMap = hit.getSourceAsMap();
                resultList.add(asMap);
            }


            map.put("rows", resultList);









        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
