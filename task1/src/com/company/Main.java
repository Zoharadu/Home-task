package com.company;

import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class Main {
    public static HashMap<String, Integer> names = new HashMap<String, Integer>();
    public static HashMap<String, Integer> Synonym = new HashMap<String, Integer>();
    public static HashMap<String, Integer> Output = new HashMap<String, Integer>();
    public static void main(String[] args) {
        try {
            File myObj = new File("src/names.txt");
            Scanner myReader = new Scanner(myObj);
            String data="";
            while (myReader.hasNextLine()) {
                data += myReader.nextLine();
            }
            System.out.println(data);
            myReader.close();
            analyzeData(data);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        boolean isEmpty = true;
        for (String key : Output.keySet()) {
            isEmpty = false;
            stringBuilder.append(key).append(" (").append(Output.get(key)).append("), ");
        }
        if (!isEmpty){
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }
        System.out.println("Output:"+stringBuilder);
      //System.out.println("result");
    }
    public static void analyzeData(String data){
        int index=data.indexOf("Synonyms");
        String names=data.substring(0,index);
        String Synonyms=data.substring(index,data.length());
        //System.out.println(names);
        //System.out.println(Synonyms);
        creatNamesHashmap(names);
        creatSynonymsHashmap(Synonyms);
        sortHashmap();
        //String str = Output.toString();
        //System.out.println("Output:"+str);
    }

    public static void creatNamesHashmap(String data){
        //System.out.println(data);
        int lastIndex=data.indexOf(":");
        //System.out.println(lastIndex);
        String namies=data.substring(lastIndex+1,data.length());
        String[] words = namies.split(",");
        int lastNameIndex;
        String name;
        String value;
        for (int i=0;i<words.length;i++){
          lastIndex=words[i].indexOf("(");
          name=words[i].substring(1,lastIndex-1);
          value=words[i].substring(lastIndex+1,words[i].indexOf(")"));
          names.put(name,stringToInt(value));
        }
        //for(Map.Entry<String, Integer> entry : names.entrySet()) {
        //    String key = entry.getKey();
        //    Integer value1 = entry.getValue();
        //    System.out.println(key+","+value1);
        // }
    }
    public static int stringToInt(String value){
        try{
            int number = Integer.parseInt(value);
            return number;
        }
        catch (NumberFormatException ex){
            return 0;
        }
    }
    public static void creatSynonymsHashmap(String Synonyms){
        int lastIndex=Synonyms.indexOf(":");
        String namey=Synonyms.substring(lastIndex+1,Synonyms.length());
        String[] words = namey.split("\\),");
        String name1,name2;
        for(int i=0;i<words.length;i++){
             name1=words[i].substring(words[i].indexOf("(")+1,words[i].indexOf(","));
             name2=words[i].substring(words[i].indexOf(",")+2,words[i].length()).replace(")","");
             //System.out.println(name1);
             //System.out.println(name2);
           if(!Synonym.containsKey(name1)){
               Synonym.put(name2,i);
               Synonym.put(name1,i);
            }
           else{
               Synonym.put(name2, Synonym.get(name1));
           }
        }
        //for(Map.Entry<String, Integer> entry : Synonym.entrySet()) {
        //   String key = entry.getKey();
        //   Integer value = entry.getValue();
        //  System.out.println(key+","+value);
        // }
    }

    public static void sortHashmap(){
        String result;
        for(Map.Entry<String, Integer> name : names.entrySet()) {
            String key = name.getKey();
            Integer value = name.getValue();
            //System.out.println(key+","+value);
            if (Synonym.containsKey(key)) {
                int val = Synonym.get(key);//value
                for (Map.Entry<String, Integer> synonym : Synonym.entrySet()) {
                    if (synonym.getValue() == val && names.containsKey(synonym.getKey()) && !synonym.getKey().equals(key)){
                        value += names.get(synonym.getKey());
                    }
                }
            }
            if(!Output.containsValue(value))
            //names.remove(entry);
            Output.put(key,value);
        }
       // for(Map.Entry<String, Integer> entry : Output.entrySet()) {
          // String key = entry.getKey();
          // Integer value = entry.getValue();
          // System.out.println(key+","+value);
         //}
    }
}
