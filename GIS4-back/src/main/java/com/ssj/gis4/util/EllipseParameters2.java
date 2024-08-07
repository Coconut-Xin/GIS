package com.ssj.gis4.util;

import com.ssj.gis4.domain.Ellipse;

/**
 * ClassName: EllipseParameters2
 * Packge:
 * Description:
 *
 * @Author:孙世杰
 * @Create 2024/6/28 21:23
 * Version 1.0
 */
public class EllipseParameters2 {


    public static Ellipse findEllipse(double[][] data) {

        if(data.length==1){
            new Ellipse(data[0][0], data[0][0], 0.05, 0.05, 0.0);
        }

        double centerX = 0;
        double centerY = 0;
        for (int i = 0; i < data.length; i++) {
            centerX += data[i][0];
            centerY += data[i][1];
        }
        centerX /= data.length;
        centerY /= data.length;
        double farthestX =0.0;
        double farthestY =0.0;
        double fdist=0.0;
        double a=0.0;
        double b=0.1;
        for (int i = 0; i < data.length; i++) {
            double dist = Math.sqrt(Math.pow(data[i][0] - centerX,2) + Math.pow(data[i][1] - centerY,2));;
            if(dist>fdist){
                farthestX = data[i][0];
                farthestY = data[i][1];
                fdist=dist;
            }
        }
        a=fdist;
        // 旋转坐标系，使得长轴与x轴对齐
        double angleRad = Math.atan2(farthestY - centerY, farthestX - centerX);

        angleRad=(angleRad+Math.PI)%Math.PI;
        double cosTheta = Math.cos(2*Math.PI-angleRad);
        double sinTheta = Math.sin(2*Math.PI-angleRad);


        for (int i = 0; i < data.length; i++) {
               //此时，我们计算而来的是经过旋转而来的但是没有平移的点的横纵坐标
               double tempX= data[i][0]-centerX;
               double tempY= data[i][1]-centerY;

               //我们要给横纵坐标旋转回去，就要旋转 （2PI-angleRad）这个角度，按照公式求sin和cos即可 ,上面求的时候已经直接计算过了
               double newX =tempX*cosTheta - tempY*sinTheta;  // 注意 angleRad 和2PI-angleRad）的关系
               double newY =tempX*sinTheta + tempY*cosTheta; // 注意 angleRad 和2PI-angleRad）的关系

               //不能除0
                if(Math.abs(newX)==fdist) continue;;

//               double bSquared = (fdist * fdist * (1 - (newY * newY) / (fdist * fdist))) / ((newX * newX) / (fdist * fdist) + 1);
             double bSquared = (newY*newY)/(1-( newX*newX/fdist/fdist ) );
              if(bSquared<0){
                  System.out.println("bSquaredL:" +bSquared);
                  bSquared = 0.0;
              }
               if(bSquared>b*b) b=Math.sqrt(bSquared);
        }

        a=fdist;


        return new Ellipse(centerX, centerY, a, b, angleRad);
    }

    public static void main(String[] args) {
//        double[][] data = {{1, 2}, {2, 2}, {2, 3}, {3, 2}, {3, 3}, {3, 4}, {4, 3}, {4, 4}};

        double[][] data ={ {119.806455,24.766068} ,{119.536455,24.846068},{118.936455,24.766068},{119.326455,24.566068} };
        System.out.println(data.length);
        Ellipse ellipse =findEllipse(data);


        System.out.println(ellipse);
    }
}
