package net.runelite.client.plugins.piplup;

import net.runelite.api.SoundEffectID;

import java.lang.reflect.Field;

public class test {
    public static void main(String[] args) throws IllegalAccessException {

        String mystring = "hello world";

        mystring = mystring.replace("hello123", "bye");

        System.out.println(mystring);


        SoundEffectID myclass = new SoundEffectID();

        Field[] fields = myclass.getClass().getDeclaredFields();
        String soundname;
        int soundid = 2663;

        for(Field f : fields){
            Class t = f.getType();
            Object v = f.get(myclass);
            String s = f.getName();
            System.out.println(t.toString() + v.toString() + s);

            if (String.valueOf(soundid).equals(v.toString()))
            {
                soundname = s;
            }
            else
            {
                soundname = "unknown sound";
            }

            System.out.println(soundname);

        }




    }
}
