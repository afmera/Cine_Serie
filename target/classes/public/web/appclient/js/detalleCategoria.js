function cargarDetalle() {
    var id = getParameterByName("id");
    httpConnect("/categoria/" + id, null, "GET",function(r){
        if(r.status!==200){
            alert(r.message);
            window.location.replace("?p=listarCategoria");
        }
        $("#nombre").val(r.data.nombre);
        $("#id").val(id);
    },function(e){
        alert(e);
        window.location.replace("?p=listarCategoria");
    });
}

$(function () {
    cargarDetalle();
    $("#frmUpdate").submit(function(){
        var entidad = new Object();
        entidad.nombre = $("#nombre").val();
        var jentidad = JSON.stringify(entidad);
        
        var id=$("#id").val();
        httpConnect("/categoria/"+id,jentidad,"PUT",function(r){
            alert(r.message+"-"+r.data.nombre);
            window.location.replace("?p=listarCategoria");
        });
        return false;
    });
});