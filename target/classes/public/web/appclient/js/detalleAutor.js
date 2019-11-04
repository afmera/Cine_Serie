function cargarDetalle() {
    var id = getParameterByName("id");
    httpConnect("/autor/" + id, null, "GET",function(r){
        if(r.status!==200){
            alert(r.message);
            window.location.replace("?p=listarAutor");
        }
        $("#nombre").val(r.data.nombre);
        $("#fechaNacimiento").val(r.data.fechaNacimiento);
        $("#id").val(id);
    },function(e){
        alert(e);
        window.location.replace("?p=listarAutor");
    });
}

$(function () {
    cargarDetalle();
    $("#frmUpdate").submit(function(){
        var entidad = new Object();
        entidad.nombre = $("#nombre").val();
        entidad.cantidadPaginas = $("#fechaNacimiento").val();
        var jentidad = JSON.stringify(entidad);
        
        var id=$("#id").val();
        httpConnect("/autor/"+id,jentidad,"PUT",function(r){
            alert(r.message+"-"+r.data.nombre);
            window.location.replace("?p=listarAutor");
        });
        return false;
    });
});