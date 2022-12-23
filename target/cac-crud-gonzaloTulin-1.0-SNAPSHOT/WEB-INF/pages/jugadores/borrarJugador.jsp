<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../comunes/inicioHTML.jsp">
    <jsp:param name="elTitulo" value="Borrar a ${jugadorABorrar.nombreCompleto}" />
</jsp:include>

<jsp:include page="../comunes/navbar.jsp" />

<section class="container py-3">
    <div class="row">
        <h1 class="h3">Borrando jugador</h1>
        <p class="lead">Estás a punto de borrar a ${jugadorABorrar.nombreCompleto}.</p>
    </div>
    <div class="container px-4 mt-3">
        <div class="row align-items-center justify-content-center">
            <div class="col-sm-7 col-md-6 col-lg-4" >
                <div class="card h-100">
                    <jsp:include page="partes/dataCardJugador.jsp">
                        <jsp:param name="fotoJugador" value="${jugadorABorrar.foto}" />
                        <jsp:param name="nombreCompletoJugador" value="${jugadorABorrar.nombreCompleto}" />
                    </jsp:include>
                </div>
            </div>
            <div class="col-sm-5 col-md-6 col-lg-4">
                <form action="${pageContext.request.contextPath}/app?accion=delete&id=${jugadorABorrar.id}"
                      method="post" class="was-validated border p-2 rounded-3 bg-light">
                    <div class="row text-center">
                        <div class="col-12 mb-2">
                            <p class="lead m-0">¿Estás seguro/a?</p>
                        </div>
                        <div class="col-12">
                            <a href="${pageContext.request.contextPath}/app" class="btn btn-secondary">Mejor no</a>
                            <button type="submit" class="btn btn-danger">Sí, borrar</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>

<jsp:include page="../comunes/footer.jsp"/>
<jsp:include page="../comunes/finHTML.jsp"/>