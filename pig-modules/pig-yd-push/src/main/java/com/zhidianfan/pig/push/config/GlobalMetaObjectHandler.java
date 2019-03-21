package com.zhidianfan.pig.push.config;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * @Author zhoubeibei
 * @Description
 * @Date Create in 2018/11/2
 * @Modified By:
 */
@Component
@Slf4j
public class GlobalMetaObjectHandler extends MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill");
        if(metaObject.hasGetter("createTime")){
            this.setFieldValByName("createTime",new Date(),metaObject);
        }

        if(metaObject.hasGetter("updateTime")){
            this.setFieldValByName("updateTime",new Date(),metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill");
        setUpdateFillValue(metaObject,"updateTime",new Date());
    }

    private void setUpdateFillValue(MetaObject metaObject,String fieldName,Object value){
        if(metaObject.hasGetter("updateTime")){
            this.setFieldValByName("updateTime",value,metaObject);
        }
        if(metaObject.hasGetter("et")){
            Object et = metaObject.getValue("et");
            Class<?> aClass = et.getClass();
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals(fieldName)) {
                    try {
                        field.setAccessible(true);
                        field.set(et,value);
                        field.setAccessible(false);
                    } catch (IllegalAccessException e) {
                        log.error(e.getMessage());
                    }
                }
            }
        }
    }
}
