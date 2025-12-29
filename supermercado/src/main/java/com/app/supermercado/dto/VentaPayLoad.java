package com.app.supermercado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VentaPayLoad {

    private Long sucursalId;
    private List<DetalleProductoCantidad> detalle;

}
