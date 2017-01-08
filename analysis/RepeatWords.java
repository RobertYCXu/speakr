package repeatWords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RepeatWords {


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
	/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	      String add = "";
	      HashMap<String,Integer> strFreq = retWordFreq(add);
	      
	      Set set = strFreq.entrySet();
	      Iterator i = set.iterator();
	      while(i.hasNext()){
	    	  Map.Entry me = (Map.Entry)i.next();
	    	  System.out.print(me.getKey() + ": ");
	    	  System.out.println(me.getValue());
	      }

	}
	*/
}