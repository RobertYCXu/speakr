package com.microsoft.CognitiveServicesExample.SpeechAnalysisLogic;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Akshay on 2017-01-08.
 */

public class RepeatedWords {
    public static ArrayList<String> retArrayList(String in){
        ArrayList<String> ret = new ArrayList<String>();
        int last=0;
        for(int a=0;a<in.length();a++){
            if((in.charAt(a)==' ' || in.charAt(a)=='.' || a == in.length()-1)&&last != a ){
                if(a==in.length()-1)
                    a++;
                ret.add(in.substring(last, a));
                last = a+1;
            }else if((in.charAt(a)==' ' || in.charAt(a)=='.' || a == in.length()-1)&&last==a){
                last++;
            }
        }
        return ret;
    }

    public static HashMap<String,Integer> retWordFreq(String slist){
        ArrayList<String> list = retArrayList(slist);
        HashMap<String,Integer> strFreq = new HashMap<String, Integer>();
        for(int a=0;a<list.size()-1;a++){
            if(list.get(a).equals(list.get(a+1))){
                if(strFreq.containsKey(list.get(a))){
                    strFreq.put(list.get(a),strFreq.get(list.get(a))+1);
                }else{
                    strFreq.put(list.get(a),1);
                }
            }
        }
        return strFreq;
    }
}
