package controller;

import java.util.HashMap;
import java.util.Map;

import dao.FileDAO;
import mall.MenuCommand;
import menu_admin.AdminBoard;
import menu_admin.AdminCart;
import menu_admin.AdminFile;
import menu_admin.AdminItem;
import menu_admin.AdminMember;
import menu_admin._AdminMain;
import menu_mall.MallJoin;
import menu_mall.MallLogin;
import menu_mall._MallMain;
import menu_member.MemberBoard;
import menu_member.MemberCart;
import menu_member.MemberInfo;
import menu_member.MemberQuit;
import menu_member.MemberShopping;
import menu_member._MemberMain;

public class MallController {
	
	private MallController() {}
	private static MallController instance = new MallController();
	public static MallController getInstance() {
		return instance;
	}
	
	private String loginId;
	private String next;
	private MenuCommand menuCom;
	public Map<String, MenuCommand> mapCont; // Map으로 키워드 와 인터페이스 저장

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}




	public void init() {
		//FileDAO.getInstance().loadAllFiles();
		mapCont = new HashMap<>();
		// 키워드는 클래스 명을 저장 하고 , 실행 클래스는 인터페이스는 상속받은 클래스를 다운캐스팅으로 저장
		mapCont.put("MallMain", new _MallMain()); 
		mapCont.put("MallJoin", new MallJoin());
		mapCont.put("MallLogin", new MallLogin());
		mapCont.put("AdminMain", new _AdminMain());
		mapCont.put("AdminMember", new AdminMember());
		mapCont.put("AdminItem", new AdminItem());
		mapCont.put("AdminBoard", new AdminBoard());
		mapCont.put("AdminFile", new AdminFile());
		mapCont.put("AdminCart", new AdminCart());
		mapCont.put("MemberBoard", new MemberBoard());
		mapCont.put("MemberCart", new MemberCart());
		mapCont.put("MemberInfo", new MemberInfo());
		mapCont.put("MemberMain", new _MemberMain());
		mapCont.put("MemberShopping", new MemberShopping());
		mapCont.put("MemberQuit", new MemberQuit());
		
		menuCom = mapCont.get("MallMain");
		menuCom.init();
		update();
	}
	
	public void update() {
		while (true) {
			
			if (!menuCom.update()) {
				if (next != null) {
					menuCom = mapCont.get(next);
					menuCom.init();
				} else {
					return;
				}
			}
		}
	}

}
