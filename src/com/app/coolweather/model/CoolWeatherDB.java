package com.app.coolweather.model;

import java.util.ArrayList;
import java.util.List;

import com.app.coolweather.db.CoolWeahterOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CoolWeatherDB {

	public static final String DB_NAME = "cool_weather";
	
	public static final int VERSION = 1;
	
	private static CoolWeatherDB coolWeatherDb;
	
	private SQLiteDatabase db;
	
	private CoolWeatherDB(Context context){
		CoolWeahterOpenHelper dbHelper = new CoolWeahterOpenHelper(context, 
				DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	//获取CoolWeatherDB实例
	public synchronized static CoolWeatherDB getInstance(Context context){
		coolWeatherDb = new CoolWeatherDB(context);
		return coolWeatherDb;
	}
	
	//将province实例储存到数据库
	public void saveProvince(Province province){
		if(province != null){
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}
	
	//从数据库中读取全国所有省份的信息
	public List<Province> loadProvinces(){
		List<Province> list  = new ArrayList<Province>();
		
		Cursor cursor = db.query("Province", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				
				province.setProvinceName(cursor.getString(
						cursor.getColumnIndex("province_name")));
				
				province.setProvinceCode(cursor.getString(
						cursor.getColumnIndex("province_code")));
				
				list.add(province);
			}while(cursor.moveToNext());
		}
		if (cursor != null){
			cursor.close();
		}
		return list;
	}
	
		//将city实例储存到数据库
		public void saveCity(City city){
			if(city != null){
				ContentValues values = new ContentValues();
				values.put("city_name", city.getCityName());
				values.put("city_code", city.getCityCode());
				values.put("province_id", city.getProvinceId());
				db.insert("City", null, values);
			}
		}
		
		//从数据库中读取某省所有市的信息
		public List<City> loadCities(int provinceId){
			List<City> list  = new ArrayList<City>();
			
			Cursor cursor = db.query("City", null, "province_id = ?",
					new String[]{String.valueOf(provinceId)}, null, null, null);
			if(cursor.moveToFirst()){
				do{
					City city = new City();
					city.setId(cursor.getInt(cursor.getColumnIndex("id")));
					
					city.setCityName(cursor.getString(
							cursor.getColumnIndex("city_name")));
					
					city.setCityCode(cursor.getString(
							cursor.getColumnIndex("city_code")));
					
					city.setProvinceId(provinceId);
					list.add(city);
				}while(cursor.moveToNext());
			}
			if (cursor != null){
				cursor.close();
			}
			return list;
		}
		
		//将county实例储存到数据库
				public void saveCounty(County county){
					if(county != null){
						ContentValues values = new ContentValues();
						values.put("county_name", county.getCountyName());
						values.put("county_code", county.getCountyCode());
						values.put("city_id", county.getCityId());
						db.insert("County", null, values);
					}
				}
				
				//从数据库中读取某省所有市的信息
				public List<County> loadCounties(int cityId){
					List<County> list  = new ArrayList<County>();
					
					Cursor cursor = db.query("County", null, "city_id = ?",
							new String[]{String.valueOf(cityId)}, null, null, null);
					if(cursor.moveToFirst()){
						do{
							County county = new County();
							county.setId(cursor.getInt(cursor.getColumnIndex("id")));
							
							county.setCountyName(cursor.getString(
									cursor.getColumnIndex("county_name")));
							
							county.setCountyCode(cursor.getString(
									cursor.getColumnIndex("county_code")));
							
							county.setCityId(cityId);
							list.add(county);
						}while(cursor.moveToNext());
					}
					if (cursor != null){
						cursor.close();
					}
					return list;
				}
}
