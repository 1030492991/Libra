package cn.coolhao.app.pojo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * @Description: 返回类
 * @Author: 谭浩
 * @Date: 2019/5/23 14:10
 */
public class JsonResult {
    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private Integer code;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;


    public static JsonResult build(Integer code, String msg, Object data) {
        return new JsonResult(code, msg, data);
    }

    public static JsonResult success(Object data) {
        return new JsonResult(data);
    }

    public static JsonResult success() {
        return new JsonResult(null);
    }

    public static JsonResult errorMsg(Integer cede,String msg) {
        return new JsonResult(cede, msg, null);
    }
    public static JsonResult error(){
        return new JsonResult(4000, "未知错误", null);
    }

    /**
     * @Description: 构造方法
     * @Author: 谭浩
     * @Date: 2019/5/24 16:03
     * @Param: []
     * @Return:
     */
    public JsonResult() {

    }
    /**
     * @Description: 方法重载
     * @Author: 谭浩
     * @Date: 2019/5/24 16:03
     * @Param: [code, msg, data]
     * @Return:
     */
    public JsonResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    /**
     * @Description: 方法重载
     * @Author: 谭浩
     * @Date: 2019/5/24 16:04
     * @Param: [data 对象]
     * @Return:
     */
    public JsonResult(Object data) {
        this.code = 200;
        this.msg = "OK";
        this.data = data;
    }



    /**
     * @Description: 将json结果集转化为LeeJSONResult对象需要转换的对象是一个类
     * @Author: 谭浩
     * @Date: 2019/5/23 14:15
     * @Param: [jsonData, clazz]
     * @Return: cn.coolhao.app.pojo.JsonResult
     */
    public static JsonResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, JsonResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("code").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

   /**
    * @Description: 没有object对象的转化
    * @Author: 谭浩
    * @Date: 2019/5/23 14:14
    * @Param: [json]
    * @Return: cn.coolhao.app.pojo.JsonResult
    */
    public static JsonResult format(String json) {
        try {
            return MAPPER.readValue(json, JsonResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @Description: Object是集合转化 需要转换的对象是一个list
     * @Author: 谭浩
     * @Date: 2019/5/23 14:14
     * @Param: [jsonData, clazz]
     * @Return: cn.coolhao.app.pojo.JsonResult
     */
    public static JsonResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("code").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @Description: 主要用于测试
     * @Author: 谭浩
     * @Date: 2019/5/24 16:05
     * @Param: []
     * @Return: java.lang.String
     */
    @Override
    public String toString() {
        return "JsonResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
    public Boolean isOK() {
        return this.code == 200;
    }

    public Integer getcode() {
        return code;
    }

    public void setcode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
