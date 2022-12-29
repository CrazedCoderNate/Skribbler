package Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@AllArgsConstructor
public class DrawColor {
    @Getter @Setter
    private int xPosition;
    @Getter @Setter
    private int yPosition;
    @Getter @Setter
    private Color color;
}
