package com.alone.threemeals.member.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.alone.threemeals.mapper.MemberMapper;
import com.alone.threemeals.mapper.RefrigeratorMapper;
import com.alone.threemeals.member.vo.MyMember;

@Controller
public class MemberController {
   @Autowired
   SqlSession sqlSession;
   @Autowired
   MyMember myMember;
   
   /** index */
   @RequestMapping(value="/", method=RequestMethod.GET)
   public String index(){
      return "index";
   }
   @RequestMapping(value="/", method=RequestMethod.POST)
   public String home(){
      return "index";
   }
   
   /** 회원가입 화면 */
   @RequestMapping(value="/memberRegView", method=RequestMethod.GET)
   public String memberRegView(){
      return "member/memberRegView";
   }
   
   
   /** 회원가입 기능 */
   @RequestMapping(value="/memberRegProcess", method=RequestMethod.POST)
   public String memberRegProcess(
            @RequestParam(value="m_Id") String m_Id,
            @RequestParam(value="m_Pwd") String m_Pwd,
            @RequestParam(value="m_Name") String m_Name,
            @RequestParam(value="m_BirthYear") String m_BirthYear,
            @RequestParam(value="m_BirthMonth") String m_BirthMonth,
            @RequestParam(value="m_BirthDay") String m_BirthDay,
            @RequestParam(value="m_Phone") String m_Phone,
            @RequestParam(value="pwd_Question") String pwd_Question,
            @RequestParam(value="pwd_Answer") String pwd_Answer,
            @RequestParam(value="m_Gender") String m_Gender,
            @RequestParam(value="email") String email,
            Model model
         ){
      
      String m_Birth = m_BirthYear+"년"+m_BirthMonth+"월"+m_BirthDay+"일";

      System.out.println(m_Id+" "+m_Pwd+" "+m_Name+" "+m_Birth+" "+m_Phone+" "+pwd_Question+" "+pwd_Answer+" "+m_Gender+" "+email);
            
      myMember.setM_Id(m_Id);
      myMember.setM_Pwd(m_Pwd);
       myMember.setM_Name(m_Name);
       myMember.setM_Birth(m_Birth);
       myMember.setM_Phone(m_Phone);
       myMember.setPwd_Question(pwd_Question);
       myMember.setPwd_Answer(pwd_Answer);
       myMember.setM_Gender(m_Gender);
       myMember.setEmail(email);
         

      MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
      
      try {
         memberMapper.insertMember(myMember);
      } catch (Exception e) {
         e.printStackTrace();
      }
      model.addAttribute("joinId", m_Id);
      model.addAttribute("joinName", m_Name);     
      
      return "member/memberRegComplete";
   }
   
   /**회원 탈퇴*/
   @RequestMapping(value="/memberOutProcess", method=RequestMethod.GET)
   public String memberOutProcess(HttpSession session){
      String m_Id = (String)session.getAttribute("m_Id");
      System.out.println(m_Id);
      MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
      RefrigeratorMapper refMapper = sqlSession.getMapper(RefrigeratorMapper.class);
      try {
    	  refMapper.deleteRefrigerator(m_Id);
    	  memberMapper.deleteMember(m_Id);
      } catch (Exception e) {
         e.printStackTrace();
      }
      session.invalidate();
      return "redirect:/";
   }
   
   
   /** 로그인 Process*/
   @RequestMapping(value="/loginProcess", method=RequestMethod.POST)
   public String loginProcess(){
      
	   return "redirect:/";
   }
   
   /** 로그인 체크 **/
   @RequestMapping(value="/loginCheck", method=RequestMethod.POST)
   public void loginCheck(
         @RequestParam(value="m_Id") String m_Id,
         @RequestParam(value="m_Pwd") String m_Pwd,
         HttpServletResponse response,
         HttpSession session
         ){
      response.setCharacterEncoding("UTF-8");
      MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
      PrintWriter pw =null;
      MyMember loginCk =null;
      try {
         loginCk = memberMapper.selectMember(m_Id);
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      try {
         pw = response.getWriter();
      } catch (IOException e) {
         e.printStackTrace();
      }
      
      if (loginCk != null && loginCk.getM_Pwd().equals(m_Pwd)) {
         pw.print("success");
         session.setAttribute("m_Id", m_Id);
      } else {
         pw.print("등록되지 않은 아이디이거나, 아이디 또는 비밀번호를 잘못 입력하셨습니다.");
      }

   }
   
   
   
   
   /** 로그아웃 Process*/
   @RequestMapping(value="/logoutProcess", method=RequestMethod.GET)
   public String logoutProcess(HttpSession session){
      session.invalidate();
      return "redirect:/";
   }
   
   
   /** 아이디 찾기 - Ajax **/
   @RequestMapping(value="/idSearch", method=RequestMethod.POST)
   public void idSearch(
         @RequestParam(value="m_Name") String m_Name,
         @RequestParam(value="m_Birth") String m_Birth,
         @RequestParam(value="m_Phone") String m_Phone,
         HttpServletResponse response
         ){

      response.setCharacterEncoding("UTF-8");
      MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
      PrintWriter pw = null;
      Map<String, String> idSearchMap = new HashMap<String, String>();
      idSearchMap.put("m_Name", m_Name);
      idSearchMap.put("m_Birth", m_Birth);
      idSearchMap.put("m_Phone", m_Phone);
      try {
         myMember = memberMapper.idSearch(idSearchMap);
      } catch (Exception e1) {
         e1.printStackTrace();
      }

      try {
         pw = response.getWriter();
      } catch (IOException e) {
         e.printStackTrace();
      }
      
      if (myMember == null) {
         pw.print("no");
      } else {
         pw.print(myMember.getM_Id());
      }
            
   }

   /** 중복검사 **/
   @RequestMapping(value="/idCheck", method=RequestMethod.POST)
   public void idCheck(
         @RequestParam(value="m_Id") String m_Id,
         HttpServletResponse response
         ){

      response.setCharacterEncoding("UTF-8");
      MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
      PrintWriter pw = null;

      
      MyMember memberCk= null;
      try {
         memberCk = memberMapper.selectMember(m_Id);
      } catch (Exception e1) {
         e1.printStackTrace();
      }

      
      try {
         pw = response.getWriter();
      } catch (IOException e) {
         e.printStackTrace();
      }
      
      
      if (memberCk == null) {
         pw.print("사용 가능한 아이디입니다.");
      } else {
         pw.print("존재하는 아이디입니다.");
      }
            
   }
   
   /** 내정보 보기*/
   @RequestMapping(value="/memberInfo", method=RequestMethod.GET)
   public String memberInfo(HttpSession session, Model model){
      MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
      String m_Id = (String)session.getAttribute("m_Id");
      
      try {
         myMember = memberMapper.selectMember(m_Id);
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      model.addAttribute("myMember", myMember);
      
      return "member/memberInfo";
   }
   
   /** 내정보 수정보기*/
   @RequestMapping(value="/memberInfoUpdateView", method=RequestMethod.POST)
   public String memberInfoUpdateView(HttpSession session, Model model){
      MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
      String m_Id = (String)session.getAttribute("m_Id");
      
      try {
         myMember = memberMapper.selectMember(m_Id);
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      model.addAttribute("myMember", myMember);
      
      return "member/memberInfoUpdate";
   }
   
   
   /** 내정보 수정하기*/
   @RequestMapping(value="/memberInfoUpdateProcess", method=RequestMethod.POST)
   public String memberInfoUpdateProcess(
           @RequestParam(value="m_Pwd") String m_Pwd,
           @RequestParam(value="pwd_Question") String pwd_Question,
           @RequestParam(value="pwd_Answer") String pwd_Answer,
           @RequestParam(value="m_Phone") String m_Phone,
           @RequestParam(value="email") String email,
		   HttpSession session, Model model){
      MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
      String m_Id = (String)session.getAttribute("m_Id");
      
      Map<String, String> updateMemberMap = new HashMap<String, String>();
      updateMemberMap.put("m_Id", m_Id);
      updateMemberMap.put("m_Pwd", m_Pwd);
      updateMemberMap.put("pwd_Question", pwd_Question);
      updateMemberMap.put("pwd_Answer", pwd_Answer);
      updateMemberMap.put("m_Phone", m_Phone);
      updateMemberMap.put("email", email);
      
      try {
         memberMapper.updateMember(updateMemberMap);
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
           
      return "redirect:memberInfo";
   }
   
   /** 아이디, 비밀번호 찾기 */
   @RequestMapping(value="/memberSearch", method=RequestMethod.GET)
   public String memberSearch(){
      return "member/memberSearch";
   }
   
   
   /** 비밀번호 찾기 - Ajax*/
   @RequestMapping(value="/pwdSearch", method=RequestMethod.POST)
   public void pwdSearch(
         @RequestParam(value="m_Id") String m_Id,
         @RequestParam(value="pwd_Question") String pwd_Question,
         @RequestParam(value="pwd_Answer") String pwd_Answer,
         HttpServletResponse response
      ){

      response.setCharacterEncoding("UTF-8");
      MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
      PrintWriter pw = null;
      MyMember pwdCk=null;      
      try {
         pwdCk = memberMapper.selectMember(m_Id);
      } catch (Exception e1) {
         e1.printStackTrace();
      }
      try {
         pw = response.getWriter();
      } catch (IOException e) {
         e.printStackTrace();
      }
      if (pwdCk == null) {
         pw.print("no");
      }else if((pwdCk != null)&&pwd_Question.equals(pwdCk.getPwd_Question()) && pwd_Answer.equals(pwdCk.getPwd_Answer())){
         pw.print("yes");
         
      } else{
         pw.print("no");
      }
               
   }
   
   
   /** 비밀 번호 찾기 메일 보내기 */
   @RequestMapping(value="/pwdSearchMailForm", method=RequestMethod.POST)
   public String pwdSearchMailForm(
            @RequestParam(value="M_Id") String m_Id,
            @RequestParam(value="Pwd_Question") String pwd_Question,
            @RequestParam(value="Pwd_Answer") String pwd_Answer,
            Model model
         ){
      
      MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
      
      try {
         myMember = memberMapper.selectMember(m_Id);
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      if((myMember != null)&&pwd_Question.equals(myMember.getPwd_Question()) && pwd_Answer.equals(myMember.getPwd_Answer())){
         model.addAttribute("myMember", myMember);
         
      }
      return "member/pwdSearchMailForm";
   }
   
   /** 비밀 번호 찾기 메일 보내기 */
   @RequestMapping(value="/pwdSearchsendMail", method=RequestMethod.POST)
   public String pwdSearchsendMail(){
      return "member/pwdSearchsendMail";
   }
   @RequestMapping(value="/sendMailComplete", method=RequestMethod.GET)
   public String sendMailComplete(){
      return "member/sendMailComplete";
   }
   
   /** star */
   @RequestMapping(value="/star", method=RequestMethod.GET)
   public String star(){
      return "member/star";
   }
   
   @RequestMapping(value="/starProcess", method=RequestMethod.GET)
   public void starProcess(
		   @RequestParam (value="star") String star
		   ){
	  System.out.println(star); 
      return;
   }
   
}