package com.ssj.gis4.util;

import com.ssj.gis4.domain.Ellipse;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.Arrays;

/**
 * ClassName: EllipseParameters
 * Packge:
 * Description:
 *
 * @Author:孙世杰
 * @Create 2024/6/27 21:26
 * Version 1.0
 */

public class EllipseParameters {

    //通过协方差矩阵求椭圆————蛮差的
    public static Ellipse js(double[][] data){
        double centerX = 0;
        double centerY = 0;
        for (int i = 0; i < data.length; i++) {
            centerX += data[i][0];
            centerY += data[i][1];
        }
        centerX /= data.length;
        centerY /= data.length;

        // 中心化数据
        double[][] centeredData = new double[data.length][];
        for (int i = 0; i < data.length; i++) {
            centeredData[i] = new double[]{data[i][0] - centerX, data[i][1] - centerY};
        }

        // 计算协方差矩阵
        RealMatrix covarianceMatrix = new Array2DRowRealMatrix(centeredData).transpose().multiply(new Array2DRowRealMatrix(centeredData)).scalarMultiply(1.0 / (data.length - 1));

        // 特征值分解
        EigenDecomposition decomposition = new EigenDecomposition(covarianceMatrix);

        // 获取特征值和特征向量
        double[] eigenvalues = decomposition.getRealEigenvalues();
        double[][] eigenvectors = decomposition.getV().getData();

        // 假设eigenvalues[0]是长轴的平方，eigenvalues[1]是短轴的平方
        double a = Math.sqrt(Math.abs(eigenvalues[0]));
        double b = Math.sqrt(Math.abs(eigenvalues[1]));

        // 找到与长轴对应的特征向量（通常是eigenvalues[0]对应的eigenvectors[0]）
        double[] majorAxisVector = eigenvectors[0];

        // 使用atan2来计算长轴与x轴的夹角（弧度）
        double angleRad = Math.atan2(majorAxisVector[1], majorAxisVector[0]);

        // 如果需要，将角度转换为度
        double angleDeg = Math.toDegrees(angleRad);


        for (int i = 0; i < data.length; i++) {

            double[] doubles = scaleEllipseToContainPoint(data[i][0], data[i][1], centerX, centerY, a, b, angleRad);
            a=doubles[0];
            b=doubles[1];
        }

        // 返回Ellipse对象，包含所有计算出的参数
        return new Ellipse(centerX, centerY, a/2, b/2, angleRad); // 注意这里假设Ellipse构造函数接受角度的度表示
    }

    public static boolean isPointInsideEllipse(double x, double y, double cx, double cy, double a, double b, double thetaRadians) {
        // 将点转换到椭圆的主轴坐标系
        double xPrime = (x - cx) * Math.cos(thetaRadians) - (y - cy) * Math.sin(thetaRadians);
        double yPrime = (x - cx) * Math.sin(thetaRadians) + (y - cy) * Math.cos(thetaRadians);
        // 判断点是否在椭圆内或椭圆上
        return (Math.pow(xPrime, 2) / Math.pow(a, 2)) + (Math.pow(yPrime, 2) / Math.pow(b, 2)) <= 1;
    }

    public static double[] scaleEllipseToContainPoint(double x, double y, double cx, double cy, double a, double b, double thetaRadians) {
        // 计算点到椭圆中心的距离
        double distanceToCenter = Math.sqrt(Math.pow(x - cx, 2) + Math.pow(y - cy, 2));

        // 转换点到椭圆主轴坐标系
        double xPrime = (x - cx) * Math.cos(thetaRadians) - (y - cy) * Math.sin(thetaRadians);
        double yPrime = (x - cx) * Math.sin(thetaRadians) + (y - cy) * Math.cos(thetaRadians);

        // 计算主轴坐标系下的点到中心的距离
        double distanceToCenterPrime = Math.sqrt(Math.pow(xPrime, 2) + Math.pow(yPrime, 2));

        // 确定缩放比例
        double scale = Math.max(distanceToCenterPrime / a, distanceToCenterPrime / b);
        if (scale < 1) {
            // 如果点已经在椭圆内，则不需要缩放
            scale = 1;
        }

        // 计算新的椭圆尺寸
        double newA = a * scale;
        double newB = b * scale;

        // 返回新的椭圆尺寸
        return new double[]{newA, newB};
    }


    public static void main(String[] args) {
//        double[][] data = {{1, 2}, {2, 2}, {2, 3}, {3, 2}, {3, 3}, {3, 4}, {4, 3}, {4, 4}};

        double[][] data ={ {119.806455,24.766068} ,{119.536455,24.846068},{118.936455,24.766068},{119.326455,24.566068} };
        System.out.println(data.length);
        Ellipse ellipse = js(data);


        System.out.println(ellipse);
    }
}
