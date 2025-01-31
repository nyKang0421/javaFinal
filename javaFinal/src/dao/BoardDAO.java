package dao;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import controller.MallController;
import dto.Board;
import util.Util;


public class BoardDAO {
	
	private ArrayList<Board> boardList;
	private Util util = Util.getInterface();
	private MallController cont = MallController.getInstance();
	private final int PAGE_SIZE = 5;
	
	private BoardDAO() {
		boardList = new ArrayList<>();
	}
	private static BoardDAO instance = new BoardDAO();
	public static BoardDAO getInstance() {
		return instance;
	}
	public void showBoard() {
		if(boardList == null||boardList.size() == 0) {
			util.printErr("현재 존재하는 게시글이 없습니다");
			return;
		}
		int totalPage = boardList.size()/PAGE_SIZE+1;
		totalPage = boardList.size() != 0 && boardList.size()%5 ==0? totalPage-1:totalPage;
		boardControll(totalPage);
	}
	
	
	
	private void boardControll(int totalPage) {
		System.out.printf("총 게시글 %d 개\n",boardList.size());
		int curPage = 1;
		int lastNum = curPage*5<boardList.size()?curPage*5:boardList.size();
		while(true) {
			System.out.printf("현재페이지 [%d / %d]\n",curPage, totalPage);
			for(int i = (curPage-1)*5; i<lastNum; i++) {
				System.out.printf("[%3d]",i+1);
				boardList.get(i).printAPage();
			}
			System.out.println("[1] 이전\n[2] 이후\n[3] 게시글보기\n[4] 게시판 나가기\n[0] 종료");
			int sel = util.getIntValue("메뉴", 0, 4);
			if (sel == 0) {
				System.out.println("[ 프로그램 종료 ]");
				cont.setNext(null);
				break;
			}else if (sel == 1) {
				if(curPage == 1) {
					util.printErr("첫 페이지 입니다");
					continue;
				}
				curPage--;
			}else if (sel == 2) {
				if(curPage == totalPage) {
					util.printErr("마지막 페이지 입니다");
					continue;
				}
				curPage++;
			}else if (sel == 3) {
				int num = util.getIntValue("글 번호",(curPage-1)*5+1, lastNum)-1;
				if(num == -2) continue;
				boardList.get(num).printAPage();
				util.printMsg("게시글내용");
				System.out.println(boardList.get(num).getContents());
				System.out.println("===============================");
				boardList.get(num).setHits(boardList.get(num).getHits()+1);
			}else if (sel == 4) {
				util.printMsg("이전 메뉴로 돌아갑니다");
				break;
			}
			lastNum = curPage*5<boardList.size()?curPage*5:boardList.size();
			
		}
		
	}

	public void insertBoard() {
		String title = util.getStrValue("제목");
		if(title == null) return;
		String contents = util.getStrValue("내용");
		if(contents == null) return;
		String date = LocalDate.now().toString();
		
		boardList.add(new Board(title, cont.getLoginId(),date,contents));
		util.printMsg("게시글 등록 완료");
	}
	
	public void deleteBoard() {
		if(boardList == null||boardList.size() == 0) {
			util.printErr("현재 존재하는 게시글이 없습니다");
			return;
		}
		ArrayList<Board> myList = checkBoard(cont.getLoginId());
		if(myList.size() == 0) {
			util.printErr("등록한 게시글이 없습니다");
			return;
		}
		System.out.println("------------ 내 게시글 목록 ----------------");
		for(int i = 0; i<myList.size();i++) {
			myList.get(i).printAPage();
			System.out.println(myList.get(i).getContents());
			System.out.println("-----------------------------------------");
		}
		int delNum = util.getIntValue("게시글 번호", myList.get(0).getBoardNum(), myList.get(myList.size()-1).getBoardNum());
		if(delNum == -1) return;
		int delIdx = -1;
		for(int i = 0; i<boardList.size();i++) {
			if(delNum == boardList.get(i).getBoardNum()&& cont.getLoginId().equals(boardList.get(i).getId())) {
				delIdx = i;
			}
		}
		if(delIdx == -1) {
			util.printErr("본인 글이 아니거나 해당 번호의 글이 존재하지 않습니다");
			return;
		}
		boardList.remove(delIdx);
		util.printMsg("게시글 삭제 완료");
	}
	private ArrayList<Board> checkBoard(String loginId) {
		ArrayList<Board> temp = new ArrayList();
		for(int i = 0; i<boardList.size(); i++) {
			if(boardList.get(i).getId().equals(loginId)) {
				temp.add(boardList.get(i));
			}
		}
		return temp;
	}
	public String getUpdateData() {
		String s = "";
		if(boardList == null||boardList.size()==0) return s;
		for(Board b : boardList) {
			s += b;
		}
		return s;
	}
	public void upLoadData(String newData) {
		if(newData == null || newData.equals("")) {
			util.printErr("불러온 데이터가 없습니다");
			return;
		}
		String[] temp = newData.split("\n");
		boardList.clear();
		int lastNum = 0;
		for(String s : temp) {
			String[] info = s.split("/");
			boardList.add(new Board(Integer.parseInt(info[0]),info[1],info[2],info[3],info[4],Integer.parseInt(info[5])));
			lastNum = Integer.parseInt(info[0]);
		}
		Board.setNum(lastNum);
		util.printMsg("데이터 불러오기 완료");
		
	}
	public void deleteBoardforAdmin() {
		if(boardList == null||boardList.size() == 0) {
			util.printErr("현재 존재하는 게시글이 없습니다");
			return;
		}
		String id = util.getStrValue("회원 아이디");
		if(id == null) return;
		ArrayList<Board> memberBoardList = checkBoard(id);
		if(memberBoardList.size() == 0) {
			util.printErr("등록한 게시글이 없습니다");
			util.printErr("회원 아이디를 확인해주세요");
			return;
		}
		System.out.printf("------------ %s 목록 ----------------\n",id);
		for(int i = 0; i<memberBoardList.size();i++) {
			memberBoardList.get(i).printAPage();
			System.out.println(memberBoardList.get(i).getContents());
			System.out.println("-----------------------------------------");
		}
		int delNum = util.getIntValue("게시글 번호", memberBoardList.get(0).getBoardNum(), memberBoardList.get(memberBoardList.size()-1).getBoardNum());
		if(delNum == -1) return;
		int delIdx = -1;
		for(int i = 0; i<boardList.size();i++) {
			if(delNum == boardList.get(i).getBoardNum()&& id.equals(boardList.get(i).getId())) {
				delIdx = i;
			}
		}
		if(delIdx == -1) {
			util.printErr("해당 회원의 글이 아니거나 해당 번호의 글이 존재하지 않습니다");
			return;
		}
		boardList.remove(delIdx);
		util.printMsg("게시글 삭제 완료");
		
	}

}
