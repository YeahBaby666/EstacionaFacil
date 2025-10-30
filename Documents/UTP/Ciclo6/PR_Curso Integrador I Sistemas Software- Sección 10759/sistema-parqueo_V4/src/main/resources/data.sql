-- Insertar espacios de parqueo (50 espacios)
INSERT INTO espacio (ubicacion, estado) VALUES
('A-01', 'DISPONIBLE'), ('A-02', 'DISPONIBLE'), ('A-03', 'DISPONIBLE'), ('A-04', 'DISPONIBLE'), ('A-05', 'DISPONIBLE'),
('A-06', 'DISPONIBLE'), ('A-07', 'DISPONIBLE'), ('A-08', 'DISPONIBLE'), ('A-09', 'DISPONIBLE'), ('A-10', 'DISPONIBLE'),
('B-01', 'DISPONIBLE'), ('B-02', 'DISPONIBLE'), ('B-03', 'DISPONIBLE'), ('B-04', 'DISPONIBLE'), ('B-05', 'DISPONIBLE'),
('B-06', 'DISPONIBLE'), ('B-07', 'DISPONIBLE'), ('B-08', 'DISPONIBLE'), ('B-09', 'DISPONIBLE'), ('B-10', 'DISPONIBLE'),
('C-01', 'DISPONIBLE'), ('C-02', 'DISPONIBLE'), ('C-03', 'DISPONIBLE'), ('C-04', 'DISPONIBLE'), ('C-05', 'DISPONIBLE'),
('C-06', 'DISPONIBLE'), ('C-07', 'DISPONIBLE'), ('C-08', 'DISPONIBLE'), ('C-09', 'DISPONIBLE'), ('C-10', 'DISPONIBLE'),
('D-01', 'DISPONIBLE'), ('D-02', 'DISPONIBLE'), ('D-03', 'DISPONIBLE'), ('D-04', 'DISPONIBLE'), ('D-05', 'DISPONIBLE'),
('D-06', 'DISPONIBLE'), ('D-07', 'DISPONIBLE'), ('D-08', 'DISPONIBLE'), ('D-09', 'DISPONIBLE'), ('D-10', 'DISPONIBLE'),
('E-01', 'DISPONIBLE'), ('E-02', 'DISPONIBLE'), ('E-03', 'DISPONIBLE'), ('E-04', 'DISPONIBLE'), ('E-05', 'DISPONIBLE'),
('E-06', 'DISPONIBLE'), ('E-07', 'DISPONIBLE'), ('E-08', 'DISPONIBLE'), ('E-09', 'DISPONIBLE'), ('E-10', 'DISPONIBLE')
ON CONFLICT (ubicacion) DO NOTHING;

-- Insertar tarifas base
INSERT INTO tarifa (tipo, descripcion, monto) 
VALUES ('HORA_NORMAL', 'Tarifa por Hora de Estacionamiento', 5.00),
       ('COSTO_RESERVA', 'Costo Fijo por Reservar', 2.00)
ON CONFLICT DO NOTHING;