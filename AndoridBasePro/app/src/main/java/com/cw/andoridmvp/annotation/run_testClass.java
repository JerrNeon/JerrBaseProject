package com.cw.andoridmvp.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2016/12/13 16:39
 */
@run_classInfo("类注解 run_testClass")
public class run_testClass {

    @run_fieldInfo(value = {77, 88})
    private String fieldInfo = "fieldInfo";

    @run_fieldInfo(value = {300})
    private int id = 55;

    @run_methodInfo(name = "ha", value = "11")
    public static String getMethodInfo() {
        return run_testClass.class.getSimpleName();
    }

    public static void main(String[] args) {
        run_classInfo runClassInfo = run_testClass.class.getAnnotation(run_classInfo.class);
        if (runClassInfo != null)
            System.out.print("Class==>\n类型：" + Modifier.toString(run_testClass.class.getModifiers()) + "\n类名："
                    + run_testClass.class.getSimpleName() + "\n注解值: " + runClassInfo.value());

        System.out.print("\n\n");

        Method[] methods = run_testClass.class.getDeclaredMethods();
        for (Method method : methods) {
            run_methodInfo runMethodInfo = method.getAnnotation(run_methodInfo.class);
            if (runMethodInfo != null)
                System.out.print("Method==>\n类型 " + Modifier.toString(method.getModifiers()) + "\n方法名 "
                        + method.getName() + "\n注解值--->" + "name: " + runMethodInfo.name() + ",value:"
                        + runMethodInfo.value() + ",id: " + runMethodInfo.id() + "\n");
        }

        System.out.print("\n\n");

        Field[] fields = run_testClass.class.getDeclaredFields();
        for(Field field : fields){
            run_fieldInfo runFieldInfo = field.getAnnotation(run_fieldInfo.class);
            if(runFieldInfo != null){
                System.out.print("Field==>\n类型 " + Modifier.toString(field.getModifiers()) + "\n变量名 "
                        + field.getName() + "\n注解值--->" + runFieldInfo.value() + "\n");
            }
        }
    }

}
