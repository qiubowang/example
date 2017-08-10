package com.example.kingsoft.CustomUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.example.kingsoft.ObjectLibs.ExtractDrawing;
import com.example.kingsoft.ObjectLibs.Person;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kingsoft on 2017/6/7.
 */

public class GsonUtil {
    private static Gson mGson = null ;
    Person person = new Person();

    public GsonUtil(){

    }

    public void object2Json(){
        gsonEncoder();
    }


    /** 1、注解Expose，2、SerializedName，3、Unitl、Since
     * SerializedName修改字段的别名，例如SerializedName("iD")
     * Expose(serialize = false,deserialize = false)本注解指定哪些字段可以序列化或反序列化，但是设置GsonBuilder.excludeFieldsWithoutExposeAnnotation才有用
     * Unitl指定某个字段在某个版本之前可被识别，Since表示某个字段在本版本及其之后才能被识别，主要是为了版本的兼容。
     *
     * 2、FieldNamingPolicy中的几个枚举值，IDENTITY表示字段名不变，UPPER_CAMEL_CASE：导出的字段名称首字母变成大写.。例如：someFieldName ---> SomeFieldName
     *    UPPER_CAMEL_CASE_WITH_SPACES: 将字段名的每个大写字母开头的用空格分开。例如：someFieldName ---> Some Field Name
     *    LOWER_CASE_WITH_UNDERSCORES：将字段每个大写字母开头的用下划线(_)分开，并将分开的字符串全部转成小写。例如：someFieldName ---> some_field_name
     *    LOWER_CASE_WITH_DASHES：将字段每个大写字母开头的用破折号(-)分开，并将分开的字符串全部转成小写。例如：someFieldName ---> some-field-name
     *  设置的FieldNamingPolicy对于已经设置注解的SerializedName没有影响，因为注解的优先级高于FieldNamingPolicy的设置。
     *
     * 3、TypeToken指定序列化对象的类型
     *
     */

    public static void gsonEncoder(){

        Person person = new Person();
        person.setName("bobo");
        person.setAddress("zhuhai");
        person.setAge(30);
        person.setSchool("华中科技大学");
        person.setID("123456");

        mGson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation() //对序列化的对象进行筛选，只有注解标注有expose且serialize = true的才会被序列化,deserialize = true才会被反序列化。
                .serializeNulls().setExclusionStrategies()
                .enableComplexMapKeySerialization() //可以对map进行序列化
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        Type typeOfT = new TypeToken<Person>(){}.getType(); //指定序列化对象的类型，数组TypeToken<List<Person>>等
        String strPer = mGson.toJson(person, typeOfT);

        //自定义排除策略
        CustomExclusionStrategies customExclusionStrategies = new CustomExclusionStrategies(person.getClass());
        mGson = new GsonBuilder()
                .setExclusionStrategies(customExclusionStrategies) //对序列化的对象进行筛选，只有注解标注有expose且serialize = true的才会被序列化
                .serializeNulls().setExclusionStrategies()
                .enableComplexMapKeySerialization() //可以对map进行序列化
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        strPer = mGson.toJson(person, typeOfT);
        int a = 0;

    }


    private static String jsonStr = "{\"fillType\":\"none\",\"height\":127.4,\"shapeId\":0,\"shapeText\":{\"anchorCenter\":false,\"paraData\":[{\"className\":\"c11\",\"text\":\"生产\"},{\"className\":\"c11\",\"text\":\"\\n需要\"}],\"vertical\":false},\"width\":296.80014,\"x\":443.59995,\"y\":691.8}";
    /**
     * 如果一个Gson字符串非常的复杂，解析JSON可以采用以下方法。
     * 1、JSONObject，JSONArray，通过层层嵌套来进行解析
     */
    public static void gsonDecoder(){
//        //通过JSONObject，JSONArray解析
//        try {
//            JSONObject jsonObject = new JSONObject(jsonStr);
//            String fillType = jsonObject.getString("fillType");
//            int x = jsonObject.getInt("x");
//            int y = jsonObject.getInt("y");
//            double width = jsonObject.getDouble("width");
//            double height = jsonObject.getDouble("height");
//            int shapeId = jsonObject.getInt("shapeId");
//            JSONObject shapeText = jsonObject.getJSONObject("shapeText");
//            boolean anchorCenter = jsonObject.getBoolean("anchorCenter");
//            boolean vertical = jsonObject.getBoolean("vertical");
//
//            JSONArray paraData = jsonObject.getJSONArray("paraData");
//            for (int i = 0, length = paraData.length(); i < length; i ++){
//                JSONObject jsonObject1 = paraData.getJSONObject(i);
//                String className = jsonObject1.getString("className");
//                String text = jsonObject1.getString("text");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        //按照提供的字符串结构，自定义一些JaveBean(类)，然后通过传入class来进行解析
        Gson gson = new Gson();
        ExtractDrawing extractDrawing = gson.fromJson(jsonStr, ExtractDrawing.class);
        int i  = 0;
    }

    /**1、fastJson默认情况下，序列化对象，则不会序列化的有一下几种：静态方法或者属性，private和protected定义的属性或者方法，setXXX(set开头的方法)，返回值为null的不会被序列化(需要序列化需要配置SerializerFeature)。
     *    因此默认情况下，fastJson只会序列化公有非静态的方法或者属性，并且set开头的方法不会被序列化，get方法开头的会去掉get并且get之后的第一个字母会转成小写，
     *    例如：segName(){},本方法不会被序列化，getName(){}，序列化的key为name。
     *
     * 2、fastJson注解，fastJson注解主要用到JSONField，本注解名字虽然是作用于属性，其实他的目标有三个，属性、方法及其参数，看源代码就可以发现@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })。
     *    本注解有ordinal(指定排列顺序，默认为0)，name(命名)，format(格式)、serialize(是否序列化默认为true)，deserialize(是否反序列化，默认true),serialzeFeatures(SerializerFeature[])，parseFeatures(Feature[])。
     *
     *  如果类中属性也是共有的，本属性的返回方法也是有共有的，那么如果让序列化的对象只保留一份，则就可以在属性或者返回方法写上注解@JSONField[serialize=false].
     *
     *  3、NameFilter，ValueFilter通过key或者vale进行过滤.
     *
     *  4、SerializerFeature说明：
     *     SerializerFeature.DisableCircularReferenceDetect消除对象之间的循环引用，例如 A引用了B，而B同也引用了A。
     *     SerializerFeature.WriteMapNullValue，默认情况下如果map为null是不会序列化输出的，如果需要输出则配置本值，输出值为null。
     *     SerializerFeature.WriteNullListAsEmpty，默认情况下列表为null是不会序列化输出的，如果需要输出增加本配置就可以了，输出为空。
     *     SerializerFeature.WriteNullStringAsEmpty，默认情况下String类型的序列化属性或者方法为null时不序列化输出，如果需要输出配置本值。
     *     SerializerFeature.WriteNullNumberAsZero，如果数字类型的为null，默认情况下不序列化输出，如果需要输出配置本属性会给一个默认值0。
     *     SerializerFeature.WriteNullBooleanAsFalse，Boolean对象为null则默认不会序列化输出，如果需要输出配置本属性默认给一个false。
     */
    public static String fastJsonEncoder(Object object){
        Person person = new Person();
        person.setName("bobo");
        person.setAddress("zhuhai");
        person.setAge(30);
        person.setSchool("华中科技大学");
        person.setID("123456");

        NameFilter nameFilter = new NameFilter() {
            Pattern pattern = Pattern.compile("[0-9]*");
            //正则表达式判定定ke是否为数字
            public boolean isIndex(String str) {
                Matcher isNum = pattern.matcher(str);
                return isNum.matches();
            }
            /**
             *
             * @param o
             * @param propertyName
             * @param propertyValue
             * @return 获取到的为key,如果要修改key的值，则在process处理后返回的就是修改之后的key的值
             */
            @Override
            public String process(Object o, String propertyName, Object propertyValue) {
                //propertyName,序列化后的每个key，propertyValue序列化后的每个key的value
                return propertyName;
            }
        };



        ValueFilter valueFilter = new ValueFilter() {
            /**
             *
             * @param o
             * @param propertyName
             * @param propertyValue
             * @return 返回的是修改后的value的值，如果需要修改某个key对应的value的值，则在processc处理之后返回的值即是修改的value的值
             */
            @Override
            public Object process(Object o, String propertyName, Object propertyValue) {

                //propertyName,序列化后的每个key，propertyValue序列化后的每个key的value
                if (propertyName.equals("bobo"))
                    return new String("13633333333");
                else
                    return null;
            }
        };
        return JSON.toJSONString(person, nameFilter,  SerializerFeature.DisableCircularReferenceDetect);
    }


    public static void  toJsonByNameFilter(){

    }

    public static void main(String arg[]){
//        gsonEncoder();
        gsonDecoder();
    }


    /**自定义注解，注解顶层带的注解是固定必须的(可以参考Expose结构)
     *
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface ICustomIgnoreAnnotation{

    }

    /**自定义排除策略
     *
     */

    public static class CustomExclusionStrategies implements ExclusionStrategy {
        Class<?> mExClass = null;

        public CustomExclusionStrategies(Class<?> exClass){
            mExClass = exClass;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            Collection<Annotation> annotations = fieldAttributes.getAnnotations();
            for (Annotation annotation : annotations){
                if (annotation.annotationType() == ICustomIgnoreAnnotation.class){
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    }

}
