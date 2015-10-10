package com.cloudtv.hahong.mycontrolapp.model;


import java.util.List;

public class CatalogProductListData {

    /**
     * data : [{"nowPrice":"1.00","sellcount":0,"mustScores":0,"price":"4.40","name":"强生婴儿皂","isScores":"2","id":"1095935375","stock":11,"picture":"http://121.40.184.131:8089/cshop/cshopImgFile/1434105537775.jpg","productType":"1"},{"nowPrice":"1.00","sellcount":0,"mustScores":0,"price":"3.50","name":"可口可乐600ml","isScores":"2","id":"10982555901","stock":33,"picture":"http://121.40.184.131:8089/cshop/cshopImgFile/1434337559804.JPG","productType":"1"},{"nowPrice":"1.50","sellcount":1,"mustScores":0,"price":"5.00","name":"雀巢咖啡180ml","isScores":"2","id":"10982566502","stock":16,"picture":"http://121.40.184.131:8089/cshop/cshopImgFile/1434337665901.jpg","productType":"1"},{"nowPrice":"1.00","sellcount":0,"mustScores":0,"price":"5.60","name":"好丽友－呀土豆","isScores":"2","id":"1095936116","stock":26,"picture":"http://121.40.184.131:8089/cshop/cshopImgFile/1434105611930.jpg","productType":"1"},{"nowPrice":"1.00","sellcount":1,"mustScores":0,"price":"4.90","name":"walch洗衣液 500ml","isScores":"2","id":"1095931893","stock":24,"picture":"http://121.40.184.131:8089/cshop/cshopImgFile/1434105189599.jpg","productType":"1"},{"nowPrice":"1.00","sellcount":0,"mustScores":0,"price":"6.90","name":"绿箭薄荷糖23.8g","isScores":"2","id":"1095939118","stock":19,"picture":"http://121.40.184.131:8089/cshop/cshopImgFile/1434105911099.jpg","productType":"1"},{"nowPrice":"1.00","sellcount":1,"mustScores":0,"price":"4.90","name":"炫迈无糖口香糖－12片","isScores":"2","id":"1095940559","stock":18,"picture":"http://121.40.184.131:8089/cshop/cshopImgFile/1434106055378.png","productType":"1"},{"nowPrice":"1.00","sellcount":1,"mustScores":0,"price":"4.90","name":"都乐果汁250ML","isScores":"2","id":"10959422610","stock":19,"picture":"http://121.40.184.131:8089/cshop/cshopImgFile/1434106226704.jpg","productType":"1"},{"nowPrice":"1.00","sellcount":0,"mustScores":0,"price":"4.00","name":"清风抽纸100抽","isScores":"2","id":"1095925651","stock":30,"picture":"http://121.40.184.131:8089/cshop/cshopImgFile/1434104565250.jpg","productType":"1"},{"nowPrice":"1.00","sellcount":1,"mustScores":0,"price":"4.00","name":"维达手帕纸","isScores":"2","id":"1095929242","stock":29,"picture":"http://121.40.184.131:8089/cshop/cshopImgFile/1434104924406.jpg","productType":"1"},{"nowPrice":"1.00","sellcount":0,"mustScores":0,"price":"5.90","name":"多芬沐浴露45ml","isScores":"2","id":"1095934294","stock":12,"picture":"http://121.40.184.131:8089/cshop/cshopImgFile/1434105429224.jpg","productType":"1"},{"nowPrice":"1.00","sellcount":1,"mustScores":0,"price":"5.90","name":"都市系列青竹纤梅","isScores":"2","id":"1095938287","stock":19,"picture":"http://121.40.184.131:8089/cshop/cshopImgFile/1434105828001.jpg","productType":"1"}]
     */
    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * nowPrice : 1.00
         * sellcount : 0
         * mustScores : 0
         * price : 4.40
         * name : 强生婴儿皂
         * isScores : 2
         * id : 1095935375
         * stock : 11
         * picture : http://121.40.184.131:8089/cshop/cshopImgFile/1434105537775.jpg
         * productType : 1
         */
        private String nowPrice;
        private int sellcount;
        private int mustScores;
        private String price;
        private String name;
        private String isScores;
        private String id;
        private int stock;
        private String picture;
        private String productType;

        public void setNowPrice(String nowPrice) {
            this.nowPrice = nowPrice;
        }

        public void setSellcount(int sellcount) {
            this.sellcount = sellcount;
        }

        public void setMustScores(int mustScores) {
            this.mustScores = mustScores;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setIsScores(String isScores) {
            this.isScores = isScores;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public String getNowPrice() {
            return nowPrice;
        }

        public int getSellcount() {
            return sellcount;
        }

        public int getMustScores() {
            return mustScores;
        }

        public String getPrice() {
            return price;
        }

        public String getName() {
            return name;
        }

        public String getIsScores() {
            return isScores;
        }

        public String getId() {
            return id;
        }

        public int getStock() {
            return stock;
        }

        public String getPicture() {
            return picture;
        }

        public String getProductType() {
            return productType;
        }
    }
}
