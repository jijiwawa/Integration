package com.forum.service.impl;

import com.forum.dao.ReplyMapper;
import com.forum.domain.Reply;
import com.forum.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    public ReplyMapper replyDao;

    public List<Reply> getRepliesOfTopic(Integer topicId) {
        return replyDao.getRepliesOfTopic(topicId);
    }

    public boolean addReply(Reply reply) {
        return replyDao.insert(reply)>0;
    }

    public int repliesNum(Integer topicId) {
        return replyDao.getRepliesNum(topicId);
    }
}
