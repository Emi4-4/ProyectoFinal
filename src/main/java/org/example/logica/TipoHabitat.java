package org.example.logica;

public enum TipoHabitat {
    // ACUARIOS (para peces)
    ACUARIO_PEQUEÑO(TipoAnimal.PEZ, 5, 300, "Acuario Pequeño"),
    ACUARIO_MEDIANO(TipoAnimal.PEZ, 15, 600, "Acuario Mediano"),
    ACUARIO_GRANDE(TipoAnimal.PEZ, 30, 1200, "Acuario Grande"),

    // HÁBITATS FELINOS (para gatos)
    HABITAT_FELINO_PEQUEÑO(TipoAnimal.GATO, 2, 500, "Hábitat Felino Pequeño"),
    HABITAT_FELINO_MEDIANO(TipoAnimal.GATO, 4, 1000, "Hábitat Felino Mediano"),
    HABITAT_FELINO_GRANDE(TipoAnimal.GATO, 8, 2000, "Hábitat Felino Grande"),

    // HÁBITATS CANINOS (para perros)
    HABITAT_CANINO_PEQUEÑO(TipoAnimal.PERRO, 2, 600, "Hábitat Canino Pequeño"),
    HABITAT_CANINO_MEDIANO(TipoAnimal.PERRO, 4, 1100, "Hábitat Canino Mediano"),
    HABITAT_CANINO_GRANDE(TipoAnimal.PERRO, 8, 2200, "Hábitat Canino Grande"),

    // JAULAS PARA PÁJAROS
    JAULA_PEQUEÑA(TipoAnimal.PAJARO, 5, 200, "Jaula Pequeña"),
    JAULA_MEDIANA(TipoAnimal.PAJARO, 10, 400, "Jaula Mediana"),
    JAULA_GRANDE(TipoAnimal.PAJARO, 20, 800, "Jaula Grande");

    private final TipoAnimal tipoAnimal;
    private final int capacidad;
    private final int precio;
    private final String nombre;

    TipoHabitat(TipoAnimal tipoAnimal, int capacidad, int precio, String nombre) {
        this.tipoAnimal = tipoAnimal;
        this.capacidad = capacidad;
        this.precio = precio;
        this.nombre = nombre;
    }

    public TipoAnimal getTipoAnimal() {
        return tipoAnimal;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }
}