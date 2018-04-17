package com.mydev.springboot.repo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.mydev.springboot.model.BlackListUser;

@Repository
public class BlackListUserRepositoryImpl extends JdbcDaoSupport implements BlackListUserRepository  {
	
	@Autowired
	public void SetDataSource(DataSource dataSource) {
		this.setDataSource(dataSource);
	}
	
	/**
	 * Converts a UUID to bytes
	 * @param id
	 * @return
	 */
	private static byte[] fromUuid(UUID id) {
		byte[] uuidBytes = new byte[16];
		
		ByteBuffer.wrap(uuidBytes)
					.order(ByteOrder.BIG_ENDIAN)
					.putLong(id.getMostSignificantBits())
					.putLong(id.getLeastSignificantBits());
		
		return uuidBytes;
	}
	
	@Override
	public BlackListUser findById(UUID userId) {
		
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate()).withProcedureName("sp_blacklist_user_get");
		
		byte[] idBinary = fromUuid(userId);
		
		Map<String, Object> pIn = new HashMap<String, Object>();
		
		pIn.put("p_UserId", idBinary);
		
		SqlParameterSource sIn = new MapSqlParameterSource(pIn);
		
		simpleJdbcCall.returningResultSet("results", new RowMapper<BlackListUser>() {

			@Override
			public BlackListUser mapRow(ResultSet rs, int index) throws SQLException {
				BlackListUser blackListUser = new BlackListUser();
				 
				blackListUser.setUserId(userId);
				blackListUser.setFullName(rs.getString("FullName"));
				blackListUser.setPhoneNumber(rs.getString("PhoneNumber"));
				blackListUser.setFbUrl(rs.getString("FbUrl"));
				blackListUser.setImageName(rs.getString("ImageName"));
				blackListUser.setReason(rs.getString("Reason"));
				blackListUser.setSalary(rs.getDouble("Salary"));
				return blackListUser;
			}			
		});
		
		try {
			Map<String, Object> results = simpleJdbcCall.execute(sIn);
			
			if (results != null) {
				@SuppressWarnings("unchecked")
				List<BlackListUser> BlackListusers = (List<BlackListUser>)results.get("results");
				
				if (!BlackListusers.isEmpty()) {
					return BlackListusers.get(0);
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}	
	
	@Override
	public void save(BlackListUser blackListUser) {
		
		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate()).withProcedureName("sp_blacklist_user_save");
		
		byte[] uuidUserId = fromUuid(blackListUser.getUserId());
		
		Map<String,Object>  pIn  = new HashMap<String,Object>();
		
		pIn.put("p_UserId",  uuidUserId);
		pIn.put("p_FullName", blackListUser.getFullName());
		pIn.put("p_PhoneNumber", blackListUser.getPhoneNumber());
		pIn.put("p_FbUrl", blackListUser.getFbUrl());
		pIn.put("p_ImageName", blackListUser.getImageName());
		pIn.put("p_Reason", blackListUser.getReason());
		pIn.put("p_Salary", blackListUser.getSalary());
		
		SqlParameterSource sIn = new MapSqlParameterSource(pIn);
		
		try {
			 simpleJdbcCall.execute(sIn);			
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void delete(UUID userId) {

		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(this.getJdbcTemplate()).withProcedureName("sp_blacklist_user_delete");
		
		byte[] idBinary = fromUuid(userId);
		
		SqlParameterSource inPara = new MapSqlParameterSource().addValue("p_UserId", idBinary);
		
		try {
			simpleJdbcCall.execute(inPara);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}	
}