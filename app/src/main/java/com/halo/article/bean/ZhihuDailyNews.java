package com.halo.article.bean;

import java.util.List;

/**
 * Created by zx on 2017/2/8.
 * Description: 对象实体
 */

public class ZhihuDailyNews {


    /**
     * date : 20170121
     * stories : [{"ga_prefix":"012122","id":9165425,"images":["http://pic2.zhimg.com/7ee215a4fb0b4c0b399a5c7f68749625.jpg"],"title":"小事 · 被家教性骚扰","type":0},{"ga_prefix":"012121","id":9165434,"images":["http://pic1.zhimg.com/ffcca2b2853f2af791310e6a6d694e80.jpg"],"title":"谁说普通人的生活就不能精彩有趣呢？","type":0},{"ga_prefix":"012120","id":9166828,"images":["http://pic4.zhimg.com/1a5d58c0aea1264b04eb7c133f2b43c3.jpg"],"title":"心理学研究特别「有趣」，并不一定是件好事","type":0},{"ga_prefix":"012120","id":9166900,"images":["http://pic3.zhimg.com/c8951086c34d0f4a1d89f52922cf2f4e.jpg"],"title":"移动互联网的风口变小，是这台「鼓风机」要停了","type":0},{"ga_prefix":"012118","id":9157263,"images":["http://pic3.zhimg.com/bf3dcc7247109bcef500c4f8ee657aaa.jpg"],"multipic":true,"title":"味道浑厚，香气十足，东北杀猪菜里怎么能少得了它","type":0},{"ga_prefix":"012117","id":9166209,"images":["http://pic1.zhimg.com/075277450c2378caa7733005e1474ff0.jpg"],"title":"为什么有人说在银行工作越久，能力越差？","type":0},{"ga_prefix":"012116","id":9166323,"images":["http://pic2.zhimg.com/bf54f37ad9a67d8d503be119fa517f15.jpg"],"multipic":true,"title":"你可能没有注意到，电子游戏的世界里遍地是篝火","type":0},{"ga_prefix":"012115","id":9166344,"images":["http://pic4.zhimg.com/061a7420ce290b7d95ef93d1c9ad9a83.jpg"],"title":"模仿白蚁巢穴，建筑师造出了不需要空调设备的大楼","type":0},{"ga_prefix":"012114","id":9164160,"images":["http://pic3.zhimg.com/dbd288e34e6ae62ab4b43e76f97c4a52.jpg"],"title":"克服拖延，最好的方法是用未来哄骗现在的自己","type":0},{"ga_prefix":"012113","id":9165945,"images":["http://pic4.zhimg.com/4841f66517bc856fc24243bdf98a115b.jpg"],"title":"去卢浮宫看到了她，才理解「到此一游」有多重要","type":0},{"ga_prefix":"012112","id":9165353,"images":["http://pic1.zhimg.com/7f2a386b526cc32d1cf76a9510c082b0.jpg"],"title":"大误 · 神不会开门","type":0},{"ga_prefix":"012111","id":9160013,"images":["http://pic4.zhimg.com/453b4161f31db87c96edaef56fc943a3.jpg"],"title":"对于年轻人，阿拉斯加有一种终极诱惑","type":0},{"ga_prefix":"012110","id":9165517,"images":["http://pic1.zhimg.com/c45250b6d82551e6a438b022038e5bb0.jpg"],"title":"去酒店看婚礼场地有哪些因素是需要重点考量的？","type":0},{"ga_prefix":"012109","id":9159786,"images":["http://pic2.zhimg.com/f1b65be20347de8e2628de55cba8ed65.jpg"],"title":"想变美就去打「溶脂针」，小心留下终身遗憾","type":0},{"ga_prefix":"012108","id":9165506,"images":["http://pic2.zhimg.com/c80dc1d75700edafd9ba9d0dc1413c79.jpg"],"title":"自动驾驶技术是人们真的需要，还是资本一厢情愿？","type":0},{"ga_prefix":"012107","id":9165038,"images":["http://pic2.zhimg.com/1a19fcc73107670340ad79760ba33df5.jpg"],"title":"在剧组工作是怎样的体验？韩寒说有这三大错觉","type":0},{"ga_prefix":"012107","id":9162759,"images":["http://pic4.zhimg.com/830b87ce21433433de311ff44e3be983.jpg"],"title":"开灯睡觉危害挺大，还是关了吧","type":0},{"ga_prefix":"012107","id":9165195,"images":["http://pic3.zhimg.com/3ecec846f1863047f346ae566726eeee.jpg"],"title":"快过年了，这些关于孩子的老生常谈，你可要记在心里","type":0},{"ga_prefix":"012106","id":9164451,"images":["http://pic3.zhimg.com/8f403403304b889465410dde96ca95ca.jpg"],"title":"瞎扯 · 如何正确地吐槽","type":0}]
     */

    private String date;
    /**
     * ga_prefix : 012122
     * id : 9165425
     * images : ["http://pic2.zhimg.com/7ee215a4fb0b4c0b399a5c7f68749625.jpg"]
     * title : 小事 · 被家教性骚扰
     * type : 0
     */

    private List<StoriesBean> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public static class StoriesBean {
        private String ga_prefix;
        private int id;
        private String title;
        private int type;
        private List<String> images;

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
