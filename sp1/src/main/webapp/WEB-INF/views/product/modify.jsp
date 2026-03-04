<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="/WEB-INF/views/includes/header.jsp" %>

<style>
	.productImages .card {
		position: relative;
		overflow: hidden;
	}

	.productImages .delete-image-btn {
		position: absolute;
		top: 0.5rem;
		right: 0.5rem;
		width: 2rem;
		height: 2rem;
		padding: 0;
		border: 0;
		border-radius: 999px;
		display: flex;
		align-items: center;
		justify-content: center;
		line-height: 0;
		box-shadow: 0 2px 6px rgba(0, 0, 0, 0.25);
		opacity: 0.92;
	}

	.productImages .delete-image-btn:hover {
		opacity: 1;
		transform: scale(1.05);
	}

	.productImages .delete-image-btn i {
		display: inline-flex;
		align-items: center;
		justify-content: center;
		width: 1em;
		height: 1em;
		font-size: 0.95rem;
		line-height: 1;
		transform: translateY(1px);
		pointer-events: none;
	}
</style>

<div class="row justify-content-center">
	<div class="col-lg-12">
		<div class="card shadow mb-4">
			<div class="card-header py-3">
				<h6 class="m-0 font-weight-bold text-primary">Product Modify</h6>
			</div>
			
			<div class="card-body">
				
				<form id="modifyForm" action="/product/modify/${product.pno}" method="post" enctype="multipart/form-data">
					<div class="mb-3 input-group input-group-lg">
	          <span class="input-group-text">No</span>
	          <input type="text" name="pno" class="form-control" value="<c:out value='${product.pno}'/>" readonly>
	        </div>
	
	        <div class="mb-3 input-group input-group-lg">
	          <span class="input-group-text">Product Name</span>
	          <input type="text" name="pname" class="form-control" value="<c:out value='${product.pname}'/>">
	        </div>
	
	        <div class="mb-3 input-group input-group-lg">
	          <span class="input-group-text">Desc</span>
	          <textarea class="form-control" name="pdesc" rows="3"><c:out value="${product.pdesc}"/></textarea>
	        </div>
	
	        <div class="mb-3 input-group input-group-lg">
	          <span class="input-group-text">Writer</span>
	          <input type="text" name="writer" class="form-control" value="<c:out value='${product.writer}'/>" readonly>
	        </div>
	
	        <div class="mb-3 input-group input-group-lg">
	          <span class="input-group-text">Price</span>
	          <input type="text" name="price" class="form-control" value="<c:out value='${product.price}'/>">
	        </div>
	        
	        <div class="mb-3">
	        	<input type="file" name="files" class="form-control" multiple>
	        </div>
	
	        <div class="float-end">
	          <a href="/product/list" class="btn">
	          	<button type="button" class="btn btn-info btnList">LIST</button>
          	</a>
	         	<button type="button" class="btn btn-warning btnModify">MODIFY</button>
	         	<button type="button" class="btn btn-danger btnRemove">REMOVE</button>
	         	<!-- -->
	        </div>
				</form>
				
			</div>
		</div>
	</div>
</div>

<div class="mb-3 productImages">
  <label class="form-label fw-bold">Product Images</label>
  <div class="row">
    <c:forEach var="image" items="${product.imageList}">
      <div class="col-md-3 mb-3">
        <div class="card">
          <a href="/images/${image.uuid}_${image.fileName}" target="_blank">
            <img src="/images/${image.uuid}_${image.fileName}" class="card-img-top img-fluid" alt="Product Image">
          </a>
          
          <button type="button" 
          	class="btn btn-danger delete-image-btn" 
          	data-file="${image.uuid}_${image.fileName}">
          	<i class="fa-solid fa-xmark"></i>
          </button>
        </div>
      </div>
    </c:forEach>
  </div>
</div>

<script type="text/javascript">
	const modifyForm = document.querySelector('#modifyForm');
	
	// 상품 삭제에 대한 이벤트 처리
	document.querySelector('.btnRemove').addEventListener('click', e => {
		if (!confirm("정말 삭제하시겠습니까?")) {
		  	return;
		}
		modifyForm.action = "/product/remove";
		modifyForm.submit();
	}, false);
	
	// 각 상품 이미지의 삭제 이벤트 처리
	document.querySelector('.productImages').addEventListener('click', e => {
		e.preventDefault();
		e.stopPropagation();
		
		const target = e.target.closest('.delete-image-btn');
		if (!target) return;
		
		const fileName = target.getAttribute('data-file'); // = target.dataset.file
		
		if (!fileName) return;
		
		// 하나의 이미지를 표현하는 <div>를 찾음
		const divObj = target.closest('.col-md-3');
		if (!confirm("사진을 삭제하시겠습니까? (삭제후 수정 버튼을 눌러야 적용됩니다.)")) {
		  	return;
		}
		divObj.remove(); // 요소 삭제
	});
	
	// 상품 수정에 대한 이벤트 처리
	document.querySelector('.btnModify').addEventListener('click', e => {
		e.preventDefault();
		e.stopPropagation();
		
		modifyForm.action = '/product/modify';
		modifyForm.method = 'post';
		
		const imageArr = document.querySelectorAll('.productImages button'); // productImages 요소 하위의 모든 button 요소
		
		if (imageArr.length > 0) {
			let html = '';
			
			for (const image of imageArr) {
				const imageFile = image.dataset.file; // 업로드된 파일명
				
				html += `<input type="hidden" name="oldImages" value="\${imageFile}">`;
				
			} // end for
			
			// insertAdjacentHTML: 지정된 위치에 HTML 코드를 삽입
			// beforebegin(요소 바로 앞), afterbegin(요소 내부 맨 앞), beforeend(요소 내부 맨 뒤), afterend(요소 바로 뒤)
			modifyForm.querySelector('div:last-child').insertAdjacentHTML('beforeend', html);
		}
		// hidden input 태그들이 생성되는 것을 확인 후 주석 해제
		modifyForm.submit(); // 서버로 전송
	});
	
	
</script>

<%@ include file="/WEB-INF/views/includes/footer.jsp" %>
