package com.alone.threemeals.mapper;

import java.util.Map;

import com.alone.threemeals.member.vo.MyMember;

public interface MemberMapper {
	public int insertMember(MyMember myMember) throws Exception;
	public MyMember selectMember(String m_Id) throws Exception;
	public int deleteMember(String m_Id) throws Exception;
	public MyMember idSearch(Map<String, String> idSearchMap) throws Exception;
	public int updateMember(Map<String, String> updateMemberMap) throws Exception;
}
