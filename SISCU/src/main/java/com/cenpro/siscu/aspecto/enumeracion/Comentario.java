package com.cenpro.siscu.aspecto.enumeracion;

public enum Comentario
{
    /*Aplicación*/
    ConsultaTodos("Se intenta consultar todos los(as) datos de %s"),
    Consulta("Se intenta consultar los(as) datos de %s"),
    ConsultaTipoDocumento("Se intenta consultar por Tipo de Documento los(as) datos de %s"),
    ConsultaDetalle("Se intenta consultar el Detalle de los(as) datos de %s"),
    ConsultaComisiones("Se intenta consultar las Comisiones de los(as) datos de %s"),
    ConsultaNoConciliada("Se intenta consultar las Transacciones No Conciliadas de los(as) datos de %s"),
    Registro("Se intenta registrar el(a) %s %s"),
    Actualizacion("Se intenta actualizar el(a) %s con los datos %s"),
    Eliminacion("Se intenta eliminar el(a) %s %s"),
    Reporte("Se intenta generar el(a) reporte de %s"),
    Visita("Se intenta visitar la página de %s"),
    VisitaConsulta("Se intenta visitar la página de Consulta de %s"),
    Ejecucion("Se intenta ejecutar el proceso de %s %s"),
    
    /*Login*/
    CredencialCorrecta("El Usuario ha accedido al sistema exitosamente"),
    CredencialIncorrecta("El Usuario intenta acceder al sistema con credenciales incorrectas"),
    NoActivo("El usuario %s intenta acceder al sistema con una cuenta bloqueada"),
    UsuarioNoEncontrado("El Usuario intenta acceder al sistema con un usuario no encontrado"),
    ErrorActiveDirectory("Ocurrió un error de conexión durante la autenticación del usuario con el Active Directory"),
    Logout("El usuario ha salido del sistema"),
    
    Ninguno("");
    
    private final String nombre;       

    private Comentario(String s) {
        nombre = s;
    }

    public String toString() {
       return this.nombre;
    }  
}