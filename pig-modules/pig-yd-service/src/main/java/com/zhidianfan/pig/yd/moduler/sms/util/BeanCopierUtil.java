package com.zhidianfan.pig.yd.moduler.sms.util;

import org.springframework.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.Map;

/**
 * 对象属性赋值工具类
 * 
 */
public class BeanCopierUtil {
	private static Map<String, BeanCopier> pool = new HashMap<String, BeanCopier>();

	public static synchronized <S, T> BeanCopier getBeanCopier(Class<S> source,
                                                               Class<T> target) {
		String key = source.getName() + target.getName();
		BeanCopier copier = null;
		if (pool.containsKey(key)) {
			copier = pool.get(key);
		}

		if (copier == null) {
			copier = BeanCopier.create(source, target, false);
			pool.put(key, copier);
		}

		return copier;
	}

	public static void copyProperties(Object orig, Object dest) {
		if (dest == null || orig == null) {
			return;
		}

		@SuppressWarnings("rawtypes")
		Class origClass = orig.getClass();
		@SuppressWarnings("rawtypes")
		Class destClass = dest.getClass();
		@SuppressWarnings("unchecked")
        BeanCopier copier = getBeanCopier(origClass, destClass);
		copier.copy(orig, dest, null);
	}
}
