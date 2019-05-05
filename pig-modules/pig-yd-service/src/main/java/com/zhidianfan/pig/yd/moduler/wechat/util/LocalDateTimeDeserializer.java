package com.zhidianfan.pig.yd.moduler.wechat.util;

import com.caucho.hessian.HessianException;
import com.caucho.hessian.io.AbstractDeserializer;
import com.caucho.hessian.io.AbstractHessianInput;
import com.caucho.hessian.io.IOExceptionWrapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * TODO: detail description
 *
 * @author wangyz
 * @version v 0.1 2019-04-30 14:28 wangyz Exp $
 */
public class LocalDateTimeDeserializer extends AbstractDeserializer {

    @Override
    public Class getType() {
        return LocalDateTime.class;
    }

    @Override
    public Object readObject(AbstractHessianInput in, Object[] fields) throws IOException {
        String[] fieldNames = (String[]) fields;

        int ref = in.addRef(null);

        long initValue = Long.MIN_VALUE;

        for (int i = 0; i < fieldNames.length; i++) {
            String key = fieldNames[i];

            if (key.equals("value")) {
                initValue = in.readUTCDate();
            } else {
                in.readObject();
            }
        }
        Object value = create(initValue);
        in.setRef(ref, value);
        return value;
    }

    private Object create(long initValue) throws IOException {
        if (initValue == Long.MIN_VALUE) {
            throw new IOException(LocalDateTime.class + " expects name.");
        }
        try {
            return LocalDateTime.ofEpochSecond(initValue / 1000, Integer.valueOf(String.valueOf(initValue % 1000)) * 1000, ZoneOffset.of("+8"));
        } catch (Exception e) {
            throw new IOExceptionWrapper(e);
        }
    }
}
