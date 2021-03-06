<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/intergration/css/editormd.min.css" />
    <link href="/intergration/css/bootstrap.min.css" rel="stylesheet">
    <script src="/intergration/js/jquery-3.2.1.js"></script>
    <script src="/intergration/js/bootstrap.min.js"></script>
    <script src="/intergration/js/editormd.min.js"></script>
    <script src="/intergration/js/lib/marked.min.js"></script>
    <title>${topic.title} - Campus </title>
</head>
<body>
<!-- 引入header文件 -->
<%@ include file="../header.jsp"%>
<div style="width: 70%;margin:1% 2% 1% 5%;float: left;">
    <div class="panel panel-default" id="main" style="">
        <div class="panel-heading" style="background-color: white">
            <div>
                <div class="panel-heading" style="background-color: white">
                    <a href="/intergration">Campus</a> › 帖子
                </div>
                <h3>${topic.title}</h3><br/>
                <div>
                    <a href="/intergration/member/${topic.user.userName}"><span ><strong>${topic.user.userName}</strong></span></a>&nbsp;&nbsp;
                    <small class="text-muted">${topic.localCreateTime}</small>&nbsp;&nbsp;
                    <small class="text-muted">${topic.click}次点击</small>&nbsp;&nbsp;
                    <c:if test="${allowUpdate==1}">
                    <a class="btn btn-success  btn-small" href="/intergration/forum/topic/updatepage?id=${topic.id}">修改</a>
                    </c:if>
                </div>
            </div>

            <div style="float: right;margin-top: -100px" >
                <img width="50px" height="50px" src="/intergration/${topic.user.headSculpture}" class="img-rounded">
            </div>
        </div>

        <%--<ul class="list-group" style="width: 100%">--%>
            <%--<li class="list-group-item">--%>
                <%--${topic.content}--%>
            <%--</li>--%>
        <%--</ul>--%>
        <div id="test-editormd">
            <textarea style="display:none;">${topic.content}</textarea>
        </div>
    </div>

    <c:if test="${!empty replies}">
        <div class="panel panel-default" id="main" style="">
            <div class="panel-heading" style="background-color: white">
        <span>
                ${fn:length(replies)} 回复  |  直到 <c:forEach items="${replies}" var="reply" varStatus="status">

            <c:if test="${status.last}">
                ${reply.createTime}
            </c:if>
        </c:forEach>
    </span>
            </div>

            <ul class="list-group" style="width: 100%">
                <!-- 遍历评论 -->
                <c:forEach items="${replies}" var="reply">
                    <li class="list-group-item">
                        <div style="height: 50px">
                            <div style="float: left;width: 6%;margin-bottom: 5px">
                                <img width="50px" height="50px" src="/intergration/${reply.user.headSculpture} " class="img-rounded">
                            </div>
                            <div style="width: 89%;float: left">
                                <a href="/member/${reply.user.userName}"><strong>${reply.user.userName}</strong></a>&nbsp;&nbsp;
                                <small class="text-muted">${reply.localCreateTime}</small>
                                <br/>
                                <div>
                                    <p>${reply.content}</p>
                                </div>
                            </div>
                        </div>
                    </li>
                </c:forEach>

            </ul>
        </div>
    </c:if>

    <c:if test="${!empty user}">

        <div class="panel panel-default" id="main" style="">
            <div class="panel-heading" style="background-color: white">
                添加一条新回复
            </div>
            <div class="panel-body">
                <div class="form-group">
                    <form action="/intergration/forum/addreply" method="post">
                        <input type="hidden" name="topicId" value="${topic.id}">
                        <input type="hidden" name="replyUserId" value="${user.id}">
                        <textarea class="form-control" rows="3" name="content" required="required"></textarea><br/>
                        <input type="submit" class="btn btn-default btn-sm" value="回复">
                    </form>
                </div>

            </div>
        </div>
    </c:if>

</div>
<!-- 引入侧边栏文件 -->
<%@ include file="../side.jsp"%>

<!-- 引入footer文件 -->
<%@ include file="../footer.jsp"%>
<script type="text/javascript">
    var testEditor;

    $(function() {
        testEditor = editormd.markdownToHTML("test-editormd", {
            htmlDecode      : "style,script,iframe",  // you can filter tags decode
            emoji           : true,
            taskList        : true,
        });

    });
</script>
</body>
</html>