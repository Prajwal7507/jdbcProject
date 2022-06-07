package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Random;

import com.model.User;
import com.util.MyDatabase;

public class UserDao {
	public String generatePin()
	{
		String s = String.valueOf(new Random().nextInt(9999));
		for(int i=s.length();i<4;i++)
			s+="0";
		return s;
	}
	
	/*public String encryptPin(String uacpin)
	{
		String nuacpin="";
		for(int i=0;i<uacpin.length();i++)
		{
			nuacpin +=(char)(uacpin.charAt(i)+1);
		}
		return nuacpin;
	}*/
	
	public String encryptPin(String uacpin)
	{
		return Base64.getEncoder().encodeToString(uacpin.getBytes());
	}
	
	/*public String decryptPin(String uacpin)
	{
		String nuacpin="";
		for(int i=0;i<uacpin.length();i++)
		{
			nuacpin +=(char)(uacpin.charAt(i)-1);
		}
		return nuacpin;
	}*/
	
	public String decryptPin(String uacpin)
	{
		return new String(Base64.getDecoder().decode(uacpin));
	}
	
	public int getMaxAcNo()
	{
		int check=0;
		String sql="select max(uacno)as uacno from user1";
		try(Connection con = new MyDatabase().getConnection();
				PreparedStatement pst = con.prepareStatement(sql);
				ResultSet rs = pst.executeQuery();) 
		{
			while(rs.next())
			{
				check = (int) rs.getObject("uacno");
			}
		}
		catch(SQLException e) 
		{
			System.out.println(e);
		}
		return check;
	}
	
	
	public String[] createAccount(User u)
	{
		String s[]=null;
		u.setUacpin(generatePin());
		String sql="insert into user1(uacname,uactype,uacbalance,uacpin)values(?,?,?,?)";
		try(Connection con=new MyDatabase().getConnection(); 
				PreparedStatement pst=con.prepareStatement(sql);)
		{
			pst.setString(1, u.getUacname());
			pst.setString(2, u.getUactype());
			pst.setDouble(3, u.getUacbalance());
			pst.setString(4, encryptPin(u.getUacpin()));
			if(pst.executeUpdate()>0)
			{
				s=new String[2];
				s[0]=String.valueOf(getMaxAcNo());
				s[1]=u.getUacpin();
			}
		}catch(SQLException e) {
			System.out.println(e);
		}
		return s;
	}
	
	
	public double checkBalance(int uacno,String uacpin)
	{
		double amount=0;
		String sql="select uacbalance from user1 where uacno=? and uacpin=?";
		try(Connection con=new MyDatabase().getConnection();
				PreparedStatement pst=con.prepareStatement(sql);)
		{
			pst.setInt(1, uacno);
			pst.setString(2, encryptPin(uacpin));
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
					amount =(double) rs.getDouble("uacbalance");
			}
			rs.close();
		}catch(SQLException e) {
			System.out.println(e);
		}
		return amount;
	}
	
	
	public boolean depositAmount(int uacno,String uacpin,double uacbalance)
	{
		boolean b=false;
		uacbalance +=checkBalance(uacno,uacpin);
		String sql="update user1 set uacbalance=? where uacno=? and uacpin=?";
		try(Connection con=new MyDatabase().getConnection(); 
				PreparedStatement pst=con.prepareStatement(sql);)
		{
			pst.setDouble(1, uacbalance);
			pst.setInt(2 , uacno);
			pst.setString(3, encryptPin(uacpin));
			if(pst.executeUpdate()>0)
			{
					b=true;
			}
		}catch(SQLException e) {
			System.out.println(e);
		}
		return b;
	}
	
	
	public boolean withdrawlAmount(int uacno,String uacpin,double uacbalance)
	{
		boolean b=false;
		uacbalance =checkBalance(uacno,uacpin)-uacbalance;
		if(uacbalance>100)
		{
		String sql="update user1 set uacbalance=? where uacno=? and uacpin=?";
		try(Connection con=new MyDatabase().getConnection(); 
				PreparedStatement pst=con.prepareStatement(sql);)
		{
			pst.setDouble(1, uacbalance);
			pst.setInt(2 , uacno);
			pst.setString(3, encryptPin(uacpin));
			if(pst.executeUpdate()>0)
			{
					b=true;
			}
		}catch(SQLException e) {
			System.out.println(e);
		}
		}
		return b;
	}
	
	public boolean loginAccount(int uacno,String uacpin)
	{
		boolean b=false;
		String sql="select uacno from user1 where uacno=? and uacpin=?";
		try(Connection con=new MyDatabase().getConnection(); 
				PreparedStatement pst=con.prepareStatement(sql);)
		{
			pst.setInt(1 , uacno);
			pst.setString(2, encryptPin(uacpin));
			ResultSet rs = pst.executeQuery();
			while(rs.next())
			{
					b=true;
			}
			rs.close();
		}catch(SQLException e) {
			System.out.println(e);
		}
		return b;
	}
	
	
	
	public User accountDetails(int uacno) {
		User u=null;
		String sql="select uacno,uacname,uactype,uacbalance,uacpin from user1 where uacno=?";
		try(Connection con=new MyDatabase().getConnection(); 
				PreparedStatement pst=con.prepareStatement(sql);) {
			pst.setInt(1, uacno);
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				u=new User();
				u.setUacno((int) rs.getObject("uacno"));
				u.setUacname((String) rs.getObject("uacname"));
				u.setUactype((String) rs.getObject("uactype"));
				u.setUacbalance((double) rs.getObject("uacbalance"));
				u.setUacpin((String) rs.getObject("uacpin"));
			}
			rs.close();
		}catch(SQLException e) {
			System.out.println(e);
		}
		return u;
	}
	
	
	public int updatePin(User u) {
		int check=0;
		String sql="update user1 set uacpin=? where uacno=?";
		try(Connection con=new MyDatabase().getConnection();
				PreparedStatement pst=con.prepareStatement(sql);)
		{
			pst.setString(1,encryptPin(u.getUacpin()));
			pst.setInt(2, u.getUacno());
			check=pst.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e);
		}
		return check;
	}
}
