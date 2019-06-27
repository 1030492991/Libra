package cn.coolhao.app.excption;

/**
 * @Description: 自定义异常类
 * @Author: 谭浩
 * @Date: 2019/5/24 14:56
 */

public class WrongException extends RuntimeException {
    private Integer code;

    public WrongException(Integer code,String msg) {
        super(msg);
        this.code = code;
    }

    public WrongException(String msg) {
        super(msg);
        this.code = 4000;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
