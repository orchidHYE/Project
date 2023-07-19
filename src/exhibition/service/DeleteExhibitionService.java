package exhibition.service;

import java.sql.Connection;
import java.sql.SQLException;

import exhibition.dao.ExhibitionDAO;
import exhibition.exception.ArticleNotFoundException;
import exhibition.model.Exhibition;
import jdbc.JDBCUtil;
import jdbc.connection.ConnectionProvider;

public class DeleteExhibitionService  {
	
	ExhibitionDAO exhibitionDAO = new ExhibitionDAO();
	
	//delete쿼리를 통한 글삭제
	public void delete(int no) throws ArticleNotFoundException {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			//1.article테이블에 delete하기전 해당글번호 가져오기
			Exhibition exhibition= exhibitionDAO.getDetail(conn, no);
			if(exhibition==null) {
				throw new ArticleNotFoundException();
			}
			
			//2.article테이블에 delete하는 메서드호출
			exhibitionDAO.delete(conn, no);
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			JDBCUtil.rollback(conn);
			throw new RuntimeException();
		}finally {
			JDBCUtil.close(conn);
		}
		
	}
}