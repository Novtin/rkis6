package javaClasses.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * Сущность "Очки"
 */
@Entity
@Table(name = "glasses")
public class Glasses {
    /**
     * Уникальный идентификатор
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Диоптрии
     */
    @Column(name = "diopters")
    @Min(-15) @Max(15)
    private Double diopters;

    /**
     * Длина дужек
     */
    @Column(name = "length_arches")
    @Min(1) @Max(100)
    private Double lengthArches;

    /**
     * Цвет линз
     */
    @Column(name = "lens_color")
    private String lensColor;

    /**
     * Цвет оправы
     */
    @Column(name = "frame_color")
    private String frameColor;

    /**
     * Бренд
     */
    @Column(name = "brand")
    private String brand;

    /**
     * Конструктор с параметрами
     * @param diopters - диоптрии
     * @param lengthArches - длина дужек
     * @param lensColor - цвет линз
     * @param frameColor - цвет оправы
     * @param brand - бренд
     */
    public Glasses(Double diopters, Double lengthArches, String lensColor, String frameColor, String brand) {
        this.diopters = diopters;
        this.lengthArches = lengthArches;
        this.lensColor = lensColor;
        this.frameColor = frameColor;
        this.brand = brand;
    }

    /**
     * Конструктор без параметров
     */
    public Glasses() {
    }

    /**
     * Геттер для id
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Сеттер для id
     * @param id - идентификатор
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Геттер для диоптрий
     * @return диоптрии
     */
    public Double getDiopters() {
        return diopters;
    }

    /**
     * Сеттер для диоптрий
     * @param diopters - диоптрии
     */
    public void setDiopters(Double diopters) {
        this.diopters = diopters;
    }

    /**
     * Геттер для длины дужек
     * @return длина дужек
     */
    public Double getLengthArches() {
        return lengthArches;
    }

    /**
     * Сеттер для длины дужек
     * @param lengthArches - длина дужек
     */
    public void setLengthArches(Double lengthArches) {
        this.lengthArches = lengthArches;
    }

    /**
     * Геттер для цвета линз
     * @return цвет линз
     */
    public String getLensColor() {
        return lensColor;
    }

    /**
     * Сеттер для цвета линзк
     * @param lensColor - цвет линз
     */
    public void setLensColor(String lensColor) {
        this.lensColor = lensColor;
    }

    /**
     * Геттер для цвета оправы
     * @return цвет оправы
     */
    public String getFrameColor() {
        return frameColor;
    }

    /**
     * Сеттер для цвета оправы
     * @param frameColor - цвет оправы
     */
    public void setFrameColor(String frameColor) {
        this.frameColor = frameColor;
    }

    /**
     * Геттер для бренда
     * @return бренд
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Сеттер для бренда
     * @param brand - бренд
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }
}
