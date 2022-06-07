package com.demo;

import com.util.Menu;

public class App 
{
    public static void main( String[] args )
    {
    	/*for(int i=1;i<=100;i++)
    		System.out.println(new UserDao().generatePin());*/
    	
    	/*String s=new UserDao().encryptPin("hello@1234");
    	System.out.println(s);
    	System.out.println(new UserDao().decryptPin(s));*/
    	
    	/*String s=new UserDao().generatePin();
    	String s1=new UserDao().decryptPin(s);
    	System.out.println(s);
    	System.out.println(s1);
    	System.out.println(new UserDao().decryptPin(s1));*/
    	
    	//System.out.println(new UserDao().getMaxAcNo());
    	/*User u=new User();
    	UserDao userDao=new UserDao();
    	double d=userDao.checkBalance(1, "6381");
    	System.out.println(u.getUacbalance());*/
    	
    	/*User u =new User();
    	u.setUacname("Amit Kumar");
    	u.setUactype("Saving");
    	u.setUacbalance(32000);
    	String s[]=new UserDao().createAccount(u);
    	if(s!=null)
    	{
    		System.out.println("A/c No: "+s[0]);
    		System.out.println("A/c Pin: "+s[1]);
    	}*/
    	
    	new Menu();
    	
    	
    	
    }
}
