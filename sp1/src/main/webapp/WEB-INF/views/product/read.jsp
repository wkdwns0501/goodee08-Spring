<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="/WEB-INF/views/includes/header.jsp" %>

<div class="row justify-content-center">
	<div class="col-lg-12">
		<div class="card shadow mb-4">
			<div class="card-header py-3">
				<h6 class="m-0 font-weight-bold text-primary">Product Read</h6>
			</div>
			
			<div class="card-body">
				
				<div class="mb-3 input-group input-group-lg">
          <span class="input-group-text">No</span>
          <input type="text" name="pno" class="form-control" value="<c:out value='${product.pno}'/>" readonly>
        </div>

        <div class="mb-3 input-group input-group-lg">
          <span class="input-group-text">Product Name</span>
          <input type="text" name="pname" class="form-control" value="<c:out value='${product.pname}'/>" readonly>
        </div>

        <div class="mb-3 input-group input-group-lg">
          <span class="input-group-text">Desc</span>
          <textarea class="form-control" name="pdesc" rows="3" readonly><c:out value="${product.pdesc}"/></textarea>
        </div>

        <div class="mb-3 input-group input-group-lg">
          <span class="input-group-text">Writer</span>
          <input type="text" name="writer" class="form-control" value="<c:out value='${product.writer}'/>" readonly>
        </div>

        <div class="mb-3 input-group input-group-lg">
          <span class="input-group-text">Price</span>
          <input type="text" name="price" class="form-control" value="<c:out value='${product.price}'/>" readonly>
        </div>

        <div class="float-end">
          <a href="/product/list" class="btn">
            <button type="button" class="btn btn-info btnList">LIST</button>
          </a>
          
          <a href="/product/modify/${product.pno}" class="btn">
          	<button type="button" class="btn btn-warning btnModify">MODIFY</button>
          </a>
        </div>
				
			</div>
		</div>
	</div>
</div>

<!-- 이미지 -->
<div class="mb-3">
  <label class="form-label fw-bold">Product Images</label>
  <div class="row">
    <c:forEach var="image" items="${product.imageList}">
      <div class="col-md-3 mb-3">
        <div class="card">
          <a href="/images/${image.uuid}_${image.fileName}" target="_blank">
            <img src="/images/${image.uuid}_${image.fileName}" class="card-img-top img-fluid" alt="Product Image">
          </a>
        </div>
      </div>
    </c:forEach>
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

<script type="text/javascript" defer="defer">
	const result = '${result}';
	const action = '${action}';

	const myModal = new bootstrap.Modal(document.getElementById('myModal'));
	const modalBody = document.querySelector('#myModal .modal-body');

	if (result && action === 'modify') {
		modalBody.textContent = result + '번 상품이 수정되었습니다';
		myModal.show();
	}
</script>

<%@ include file="/WEB-INF/views/includes/footer.jsp" %>