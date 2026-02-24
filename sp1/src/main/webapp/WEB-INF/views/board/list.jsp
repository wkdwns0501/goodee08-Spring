<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<%@ include file="/WEB-INF/views/includes/header.jsp"%>

<div class="row justify-content-center">
	<div class="col-lg-12">
		<div class="card shadow mb-4">
			<div class="card-header py-3">
				<h6 class="m-0 font-weight-bold text-primary">Board List</h6>
			</div>

			<div class="card-body">
				<table class="table table-bordered" id="dataTable">
					<thead>
						<tr>
							<th>Bno</th>
							<th>Title</th>
							<th>Writer</th>
							<th>RegDate</th>
						</tr>
					</thead>
					<tbody class="tbody">
						<!-- 방법 1 -->
						<c:forEach var="board" items="${boardList}">
						<!-- 방법 2 -->
						<%-- <c:forEach var="board" items="${boardDTO.boardDTOList}"> --%>
							<tr data-bno="${board.bno}">
								<td><c:out value="${board.bno}" /></td>
								<td>
									<a href="/board/read/${board.bno}">
										<c:out value="${board.title}" />        					
									</a>
								</td>
								<td><c:out value="${board.writer}" /></td>
								<td><c:out value="${board.createdDate}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title fs-5" id="exampleModalLabel">알림</h1>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">...</div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript" defer="defer">
	const result = '${result}';
	const action = '${action}';

	const myModal = new bootstrap.Modal(document.getElementById('myModal'));
	const modalBody = document.querySelector('#myModal .modal-body');

	if (result) {
		if (action === 'add') {
			modalBody.textContent = result + '번 게시글로 등록되었습니다';
		} else if (action === 'remove') {
			modalBody.textContent = result + '번 글이 삭제되었습니다';
		}

		myModal.show();
	}
</script>

<%@include file="/WEB-INF/views/includes/footer.jsp"%>
