package com.example.tiamo.sumproject;

public class UrlApis {
    public static String HOME_NEW_DESIGN_URI = "commodity/v1/commodityList";
    public static String BANNER_URI = "commodity/v1/bannerShow";
    public static String HOME_SERCH = "commodity/v1/findCommodityByKeyword?keyword=%s&page=%d&count=10";
    public static String SHOP_URI = "commodity/v1/findCommodityListByLabel?labelId=%s&page=%d&count=5";
    public static String SHOP_DETAILS = "/commodity/v1/findCommodityDetailsById?commodityId=%d";
    public static String SHOP_ONE_LEVEL = "commodity/v1/findFirstCategory";
    public static String SHOP_TWO_LEVEL = "commodity/v1/findSecondCategory?firstCategoryId=%s";
    public static String SHOP_TWO_LEVEL_SERCH = "commodity/v1/findCommodityByCategory?categoryId=%s&page=%d&count=5";
    public static String CIRCLE_CANCLEGREAT = "circle/verify/v1/cancelCircleGreat?circleId=%d";
    public static String CIRCLE_GREAT = "circle/verify/v1/addCircleGreat";
    public static String CIRCLE_PATH = "circle/v1/findCircleList?userId=%d&sessionId=%s&page=%d&count=5";
    //订单
    public static String INDENT_FIND = "order/verify/v1/findOrderListByStatus?status=%d&page=%d&count=5";
    //详情页
    public static String HOMEPAGE_DETAILS = "commodity/v1/findCommodityDetailsById?commodityId=%d";
    //加入购物车
    public static String ADD_SHOPPING = "order/verify/v1/syncShoppingCart";
    //查询购物车
    public static String SELECT_SHOP = "order/verify/v1/findShoppingCart";
}
