<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Admin Panel</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
</head>
<body>
	<!-- Top Navbar -->
  <nav class="navbar navbar-expand-lg navbar-custom px-4">
    <a class="navbar-brand" href="#">AdminPanel</a>
    <div class="collapse navbar-collapse">
      <ul class="navbar-nav me-auto">
        <li class="nav-item"><a class="nav-link" href="#">Dashboard</a></li>
        <li class="nav-item"><a class="nav-link" href="#">Users</a></li>
        <li class="nav-item"><a class="nav-link" href="#">Settings</a></li>
      </ul>
      <span class="navbar-text">
        <sec:authorize access="isAuthenticated()">
          <sec:authentication property="name"/><!--  Authentication.getName() = Username(로그인한 유저의 아이디) -->
          |
          <a href="${pageContext.request.contextPath}/account/logout" class="text-white text-decoration-underline">Logout</a>
        </sec:authorize>
        <sec:authorize access="isAnonymous()">
          <a href="${pageContext.request.contextPath}/account/login" class="text-white text-decoration-underline">Login</a>
        </sec:authorize>
      </span>
    </div>
  </nav>

  <!-- Sidebar + Content -->
  <div class="main-wrapper">
    <!-- Sidebar -->
    <div class="sidebar pt-3">
      <div class="list-group list-group-flush">
        <a href="#" class="list-group-item active">Dashboard</a>
        <a href="#" class="list-group-item">Analytics</a>
        <a href="#" class="list-group-item">User List</a>
        <a href="#" class="list-group-item">Roles</a>
        <a href="#" class="list-group-item">System Logs</a>
        <a href="${pageContext.request.contextPath}/board/list" class="list-group-item">Board List</a>
        <a href="${pageContext.request.contextPath}/board/add" class="list-group-item">Board Add</a>
        <a href="${pageContext.request.contextPath}/product/list" class="list-group-item">Product List</a>
        <a href="${pageContext.request.contextPath}/product/add" class="list-group-item">Product Add</a>
      </div>
    </div>

		<!-- Main Content -->
    <div class="content">
