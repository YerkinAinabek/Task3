<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>
    <title>User Management Application</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark" style="background-color: tomato">
        <div>
            <a href="https://www.javaguides.net" class="navbar-brand"> User Management App </a>
        </div>

        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/list" class="nav-link">Users</a></li>
        </ul>
    </nav>
</header>
<br>
<div class="container col-md-5">
    <div class="card">
        <div class="card-body">
            <c:if test="${players != null}">
            <form action="update" method="post">
                </c:if>
                <c:if test="${players == null}">
                <form action="insert" method="post">
                    </c:if>

                    <caption>
                        <h2>
                            <c:if test="${players != null}">
                                Edit User
                            </c:if>
                            <c:if test="${players == null}">
                                Add New User
                            </c:if>
                        </h2>
                    </caption>

                    <c:if test="${players != null}">
                        <input type="hidden" name="id" value="<c:out value='${players.player_id}' />" />
                    </c:if>

                    <fieldset class="form-group">
                        <label>User Name</label> <input type="text" value="<c:out value='${players.nickname}' />" class="form-control" name="nickname" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>User Email</label> <input type="text" value="<c:out value='${players.player_lvl}' />" class="form-control" name="player_lvl">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>User Country</label> <input type="text" value="<c:out value='${players.biography}' />" class="form-control" name="biography">
                    </fieldset>

                    <button type="submit" class="btn btn-success">Save</button>
                </form>
        </div>
    </div>
</div>
</body>

</html>