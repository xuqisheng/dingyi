package com.zhidianfan.pig.yd.moduler.wechat.util;

import com.caucho.hessian.io.ExtSerializerFactory;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author wangyz
 * @version v 0.1 2019-04-30 13:16 wangyz Exp $
 */
public class HessianRedisSerializer<T> implements RedisSerializer<T> {

    private static final Logger logger = LoggerFactory.getLogger(HessianRedisSerializer.class);


    private static final SerializerFactory serializerFactory = getSerializerFactory();

    /**
     * Serialize the given object to binary data.
     *
     * @param t object to serialize
     * @return the equivalent binary data
     */
    @Override
    public byte[] serialize(T t) throws SerializationException {

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Hessian2Output hessian2Output = new Hessian2Output(byteArrayOutputStream);
            hessian2Output.setSerializerFactory(serializerFactory);

            hessian2Output.startMessage();
            hessian2Output.writeObject(t);
            hessian2Output.completeMessage();
            hessian2Output.close();
            return byteArrayOutputStream.toByteArray();

        } catch (Exception e) {
            logger.info("序列化失败:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Deserialize an object from the given binary data.
     *
     * @param bytes object binary representation
     * @return the equivalent object instance
     */
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null)
            return null;

        Hessian2Input hessian2Input = new Hessian2Input(new ByteArrayInputStream(bytes));
        hessian2Input.setSerializerFactory(serializerFactory);
        try {
            hessian2Input.startMessage();
            Object o = hessian2Input.readObject();
            hessian2Input.completeMessage();
            hessian2Input.close();
            return (T) o;
        } catch (Exception e) {
            logger.info("反序列化失败:", e);
        }
        return null;
    }

    private static SerializerFactory getSerializerFactory() {
        ExtSerializerFactory extSerializerFactory = new ExtSerializerFactory();
        extSerializerFactory.addSerializer(java.time.LocalDateTime.class, new LocalDateTimeSerializer());
        extSerializerFactory.addDeserializer(java.time.LocalDateTime.class, new LocalDateTimeDeserializer());

        SerializerFactory serializerFactory = new SerializerFactory();
        serializerFactory.addFactory(extSerializerFactory);
        return serializerFactory;
    }
}