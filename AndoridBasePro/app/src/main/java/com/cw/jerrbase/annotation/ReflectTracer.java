package com.cw.jerrbase.annotation;

import java.lang.reflect.Method;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2016/10/12 16:52
 */
public class ReflectTracer {

    public static void tracerReflectAnnoation(Class<?> t) {
        for (Method method : t.getDeclaredMethods()) {
            ReflectAnnotation reflectAnnotation = method.getAnnotation(ReflectAnnotation.class);
            if (reflectAnnotation != null)
                System.out.println("ReflectAnnotation:--->author:" + reflectAnnotation.autor() + ",date:" + reflectAnnotation.date() + ",version:" + reflectAnnotation.version());
        }
    }

    public static void main(String[] arg){
        ReflectTracer.tracerReflectAnnoation(ReflectTracer.class);
    }

    @ReflectAnnotation(date = "2016-01")
    public void text(){
        System.out.println("完成");
    }

}
