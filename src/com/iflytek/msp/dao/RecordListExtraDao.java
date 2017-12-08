package com.iflytek.msp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.iflytek.msp.po.RecordList;
import com.iflytek.msp.po.RecordListExtra;
import com.iflytek.msp.util.DBUtil;

public class RecordListExtraDao {
	
	public RecordListExtra checkRecordExtraExist(String task_id){
		RecordListExtra rList = null;
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select id from rec_record_lists_extra where task_id=? order by id DESC limit 1";
			connection = DBUtil.getConnection();
			ps = DBUtil.getPreparedStatement(connection, sql);
			ps.setString(1, task_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				rList = new RecordListExtra();
				rList.setId(rs.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, ps, rs);
		}
		
		return rList;
	}
}
