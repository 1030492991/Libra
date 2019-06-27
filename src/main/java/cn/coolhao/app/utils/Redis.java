package cn.coolhao.app.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Description: reids 工具类
 * @Author: 谭浩
 * @Date: 2019/5/24 10:29
 */
@Component
public class Redis {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Redis(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    /**
     * 根据key值 设置过期时间
     * @Description: 设置过期时间的方法
     * @Author: 谭浩
     * @Date: 2019/5/24 10:37
     * @Param: [key, time]
     * @Return: boolean
     */
    public boolean expire(String key,long time){
        try {
            if(time>0){
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 根据key 获取过期时间
     * @Description: 根据key 获取过期时间
     * @Author: 谭浩
     * @Date: 2019/5/24 10:38
     * @Param: [key] 键 不能为null
     * @Return: long 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }
    /**
     * 判断缓存是否存在
     * @Description: 判断缓存是否存在
     * @Author: 谭浩
     * @Date: 2019/5/24 10:39
     * @Param: [key]
     * @Return: boolean true or false
     */
    public boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 删除缓存
     * @Description: 删除单个或多个缓存
     * @Author: 谭浩
     * @Date: 2019/5/24 10:42
     * @Param: [key]
     * @Return: void
     */
    @SuppressWarnings("unchecked") //抑制单类型警告
    public void del(String ... key){
        if(key!=null&&key.length>0){
            if(key.length==1){
                redisTemplate.delete(key[0]);
            }else{
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }
    /**
     * 获取缓存
     * @Description: 获取缓存
     * @Author: 谭浩
     * @Date: 2019/5/24 10:45
     * @Param: [key]
     * @Return: java.lang.Object
     */
    public Object get(String key){
        return key==null?null:redisTemplate.opsForValue().get(key);
    }
    /**
     * 添加缓存
     * @Description: 添加缓存
     * @Author: 谭浩
     * @Date: 2019/5/24 10:45
     * @Param: [key, value]
     * @Return: boolean
     */
    public boolean set(String key,Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 根据key获取Set中的所有值
     * @param key 键
     * @return
     */
    public Set<Object> sGet(String key){
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 添加缓存并设置过期时间
     * @Description: 添加缓存并设置过期时间
     * @Author: 谭浩
     * @Date: 2019/5/24 10:46
     * @Param: [key, value, time]
     * @Return: boolean
     */
    public boolean set(String key,Object value,long time){
        try {
            if(time>0){
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            }else{
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     * @Description: 将数据放入set缓存
     * @Author: 谭浩
     * @Date: 2019/5/24 10:54
     * @Param: [key, values] values 可以是多个
     * @Return: long
     */
    public long sSet(String key, Object...values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 获取hashKey对应的所有键值
     * @Description: 获取hashKey对应的所有键值
     * @Author: 谭浩
     * @Date: 2019/5/24 11:08
     * @Param: [key]
     * @Return: java.Verify.Map<java.lang.Object,java.lang.Object> 对应的多个键值
     */
    public Map<Object,Object> hmget(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 将set数据放入缓存
     * @Description: 将set数据放入缓存并设置时间
     * @Author: 谭浩
     * @Date: 2019/5/24 10:55
     * @Param: [key, time, values] key,time,values 为必填
     * @Return: long
     */
    public long sSetAndTime(String key,long time,Object...values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if(time>0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 存入Map数组
     * @Description: 存入Map数组
     * @Author: 谭浩
     * @Date: 2019/5/24 11:06
     * @Param: [key 键, map 对应多个键值]
     * @Return: boolean true 成功 false 失败
     */
    public boolean hmset(String key, Map<String,Object> map){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * @Description: 存入Map数组 并设置时间
     * @Author: 谭浩
     * @Date: 2019/5/24 11:08
     * @Param: [key, map, time]
     * @Return: boolean
     */
    public boolean hmset(String key, Map<String,Object> map, long time){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if(time>0){
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //========================list=======================
    /**
     * 添加list到缓存
     * @Description: 添加list到缓存
     * @Author: 谭浩
     * @Date: 2019/5/24 11:23
     * @Param: [key, value]
     * @Return: boolean
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * @Description:
     * @Author: 谭浩
     * @Date: 2019/5/24 11:25
     * @Param: [key, value, time]
     * @Return: boolean
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * @Description: 获取整个list缓存
     * @Author: 谭浩
     * @Date: 2019/5/24 11:59
     * @Param: [key]
     * @Return: java.Verify.List<java.lang.Object>
     */
    public List<Object> lGet(String key){
        try {
            long end=lGetListSize(key);
            return redisTemplate.opsForList().range(key, 0, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取list缓存的内容
     * @Description: 获取list缓存的内容
     * @Author: 谭浩
     * @Date: 2019/5/24 11:23
     * @Param: [key, start, end]
     * @Return: java.Verify.List<java.lang.Object>
     */
    public List<Object> lGet(String key, long start, long end){
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * @Description: 获取list缓存的长度
     * @Author: 谭浩
     * @Date: 2019/5/24 11:24
     * @Param: [key]
     * @Return: long
     */
    public long lGetListSize(String key){
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * @Description: 通过索引 获取list中的值
     * @Author: 谭浩
     * @Date: 2019/5/24 11:24
     * @Param: [key, index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推]
     * @Return: java.lang.Object
     */
    public Object lGetIndex(String key,long index){
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 使用redis 的消息队列
     * @Description: 使用redis 的消息队列
     * @Author: 谭浩
     * @Date: 2019/5/24 10:49
     * @Param: [channel, message]
     * @Return: void
     */
    public void convertAndSend(String channel, Object message){
        redisTemplate.convertAndSend(channel,message);
    }

    /**
     * 右进栈
     * @Description: 右进栈
     * @Author: 谭浩
     * @Date: 2019/5/24 10:50
     * @Param: [listKey, values]
     * @Return: void
     */
    public void rPush(String listKey,Object... values) {
        //绑定操作
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        //插入数据
        boundValueOperations.rightPushAll(values);
        //设置过期时间
//        boundValueOperations.expire(expireEnum.getTime(),expireEnum.getTimeUnit());
    }
    /**
     * 左进栈
     * @Description: 左进栈
     * @Author: 谭浩
     * @Date: 2019/5/24 10:51
     * @Param: [listKey, values]
     * @Return: void
     */
    public void lPush(String listKey,Object... values) {
        //绑定操作
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        //插入数据
        boundValueOperations.leftPushAll(values);
        //设置过期时间
//        boundValueOperations.expire(expireEnum.getTime(),expireEnum.getTimeUnit());
    }
    /**
     * 右出栈
     * @Description: 右出栈
     * @Author: 谭浩
     * @Date: 2019/5/24 10:52
     * @Param: [listKey]
     * @Return: java.lang.Object
     */
    public Object rPop(String listKey){
        //绑定操作
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        return boundValueOperations.rightPop();
    }
    /**
     * 左出栈
     * @Description: 左出栈
     * @Author: 谭浩
     * @Date: 2019/5/24 10:53
     * @Param: [listKey]
     * @Return: java.lang.Object
     */
    public Object lPop(String listKey){
        //绑定操作
        BoundListOperations<String, Object> boundValueOperations = redisTemplate.boundListOps(listKey);
        return boundValueOperations.leftPop();
    }
}
