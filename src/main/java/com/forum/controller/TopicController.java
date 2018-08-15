package com.forum.controller;

import com.forum.domain.Reply;
import com.forum.domain.Topic;
import com.forum.service.ReplyService;
import com.forum.service.impl.TopicServiceImpl;
import com.mainpage.domain.User;
import com.mainpage.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/forum")
public class TopicController {
    @Autowired
    public TopicServiceImpl topicService;
    @Autowired
    public UserServiceImpl userService;
    @Autowired
    public ReplyService replyService;

    @RequestMapping("/main")
    public ModelAndView toMain(HttpSession session){
        ModelAndView indexPage=new ModelAndView("forum/main");
        //全部主题
        List<Topic> topics=topicService.listTopicsAndUsers();
        //获取用户信息
        String name=(String) session.getAttribute("username");
        User user=userService.getUserByUserName(name);
        indexPage.addObject("topics",topics);
        indexPage.addObject("user",user);
        return  indexPage;
    }
    @RequestMapping("/addpage")
    public ModelAndView addTopic(){
        ModelAndView newTopicPage=new ModelAndView("forum/addtopic");

        //获取统计信息
        int topicsNum=topicService.getTopicsNum();

        newTopicPage.addObject("topicsNum",topicsNum);
        return  newTopicPage;
    }
    @RequestMapping("/post")
    @ResponseBody
    public Object postTopic(HttpSession session, HttpServletRequest request){
        HashMap<String, String> res = new HashMap<String, String>();
        //未登陆
        if(session.getAttribute("userId")==null){
            res.put("stateCode", "0");
            return res;
        }
        //处理参数
        int userId=(Integer) session.getAttribute("userId");
        int userType=(Integer)session.getAttribute("userType");
        String title=request.getParameter("title");
        String content=request.getParameter("content");
        //新建Topic
        Topic topic=new Topic();
        topic.setUserId(userId);
        topic.setTitle(title);
        topic.setContent(content);
        topic.setCreateTime(new Date());
        topic.setUpdateTime(new Date());
        //添加topic
        if(userType!=0) {
            topicService.addTopic(topic);
            res.put("stateCode", "2");
        }else {
            res.put("stateCode", "1");
        }
        return res;
    }

    @RequestMapping("/t/{id}")
    public ModelAndView toTopic(@PathVariable("id")Integer id, HttpSession session){
        //点击量加一
        boolean ifSucc=topicService.clickAddOne(id);
        //获取主题信息
        Topic topic=topicService.selectById(id);
        //获取主题全部评论
        List<Reply> replies=replyService.getRepliesOfTopic(id);
        //获取评论数
        int repliesNum=replyService.repliesNum(id);
        //获取统计信息
        int topicsNum=topicService.getTopicsNum();
        //获取用户信息
        Integer uid=(Integer) session.getAttribute("userId");
        String name=(String) session.getAttribute("username");
        User user=userService.getUserByUserName(name);
        //最热主题
        List<Topic> hotestTopics=topicService.listMostCommentsTopics();

        //渲染视图
        ModelAndView topicPage=new ModelAndView("forum/detail");
        topicPage.addObject("topic",topic);
        topicPage.addObject("replies",replies);
        topicPage.addObject("repliesNum",repliesNum);
        topicPage.addObject("topicsNum",topicsNum);
        topicPage.addObject("user",user);
        topicPage.addObject("hotestTopics",hotestTopics);
        return topicPage;
    }

    @RequestMapping(value = "/addreply",method = RequestMethod.POST)
    public ModelAndView addReply(HttpServletRequest request, HttpSession session){
        //处理参数
        int topicId=Integer.parseInt(request.getParameter("topicId"));
        Integer replyUserId=Integer.parseInt(request.getParameter("replyUserId"));
        String content=request.getParameter("content");
        //创建reply
        Reply reply=new Reply();
        reply.setTopicId(topicId);
        reply.setReplyUserId(replyUserId);
        reply.setContent(content);
        reply.setCreateTime(new Date());
        //执行添加
        boolean ifSucc=replyService.addReply(reply);

        //新建视图
        ModelAndView view=new ModelAndView("redirect:/forum/t/"+topicId);
        return view;
    }
}