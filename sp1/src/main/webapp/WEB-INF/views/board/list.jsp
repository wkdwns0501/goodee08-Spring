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
			
				<div class="d-flex justify-content-end" style="margin-bottom: 2em">
					<div style="width: 50%;" class="d-flex">
						<select name="typeSelect" class="form-select form-control me-2">
							<option value="">--</option>
							<option value="T" ${boardPageDTO.types == 'T' ? 'selected' : ''}>제목</option>
					   	<option value="C" ${boardPageDTO.types == 'C' ? 'selected' : ''}>내용</option>
					   	<option value="W" ${boardPageDTO.types == 'W' ? 'selected' : ''}>작성자</option>
					   	<option value="TC" ${boardPageDTO.types == 'TC' ? 'selected' : ''}>제목 OR 내용</option>
					   	<option value="TW" ${boardPageDTO.types == 'TW' ? 'selected' : ''}>제목 OR 작성자</option>
					   	<option value="TCW" ${boardPageDTO.types == 'TCW' ? 'selected' : ''}>제목 OR 내용 OR 작성자</option>
						</select>
						<input type="text" class="form-control me-2" name="keywordInput" value="<c:out value='${boardPageDTO.keyword}'/>" />
						<button class="btn btn-outline-info searchBtn">Search</button>
					</div>
				</div>

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
						<%-- <c:forEach var="board" items="${boardList}"> --%>
						<!-- 방법 2 -->
						<c:forEach var="board" items="${boardPageDTO.boardDTOList}">
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
				
				<!-- 페이징 데이터 -->
				<div class="d-flex justify-content-center">
					<ul class="pagination">
				  	<li class="page-item ${boardPageDTO.prev ? '' : 'disabled'}">
						  <a class="page-link" href="${boardPageDTO.prev ? boardPageDTO.start - 1 : 1}" tabindex="-1">&lt;&lt;</a>
						</li>
				    
				    <li class="page-item ${boardPageDTO.pagePrev ? '' : 'disabled'}">
						  <a class="page-link" href="${boardPageDTO.pagePrev ? boardPageDTO.page - 1 : boardPageDTO.page}" tabindex="-1">&lt;</a>
						</li>
				    
				    <c:forEach var="num" items="${boardPageDTO.pageNums}">
				      <li class="page-item ${boardPageDTO.page == num ? 'active' : ''}">
				      	<a class="page-link" href="${num}">${num}</a>
				      </li>
				    </c:forEach>
				
				    <li class="page-item ${boardPageDTO.pageNext ? '' : 'disabled'}">
						  <a class="page-link" href="${boardPageDTO.pageNext ? boardPageDTO.page + 1 : boardPageDTO.page}">&gt;</a>
						</li>
				    
				    <li class="page-item ${boardPageDTO.next ? '' : 'disabled'}">
						  <a class="page-link" href="${boardPageDTO.next ? boardPageDTO.end + 1 : boardPageDTO.lastPage}">&gt;&gt;</a>
						</li>
				  </ul>
				</div>
				
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
	
	// 페이지 번호 클릭 이벤트 처리
	const pagingDiv = document.querySelector('.pagination');
	
	// 이벤트 버블링을 이용해 페이지 링크 클릭 처리
	pagingDiv.addEventListener('click', (e) => {
		e.preventDefault(); // 기본 이벤트 방지
		e.stopPropagation(); // 상위로 이벤트 전파 방지
		
		// li/ul 클릭 시 href가 없을 수 있으므로 실제 링크만 처리
		const target = e.target.closest('a.page-link');
		if (!target) return;
		
		// disabled 버튼은 화면에만 보이고 실제 이동은 막음
		if (target.closest('.page-item')?.classList.contains('disabled')) return;
		// console.log(target); // 예: <a class="page-link" href="11">11</a>
		
		// href 속성값을 읽어 페이지 번호로 사용
		const targetPage = target.getAttribute('href');
		if (!targetPage) return;
		const size = ${boardPageDTO.size} || 10; // BoardListPagingDTO의 size
		
		// URLSearchParams로 page/size 쿼리스트링 생성
		const params = new URLSearchParams({
			page: targetPage,
			size: size
		});
		
		const types = '${boardPageDTO.types}';
		const keyword = '${boardPageDTO.keyword}';
		
		if (types && keyword) {
			params.set('types', types);
			params.set('keyword', keyword);
		}
		
		// JSP EL과 충돌하지 않게 백틱 템플릿 문자열 사용
		location.href = `/board/list?\${params.toString()}`;
		// location.href = '/board/list?' + params.toString();
		
	});
	
	// 검색 버튼 클릭 이벤트 처리
	document.querySelector('.searchBtn').addEventListener('click', e => {
		const keyword = document.querySelector('input[name="keywordInput"]').value;
		// const selectObj = document.querySelector('select[name="typeSelect"]');
		// console.log(selectObj);
		// const types = selectObj.options[selectObj.selectedIndex].value;
		const types = document.querySelector('select[name="typeSelect"]').value;
		
		const params = new URLSearchParams({
			types,
			keyword
		});
		
		location.href = '/board/list?' + params.toString();
	});
	
</script>

<%@include file="/WEB-INF/views/includes/footer.jsp"%>
