package com.example.demo;

import com.example.demo.model.persistence.AppUser;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.UserOrder;

import java.lang.reflect.Field;

public class TestUtils {

    public static void injectObjects(Object target, String fieldName, Object toInject){
        boolean isPrivate = false;
        try{
            Field field = target.getClass().getDeclaredField(fieldName);

            if(!field.isAccessible()){
                isPrivate= true;
                field.setAccessible(true);
            }
            field.set(target, toInject);

            if(isPrivate){
                field.setAccessible(false);
            }

        }catch(NoSuchFieldException e){
            e.printStackTrace();

        }catch(IllegalAccessException e){
            e.printStackTrace();
        }
    }

    public static AppUser getAppUser(){
        return new AppUser();
    }

    public static Item getItem(){
        return new Item();
    }

    public static UserOrder getOrder(){
        return new UserOrder();
    }

    public static Cart getCart(){
        return new Cart();
    }

}
