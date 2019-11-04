function cargarCategorias(fn) {
    httpConnect("/categoria", null, "GET", function (r) {
        var html = "<select id='categoria' name='categoria' class='form-control' required>";
        html += "<option value=''>[SELECCIONAR OPCION]</option>";
        for (var i = 0; i < r.data.length; i++) {
            var o = r.data[i];
            html += "<option value='" + i + "'>" + o.nombre + "</option>";
        }
        html += "</select>";
        $("#contentCategoria").html(html);
        fn();
    });
}

function cargarDetalle() {
    var id = getParameterByName("id");
    httpConnect("/libro/" + id, null, "GET",function(r){
        if(r.status!==200){
            alert(r.message);
            window.location.replace("?p=listarLibro");
        }
        $("#nombre").val(r.data.nombre);
        $("#cantidadPaginas").val(r.data.cantidadPaginas);
        $("#categoria").val(r.data.categoria);
        $("#id").val(id);
    },function(e){
        alert(e);
        window.location.replace("?p=listarLibro");
    });
}

$(function () {
    cargarCategorias(cargarDetalle);
    $("#frmUpdateLibro").submit(function(){
        var libro = new Object();
        libro.nombre = $("#nombre").val();
        libro.cantidadPaginas = $("#cantidadPaginas").val();
        var jlibro = JSON.stringify(libro);
        
        var id=$("#id").val();
        httpConnect("/libro/"+id,jlibro,"PUT",function(r){
            alert(r.message+"-"+r.data.nombre);
            window.location.replace("?p=listarLibro");
        });
        return false;
    });
});