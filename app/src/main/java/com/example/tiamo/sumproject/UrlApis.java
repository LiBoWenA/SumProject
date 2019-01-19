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
    public static String INDENT_FIND = "order/verify/v1/findOrderListByStatus?status=%d&page=%d&count=20";
    //详情页
    public static String HOMEPAGE_DETAILS = "commodity/v1/findCommodityDetailsById?commodityId=%d";
    //加入购物车
    public static String ADD_SHOPPING = "order/verify/v1/syncShoppingCart";
    //查询购物车
    public static String SELECT_SHOP = "order/verify/v1/findShoppingCart";
    //用户自身收货地址
    public static String ADDRESS_GOODS = "user/verify/v1/receiveAddressList";
    //创建订单
    public static String CREAT_INDENT = "order/verify/v1/createOrder";
    //查询用户信息
    public static String SELECT_USER = "user/verify/v1/getUserById";
    //修改昵称
    public static String UPDATE_USER_NAME = "user/verify/v1/modifyUserNick";
    //修改密码
    public static String UPDATE_USER_PASS = "user/verify/v1/modifyUserPwd";
    //查询收货地址
    public static String SELECT_ADDRESS = "user/verify/v1/receiveAddressList";
    //修改收货地址
    public static String UPDATE_ADDRESS = "user/verify/v1/changeReceiveAddress";
    //新增收货地址
    public static String INSERT_ADDRESS = "user/verify/v1/addReceiveAddress";
    //支付
    public static String PAY_SHOP = "order/verify/v1/pay";
    //收货
    public static String TAKE_GOODS = "order/verify/v1/confirmReceipt";
    //足迹
    public static String FOOT_URL = "commodity/verify/v1/browseList?page=%d&count=%d";
    //上传头像
    public static String USER_IMAGE = "user/verify/v1/modifyHeadPic";
    //默认勾选地址
    public static String ADDRESS_CHECK = "user/verify/v1/receiveAddressList";
    //查询钱包
    public static String SELECT_MONEY = "user/verify/v1/findUserWallet?page=%d&count=10";
    //发布圈子
    public static String PUT_CIECLE = "circle/verify/v1/releaseCircle";
    //商品评论
    public static String PUT_COMMENT = "commodity/verify/v1/addCommodityComment";
    //我的圈子
    public static String MYCIRCLE = "circle/verify/v1/findMyCircleById?page=%d&count=10";
}
