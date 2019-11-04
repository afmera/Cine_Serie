/**
 *
 * @author ANDRÉS FELIPE MERA TRÓCHEZ
 */

/**
 * Metodo principal para la gestion de la tupla selecionada.
 * @returns {undefined} false.
 */
$(function () {
//    $('#tipo').selectpicker();
    $("#frmCrear").submit(function () {
        var entity = new Object();
        entity.conTitulo = $("#titulo").val();
        entity.conAnoLanzamiento = $("#ano_lazamiento").val();
        entity.conLongitudMinutos = $("#duracion").val();
        if (sinopsis.length > 10000)
        {
            alert("ADVERTENCIA", "La ca de texto debe tener menos de 10000 caracteres.");
        }
        else
        {
            entity.conSinopsis = $("#sinopsis").val();
            entity.conTipo = $("#tipo").val();
            entity.conCantidad = $("#cantidad").val();
            //************
            var jEntity = JSON.stringify(entity);
            //************
            httpConnect("/contenido", jEntity, "POST", function (r) {
                alert(r.message + "-" + r.data.conTitulo);
                $("button[type=reset]").click();
            });
            //************
        }
        return false;
    });
});