<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ include file="/WEB-INF/views/includes/header.jsp" %>
	
<div class="row justify-content-center">
  <div class="col-lg-12">
   	<div class="card shadow mb-4">
     	<div class="card-header py-3">
      	<h6 class="m-0 font-weight-bold text-primary">Board Read</h6>
     	</div>
     
     	<div class="card-body">
			      	
				<div class="mb-3 input-group input-group-lg">
			    <span class="input-group-text">Bno</span>
			    <input type="text" class="form-control" value="<c:out value='${board.bno}'/>" readonly>
			  </div>
			
			  <div class="mb-3 input-group input-group-lg">
			    <span class="input-group-text">Title</span>
			    <input type="text" name="title" class="form-control" value="<c:out value='${board.title}'/>" readonly>
			  </div>
			
			  <div class="mb-3 input-group input-group-lg">
			    <span class="input-group-text">Content</span>
			    <textarea class="form-control" name="content" rows="3" readonly><c:out value="${board.content}"/></textarea>
			  </div>
			
			  <div class="mb-3 input-group input-group-lg">
			    <span class="input-group-text">Writer</span>
			    <input type="text" name="writer" class="form-control" value="<c:out value='${board.writer}'/>" readonly>
			  </div>
			
			  <div class="mb-3 input-group input-group-lg">
			    <span class="input-group-text">RegDate</span>
			    <input type="text" name="regDate" class="form-control" value="<c:out value='${board.createdDate}'/>" readonly>
			  </div>
			
			  <div class="float-end">
			    <a href="/board/list">
				    <button type="button" class="btn btn-info btnList">LIST</button>	      	
					</a>
					
					<sec:authentication property="principal" var="secInfo"/>
					<sec:authentication property="authorities" var="roles"/>
					
				  <c:if test="${!board.delFlag && (secInfo.uid == board.writer || fn:contains(roles, 'ROLE_ADMIN'))}">
				  	<a href="/board/modify/${board.bno}">
				    	<button type="button" class="btn btn-warning btnModify">MODIFY</button>
				  	</a>
				  </c:if>
			  </div>
			
			</div>
   	</div>
  </div>
</div>

<div class="col-lg-12">
	<div class="card shadow mb-4">
		<div class='m-4'>
			<!--댓글 작성 폼 -->
			<form id="replyForm" class="mt-4">
				<!-- 게시글 번호 hidden 처리 -->
				<input type="hidden" name="bno" value="${board.bno}" />
				
				<div class="mb-3 input-group input-group-lg">
					<span class="input-group-text">Replyer</span>
					<input type="text" name="replyer" class="form-control" required />
				</div>

				<div class="mb-3 input-group">
					<span class="input-group-text">Reply Text</span>
					<textarea name="replyText" class="form-control" rows="3" required></textarea>
				</div>
				
				<div class="text-end">
					<button type="submit" class="btn btn-primary addReplyBtn">Submit Reply</button>
				</div>
			</form>
			<!-- 댓글 작성 폼 끝 -->
		</div>
	</div>
</div>

<div class="col-lg-12">
	<div class="card shadow mb-4">
		<div class='m-4'>
			<!--댓글 목록 -->
			<ul class="list-group replyList">
				
			</ul>
			<!-- 댓글 목록 -->
			
			<!-- 페이징 -->
			<div aria-label="댓글 페이지 네비게이션" class="mt-4">
				<ul class="pagination justify-content-center">
				
				</ul>
			</div>
			<!-- 페이징 끝 -->
		</div>
	</div>
</div>

<!-- Reply Modal -->
<div class="modal fade" id="replyModal" tabindex="-1" aria-labelledby="replyModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="replyModalLabel">댓글 수정 / 삭제</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			
			<div class="modal-body">
				<form id="replyModForm">
					<input type="hidden" name="rno" value="33">
					<div class="mb-3">
						<label for="replyText" class="form-label">댓글 내용</label>
						<input type="text" name="replyText" id="replyText" class="form-control" value="Reply Text">
					</div>
				</form>
      </div>
      
      <div class="modal-footer">
				<button type="button" class="btn btn-primary btnReplyMod">수정</button>
				<button type="button" class="btn btn-danger btnReplyDel">삭제</button>
				<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
			</div>
		</div>
	</div>
</div>

<!-- Result Modal -->
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

<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

<script type="text/javascript">
	const replyForm = document.querySelector('#replyForm');
	document.querySelector('.addReplyBtn').addEventListener('click', e => {
		e.preventDefault(); // 버튼 타입 button이면 없어도 됨
		// 여기서는 form의 기본 submit을 막고 AJAX 요청만 수행하도록 만듦
		// 이게 없으면 AJAX 요청 직후 form submit -> 페이지 이동 -> JS 실행 흐름 끊김
		e.stopPropagation();
		// 상위 클릭 로직을 차단하기 위한 방어 코드로 상위 이벤트가 있을 때만 의미 있음(선택적으로 사용)
		
		// FormData: 
		// HTML <form> 요소의 입력값을 이름–값(key–value) 쌍 형태로 수집하여
		// HTTP 요청 본문(request body)으로 전송하기 위한 웹 표준 객체
		// 항상 multipart/form-data 방식 => 파일 업로드에 주로 쓰임
		// 파일 있으면 FormData, 없으면 JSON도 고려 가능 
		const formData = new FormData(replyForm); 
		// 이 한 줄로 form 안의 모든 입력 요소를 자동 수집(이때 name 속성 기준으로 수집하기 때문에 name 필수)
		
		axios.post('/replies', formData)
			.then(res => {
				//console.log("------------ server response ------------");
				//console.log(res);
				replyForm.reset();
				getReplies(1, true); // 댓글 목록 새로고침, 마지막 페이지로 이동
			});
	});
	
	// 댓글 목록 요청
	let currentPage = 1; // 현재 댓글 페이지
	let currentSize = 10; // 한번에 보여질 댓글 개수
	
	const bno = ${board.bno};
	
	function getReplies(pageNum, goLast) {
		axios.get('/replies/' + bno + '/list', {
			params: {
				page: pageNum || currentPage,
				size: currentSize
			}
		}).then(res => {
			// console.log(res);
			const data = res.data;
			// console.log(data);
			
			// 마지막 댓글 페이지 호출
			// ES5차 : 일반 변수 선언
			//const totalCount = data.totalCount;
			//const page = data.page;
			//const size = data.size;
			// ES6차 : 구조 분해 할당(비구조화 할당)
			const {totalCount, page, size} = data;
			
			if (goLast && totalCount > (page * size)) {
				// 마지막 페이지 계산
				const lastPage = Math.ceil(totalCount / size);
				
				getReplies(lastPage);
			} else {
				currentPage = page;
				currentSize = size;
				printReplies(data); // 출력
			}
		});
	}
	getReplies(1, true); // 페이지가 로드되면 우선 1페이지의 댓글 목록을 가져옴
	
	// 댓글 출력
	const replyList = document.querySelector('.replyList');
	
	function printReplies(data) {
		const {replyDTOList, page, size, prev, next, start, end, pageNums} = data;
		
		let liStr = '';
		
		for (const replyDTO of replyDTOList) {
			liStr += `
				<li class="list-group-item" data-rno="\${replyDTO.rno}">
					<div class="d-flex justify-content-between">
						<div class="mt-1 text-secondary small">
							\${replyDTO.replyer}
						</div>
						<div class="text-muted small">
							\${replyDTO.replyDate}
						</div>
					</div>
					<div>
						<strong>\${replyDTO.rno}</strong> - \${replyDTO.replyText}
					</div>
				</li>
			`;
		}
		replyList.innerHTML = liStr;
		
		// 댓글 페이징 처리 
		let pagingStr = '';
		
		if (prev) {
			pagingStr += `
				<li class="page-item">
					<a class="page-link" href="\${start -1}" tabindex="-1">&lt;</a>
				</li>
			`;
		}
		for (const i of pageNums) {
			pagingStr += `
				<li class="page-item \${i === page ? 'active' : ''}">
					<a class="page-link" href="\${i}">\${i}</a>
				</li>
			`;
		}
		if (next) {
			pagingStr += `
				<li class="page-item">
					<a class="page-link" href="\${end + 1}">&gt;</a>
				</li>
			`;
		}
		document.querySelector(".pagination").innerHTML = pagingStr;
	}
	
	// 각 페이지 번호의 이벤트 처리
	document.querySelector(".pagination").addEventListener("click", e => {
		e.stopPropagation();
		e.preventDefault();
	
		const target = e.target;
		const href = target.getAttribute("href");
		
		if (!href) return;
		
		console.log(href); // href: 페이지 번호
		
		getReplies(href);
	});
	
	const replyModal = new bootstrap.Modal(document.querySelector('#replyModal'));
	const replyModForm = document.querySelector('#replyModForm');
		
	replyList.addEventListener('click', e => {
		// replyModal.show(); // 모달창 동작 확인용
		
		// 가장 가까운 상위 li 요소 찾기(자기 자신 포함)
		const targetLi = e.target.closest("li");
		
		// data- : 요소에 데이터를 저장하는 용도(사용자 정의 속성을 만듦)
		// input 처럼 value 속성을 지원하는 요소가 아닐때 유용
		//const rno = targetLi.getAttribute("data-rno");
		const rno = targetLi.dataset.rno;
		//const rno = targetLi.getAttribute("data-rno-apple");
		//const rno = targetLi.dataset.rnoApple; // 카멜케이스 주의
		
		if (!rno) return;
		
		// AJAX로 GET 요청을 보내고
		// 서버로부터 받은 댓글 데이터(rno, replyText)를 모달창에 출력 후 띄우기
		// (이때 삭제된 댓글은 조회할 수 없다는 alert 띄우기)
		axios.get('/replies/' + rno).then(res => {
			const target = res.data;
			//console.log(target);
			
			// 댓글 데이터 출력
			if (target.delFlag === false) {
				replyModForm.querySelector('input[name="rno"]').value = target.rno;
				replyModForm.querySelector('#replyText').value = target.replyText;
				replyModal.show();
			} else {
				alert("삭제된 댓글은 조회할 수 없습니다.");
			}
		});
	});
	
	// 댓글 삭제 처리
	document.querySelector('.btnReplyDel').addEventListener('click', e => {
		const formData = new FormData(replyModForm);
		
		const rno = formData.get("rno");
		
		// Axios로 서버의 삭제 기능을 호출
		axios.delete('/replies/' + rno).then(res => {
			const data = res.data;
			
			// 정상적인 결과라면 모달창을 닫고 현재 댓글 페이지를 다시 호출
			if (data.result === 'deleted') {
				replyModal.hide();
				getReplies(currentPage);
			}
		});
	});
	
	document.querySelector('.btnReplyMod').addEventListener('click', e => {
		const formData = new FormData(replyModForm);
		
		const rno = formData.get("rno");
		
		// Axios로 서버의 삭제 기능을 호출 + formData 전송
		axios.put('/replies/' + rno, formData).then(res => {
			const data = res.data;
			
			// 정상적인 결과라면 모달창을 닫고 현재 댓글 페이지를 다시 호출
			if (data.result === 'updated') {
				replyModal.hide();
				getReplies(currentPage);
			}
		});
	});
</script>

<script type="text/javascript" defer="defer">
	const result = '${result}';
	const action = '${action}';

	const myModal = new bootstrap.Modal(document.getElementById('myModal'));
	const modalBody = document.querySelector('#myModal .modal-body');

	if (result && action === 'modify') {
		modalBody.textContent = result + '번 게시글이 수정되었습니다';
		myModal.show();
	}
</script>

<%@ include file="/WEB-INF/views/includes/footer.jsp" %>
