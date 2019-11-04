$(function(){
    $("#frmCrear").submit(function(){
        var libro = new Object();
        libro.nombre = $("#nombre").val();
        libro.cantidadPaginas = $("#cantidadPaginas").val();
        
        var jlibro = JSON.stringify(libro);
        
        httpConnect("/categoria",jlibro,"POST",function(r){
            alert(r.message+"-"+r.data.nombre);
            $("button[type=reset]").click();
        });
        
        return false;
    });
});