package dao;

import java.util.ArrayList;

import dto.Board;
import dto.Member;
import util.Util;

public class MemberDAO {
	
	private ArrayList<Member> memberList;
	Util util = Util.getInterface();
	
	private MemberDAO() {
		memberList = new ArrayList();
		memberList.add(new Member("admin","admin","관리자"));
	}
	private static MemberDAO instance = new MemberDAO();
	public static MemberDAO getInstance() {
		return instance;
	}
	public int getListSize() {
		return memberList.size();
	}
	public Object getMemberById(String id) {
		if(memberList == null || memberList.size() == 0) return null;
		for(int i = 0; i<memberList.size();i++) {
			if(memberList.get(i).getId().equals(id)) return i;
		}
		return null;
	}
	public boolean insertMember(String id, String pw, String name) {
		memberList.add(new Member(id,pw,name));
		Member m = memberList.get(memberList.size()-1);
		System.out.printf("MemberNum : %s/ID : %s/PW : %s/MemberName : %s\n",
				m.getMemberNum(),m.getId(),m.getPw(),m.getMemberName());
		return true;
	}
	public Object isValidMember(String id, String pw) {
		if(getMemberById(id) == null) return null;
		if(memberList.get((int) getMemberById(id)).getPw().equals(pw)) return "Ok";
		return null;
	}
	public void printAllMember() {
		if(memberList.size() == 0) {
			util.printMsg("no Item data");
			return;
		}
		for(Member m : memberList)
			m.printAMember();
		
	}
	public boolean removeAMember(String id) {
		for(int i = 0; i<memberList.size();i++) {
			if(memberList.get(i).getId().equals(id)) {
				memberList.remove(i);
				util.printMsg("회원 가입내역 삭제 성공");
				return true;
			}
		}
		util.printErr("회원 삭제 실패");
		return false;
	}
	public void ModifyPw(String loginId) {
		int idx = checkIdx(loginId);
		if(idx == -1) {
			util.printErr("진행 오류");
			return;
		}
		memberList.get(idx).printAMember();
		String pw = util.getStrValue("비밀번호");
		if(!memberList.get(idx).getPw().equals(pw)) {
			util.printErr("PW 입력 오류");
			return;
		}
		String newPw = util.getStrValue("새 비밀번호");
		if(newPw == null) return;
		if(newPw.equals(pw)) {
			util.printErr("기존과 동일합니다");
			return;
		}
		memberList.get(idx).setPw(newPw);
		memberList.get(idx).printAMember();
		util.printMsg("회원정보 수정 완료");
	}
	private int checkIdx(String loginId) {
		int idx = -1;
		for(int i = 0; i<memberList.size(); i++) {
			if(memberList.get(i).getId().equals(loginId)) {
				idx = i;
			}
		}
		return idx;
	}
	public String getUpdateData() {
		String s = "";
		if(memberList == null||memberList.size()==0) return s;
		for(Member m : memberList) {
			s += m;
		}
		return s;
	}
	public void upLoadData(String newData) {
		if(newData == null || newData.equals("")) {
			util.printErr("불러온 데이터가 없습니다");
			return;
		}
		String[] temp = newData.split("\n");
		memberList.clear();
		int lastNum = 0;
		for(String s : temp) {
			String[] info = s.split("/");
			memberList.add(new Member(Integer.parseInt(info[0]),info[1],info[2],info[3]));
			lastNum = Integer.parseInt(info[0]);
			//System.out.println(info[0]);
		}
		Member.setNum(lastNum+1);
		util.printMsg("데이터 불러오기 완료");
		
	}
	
	
	

}
