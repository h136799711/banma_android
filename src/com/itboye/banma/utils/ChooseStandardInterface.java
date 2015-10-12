package com.itboye.banma.utils;

public class ChooseStandardInterface {
	ChooseStandardListener chooseStandardListener;
	
	public ChooseStandardInterface(){
		
	}
	
	public void setChooseStandardListener(ChooseStandardListener ch){
		chooseStandardListener = ch;
	}
	public void changeview(){
		chooseStandardListener.changeview();
	}
}
