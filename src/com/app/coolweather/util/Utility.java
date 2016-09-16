package com.app.coolweather.util;

import android.text.TextUtils;

import com.app.coolweather.model.City;
import com.app.coolweather.model.CoolWeatherDB;
import com.app.coolweather.model.County;
import com.app.coolweather.model.Province;

public class Utility {
	
	//�����ʹ�����������ص�ʡ������
	public synchronized static boolean handleProvincesResponse(
			CoolWeatherDB coolWeatherDB, String response){
		if(!TextUtils.isEmpty(response)){
			String[] allProvinces = response.split(",");
			if(allProvinces != null && allProvinces.length > 0){
				for(String p:allProvinces){
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					
					//���������������ݴ���Province��
					coolWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}
	
	//�����ʹ�����������ص��м�����
		public synchronized static boolean handleCitiesResponse(
				CoolWeatherDB coolWeatherDB, String response, int provinceId){
			if(!TextUtils.isEmpty(response)){
				String[] allCities = response.split(",");
				if(allCities != null && allCities.length > 0){
					for(String p:allCities){
						String[] array = p.split("\\|");
						City city = new City();
						city.setCityCode(array[0]);
						city.setCityName(array[1]);
						city.setProvinceId(provinceId);
						
						//���������������ݴ���City��
						coolWeatherDB.saveCity(city);
					}
					return true;
				}
			}
			return false;
		}
		
		//�����ʹ�����������ص��ؼ�����
				public synchronized static boolean handleCountiesResponse(
						CoolWeatherDB coolWeatherDB, String response, int cityId){
					if(!TextUtils.isEmpty(response)){
						String[] allCounties = response.split(",");
						if(allCounties != null && allCounties.length > 0){
							for(String p:allCounties){
								String[] array = p.split("\\|");
								County county = new County();
								county.setCountyCode(array[0]);
								county.setCountyName(array[1]);
								county.setCityId(cityId);
								
								//���������������ݴ���County��
								coolWeatherDB.saveCounty(county);
							}
							return true;
						}
					}
					return false;
				}
	
}
