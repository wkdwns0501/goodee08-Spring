<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ include file="/WEB-INF/views/includes/header.jsp" %>
	
<div class="row justify-content-center">
	<div class="col-lg-12">
  	<div class="card shadow mb-4">
    	<div class="card-header py-3">
      	<h6 class="m-0 font-weight-bold text-primary">Board Modify</h6>
     	</div>
     
     	<div class="card-body">
	     	<form id="actionForm" action="/board/modify" method="post">
      		<div class="mb-3 input-group input-group-lg">
				    <span class="input-group-text">Bno</span>
				    <input type="text" name="bno" class="form-control" value="<c:out value='${board.bno}'/>" readonly>
				  </div>
				
				  <div class="mb-3 input-group input-group-lg">
				    <span class="input-group-text">Title</span>
				    <input type="text" name="title" class="form-control" value="<c:out value='${board.title}'/>">
				  </div>
				
				  <div class="mb-3 input-group input-group-lg">
				    <span class="input-group-text">Content</span>
				    <textarea class="form-control" name="content" rows="3"><c:out value="${board.content}"/></textarea>
				  </div>
				
				  <div class="mb-3 input-group input-group-lg">
				    <span class="input-group-text">Writer</span>
				    <input type="text" name="writer" class="form-control" value="<c:out value='${board.writer}'/>" readonly>
				  </div>
				
				  <div class="mb-3 input-group input-group-lg">
				    <span class="input-group-text">RegDate</span>
				    <input type="text" class="form-control" value="<c:out value='${board.createdDate}'/>" readonly>
				  </div>
      	</form>
      	
      	<div class="float-end">
      		<!-- class 속성값으로 버튼을 식별할 수 있도록 구성 -->
         	<button type="button" class="btn btn-info btnList">LIST</button>
 	
					<sec:authentication property="principal" var="secInfo"/>
					<sec:authentication property="authorities" var="roles"/>
					
					<c:if test="${!board.delFlag && (secInfo.uid == board.writer || fn:contains(roles, 'ROLE_ADMIN'))}">
				   	<button type="button" class="btn btn-warning btnModify">MODIFY</button>
				   	<button type="button" class="btn btn-danger btnRemove">REMOVE</button>
				 	</c:if>
      	</div>
      	
     	</div>
   	</div>
  </div>
</div>

<script type="text/javascript">
	// 어떤 버튼을 클릭했는지에 따라서 다르게 동작할 수 있도록 JS로 이벤트 처리 작성
	const formObj = document.querySelector("#actionForm");
	
	// false는 이벤트 버블링을 막기 위한 옵션 (이벤트가 상위 요소로 전파되는 것을 방지)
	// 생략시 기본값 false
	document.querySelector(".btnModify").addEventListener("click", () => {
	  formObj.action = '/board/modify';
	  formObj.method = 'post';
	  formObj.submit();
	}, false);
	
	document.querySelector(".btnList").addEventListener("click", () => {
	  location.href = '/board/list';
	}, false);
	
	document.querySelector(".btnRemove").addEventListener("click", () => {
		  if (!confirm("정말 삭제하시겠습니까?")) {
		  	return;
		  }
		  formObj.action = '/board/remove';
		  formObj.method = 'post';
		  formObj.submit();
		}, false);
</script>

<%@include file="/WEB-INF/views/includes/footer.jsp" %>
