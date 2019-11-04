$(function(){
    $("#frmCrear").submit(function(){
        var entidad = new Object();
        entidad.nombre = $("#nombre").val();
        entidad.fechaNacimiento = $("#fechaNacimiento").val();
        
        var jentidad = JSON.stringify(entidad);
        
        httpConnect("/autor",jentidad,"POST",function(r){
            alert(r.message+"-"+r.data.nombre);
            $("button[type=reset]").click();
        });
        
        return false;
    });
});