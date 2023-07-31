package auth.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

public class SetService {
	private MemberDao memberDao = new MemberDao();
	
	public auth.service.User login(String id, String password) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			Member member = memberDao.selectById(conn, id);
			if(member == null) {
				throw new LoginFailException();
			}
			//일치하면 true리턴, 불일치하면 false리턴
			if(!member.matchPassword(password)) {
				throw new LoginFailException();
			}
			return new auth.service.User(member.getMember_id(), member.getMember_name());
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}finally {
			JdbcUtil.close(conn);
		}
	}//login
		
	}
//class
