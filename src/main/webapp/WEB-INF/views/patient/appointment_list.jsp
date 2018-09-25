<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section class="container">
	<h1>
		Hello ${patient.firstName} :
		<spring:message code="appointments" />
	</h1>
	<div class="form-group form-group-sm">
		<table class="table">
			<tbody>
				<c:forEach items="${appointments}" var="appointment">
					<tr>
						<td>----------------------------------------------------</td>
					</tr>
					<tr>
						<td>Doctor name : ${appointment.doctor.firstName} ${appointment.doctor.lastName}</td>
					</tr>
					<tr>
						<td>Doctor Category : ${appointment.doctor.doctorCategory.categoryName}</td>
					</tr>
					<tr>
						<td>Time : ${appointment.timeSpan}</td>
					</tr>
					<tr>
						<td>Date : ${appointment.date}</td>
					</tr>
					<tr>
						<td>----------------------------------------------------</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="5"><a href="<spring:url value='/' />"
						class="btn btn-default"><spring:message code="home" /></a></td>
				</tr>
			</tbody>
		</table>
	</div>
</section>
