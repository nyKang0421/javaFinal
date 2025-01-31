package menu_admin;

import controller.MallController;
import dao.CartDAO;
import dao.MemberDAO;
import mall.MenuCommand;
import util.Util;

public class AdminMember implements MenuCommand {
	private MallController cont;
	private Util util;
	private MemberDAO mdao;
	private CartDAO cdao;

	@Override
	public void init() {
		cont = MallController.getInstance();
		util = Util.getInterface();
		cdao = CartDAO.getInstance();
		mdao = MemberDAO.getInstance();
		System.out.println("=====[ 관리자 회원관리 ]=====");
		System.out.println("[1] 회원목록\n[2] 회원삭제\n[3] 뒤로가기\n[0] 종료");
		System.out.println("=====================");
		
	}

	@Override
	public boolean update() {
		int sel = util.getIntValue("메뉴", 0, 3);
		if (sel == 0) {
			System.out.println("[ 프로그램 종료 ]");
			cont.setNext(null);
		}else if (sel == 1) {
			mdao.printAllMember();
			cont.setNext("AdminMember");

		}else if (sel == 2) {
			if(mdao.getListSize() <= 1) {
				util.printErr("회원이 존재하지 않습니다");
			}else{
				System.out.println("회원 삭제시 구매내역이 사라집니다");
				String id = util.getStrValue("아이디");
				if(id == null) return false;
				if (id.equals("admin")) {
					util.printErr("관리자는 삭제 불가합니다");
				}else {
					if(mdao.removeAMember(id))
					cdao.removeCart(id);
				}
			}
			cont.setNext("AdminMember");
			
		}else if (sel == 3) {
			util.printMsg("관리자 메뉴로 돌아갑니다");
			cont.setNext("AdminMain");

		}
		
		return false;
	}

}
