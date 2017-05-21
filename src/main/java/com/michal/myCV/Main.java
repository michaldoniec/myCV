package com.michal.myCV;

public class Main {

	public static void main(String[] args) {
		try{
			Application.run();
		} catch(RuntimeException e){
			System.out.println(e);
			Application.getApplicationInstance().stop();
		}
	}
}
