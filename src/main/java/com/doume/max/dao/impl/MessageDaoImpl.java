package com.doume.max.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.doume.max.entity.Message;
import com.doume.max.dao.MessageDao;

@Repository("messageDao")
public class MessageDaoImpl extends BaseDaoImpl<Message> implements MessageDao{
	
	@Override
	public List<Message> findByReceiverMsgType(Long receiverId,Integer msgType) {
		final String QUERY_BY_MSGTYPE = "from Message where receiverId = ? and bitand(msgType,?)!=0";
		return find(QUERY_BY_MSGTYPE,receiverId,msgType);
	}

	@Override
	public List<Message> findByReceiver(Long receiverId) {
		final String QUERY_BY_RECEIVER = "from Message where receiverId = ? and bitand(msgType,?)=0";
		return find(QUERY_BY_RECEIVER,receiverId,Message.RECEIVER_DEL);
	}

	@Override
	public List<Message> findBySender(Long senderId) {
		final String QUERY_BY_SENDER = "from Message where senderId = ? and bitand(msgType,?)=0";
		return find(QUERY_BY_SENDER,senderId,Message.SENDER_DEL);
	}
	@Override
	public List<Message> findSystemMessage() {
		final String hql = "from Message where isNULL(receiverId);";
		return findByPage(hql,0,20);
	}
	@Override
	public List<Message> findSystemMessage(Long adminId) {
		final String hql = "from Message where isNULL(receiverId) and senderId=?";
		return findByPage(hql,0,20,adminId);
	}
}
