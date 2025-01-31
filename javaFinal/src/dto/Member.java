package dto;

public class Member {
	private static int num = 1000;
	private int memberNum;
	private String id;
	private String pw;
	private String memberName;
	public Member(String id, String pw, String name) {
		memberNum = num++;
		this.id = id;
		this.pw = pw;
		memberName = name;
		//System.out.println(num);
	}
	public Member(int memberNum,String id, String pw, String name) {
		this.memberNum = memberNum;
		this.id = id;
		this.pw = pw;
		memberName = name;
		//System.out.println(num);
	}
	
	public static int getNum() {
		return num;
	}
	public static void setNum(int num) {
		Member.num = num;
	}
	public int getMemberNum() {
		return memberNum;
	}
	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	@Override
	public String toString() {
		return String.format("%s/%s/%s/%s\n", memberNum,id,pw,memberName);
	}
	public void printAMember() {
		System.out.printf("[%6s][%15s][%15s][%10s]\n",memberNum,id,pw,memberName);
		
	}
	
	
	

}
