<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${pageContext.request.contextPath}/resources/css/base.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/bootstrap-theme.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/bootstrap-switch.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/bootstrap-datetimepicker.min.css" rel="stylesheet">

<script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.1.min.js" /></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js" /></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-switch.min.js" /></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/locales/bootstrap-datetimepicker.zh-TW.js"></script>
<title><spring:message code="title.index"/></title>
<style type="text/css">
body {
	padding-top: 5px;
}

.modal-style{
/* 	水平/垂直間距 */
 	border-spacing:20px 5px;
/*  告訴瀏覽器水平/垂直分開計算 */
	border-collapse:separate;
}
</style>
<script type="text/javascript">

var id;

$(function() {
	$('.form_date').datetimepicker({
        language:  'zh-TW',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
    });

	$("body").on("click", "#detail", function() {
		id = $(this).attr('data-id');
		$.ajax({
			type : "get",
			dataType : 'json',
			url : window.location.pathname + '/employeeDetail/' + id,
			success : function(res) {
				$('#firstNameEdit').val(res.firstName);
				$('#lastNameEdit').val(res.lastName);
				$('#birthDateEdit').val(res.birthDate);
				$('#cellPhoneEdit').val(res.cellPhone);
			},
			error : function(res) {
				console.log(res);
			}
		});
	});
});

function newModalSave() {
	var data = {
		'firstName' : $('#firstName').val(),
		'lastName' : $('#lastName').val(),
		'birthDate' : $('#birthDate').val(),
		'cellPhone' : $('#cellPhone').val()
	};

	$.ajax({
		type : "post",
		dataType : 'json',
		url : window.location.pathname + '/employeeNew',
		contentType : "application/json",
		data : JSON.stringify(data),
		success : function(res) {
			if(res.result == null || res.result == ""){
				if($.isArray(res.message)){
					$('#msgModal .modal-header').empty();
					$.each(res.message, function(index){
						$('#msgModal .modal-header').append("<center><h4 class='modal-title'>" +res.message[index].message+ "</h4></center>");
					});
				}else{
					$('#msgModal h4').html(res.message);
				}
				$('#newModal').modal('hide');
				$('#msgModal').modal("show");
				return;
			}
			
			$('#newModal').modal('hide');
			location.reload(true);
		},
		error : function(res) {
			console.log(res);
		}
	});
}

function updateModalSave() {
	var data = {
			'id' : id,
			'firstName' : $('#firstNameEdit').val(),
			'lastName' : $('#lastNameEdit').val(),
			'birthDate' : $('#birthDateEdit').val(),
			'cellPhone' : $('#cellPhoneEdit').val()
		};
	
	$.ajax({
		type : "post",
		dataType : 'json',
		url : window.location.pathname + '/employeeUpdate',
		contentType : "application/json",
		data : JSON.stringify(data),
		success : function(res) {
			if(res.result == null || res.result == ""){
				if($.isArray(res.message)){
					$('#msgModal .modal-header').empty();
					$.each(res.message, function(index){
						$('#msgModal .modal-header').append("<center><h4 class='modal-title'>" +res.message[index].message+ "</h4></center>");
					});
				}else{
					$('#msgModal h4').html(res.message);
				}
				$('#updateModal').modal('hide');
				$('#msgModal').modal("show");
				return;
			}
			
			$('#updateModal').modal('hide');
			location.reload(true);
		},
		error : function(res) {
			console.log(res);
		}
	});
}

function deleteItem(id) {
	$.ajax({
		type : "get",
		dataType : 'json',
		url : window.location.pathname + '/employeeDelete/' + id,
		contentType : "application/json",
		success : function(res) {
			location.reload(true);
		},
		error : function(res) {
			console.log(res);
		}
	});
}

function cancel(){
	$('#newModal input').val('');
	$('#updateModal input').val('');
};
</script>
<jsp:include page="constant.jsp"/>
</head>
<body>
	<jsp:include page="${requestScope.language}"/>
	<div>
		<center>
			<table class="table" style="width: 100%">
				<tr>
					<td>
						<table style="position: relative; width: 100%; top: 35px;">
							<tr>
								<td align='right'>
									<button style='margin-right: 2px;' type="button" class="btn btn-info" data-toggle="modal" data-target="#newModal"><spring:message code="label.new"/></button>
								</td>
							</tr>
							<tr><td><br></td></tr>
							<tr>
								<td>
									<table class="table table-bordered">
										<thead>
											<tr>
												<th><spring:message code="label.manager"/></th>
												<th><spring:message code="label.first.name"/></th>
												<th><spring:message code="label.last.name"/></th>
												<th><spring:message code="label.birth.date"/></th>
												<th><spring:message code="label.cellphone"/></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${list}" var="employee">
												<tr>
													<td>
														<button type="button" class="btn btn-info" data-id="${employee.id}" id="detail" data-toggle="modal" data-target="#updateModal"><spring:message code="label.edit"/></button> /
														<button type="button" class="btn btn-info" onclick="deleteItem(${employee.id})"><spring:message code="label.delete"/></button>
													</td>
													<td>${employee.firstName}</td>
													<td>${employee.lastName}</td>
													<td>${employee.birthDate}</td>
													<td>${employee.cellPhone}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</center>
	</div>
	
	<!-- newModal -->
	<div id="newModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content -->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"><spring:message code="modal.title.new.employee"/></h4>
					<label id="exist"></label>
				</div>
				<div class="modal-body">
					<button type="button" class="btn btn-default" data-dismiss="modal" onclick="cancel()">
						<spring:message code="modal.label.cancel"/>
					</button>
					<button type="button" class="btn btn-default" onclick="newModalSave();">
						<spring:message code="modal.label.save"/>
					</button>
					<hr class="divider1">
					<div class="input-group">
						<form id="updateForm">
							<table class='modal-style'>
								<tr>
									<td>
										<label><spring:message code="modal.label.employee.first.name"/>: </label> 
										<input id="firstName" type="text" class="form-control">
									</td>
								</tr>
								<tr>
									<td>
										<label><spring:message code="modal.label.employee.last.name"/>: </label>
										<input id="lastName" type="text" class="form-control">
									</td>
								</tr>
								<tr>
									<td>
										<label><spring:message code="modal.label.employee.birth.date"/>: </label>
										<div class="input-group date form_date" data-date="" data-date-format="yyyy/mm/dd" data-link-field="birthDate" data-link-format="yyyy/mm/dd">
											<input id="birthDate" class="form-control" type="text" placeholder="<spring:message code="modal.label.employee.birth.date.format"/>" readonly> 
											<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<label><spring:message code="modal.label.employee.cellphone"/>: </label>
										<input id="cellPhone" type="text" class="form-control">
									</td>
								</tr>
							</table>
						</form>
					</div>
				</div>
				<div class="modal-footer"></div>
			</div>
		</div>
	</div>
	
	<!-- updateModal -->
	<div id="updateModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content -->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"><spring:message code="modal.title.edit.employee"/></h4>
					<label id="exist"></label>
				</div>
				<div class="modal-body">
					<button type="button" class="btn btn-default" data-dismiss="modal" onclick="cancel()">
						<spring:message code="modal.label.cancel"/>
					</button>
					<button type="button" class="btn btn-default" onclick="updateModalSave();">
						<spring:message code="modal.label.save"/>
					</button>
					<hr class="divider1">
					<div class="input-group">
						<form id="updateForm">
							<table class='modal-style'>
								<tr>
									<td>
										<label><spring:message code="modal.label.employee.first.name"/>: </label> 
										<input id="firstNameEdit" type="text" class="form-control">
									</td>
								</tr>
								<tr>
									<td>
										<label><spring:message code="modal.label.employee.last.name"/>: </label>
										<input id="lastNameEdit" type="text" class="form-control">
									</td>
								</tr>
								<tr>
									<td>
										<label><spring:message code="modal.label.employee.birth.date"/>: </label>
										<div class="input-group date form_date" data-date="" data-date-format="yyyy/mm/dd" data-link-field="birthDate" data-link-format="yyyy/mm/dd">
											<input id=birthDateEdit class="form-control" type="text" placeholder="<spring:message code="modal.label.employee.birth.date.format"/>" readonly> 
											<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<label><spring:message code="modal.label.employee.cellphone"/>: </label>
										<input id="cellPhoneEdit" type="text" class="form-control">
									</td>
								</tr>
							</table>
						</form>
					</div>
				</div>
				<div class="modal-footer"></div>
			</div>
		</div>
	</div>
	
	<!-- msgModal -->
	<div id="msgModal" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content -->
			<div class="modal-content">
				<div class="modal-header">
					<center><h4 class="modal-title"></h4></center>
				</div>
				<div class="modal-body">
					<center><button type="button" class="btn btn-default" data-dismiss="modal">取消</button></center>
				</div>
			</div>
		</div>
	</div>
</body>
</html>