package com.qingcheng.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qingcheng.pojo.goods.Goods;
import com.qingcheng.pojo.goods.Sku;
import com.qingcheng.pojo.goods.Spu;
import com.qingcheng.service.goods.CategoryService;
import com.qingcheng.service.goods.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;

import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Reference
    private SpuService spuService;
    @Autowired
    private TemplateEngine templateEngine;
    @Reference
    private CategoryService categoryService;

    @Value("${pagePath}")
    private String pagePath;

    @RequestMapping("/createPage")
    public void createPage(String spuId) {
        Goods good = spuService.findGoodsById(spuId);
        Spu spu = good.getSpu();
        List<String> categoryList = new ArrayList<String>();
        categoryList.add(categoryService.findById(spu.getCategory1Id()).getName());
        categoryList.add(categoryService.findById(spu.getCategory2Id()).getName());
        categoryList.add(categoryService.findById(spu.getCategory3Id()).getName());
        List<Sku> skuList = good.getSkuList();

        Map uslMap = new HashMap();
        for (Sku sku : skuList) {
            String specJson = JSON.toJSONString(JSON.parseObject(sku.getSpec()), SerializerFeature.MapSortField);
            uslMap.put(specJson, sku.getId() + ".html");
        }

        for (Sku sku : skuList) {
            Context context = new Context();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("spu", spu);
            map.put("sku", sku);
            map.put("categoryList", categoryList);
            map.put("spuImgs", spu.getImages().split(","));
            Map paraItems = JSONObject.parseObject(spu.getParaItems());
            map.put("paraItems", paraItems);
            Map<String, String> specItem = (Map) JSONObject.parseObject(sku.getSpec());
            map.put("specItem", specItem);
            Map<String, List> specMap = (Map) JSONObject.parse(spu.getSpecItems());

            for (String key : specMap.keySet()) {
                List<String> list = specMap.get(key);
                List<Map> mapList = new ArrayList<Map>();
                Map mapa = new HashMap();
                for (String value : list) {

                    mapa.put("option", value);
                    if (specItem.get(key).equals(value)) {
                        mapa.put("checked", true);
                    } else {
                        mapa.put("checked", false);
                    }
                    Map<String, String> spec = (Map) JSONObject.parseObject(sku.getSpec());
                    spec.put(key, value);
                    String specJson = JSON.toJSONString(spec, SerializerFeature.MapSortField);


                    mapa.put("url", uslMap.get(specJson));


                    mapList.add(mapa);
                }
                specMap.put(key, mapList);
            }


            map.put("specMap", specMap);
            map.put("skuImgs", sku.getImages().split(","));
            context.setVariables(map);
            File dir = new File(pagePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, sku.getId() + ".html");
            try {
                PrintWriter printWriter = new PrintWriter(file, "utf-8");
                templateEngine.process("item", context, printWriter);
                System.out.println("生成页面了");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}