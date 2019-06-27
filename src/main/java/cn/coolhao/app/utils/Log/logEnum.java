package cn.coolhao.app.utils.Log;

/**
 * @Description: 备注
 * @Author: 谭浩
 * @Date: 2019/5/23 13:22
 */
public enum logEnum {
    BUSSINESS("log"),
    ;
    private String category;

    logEnum(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
