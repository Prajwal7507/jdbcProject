package com.util;

import java.util.Scanner;

import com.dao.UserDao;
import com.model.User;

public class Menu
	{
		public Menu()
		{
			int ch1=0;
			do 
			{
			System.out.println("-------------: Friends Bank :---------------");
			System.out.println("1. New User");
			System.out.println("2. Existing User");
			System.out.println("3. Forget Pin");
			System.out.println("4. Exit");
			System.out.println("-------------------------------------------");
			System.out.println("Enter Your Choice");
			ch1 = new Scanner(System.in).nextInt();
			switch(ch1)
			{
			case 1:
			{
				int ch2=0;
				System.out.println("-------------: Friends Bank :---------------");
				System.out.println("1. Create New Account");
				System.out.println("2. Exit");
				System.out.println("-------------------------------------------");
				System.out.println("Enter Your Choice : ");
				ch2 = new Scanner(System.in).nextInt();
				if(ch2==1)
				{
					User u = new User();
					System.out.print("Enter Your Name: ");
					u.setUacname(new Scanner(System.in).nextLine());
					System.out.print("Enter A/C Type: ");
					u.setUactype(new Scanner(System.in).nextLine());
					System.out.print("Enter Your Opening Balance: ");
					u.setUacbalance(new Scanner(System.in).nextInt());
					String s[] = new UserDao().createAccount(u);
					if(s!=null)
					{
						System.out.println();
						System.out.println("Dear "+u.getUacname());
						System.out.println("Your A/C Type "+u.getUactype()+" Create Successfully With RS."+u.getUacbalance());
						System.out.println("Your A/C No.:- "+s[0]);
						System.out.println("Your Pin :- "+s[1]);
						System.exit(1);
					}	
				}
				System.exit(1);
				break;
			}
			case 2:
			{
				int ch2=0;
				System.out.println("Enter Your Account No: ");
				int uacno = new Scanner(System.in).nextInt();
				System.out.println("Enter Your Pin: ");
				String uacpin = new Scanner(System.in).next();
				if(new UserDao().loginAccount(uacno, uacpin))
				{
					do
					{
					System.out.println("-------------: Friends Bank :---------------");
					System.out.println("1. Deposite Amount");
					System.out.println("2. Withdrawal Amount");
					System.out.println("3. Check Balance");
					System.out.println("4. Account Details");
					System.out.println("5. Change Pin");
					System.out.println("6. Back");
					System.out.println("7. Exit");
					System.out.println("-------------------------------------------");
					System.out.println("Enter Your Choice : ");
					ch2=new Scanner(System.in).nextInt();
					switch(ch2)
					{
					case 1:
					{
						System.out.println("Enter Deposited Amount : ");
						double amount = new Scanner(System.in).nextInt();
						if(new UserDao().depositAmount(uacno, uacpin, amount))
						{
							System.out.println("Deposited Successfully Rs."+amount);
							System.out.println("Your Updated Balance : Rs. "+new UserDao().checkBalance(uacno, uacpin));
						}
						else
						{
							System.out.println("Payment Failed - Deposited Fail !!!!!!");
							System.exit(1);
							
						}
							break;
					}
					case 2:
					{
						System.out.println("Enter Withdrawal Amount : ");
						double amount = new Scanner(System.in).nextInt();
						if(new UserDao().withdrawlAmount(uacno, uacpin, amount))
						{
							System.out.println("Withdrawal Successfully Rs. "+amount);
							System.out.println("Your Updated Balance : "+new UserDao().checkBalance(uacno, uacpin));
						}
						else
						{
							System.out.println("Payement Failed - Withdrawal Fail !!!!!!");
							System.exit(1);
						}
						break;
					}
					case 3:
					{
						new UserDao().checkBalance(uacno, uacpin);
						System.out.println("Your Current Balance Is : "+new UserDao().checkBalance(uacno, uacpin));
						break;
					}
					case 4:
					{
						User u=new UserDao().accountDetails(uacno);
						System.out.println("A/c No : "+u.getUacno());
						System.out.println("A/c holder name : "+u.getUacname());
						System.out.println("A/c type : "+u.getUactype());
						System.out.println("Balance : "+u.getUacbalance());
						break;
					}
					case 5:
					{
						String nuacpin="";
						User u=new UserDao().accountDetails(uacno);
						System.out.println("Your old pin is : "+new UserDao().decryptPin(u.getUacpin()));
						System.out.print("Enter new pin : ");
						nuacpin=new Scanner(System.in).nextLine(); 
						u.setUacpin(nuacpin);
						if( new UserDao().updatePin(u)>0)
						{
							System.out.println("Pin changed successfully");
						}
						System.exit(1);
						break;
					}
					case 7:
					{
						break;
					}
					}
					}
					while(ch2==6);
					}
				break;
			}
			case 3:
			{
				System.out.println("Enter Your A/C No. :-");
				int uacno = new Scanner(System.in).nextInt();
				User u = new UserDao().accountDetails(uacno);
				if(u==null)
				{
					System.out.print("Wrong Details");
				}
				else
				{
					if(uacno==u.getUacno())
					{
						System.out.println("Enter New Pin : ");
						String uacpin = new Scanner(System.in).nextLine();
						u.setUacpin(uacpin);
						if(new UserDao().updatePin(u)>0)
						{
							System.out.println("Pin Changed Succesfully");
						}
					}
				}
				break;
			}	
			}
			}
			while(ch1!=4);
		}
	}