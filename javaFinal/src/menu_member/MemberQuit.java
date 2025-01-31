package menu_member;

import controller.MallController;
import dao.CartDAO;
import dao.MemberDAO;
import mall.MenuCommand;
import util.Util;

public class MemberQuit implements MenuCommand {
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
		System.out.println("=====[ 회원 탈퇴 ]=====");
		
	}

	@Override
	public boolean update() {
		String pw = util.getStrValue("비밀번호");
		if(pw != null) {
			if(mdao.isValidMember(cont.getLoginId(), pw)!=null) {
				if(mdao.removeAMember(cont.getLoginId())) {
					cdao.removeCart(cont.getLoginId());
					cont.setNext("MallMain");
					util.printMsg("탈퇴 완료");
					cont.setLoginId(null);
				}
			}else {
				util.printErr("탈퇴 실패");
				cont.setNext("MemberMain");
			}
		}
		
		return false;
	}

}
