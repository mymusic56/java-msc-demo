package com.iflytek.msp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.iflytek.msp.po.RecordList;
import com.iflytek.msp.po.RecordListExtra;
import com.iflytek.msp.util.DBUtil;

public class RecordListDao {
	
	private static Connection connection = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	
	/**
	 * 保存转换后的文字信息
	 * @return
	 */
	public boolean addRecordTextInfo(String task_id, String originalStr, String segementStr){
		int count = 0;
		try {
			
			String sql = "insert into rec_record_lists_extra(task_id,content,content_words) values(?,?,?)";
			connection = DBUtil.getConnection();
			ps = DBUtil.getPreparedStatement(connection, sql);
			ps.setString(1, task_id);
			ps.setString(2, originalStr);
			ps.setString(3, segementStr);
			
			count = ps.executeUpdate();
			System.out.println(count);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, ps, rs);
		}
		if(count > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 保存转换后的文字信息
	 * @return
	 */
	public boolean updateRecordTextInfo(int id, String originalStr, String segementStr){
		int count = 0;
		try {
			
			String sql = "update rec_record_lists_extra set content=?, content_words=? where id=?";
			connection = DBUtil.getConnection();
			ps = DBUtil.getPreparedStatement(connection, sql);
			ps.setString(1, originalStr);
			ps.setString(2, segementStr);
			ps.setInt(3, id);
			
			count = ps.executeUpdate();
			System.out.println(count);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, ps, rs);
		}
		if(count > 0){
			return true;
		}
		return false;
	}
	
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
	
	/**
	 * 更具上传第三方服务器状态来获取录音记录
	 * 
	 * @param has_convert
	 * @return RecordList rList
	 */
	public RecordList getRecord(int has_convert){
		RecordList rList = null;
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from rec_record_lists where enabled=1 and up_status=1 and has_convert=? order by id ASC limit 1";
			connection = DBUtil.getConnection();
			ps = DBUtil.getPreparedStatement(connection, sql);
			ps.setInt(1, has_convert);
			rs = ps.executeQuery();
			while (rs.next()) {
				rList = new RecordList();
				rList.setId(rs.getInt("id"));
				rList.setPath(rs.getString("file_path"));
				rList.setHasConvert(rs.getInt("has_convert"));
				rList.setFileSize(rs.getInt("file_size"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, ps, rs);
		}
		
		return rList;
	}
	
	/**
	 * 
	 * 更具上传第三方服务器状态来获取录音记录[查询录音文件的处理状态时使用]
	 * 
	 * 1、必须是当前时间大于待处理时间
	 * 2、查询次数和时间间隔限制
	 * 
	 * @param has_convert
	 * @return RecordList rList
	 */
	public RecordList getRecord(int has_convert, int times){
		RecordList rList = null;
		
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from rec_record_lists where enabled=1 and up_status=1 and has_convert=? and next_query_time <= ? order by id ASC limit 1";
			connection = DBUtil.getConnection();
			ps = DBUtil.getPreparedStatement(connection, sql);
			ps.setInt(1, has_convert);
			ps.setInt(2, times);
			rs = ps.executeQuery();
			
			System.out.println(sql+",has_convert:"+String.valueOf(has_convert)+",times:"+String.valueOf(times));
			
			while (rs.next()) {
				rList = new RecordList();
				rList.setId(rs.getInt("id"));
				rList.setPath(rs.getString("file_path"));
				rList.setHasConvert(rs.getInt("has_convert"));
				rList.setTaskId(rs.getString("task_id"));
				rList.setFileSize(rs.getInt("file_size"));
				rList.setQueryTimes(rs.getInt("query_times"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, ps, rs);
		}
		
		return rList;
	}
	
	public boolean updateUploadStatus(int id, int status){
		int count = 0;
		try {
			String sql = "update rec_record_lists set has_convert=? where id=?";
			connection = DBUtil.getConnection();
			ps = DBUtil.getPreparedStatement(connection, sql);
			ps.setInt(1, status);
			ps.setInt(2, id);
			
			count = ps.executeUpdate();
			System.out.println(count);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, ps, rs);
		}
		if(count > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 更新更新task_id
	 * @param id
	 * @param status
	 * @param task_id
	 * @return
	 */
	public boolean updateUploadStatus(int id, int status, String task_id, int next_query_time){
		int count = 0;
		try {
			String sql = "update rec_record_lists set has_convert=?,task_id=?,next_query_time=? where id=?";
			connection = DBUtil.getConnection();
			ps = DBUtil.getPreparedStatement(connection, sql);
			ps.setInt(1, status);
			ps.setString(2, task_id);
			ps.setInt(3, next_query_time);
			ps.setInt(4, id);
			
			count = ps.executeUpdate();
			System.out.println(count);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, ps, rs);
		}
		if(count > 0){
			return true;
		}
		return false;
	}
	
	public boolean updateRecordInfo(int id, int status, String contentJson){
		int count = 0;
		try {
			
			String sql = "update rec_record_lists set has_convert=? where id=?";
			connection = DBUtil.getConnection();
			ps = DBUtil.getPreparedStatement(connection, sql);
			ps.setInt(1, status);
			ps.setInt(2, id);
			
			count = ps.executeUpdate();
			System.out.println(count);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, ps, rs);
		}
		if(count > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 更新录音转文字，下一次处理的时间
	 * @param id
	 * @param times
	 * @return
	 */
	public boolean updateNextQueryTime(int id, int times){
		int count = 0;
		try {
			
			String sql = "update rec_record_lists set next_query_time=?,query_times=query_times+1 where id=?";
			connection = DBUtil.getConnection();
			ps = DBUtil.getPreparedStatement(connection, sql);
			ps.setInt(1, times);
			ps.setInt(2, id);
			
			count = ps.executeUpdate();
			System.out.println(count);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(connection, ps, rs);
		}
		if(count > 0){
			return true;
		}
		return false;
	} 
	
}
