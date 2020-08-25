package com.alone.threemeals.member.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.jws.WebParam.Mode;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alone.threemeals.mapper.RefrigeratorMapper;
import com.alone.threemeals.member.vo.Refrigerator;

@Controller
public class RefrigeratorController {
	@Autowired
	SqlSession sqlSession;
	@Autowired
	Refrigerator refrigerator;

	@RequestMapping(value="/refrigeratorRegView", method=RequestMethod.GET)
	public String refrigeratorRegView(HttpSession session){
		String m_Id = (String)session.getAttribute("m_Id");
		RefrigeratorMapper refrigeratorMap = sqlSession.getMapper(RefrigeratorMapper.class);
		
		try {
			refrigerator = refrigeratorMap.selectRefrigerator(m_Id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(refrigerator != null)
			return "index";
		
		else
			return "refrigerator/refrigeratorRegView";
	};


	@RequestMapping(value="/refrigeratorRegProcess", method=RequestMethod.POST)
	public String refrigeratorRegProcess(
			@RequestParam(value="r_Ingredient") String[] r_ingredient,
			HttpSession session,
			Model model
			){
		String m_Id = (String)session.getAttribute("m_Id");
		String r_Ingredient = "";
		RefrigeratorMapper refrigeratorMap = sqlSession.getMapper(RefrigeratorMapper.class);
		Refrigerator refri=new Refrigerator();
		r_Ingredient = r_ingredient[0];
		for(int i=1; i<r_ingredient.length; i++)
			r_Ingredient = (r_Ingredient+","+r_ingredient[i]);
		
		refri.setM_Id(m_Id);
		refri.setR_Ingredient(r_Ingredient);
		try {
			refrigeratorMap.insertRefrigerator(refri);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return "refrigerator/refrigeratorRegComplete";
	}
	
	@RequestMapping(value="myRefrigeratorView", method=RequestMethod.GET)
	public String myRefrigeratorView(
			String m_Id,
			HttpSession session,
			Model model
			){
		m_Id = (String)session.getAttribute("m_Id");
		
		RefrigeratorMapper refrigeratorMap = sqlSession.getMapper(RefrigeratorMapper.class);
		try {
			refrigerator = refrigeratorMap.selectRefrigerator(m_Id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(refrigerator == null)
			return "refrigerator/refrigeratorRegView";
		
		else{
		String r_ingredient = refrigerator.getR_Ingredient();
		String[] r_Ingredient = r_ingredient.split(",");
		model.addAttribute("r_Ingredient", r_Ingredient);
		model.addAttribute("dataSize", r_Ingredient.length);
		}	
		return "refrigerator/myRefrigeratorView";
	}
	
	

	@RequestMapping(value="/refrigeratorFindComplete", method=RequestMethod.POST)
	public String refrigeratorFind(
			@RequestParam(value="r_Ingredient") String[] r_Ingredient,
			Model model
			){
								
		List<ArrayList<Refrigerator>> listAll = new ArrayList<ArrayList<Refrigerator>>();
		ArrayList<Refrigerator> list = new ArrayList<Refrigerator>(); 
		RefrigeratorMapper refrigeratorMap = sqlSession.getMapper(RefrigeratorMapper.class);
		
		int i=0;
		for(i=0; i<r_Ingredient.length; i++){
			String main_Ingredient = r_Ingredient[i];
			System.out.println("==="+main_Ingredient);
			try {
				
				list = refrigeratorMap.searchRefrigerator(main_Ingredient);
				listAll.add(list);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		model.addAttribute("listAll", listAll);
		model.addAttribute("allSize", r_Ingredient.length);
		
		return "refrigerator/refrigeratorFindComplete";
	};

}
