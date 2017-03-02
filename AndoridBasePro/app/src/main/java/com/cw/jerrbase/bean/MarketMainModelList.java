package com.cw.jerrbase.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (首页搜索)
 * @create by: chenwei
 * @date 2016/9/18 10:09
 */
public class MarketMainModelList implements Serializable {

    private PageModelVO page;//分类
    private List<MarketMainModel> mainList;//首页搜索列表

    public PageModelVO getPage() {
        return page;
    }

    public void setPage(PageModelVO page) {
        this.page = page;
    }

    public List<MarketMainModel> getMainList() {
        return mainList;
    }

    public void setMainList(List<MarketMainModel> mainList) {
        this.mainList = mainList;
    }
}
