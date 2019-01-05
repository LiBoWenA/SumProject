package com.example.tiamo.sumproject;

public class UrlApis {
    public static String HOME_NEW_DESIGN_URI = "commodity/v1/commodityList";
    public static String BANNER_URI = "commodity/v1/bannerShow";
    public static String HOME_SERCH = "commodity/v1/findCommodityByKeyword?keyword=%s&page=%d&count=10";
    public static String SHOP_URI = "commodity/v1/findCommodityListByLabel?labelId=%s&page=%d&count=5";
    public static String SHOP_DETAILS = "/commodity/v1/findCommodityDetailsById?commodityId=%d";
    public static String SHOP_ONE_LEVEL = "commodity/v1/findFirstCategory";
    public static String SHOP_TWO_LEVEL = "commodity/v1/findSecondCategory?firstCategoryId=%s";
    public static String SHOP_TWO_LEVEL_SERCH = "commodity/v1/findCommodityByCategory?categoryId=%s&page=%d&count=6";
}
