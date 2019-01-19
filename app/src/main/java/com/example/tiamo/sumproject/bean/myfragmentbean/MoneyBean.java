package com.example.tiamo.sumproject.bean.myfragmentbean;

import com.google.gson.Gson;

import java.util.List;

public class MoneyBean {

    /**
     * result : {"balance":99995202,"detailList":[{"amount":139,"consumerTime":1547697072000,"orderId":"2019011513560110421","userId":21},{"amount":139,"consumerTime":1547608036000,"orderId":"2019011611055951421","userId":21},{"amount":129,"consumerTime":1547565377000,"orderId":"2019011514024081721","userId":21},{"amount":268,"consumerTime":1547547875000,"orderId":"2019011514141259921","userId":21},{"amount":3499,"consumerTime":1547531153000,"orderId":"2019011513453361121","userId":21},{"amount":139,"consumerTime":1547471440000,"orderId":"2019011421102834221","userId":21},{"amount":189,"consumerTime":1547471375000,"orderId":"2019011019402272521","userId":21},{"amount":295,"consumerTime":1547288958000,"orderId":"2019011022253195521","userId":21}]}
     * message : 查询成功
     * status : 0000
     */

    private ResultBean result;
    private String message;
    private String status;

    public static MoneyBean objectFromData(String str) {

        return new Gson().fromJson(str, MoneyBean.class);
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class ResultBean {
        /**
         * balance : 99995202
         * detailList : [{"amount":139,"consumerTime":1547697072000,"orderId":"2019011513560110421","userId":21},{"amount":139,"consumerTime":1547608036000,"orderId":"2019011611055951421","userId":21},{"amount":129,"consumerTime":1547565377000,"orderId":"2019011514024081721","userId":21},{"amount":268,"consumerTime":1547547875000,"orderId":"2019011514141259921","userId":21},{"amount":3499,"consumerTime":1547531153000,"orderId":"2019011513453361121","userId":21},{"amount":139,"consumerTime":1547471440000,"orderId":"2019011421102834221","userId":21},{"amount":189,"consumerTime":1547471375000,"orderId":"2019011019402272521","userId":21},{"amount":295,"consumerTime":1547288958000,"orderId":"2019011022253195521","userId":21}]
         */

        private int balance;
        private List<DetailListBean> detailList;

        public static ResultBean objectFromData(String str) {

            return new Gson().fromJson(str, ResultBean.class);
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public List<DetailListBean> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<DetailListBean> detailList) {
            this.detailList = detailList;
        }

        public static class DetailListBean {
            /**
             * amount : 139
             * consumerTime : 1547697072000
             * orderId : 2019011513560110421
             * userId : 21
             */

            private int amount;
            private long consumerTime;
            private String orderId;
            private int userId;

            public static DetailListBean objectFromData(String str) {

                return new Gson().fromJson(str, DetailListBean.class);
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public long getConsumerTime() {
                return consumerTime;
            }

            public void setConsumerTime(long consumerTime) {
                this.consumerTime = consumerTime;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
