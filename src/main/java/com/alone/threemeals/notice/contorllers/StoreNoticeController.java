package com.alone.threemeals.notice.contorllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alone.threemeals.mapper.StoreNoticeMapper;
import com.alone.threemeals.notice.vo.StoreNotice;
import com.alone.threemeals.notice.vo.StoreNoticeComment;

@Controller
public class StoreNoticeController {
	@Autowired
	SqlSession sqlSession;
	@Autowired
	StoreNotice storeNotice;
	@Autowired
	StoreNoticeComment storeNoticeComment;

	/** 편의점 평가 게시판 글쓰기 화면 */
	@RequestMapping(value="/storeNoticeRegView", method=RequestMethod.POST)
	public String storeNoticeRegView(){
		return "storenotice/storeNoticeRegView";
	}

	/** 편의점 평가 글쓰기 기능 */
	@RequestMapping(value="/storeNoticeRegProcess", method=RequestMethod.POST)
	public String storeNoticeRegProcess(
			@RequestParam(value="n_Title") String n_Title,
			@RequestParam(value="n_Writer") String n_Writer,
			@RequestParam(value="n_File") CommonsMultipartFile n_File,
			@RequestParam(value="star") String star,
			@RequestParam(value="n_Content") String n_Content,
			HttpServletRequest request,
			Model model 
			){

		int n_Star = Integer.parseInt(star);
		storeNotice.setN_Title(n_Title);
		storeNotice.setN_Writer(n_Writer);
		storeNotice.setN_File(n_File);
		storeNotice.setN_Star(n_Star);
		storeNotice.setN_Content(n_Content);
		String fileName = storeNotice.getN_File().getOriginalFilename();
		long time = System.currentTimeMillis();
		String fileOld[] = fileName.split("\\.");
		fileName = fileOld[0]+"_"+time+"."+fileOld[1];
		storeNotice.setN_Picture(fileName);
		String realPath = request.getServletContext().getRealPath("/upload");
		String path = realPath+"\\"+fileName;


		FileOutputStream fs = null;
		try{
			fs = new FileOutputStream(path);
			fs.write(storeNotice.getN_File().getBytes());
			if(fs != null) fs.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		StoreNoticeMapper storeMap = sqlSession.getMapper(StoreNoticeMapper.class);

		try {
			storeMap.insertStoreNotice(storeNotice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:storeNoticeList";
	}

	/** 편의점 평가 게시판 리스트 */
	@RequestMapping(value="/storeNoticeList", method=RequestMethod.GET)
	public String storeNoticeList( 
			String find, String qtext, String currPage, HttpServletRequest request){

		System.out.println(find + ", " + qtext + " , " + currPage);


		if(find == null) find="n_Writer";
		if(qtext == null) qtext="";

		int tempPage = 0;

		if(currPage == null || currPage.equals(""))
			tempPage = 1;
		else 
			tempPage = Integer.parseInt(currPage);

		int totalPages = 1;

		int srow = 1+(tempPage-1)*6;
		int erow = 6+(tempPage-1)*6;

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(date);

		Map<String, Object> findMap = new HashMap<String, Object>();
		findMap.put("find", find);
		findMap.put("qtext", qtext);
		findMap.put("srow", srow);
		findMap.put("erow", erow);

		StoreNoticeMapper storeMap = sqlSession.getMapper(StoreNoticeMapper.class);
		List<StoreNotice> storelist=null;

		try {
			storelist = storeMap.selectAllStore(findMap);
			totalPages = storeMap.getCount(findMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(totalPages%6 != 0)
			totalPages = totalPages/6+1;
		else
			totalPages = totalPages/6;

		request.setAttribute("find", find);
		request.setAttribute("qtext", qtext);
		request.setAttribute("today", today);
		request.setAttribute("currPage", tempPage);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("storelist", storelist);

		return "storenotice/storeNoticeList";
	}

	/** 편의점 평가 게시판 글보기 */
	@RequestMapping(value="/storeNoticeDetail", method=RequestMethod.GET)
	public String storeNoticeDetail(@RequestParam(value="n_Seq") String seq,
			String currPage,
			Model model){
		StoreNoticeMapper storeMap = sqlSession.getMapper(StoreNoticeMapper.class);
		List<StoreNoticeComment> storeCommentList = null;
		int n_Seq = Integer.parseInt(seq);
		int grok=0;
		int nongrok=0;
		
		int tempPage = 0;
		if(currPage == null || currPage.equals(""))
			tempPage = 1;
		else{
			tempPage = Integer.parseInt(currPage);
		}
		
		int totalPages = 1;
		int srow = 1+(tempPage-1)*4;
		int erow = 4+(tempPage-1)*4;
		
		Map<String, Integer> pageMap = new HashMap<String, Integer>();
		
		pageMap.put("n_Seq",n_Seq);
		pageMap.put("srow",srow);
		pageMap.put("erow",erow);
		
		try {
			grok = storeMap.grokCount(n_Seq);
			nongrok= storeMap.nongrokCount(n_Seq);
			storeMap.updateHit(n_Seq);
			storeNotice = storeMap.selectOneStore(n_Seq);
			storeCommentList = storeMap.selectAllStoreComment(pageMap);
			totalPages = storeMap.commentGetCount(n_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(totalPages%4 != 0)
			totalPages = totalPages/4+1;
		else
			totalPages = totalPages/4;
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateview = sdf.format(storeNotice.getN_RegDate());
		
		
		List<String> dateList_ymd = new ArrayList<String>();
		SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");		
		for(int i=0;i<storeCommentList.size();i++){			
			dateList_ymd.add(i, ymd.format(storeCommentList.get(i).getC_RegDate()));
		};				
		List<String> dateList_hm = new ArrayList<String>();
		SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
		for(int i=0;i<storeCommentList.size();i++){			
			dateList_hm.add(i, hm.format(storeCommentList.get(i).getC_RegDate()));
		};		
		Date date = new Date();
		String today = ymd.format(date);		
		
		model.addAttribute("dateList_ymd", dateList_ymd);
		model.addAttribute("dateList_hm", dateList_hm);
		model.addAttribute("today", today);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("grok", grok);
		model.addAttribute("nongrok", nongrok);
		model.addAttribute("dateview", dateview);
		model.addAttribute("store", storeNotice);
		model.addAttribute("storeCommentList", storeCommentList);
		return "storenotice/storeNoticeDetail";
	}

	/** 편의점 평가 글 수정화면 */
	@RequestMapping(value="/storeNoticeUpdateView", method=RequestMethod.GET)
	public String storeNoticeUpdateView(@RequestParam(value="n_Seq") String seq, Model model){
		StoreNoticeMapper storeMap = sqlSession.getMapper(StoreNoticeMapper.class);		
		int n_Seq = Integer.parseInt(seq);
		try {
			storeNotice = storeMap.selectOneStore(n_Seq);		
		} catch (Exception e) {
			e.printStackTrace();
		}		
		model.addAttribute("store", storeNotice);
		return "storenotice/storeNoticeUpdateView";
	}


	/** 편의점 평가 글 수정기능 */
	@RequestMapping(value="/storeNoticeUpdateProcess", method=RequestMethod.POST)
	public String storeNoticeUpdateProcess(
			@RequestParam(value="n_Seq") String seq,
			@RequestParam(value="n_Title") String n_Title,
			@RequestParam(value="n_File") CommonsMultipartFile n_File,
			@RequestParam(value="star") String star,
			@RequestParam(value="n_Content") String n_Content,
			HttpServletRequest request,
			Model model
			){
		StoreNoticeMapper storeMap = sqlSession.getMapper(StoreNoticeMapper.class);
		StoreNotice storeChoice = null;
		int n_Seq = Integer.parseInt(seq);
		int n_Star = Integer.parseInt(star);

		try {
			storeChoice = storeMap.selectOneStore(n_Seq);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		storeNotice.setN_Seq(n_Seq);
		storeNotice.setN_Title(n_Title);
		storeNotice.setN_Star(n_Star);
		storeNotice.setN_Content(n_Content);

		storeNotice.setN_File(n_File);
		String fileName = storeNotice.getN_File().getOriginalFilename();

		if(fileName.equals("")){
			storeNotice.setN_Picture(storeChoice.getN_Picture());
		}
		else{
			long time = System.currentTimeMillis();
			String fileOld[] = fileName.split("\\.");
			fileName = fileOld[0]+"_"+time+"."+fileOld[1];
			storeNotice.setN_Picture(fileName);
			String realPath = request.getServletContext().getRealPath("/upload");
			String path = realPath+"\\"+fileName;
			String delPath = realPath+"\\"+storeChoice.getN_Picture();
			FileOutputStream fs = null;
			File file = new File(delPath);
			file.delete();

			try{
				fs = new FileOutputStream(path);
				fs.write(storeNotice.getN_File().getBytes());
				if(fs != null) fs.close();
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}

		try {
			storeMap.updateStore(storeNotice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:storeNoticeDetail?n_Seq="+n_Seq;
	};



	/** 편의점 평가 게시판 글 삭제 */
	@RequestMapping(value="/storeNoticeDelete", method=RequestMethod.GET)
	public String storeNoticeDelete(
			@RequestParam(value="n_Seq") String n_seq
			){
		int cnt =0;
		int n_Seq = Integer.parseInt(n_seq);
		StoreNoticeMapper storeMap = sqlSession.getMapper(StoreNoticeMapper.class);
		
		try {
			cnt = storeMap.commentGetCount(n_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(cnt != 0){
			try {
				storeMap.deleteCommentByNotice(n_Seq);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			storeMap.deleteStore(n_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:storeNoticeList";
	}
	
	/** 댓글 추가 */
	@RequestMapping(value="/storeCommentRegProcess", method=RequestMethod.POST)
	public void storeCommentRegProcess(
			@RequestParam(value="n_Seq") String n_seq,
			@RequestParam(value="c_Writer") String c_Writer,
			@RequestParam(value="c_Content") String c_Content,
			@RequestParam(value="c_Grok") String c_Grok,
			HttpServletResponse response
			){
		PrintWriter pw =null;

		int n_Seq = Integer.parseInt(n_seq);
		storeNoticeComment.setN_Seq(n_Seq);
		storeNoticeComment.setC_Writer(c_Writer);
		storeNoticeComment.setC_Content(c_Content);
		storeNoticeComment.setC_Grok(c_Grok);

		StoreNoticeMapper storeMap = sqlSession.getMapper(StoreNoticeMapper.class);

		System.out.println("//////"+storeNoticeComment);
		try {
			storeMap.minusHit(n_Seq);
			storeMap.insertStoreComment(storeNoticeComment);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			pw = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.print("success");
	}

	
	
	
	/** 댓글 삭제 */
	@RequestMapping(value="/storeCommentDelete", method=RequestMethod.POST)
	public void storeCommentDelete(
			@RequestParam(value="n_Seq") String n_seq,
			@RequestParam(value="c_Seq") String c_seq,
			HttpServletResponse response
			){
		PrintWriter pw =null;

		int n_Seq = Integer.parseInt(n_seq);
		int c_Seq = Integer.parseInt(c_seq);

		StoreNoticeMapper storeMap = sqlSession.getMapper(StoreNoticeMapper.class);

		try {
			storeMap.minusHit(n_Seq);
			storeMap.deleteStoreComment(c_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			pw = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.print("success");
	}

	/** 댓글 수정 */
	@RequestMapping(value="/storeCommentUpdate", method=RequestMethod.POST)
	public void storeCommentUpdate(		
			@RequestParam(value="n_Seq") String n_seq,
			@RequestParam(value="c_Seq") String c_seq,
			@RequestParam(value="c_Content") String c_Content,
			@RequestParam(value="c_Grok") String c_Grok,
			HttpServletResponse response
			){
		PrintWriter pw =null;

		int n_Seq = Integer.parseInt(n_seq);
		int c_Seq = Integer.parseInt(c_seq);
		storeNoticeComment.setC_Seq(c_Seq);
		storeNoticeComment.setC_Content(c_Content);
		storeNoticeComment.setC_Grok(c_Grok);

		StoreNoticeMapper storeMap = sqlSession.getMapper(StoreNoticeMapper.class);

		try {
			storeMap.minusHit(n_Seq);
			storeMap.updateStoreComment(storeNoticeComment);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			pw = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.print("success");
	}


}