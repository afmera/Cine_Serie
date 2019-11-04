function cargarCategorias() {
    httpConnect("/categoria", null, "GET", function (r) {
        var html = "<select id='categoria' name='categoria' class='form-control' required>";
        html += "<option value=''>[SELECCIONAR OPCION]</option>";
        for (var i = 0; i < r.data.length; i++) {
            var o = r.data[i];
            html += "<option value='" + i + "'>" + o.nombre + "</option>";
        }
        html += "</select>";
        $("#contentCategoria").html(html);
    });
}

$(function () {
    cargarCategorias();
    
    $('#example').dashboardCodeBsMultiSelect();
    
    $("#frmCrear").submit(function () {
        var libro = new Object();
        libro.nombre = $("#nombre").val();
        libro.cantidadPaginas = $("#cantidadPaginas").val();
        libro.categoria = $("#categoria").val();
        //************
        var jlibro = JSON.stringify(libro);
        //************
        httpConnect("/libro", jlibro, "POST", function (r) {
            alert(r.message + "-" + r.data.nombre);
            $("button[type=reset]").click();
        });
        //************
        return false;
    });
});

//http://develoteca.com/plugin-jquery-crear-multiselect-bootstrap/