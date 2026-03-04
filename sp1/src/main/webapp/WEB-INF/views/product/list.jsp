<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@include file="/WEB-INF/views/includes/header.jsp" %>

<style>
	#dataTable th,
	#dataTable td {
		vertical-align: middle;
	}

	#dataTable .col-no {
		width: 90px;
		text-align: center;
		white-space: nowrap;
	}

	#dataTable .col-name {
		text-align: left;
	}

	#dataTable .col-price {
		width: 160px;
		text-align: center;
		white-space: nowrap;
	}

	#dataTable .col-writer {
		width: 160px;
		text-align: center;
		white-space: nowrap;
	}

	.product-link {
		display: inline-flex;
		align-items: center;
		gap: 0.6rem;
		color: inherit;
		text-decoration: none;
	}

	.product-link:hover {
		text-decoration: underline;
	}

	.deleted-row .product-link,
	.deleted-row .product-link:hover {
		text-decoration: line-through;
	}

	.product-thumb {
		width: 52px;
		height: 52px;
		object-fit: cover;
		border-radius: 0.35rem;
		flex-shrink: 0;
	}

	.deleted-row {
		background-color: #f0f0f0;
		color: #888;
		text-decoration: line-through;
		font-style: italic;
	}
	.deleted-row img {
		opacity: 0.4;
	}
</style>

<div class="row justify-content-center">
	<div class="col-lg-12">
		<div class="card shadow mb-4">
			<div class="card-header py-3">
				<h6 class="m-0 font-weight-bold text-primary">Product List</h6>
			</div>
			
			<div class="card-body">
				<table class="table table-bordered table-hover" id="dataTable">
					<thead>
						<tr>
							<th class="col-no">No</th>
							<th class="col-name">Product Name</th>
							<th class="col-price">Price</th>
							<th class="col-writer">Writer</th>
						</tr>
					</thead>
					
					<tbody class="tbody">
						<c:forEach var="product" items="${productListDTO.productDTOList}">
							<tr class="${not product.sale ? 'deleted-row' : ''}" data-bno="${product.pno}">
								<td class="col-no">
									<c:out value="${product.pno}"/>
								</td>
								<td class="col-name">
									<a class="product-link" href='/product/read/${product.pno}'>
										<img class="product-thumb" src="/images/s_${product.uuid}_${product.fileName}">
										<c:out value="${product.pname}"/>
									</a>
								</td>
								<td class="col-price">
									<c:out value="${product.price}"/>
								</td>
								<td class="col-writer">
									<c:out value="${product.writer}"/>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<!-- 페이징 데이터 -->
				<div class="d-flex justify-content-center">
					<ul class="pagination">
				  	<li class="page-item ${productListDTO.prev ? '' : 'disabled'}">
						  <a class="page-link" href="${productListDTO.prev ? productListDTO.start - 1 : 1}" tabindex="-1">&lt;&lt;</a>
						</li>
				    
				    <li class="page-item ${productListDTO.pagePrev ? '' : 'disabled'}">
						  <a class="page-link" href="${productListDTO.pagePrev ? productListDTO.page - 1 : productListDTO.page}" tabindex="-1">&lt;</a>
						</li>
				    
				    <c:forEach var="num" items="${productListDTO.pageNums}">
				      <li class="page-item ${productListDTO.page == num ? 'active' : ''}">
				      	<a class="page-link" href="${num}">${num}</a>
				      </li>
				    </c:forEach>
				
				    <li class="page-item ${productListDTO.pageNext ? '' : 'disabled'}">
						  <a class="page-link" href="${productListDTO.pageNext ? productListDTO.page + 1 : productListDTO.page}">&gt;</a>
						</li>
				    
				    <li class="page-item ${productListDTO.next ? '' : 'disabled'}">
						  <a class="page-link" href="${productListDTO.next ? productListDTO.end + 1 : productListDTO.lastPage}">&gt;&gt;</a>
						</li>
				  </ul>
				</div>  
				  
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="myModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">알림</h5>
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
			modalBody.textContent = result + '번 상품으로 등록되었습니다';
		} else if (action === 'remove') {
			modalBody.textContent = result + '번 상품이 판매 중지되었습니다';
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
		
		// href 속성값을 읽어 페이지 번호로 사용
		const targetPage = target.getAttribute('href');
		if (!targetPage) return;
		const size = ${productListDTO.size} || 10;
		
		// URLSearchParams로 page/size 쿼리스트링 생성
		const params = new URLSearchParams({
			page: targetPage,
			size: size
		});
		
		location.href = '/product/list?' + params.toString();
	});
	
</script>

<%@include file="/WEB-INF/views/includes/footer.jsp" %>
