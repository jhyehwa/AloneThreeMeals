package com.alone.threemeals.mapper;

import java.util.ArrayList;
import java.util.Map;

import com.alone.threemeals.member.vo.Refrigerator;

public interface RefrigeratorMapper {
	public int insertRefrigerator(Refrigerator refrigerator) throws Exception;
	public Refrigerator selectRefrigerator(String m_Id) throws Exception;
	public ArrayList<Refrigerator> searchRefrigerator(String main_Ingredient) throws Exception;
	public int getCount(String main_Ingredient)throws Exception;
	public int deleteRefrigerator(String m_Id)throws Exception;
	
}
