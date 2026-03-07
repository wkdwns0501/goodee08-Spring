<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ include file="/WEB-INF/views/includes/header.jsp"%>

<div class="row justify-content-center">
	<div class="col-lg-12">
		<div class="card shadow mb-4">
			<div class="card-header py-3">
				<h6 class="m-0 font-weight-bold text-primary">Board Register</h6>
			</div>
			
			<!-- 사용자 인증 정보 확인용 -->
			<%-- <sec:authentication property="principal"/> --%>

			<div class="card-body">
				<form action="/board/add" method="post">

					<div class="mb-3">
						<label class="form-label">Title</label> 
						<input type="text" name="title" class="form-control">
					</div>

					<div class="mb-3">
						<label class="form-label">Content</label>
						<textarea class="form-control" name="content" rows="3"></textarea>
					</div>

					<div class="mb-3">
						<label class="form-label">Writer</label> 
						<input type="text" value='<sec:authentication property="principal.uid"/>' 
									 name="writer" class="form-control" readonly>
					</div>

					<!-- Submit Button -->
					<div class="d-flex justify-content-end">
						<button type="submit" class="btn btn-primary btn-lg">Submit</button>
					</div>

				</form>
			</div>
		</div>
	</div>
</div>

<%@ include file="/WEB-INF/views/includes/footer.jsp"%>