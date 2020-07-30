package util;
import java.util.*;

public class JSON
{
    public static String toJSON(Object o)
    {
        if (o instanceof List)
            return toJSON((List)o);
        else if (o instanceof Map)
            return toJSON((Map<String, Object>)o);
        else if (o instanceof String || o instanceof Character)
            return '"' + escape(o.toString()) + '"';
        else if (o instanceof Number || o instanceof Boolean)
            return o.toString();
        else if (o instanceof Date)
            return Long.toString(((Date)o).getTime());
        else
            return toJSON(Reflect.toMap(o));
    }

    public static String toJSON(Object... args)
    {
        return toJSON(Arrays.asList(args));
    }

    private static String toJSON(List list)
    {
        String[] strings = new String[list.size()];
        int i = 0;
        for (Object e: list)
            strings[i++] = toJSON(e);
        return "[" + join(strings) + "]";
    }

    private static String toJSON(Map<String, Object> map)
    {
        String[] strings = new String[map.size()];
        int i = 0;
        for (Map.Entry<String, Object> e: map.entrySet())
            strings[i++] = toJSON(e.getKey()) + ":" + toJSON(e.getValue());
        return "{" + join(strings) + "}";
    }

    private static String join(String... args)
    {
        if (args == null || args.length == 0)
            return "";
        StringBuilder result = new StringBuilder();
        result.append(args[0]);
        for (int i = 1; i < args.length; i++)
            result.append(',').append(args[i]);
        return result.toString();
    }

    private static String escape(String s)
    {
        String[][] mappings = {
                {"\\", "\\\\"},
                {"\b", "\\b"},
                {"\t", "\\t"},
                {"\n", "\\n"},
                {"\f", "\\f"},
                {"\r", "\\r"},
                {"\"", "\\\""}};
        for (String[] e: mappings)
            s = s.replace(e[0], e[1]);
        return s;
    }
}