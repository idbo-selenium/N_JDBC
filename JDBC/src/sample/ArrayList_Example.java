package sample;

import java.util.ArrayList;

public class ArrayList_Example {

	public static void main(String[] args){
		ArrayList<String> arrayList = new ArrayList<String>();
		System.out.println("initially elements in arraylist "+arrayList);
		arrayList.add("rama");
		arrayList.add("sita");
		arrayList.add("lakshmana");
		System.out.println("arrayList after adding elements"+arrayList);
		
		System.out.println("Size of ArrayList : "+arrayList.size());
		arrayList.remove(0);		
		//arrayList.clear();
		System.out.println("Now elements in arraylist"+arrayList);	
		System.out.println("Size of ArrayList : "+arrayList.size());
	}
}