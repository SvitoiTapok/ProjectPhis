package x.project;

import lombok.Getter;

@Getter
public enum Mode {
    VELOCITY("Скорость"),
    FRICTION_FORCE("Сила трения"),
    SPRING_FORCE("Сила упругости"),
    ACCELERATION("Ускорение");

    private final String name;

    Mode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
