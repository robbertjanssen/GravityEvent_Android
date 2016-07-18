package com.dove.gravity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


public class SavePref {
	
	 private static final String TAG = "SavePref";
	Context con;
	 String save_id;
	
	public  void SavePref(Context c ){
		
		con = c;
	//	save_id = s_id;
	}

	public String getId(){
		   SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		  String name = preferences.getString("id","");
		  
		  Log.d(TAG, "id received "+name);
		  
		  return name ;
	}
	

	public void setId(String id) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		 SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		  SharedPreferences.Editor editor = preferences.edit();
		  editor.putString("id", id);
		  editor.commit();
		//  System.out.println("val save pwd ");
		  
		  Log.d(TAG, "id saved");
		
	}
	public String getMessage(){
		   SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		  String name = preferences.getString("message","");
		  
		  Log.d(TAG, "message received "+name);
		  
		  return name ;
	}
	

	public void setMessage(String message)  {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		 SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		  SharedPreferences.Editor editor = preferences.edit();
		  editor.putString("message", message);
		  editor.commit();
		//  System.out.println("val save pwd ");
		  
		  Log.d(TAG, "message saved");
		
	}
	public void setbanner_id(String id) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		 SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		  SharedPreferences.Editor editor = preferences.edit();
		  editor.putString("banner_id1", id);
		  editor.commit();
		//  System.out.println("val save pwd ");
		  
		  Log.d(TAG, "id saved");
		
	}
	public String getbanner_id(){
		   SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		  String name = preferences.getString("banner_id1","");
		  
		  Log.d(TAG, "message received "+name);
		  
		  return name ;
	}


	public void setFname(String fname) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("fname", fname);
		editor.commit();
		//  System.out.println("val save pwd ");

		Log.d(TAG, "fname saved");
	}

	public String getFname(){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		String name = preferences.getString("fname","");

		Log.d(TAG, "message received "+name);

		return name ;
	}

	public void setLname(String lname) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("lname", lname);
		editor.commit();
		//  System.out.println("val save pwd ");

		Log.d(TAG, "lname saved");
	}

	public String getLname(){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		String name = preferences.getString("lname","");

		Log.d(TAG, "lname received "+name);

		return name ;
	}

	public void setEmail(String email) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("email", email);
		editor.commit();
		//  System.out.println("val save pwd ");

		Log.d(TAG, "email saved");
	}
	public String getEmail(){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		String name = preferences.getString("email","");

		Log.d(TAG, "email received "+name);

		return name ;
	}

	public void setPass(String pass) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("password", pass);
		editor.commit();
		//  System.out.println("val save pwd ");

		Log.d(TAG, "pass saved");
	}
	public String getPass(){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		String name = preferences.getString("password","");

		Log.d(TAG, "password received "+name);

		return name ;
	}

	public void setPhone(String phone) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("phone", phone);
		editor.commit();
		//  System.out.println("val save pwd ");

		Log.d(TAG, "phone saved");
	}
	public String getPhone(){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		String name = preferences.getString("phone","");

		Log.d(TAG, "phone received "+name);

		return name ;
	}

	public void setShowPhone(String showPhone) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("show_phone", showPhone);
		editor.commit();
		//  System.out.println("val save pwd ");

		Log.d(TAG, "showPhone saved");
	}
	public String getShowPhone(){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		String name = preferences.getString("show_phone","");

		Log.d(TAG, "show_phone received "+name);

		return name ;
	}

	public void setMembershiptype(String membershiptype) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("membership_type", membershiptype);
		editor.commit();
		//  System.out.println("val save pwd ");

		Log.d(TAG, "membershiptype saved");
	}
	public String getMembershiptype(){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		String name = preferences.getString("membership_type","");

		Log.d(TAG, "membership_type received "+name);

		return name ;
	}

	public void setPaymentstatus(String paymentstatus) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("payment_status", paymentstatus);
		editor.commit();
		//  System.out.println("val save pwd ");

		Log.d(TAG, "paymentstatus saved");
	}
	public String getPaymentstatus(){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		String name = preferences.getString("payment_status","");

		Log.d(TAG, "payment_status received "+name);

		return name ;
	}

	public void setActive(String active) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("active", active);
		editor.commit();
		//  System.out.println("val save pwd ");

		Log.d(TAG, "active saved");
	}
	public String getActive(){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		String name = preferences.getString("active","");

		Log.d(TAG, "active received "+name);

		return name ;
	}

	public void setType(String type) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("type", type);
		editor.commit();
		//  System.out.println("val save pwd ");

		Log.d(TAG, "type saved");
	}
	public String getType(){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		String name = preferences.getString("type","");

		Log.d(TAG, "type received "+name);

		return name ;
	}

	public void setIp(String ip) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("ip", ip);
		editor.commit();
		//  System.out.println("val save pwd ");

		Log.d(TAG, "ip saved");
	}
	public String getIp(){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		String name = preferences.getString("ip","");

		Log.d(TAG, "ip received "+name);

		return name ;
	}

	public void setTempverify(String tempverify) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("temp_verify", tempverify);
		editor.commit();
		//  System.out.println("val save pwd ");

		Log.d(TAG, "tempverify saved");
	}
	public String getTempverify(){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		String name = preferences.getString("temp_verify","");

		Log.d(TAG, "temp_verify received "+name);

		return name ;
	}

	public void setDate(String date) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("date", date);
		editor.commit();
		//  System.out.println("val save pwd ");

		Log.d(TAG, "date saved");
	}
	public String getDate(){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(con);
		String name = preferences.getString("date","");

		Log.d(TAG, "date received "+name);

		return name ;
	}
}