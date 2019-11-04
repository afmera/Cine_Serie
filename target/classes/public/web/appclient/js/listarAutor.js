function detalle(target){
    var id = $(target).data("id");
    window.location.replace("?p=detalleAutor&id="+id);
}

function eliminar(target) {
    var id = $(target).data("id");
    httpConnect("/autor/" + id, null, "DELETE",
            function (r) {
                alert(r.message);
                cargarDatos();
            });
}

function cargarDatos() {
    httpConnect("/autor", null, "GET", function (r) {
        var html = "";
        for (var i = 0; i < r.data.length; i++) {
            var libro = r.data[i];
            html += "<tr>";
            html += "<td>" + libro.nombre + "</td>";
            html += "<td>" + libro.fechaNacimiento + "</td>";
            html += "<td>";
            html += "<div data-id='" + i + "' class='material-icons delete' style='color:red'>delete</div>";
            html += "<div data-id='" + i + "' class='material-icons edit' style='color:green'>edit</div>";
            html += "</td>";
            html += "</tr>";
        }
        $("tbody").html(html);

        $(".delete").click(function () {
            if (confirm("Desea eliminar el recurso?")) {
                eliminar(this);
            }
        });
        $(".edit").click(function () {
            detalle(this);
        });

    });
}

$(function () {
    cargarDatos();
});