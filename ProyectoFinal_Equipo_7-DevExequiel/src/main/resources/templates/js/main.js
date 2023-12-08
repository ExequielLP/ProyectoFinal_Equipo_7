    function mostrarProveedores(rubroId) {
        // Oculta todos los proveedores
        $('[class^="cardProveedor"]').hide();
        
        // Muestra solo los proveedores correspondientes al rubro seleccionado
        $('[id^="proveedor_"]').hide(); // Oculta todos los proveedores antes de mostrar el seleccionado
        $('[data-rubro-id="' + rubroId + '"]').show(); // Muestra los proveedores correspondientes al rubro seleccionado
    }