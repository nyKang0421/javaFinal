package dto;

public class Board {
	private static int num;
	private int boardNum;
	private String title;
	private String id;
	private String date;
	private String contents;
	private int hits;
	
	
	public static int getNum() {
		return num;
	}
	public static void setNum(int num) {
		Board.num = num;
	}
	public int getBoardNum() {
		return boardNum;
	}
	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public void printAPage() {
		System.out.printf("(등록번호:%3s) 제목 : %-10s작성자 : %-10s날짜 : %-12s조회수 : %-3s\n",boardNum,title,id,date,hits);
		
	}
	@Override
	public String toString() {
		return String.format("%s/%s/%s/%s/%s/%s\n",boardNum,title,id,date,contents,hits);
	}
	public Board(String title, String id, String date, String contents) {
		boardNum = ++num;
		this.title = title;
		this.id = id;
		this.date = date;
		this.contents = contents;
		this.hits = 0;
	}
	public Board(int boardNum,String title, String id, String date, String contents,int hits) {
		this.boardNum = boardNum;
		this.title = title;
		this.id = id;
		this.date = date;
		this.contents = contents;
		this.hits = hits;
	}
	public String getAPage() {
		return String.format("(%3s)제목 : %-10s작성자 : %-10s날짜 : %-12s조회수 : %-3s\n %s\n",boardNum,title,id,date,hits,contents);
	}
	

}
