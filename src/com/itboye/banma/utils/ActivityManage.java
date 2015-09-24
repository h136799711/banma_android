package com.itboye.banma.utils;

import java.util.Stack;

import android.app.Activity;
import android.content.Context;

public class ActivityManage {
	/** 
	 * 应用程序Activity管理类：用于Activity管理和应用程序退出 
	 * @author liux (http://my.oschina.net/liux) 
	 * @version 1.0 
	 * @created 2012-3-21 
	 */   
	      
	    private static Stack<Activity> activityStack;  
	    private static ActivityManage instance;  
	  
	    public static ActivityManage getActivityManage(){  
	        if(instance==null){  
	            instance=new ActivityManage();  
	        }  
	        return instance;  
	    }  
	    /** 
	     * 添加Activity到堆栈 
	     */  
	    public void addActivity(Activity activity){  
	        if(activityStack==null){  
	            activityStack=new Stack<Activity>();  
	        }  
	        activityStack.add(activity);  
	    }  
	    /** 
	     * 获取当前Activity（堆栈中最后一个压入的） 
	     */  
	    public Activity currentActivity(){  
	        Activity activity=activityStack.lastElement();  
	        return activity;  
	    }  
	    /** 
	     * 结束当前Activity（堆栈中最后一个压入的） 
	     */  
	    public void finishActivity(){  
	        Activity activity=activityStack.lastElement();  
	        finishActivity(activity);  
	    }  
	    /** 
	     * 结束指定的Activity 
	     */  
	    public void finishActivity(Activity activity){  
	        if(activity!=null){  
	            activityStack.remove(activity);  
	            activity.finish();  
	            activity=null;  
	        }  
	    }  
	    /** 
	     * 结束指定类名的Activity 
	     */  
	    public void finishActivity(Class<?> cls){  
	        for (Activity activity : activityStack) {  
	            if(activity.getClass().equals(cls) ){  
	                finishActivity(activity);  
	            }  
	        }  
	    }  
	    /** 
	     * 结束所有Activity 
	     */  
	    public void finishAllActivity(){  
	        for (int i = 0, size = activityStack.size(); i < size; i++){  
	            if (null != activityStack.get(i)){  
	                activityStack.get(i).finish();  
	            }  
	        }  
	        activityStack.clear();  
	    }  
	    /** 
	     * 退出应用程序 
	     */  
	    public void AppExit(Context context) {  
	        try {  
	            finishAllActivity();  
	          //  ActivityManage activityMgr= (ActivityManage) context.getSystemService(Context.ACTIVITY_SERVICE);  
	           // activityMgr.restartPackage(context.getPackageName());  
	            System.exit(0);  
	        } catch (Exception e) { }  
	    }  
}
