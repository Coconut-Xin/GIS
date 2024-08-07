package com.ssj.gis4.domain;

import lombok.*;

/**
 * ClassName: Ellipse
 * Packge:
 * Description:
 *
 * @Author:孙世杰
 * @Create 2024/6/27 21:26
 * Version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ellipse {
    Double centerX;
    Double centerY;
    Double radiusLong;
    Double radiusShort;
    Double rotation  ;

}
