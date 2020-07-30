package util;
import java.lang.reflect.*;
import java.util.*;

public class Reflect
{
    public static Object toObject(Class cls, String[] columns, Object[] data)
    {
        try
        {
            Object o = cls.newInstance();
            for (int i = 0; i < columns.length; i++)
            {
                String key = columns[i];
                Object val = data[i];
                Field field = cls.getDeclaredField(lowerFirst(key));
                Method setter = cls.getDeclaredMethod("set" + key, field.getType());
                setter.invoke(o, val);
            }
            return o;
        }
        catch (ReflectiveOperationException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static Object getClassInstance(String className)
    {
        try
        {
            return Class.forName(className).getDeclaredMethod("getInstance").invoke(null);
        }
        catch (ReflectiveOperationException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static Map<String, Object> toMap(Object o)
    {
        try
        {
            Map<String, Object> map = new HashMap();
            Class cls = o.getClass();
            Field[] fields = cls.getDeclaredFields();
            for (Field e: fields)
            {
                String key = e.getName();
                Method getter = cls.getDeclaredMethod("get" + upperFirst(key));
                Object val = getter.invoke(o);
                if (val != null)
                    map.put(key, val);
            }
            return map;
        }
        catch (ReflectiveOperationException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private static String upperFirst(String s)
    {
        if (s == null || s.length() == 0)
            return s;
        else
            return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    private static String lowerFirst(String s)
    {
        if (s == null || s.length() == 0)
            return s;
        else
            return Character.toLowerCase(s.charAt(0)) + s.substring(1);
    }
}