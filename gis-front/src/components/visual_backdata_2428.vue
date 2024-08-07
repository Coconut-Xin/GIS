<template>
  <div class="container">
    <div class="top" style="height: 30px;margin: 0px auto; padding-left: 30px">
      <el-button type="primary" plain @click="toggleClusterLayer" >集群层</el-button>
      

    </div>
    <div id="map"></div>
    <div id="popup" class="ol-popup">
      <a href="#" id="popup-closer" class="ol-popup-closer"></a>
      <div id="popup-content"></div>
    </div>
    <el-drawer v-model="drawerVisible" title="I am the title" :with-header="false" size="35%">
      <div class="row-container">
        <span class="row-container-item">设备属性信息</span>
      </div>
      <el-row class="row-1" :gutter="20">
        <!-- <i class="el-icon-info tip-icon">&nbsp;&nbsp;资料卡</i>
        <span class="tip-blank"></span> -->
        <div class="tip-name">
          <span class="tip-name-item">设备名称</span>
          <span class="tip-split-item">|</span>
          <span class="tip-name-item-property">{{ currentFeature.get('vehicleName') }}</span>
        </div>
        <span class="tip-blank"></span>
      </el-row>
      <!-- 详细信息 -->
      <el-row class="row-2" :gutter="20">
        <el-col :span="13">
          <!-- //左边部分 -->
          <div class="basic-item">
            <span class="basic-item-property">型号：</span><span>{{ currentFeature.get('vehicleId') }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">时间：</span><span>{{ currentFeature.get('createTime') }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">速度：</span><span>{{ currentFeature.get('speed') }}km/h</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">高度：</span><span>{{ currentFeature.get('height') }}m</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">经度：</span><span>{{ currentFeature.get('longitude') }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">纬度：</span><span>{{ currentFeature.get('latitude') }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">方向角：</span><span>{{ currentFeature.get('directionAngle') }}°</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">俯仰角：</span><span>{{ currentFeature.get('pitchAngle') }}°</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">滚转角：</span><span>{{ currentFeature.get('rollAngle') }}°</span>
          </div>

        </el-col>
        <el-col :span="11">
          <!-- //右边部分 -->
          <div class="basic-item">
            <span class="basic-item-property">剩余航程：</span>
            <span>{{ currentFeature.get('surplusVoyage') }}km</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">剩余时间：</span>
            <span>{{ currentFeature.get('surplusTime') }}min</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">剩余能量：</span>
            <span>{{ currentFeature.get('surplusEnergy') }}Wh</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">任务编号：</span>
            <span>{{ currentFeature.get('taskId') }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">集群编号：</span>
            <span>{{ currentFeature.get('clusterId') }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">通信数量：</span>
            <span>{{ currentFeature.get('communicationsNumber') }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">通信状况：</span>
            <span>{{ currentFeature.get('communicationSituation') }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">损伤状况：</span>
            <span>{{ currentFeature.get('damageSituation') }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">电子干扰：</span>
            <span>{{ currentFeature.get('electronicInterferenceStatus') }}</span>
          </div>
        </el-col>
      </el-row>

    </el-drawer>
  </div>
</template>

<script>
import   'ol/ol.css'
import Map                     from 'ol/Map'
import View                    from 'ol/View'
import TileLayer               from 'ol/layer/Tile'
import VectorLayer             from 'ol/layer/Vector'
import XYZ                     from 'ol/source/XYZ'
import { Vector as VectorSource } from "ol/source";
import Feature                 from "ol/Feature"
import { Point, Polygon }      from 'ol/geom'
import { Style, Fill, Stroke } from 'ol/style'
import { Circle, Text }        from 'ol/style';
import ImageLayer from              'ol/layer/Image';    
import ImageCanvas from             'ol/source/ImageCanvas';  

import { ZoomSlider, FullScreen } from 'ol/control';
import Overlay from "ol/Overlay";
import {fromLonLat} from 'ol/proj';  
import { getRandomData } from '@/api/node/node';
import { getVehicleData } from '@/api/node/node';
import { getClusterData } from '@/api/cluster/cluster';


export default {
  name: 'FirstMap',
  data() {
    return {
      randomData: [],
      featureList: [],
      canvasLayers: [],//canvas的图层列表
      cxtList: [], //canvas的context的列表，用于快速重绘矩阵
      canvasList:[],
      currentFeature: [],
      map: null,
      overlay: null,
      drawerVisible: false,
      clusterLayerVisible: false,
      vehicleData: [], //getVehicleData()方法获取vehicleData数据
      clusterData: [], //getClusterData()方法获取clusterData数据
      ellipseData: [], //获取的ellipseData数据

      ws: null,//webSocket对象
      message: null,//webSocket对象接收到的消息（转换过了）
  
    }
  },
  methods: {
    initMap() {
      const gaodemap = new TileLayer({
        name: '高德地图',
        source: new XYZ({
          // 使用XYZ方式加载高德地图
          url: 'http://webst0{1-4}.is.autonavi.com/appmaptile?lang=zh_cn&size=1&scale=1&style=6&x={x}&y={y}&z={z}',
          wrapX: false
        })
      });
      this.map = new Map({
        target: "map",
        layers: [gaodemap],
        /* view设置地图的显示范围，包括中心点，放大级别，坐标 */
        view: new View({
          center: [120.29, 24.35],
          zoom: 9,
          projection: "EPSG:4326"
        })
      })

      /* 添加地图放大缩小控件 */
      const zoomslider = new ZoomSlider();
      this.map.addControl(zoomslider);

      /* 添加地图全屏控件 */
      const fullscreen = new FullScreen();
      this.map.addControl(fullscreen);


    },

    initWebSocket(){
        this.ws = new WebSocket('ws://localhost:8080/websocket');

        // 连接打开
        this.ws.onopen = () => {
          console.log('WebSocket connection opened');
        };

        // 接收消息
        this.ws.onmessage = (event) => {
          this.message = JSON.parse(event.data);
          this.vehicleData = this.message.nodeList;
          this.clusterData = this.message.clusterlist;
          this.ellipseData = this.message.ellipseList;
          console.log('Message from server:', this.message.nodeList);
          console.log('Message from server_this.message.ellipseList:', this.message.ellipseList);
          //this.updateCanvas();
        };

        // 连接关闭
        this.ws.onclose = () => {
          console.log('WebSocket connection closed');
        };

        // 连接出错
        this.ws.onerror = (error) => {
          console.error('WebSocket error:', error);
        };

    },
    
    //画布~未实现
    initClusetCanvas(){
      canvasS = document.createElement('canvas');  
      const ctx = canvas.getContext('2d'); 
      canvas.width = 256;  
      canvas.height = 256;  


    } ,

    updateCanvas(){
      let i=0;
      var resolution = this.map.getView().getResolution(); 
      console.log("resolution:"+resolution);
      for (; i < this.ellipseData.length; i++) {
        console.log("updateCanvasING1111111111111111:");
          if(i>=this.cxtList.length) {
            console.log("updateCanvasING222222222222222222:");
            var canvas=document.createElement('canvas'); 
            this.canvasList.push(canvas);
            this.cxtList.push(canvas.getContext('2d'));
            var imageSource = new ImageCanvas({    
              canvasFunction: function(extent, resolution, pixelRatio, size, projection) {    
                // 由于我们只是返回预先创建的Canvas，这里不需要重新绘制  
                return  canvas;    
              },    
              projection: "EPSG:4326", // 设置投影   
              ratio: 1, // 设置分辨率比率    
            });  
            // 创建图像图层，使用ImageCanvas源  
            var canvasLayer = new ImageLayer({    
              source: imageSource,    
            });  
            this.canvasLayers.push(canvasLayer);
            this.map.addLayer(canvasLayer);
          }
          var ellipse =this.ellipseData[i];

          //坐标转换
          var centerCoord = this.map.getPixelFromCoordinate(fromLonLat([ellipse.centerX,ellipse.centerY]));
          var radiusX = ellipse.radiusLong / resolution;
          var radiusY = ellipse.radiusShort / resolution;

          //开始绘制
          var canvas=this.canvasList[i]; 
          canvas.width = 256;    
          canvas.height = 256;  
          var ctx=this.cxtList[i];
          ctx.clearRect(0, 0, canvas.width, canvas.height);
          ctx.save(); // 保存当前绘图状态  
          ctx.translate(centerCoord[0], centerCoord[1]); // 将原点移动到椭圆的中心  
          ctx.scale(radiusX / radiusY, 1); // 根据椭圆的长轴和短轴进行缩放  
          ctx.beginPath();  
          ctx.arc(0, 0, radiusY, 0, 2 * Math.PI); // 在缩放后的坐标系中绘制一个圆，它看起来就像一个椭圆  
          ctx.fill();  
          //ctx.stroke(); // 描边椭圆，如果你想填充则使用ctx.fill()并设置fillStyle
          ctx.restore(); // 恢复之前保存的绘图状态  

      }
      //后面应该继续遍历 i 《 canvasLayers ,将此外的canvasLayers进行隐藏/删除，
      //同时要考虑只有一个点的特殊情况，直接绘制圆即可


    },
    


    /**
    * 根据后端数据，批量添加坐标点
    */
    handleAddBatchFeature() {
      var properData = this.vehicleData ;
      console.log("根据传入的vehicleData批量添加点", properData);
      const _that = this;
      // _that.features = [];
      /*将矢量数据源添加到矢量图层*/
      _that.layervector = new VectorLayer({
        source: new VectorSource(),
        name: '系统层'
      })
      _that.map.addLayer(_that.layervector);
      // _that.map.getLayers().push(_that.layervector);
      // 循环添加point
      for (let i = 0; i < properData.length; i++) {
        // 创建point
        let point = new Feature({
          geometry: new Point([properData[i].longitude, properData[i].latitude]),
          vehicleName: properData[i].vehicleName,
          vehicleId: properData[i].vehicleId,
          createTime: properData[i].createTime,
          speed: properData[i].speed,
          longitude: properData[i].longitude,
          latitude: properData[i].latitude,
          height: properData[i].height,
          directionAngle: properData[i].directionAngle,
          pitchAngle: properData[i].pitchAngle,
          rollAngle: properData[i].rollAngle,
          surplusVoyage: properData[i].surplusVoyage,
          surplusTime: properData[i].surplusTime,
          surplusEnergy: properData[i].surplusEnergy,
          taskId: properData[i].taskId,
          clusterId: properData[i].clusterId,
          communicationsNumber: properData[i].communicationsNumber,
          communicationSituation: properData[i].communicationSituation,
          damageSituation: properData[i].damageSituation,
          electronicInterferenceStatus: properData[i].electronicInterferenceStatus,
        });

        // 设置层级样式
        let style = new Style({
          image: new Circle({//image属性设置点的样式
            radius: 10,//点的半径
            fill: new Fill({
              color: "#ff2d51"//点的填充颜色
            }),
            stroke: new Stroke({//点的描边样式
              width: 2,//描边的宽度
              color: "#fff"//描边的颜色
            })
          }),
          text: new Text({
            text: (i + 1).toString(),
            fill: new Fill({
              color: "#fff",
              font: "15px Arial"
            }),
          })
        });
        point.setStyle(style);
        point.set("nodePoint", true);
        _that.featureList.push(point);

      }

      // _that.layervector.setStyle(style);
      _that.layervector.getSource().addFeatures(_that.featureList);
    },

    /**
     * 添加弹出框
     */
    addPop() {
      const _that = this;

      // var source = new VectorSource({
      //   wrapX: false
      // });

      // for (let i = 0; i < this.properData.length; i++) {
      //   var feature = new Feature({
      //     geometry: new Point([this.properData[i].longitude, this.properData[i].latitude]),
      //     name: this.properData[i].name,
      //     SN: this.properData[i].SN,
      //     speed: this.properData[i].speed,
      //     longitude: this.properData[i].longitude,
      //     latitude: this.properData[i].latitude,
      //   });
      //   this.featureList.push(feature);
      //   source.addFeature(feature)
      // }
      // //创建图层
      // var vectorLayer = new VectorLayer({
      //   source: source,
      // });
      // _that.map.addLayer(vectorLayer);

      // 获取pop的dom对象
      var container = document.getElementById('popup');// 弹出框的 HTML 元素
      var content = document.getElementById('popup-content');
      // var content = this.$refs.content;
      var closer = document.getElementById('popup-closer');
      // 创建弹出框
      var popup = new Overlay({
        element: container,
        autoPan: true,
        positioning: 'bottom-center',
        stopEvent: false,
        offset: [0, -15],
        autoPanAnimation: {
          duration: 250
        }
      });
      _that.map.addOverlay(popup);

      // 关闭popup
      closer.onclick = function () {
        popup.setPosition(undefined);
        closer.blur();
        return false;
      };

      // 监听鼠标点击事件，点击feature后，弹出框出现
      _that.map.on('click', function (evt) {
        var coordinate = evt.coordinate;//获取点击的坐标
        console.log("该点的坐标" + coordinate);
        var feature = _that.map.forEachFeatureAtPixel(evt.pixel, function (feature) {

          return feature;
        })
        if (feature) {
          if (feature.get('nodePoint') == true) {
            //清空html
            content.innerHTML = "";
            _that.currentFeature = feature;

            var parent = document.createElement('div');//显示name和img的父元素
            content.appendChild(parent);

            //无人机名称
            var name = document.createElement('h3');
            name.innerHTML = feature.get('vehicleName');
            parent.appendChild(name);

            //无人机详细信息图片
            var img = document.createElement('img');
            img.setAttribute('src', 'images/detail.png');
            img.setAttribute('width', '24');
            img.setAttribute('height', '24');
            img.addEventListener('click', () => {
              _that.getDetail();
            });
            parent.appendChild(img);

            parent.style.display = 'flex'; // Flexbox 布局
            parent.style.alignItems = 'center'; // 垂直居中子元素（如果 content 的高度大于子元素）  
            parent.style.justifyContent = 'center'; // 水平居中子元素  
            parent.style.gap = '20px'; // 设置子元素之间的间距

            //无人机编号
            var SN = document.createElement('p');
            SN.innerHTML = '编号：' + feature.get('vehicleId');
            content.appendChild(SN);

            //无人机速度
            var speed = document.createElement('p');
            speed.innerHTML = '速度：' + feature.get('speed') + "km/h";
            content.appendChild(speed);

            // 经度
            var longitude = document.createElement('p');
            longitude.innerHTML = '经度：' + feature.get('longitude');
            content.appendChild(longitude);

            // 纬度
            var latitude = document.createElement('p');
            latitude.innerHTML = '纬度：' + feature.get('latitude');
            content.appendChild(latitude);

            // 弹出框出现
            popup.setPosition(coordinate);// 将弹窗位置设置为鼠标点击处

          }
        }
      })
      // 监听鼠标移动事件，鼠标移动到feature区域，变为手形
      _that.map.on('pointermove', function (evt) {
        // var pixel = _that.map.getEventPixel(evt.originalEvent);
        // var hit = _that.map.hasFeatureAtPixel(pixel);
        // _that.map.getTargetElement().style.cursor = hit ? 'pointer' : '';
        let feature = _that.map.forEachFeatureAtPixel(evt.pixel, function (feature) {
          return feature;
        })
        if (feature) {
          if (feature.get('nodePoint') == true) {
            _that.map.getTargetElement().style.cursor = 'pointer';
          }
        } else {
          _that.map.getTargetElement().style.cursor = '';
        }
      })


    },

    /*
    信息明细
    */
    getDetail() {
      console.log("信息明细");
      this.drawerVisible = true;
    },

    /**
     * 集群层的显示与隐藏
     */
    toggleClusterLayer() {
      var _that = this;
      //切换状态
      this.clusterLayerVisible = !this.clusterLayerVisible;
      //如果显示，则添加图层
      if (this.clusterLayerVisible) {
        this.fetchClusterData2();
      } else {
        //如果隐藏，则移除图层
        var layersArray = _that.map.getLayers().getArray();//获取所有图层的数组
        var layersToRemove = layersArray.filter(function (layer) {//过滤出需要移除的集群图层
          return layer.get('name') === "集群层";
        });
        layersToRemove.forEach(function (layer) {
          _that.map.removeLayer(layer);
        });
      }
    },


    /*
    绘制椭圆
    */
    drawEllipse(cluster) {
      var nodeslist = cluster.nodesList;
      //计算多个point的中心
      var pointfeatures = this.layervector.getSource().getFeatures();
      //遍历所有的点
      let sumX = 0, sumY = 0, count = 0;
      pointfeatures.forEach(function (feature) {
        // 确保是点
        if (feature.getGeometry().getType() === 'Point') {
          // 获取feature的属性
          var featureProperties = feature.getProperties();
          //如果featureProperties.vehicleId在nodeslist中，则计算中心点坐标
          if (nodeslist.includes(featureProperties.vehicleId)) {
            console.log("featureProperties.vehicleId:" + featureProperties.vehicleId);
            sumX += featureProperties.longitude;
            sumY += featureProperties.latitude;
            count++;
          }
        }
      });
      console.log("sumX:" + sumX);
      console.log("sumY:" + sumY);
      console.log("count:" + count);
      // 椭圆参数  
      var centerX = sumX / count;
      var centerY = sumY / count;
      const scaleFactor = 0.05; // 缩放因子，根据点数变化
      var radiusMinor = 0.2 + (count - 1) * scaleFactor; // 短半轴（单位：米）控制椭圆大小 
      console.log("radiusMinor:" + radiusMinor);
      var ratio = 1.5; // 长半轴与短半轴的比例  
      var rotation = 0; // 旋转角度（这里不旋转，所以为0）

      // 椭圆上点的数量（越多越精确）  
      var numPoints = 32;

      // 计算椭圆上点的坐标  
      var coordinates = [];
      for (let i = 0; i < numPoints; i++) {
        var theta = (i / (numPoints - 1)) * 2 * Math.PI;
        var x = centerX + radiusMinor * ratio * Math.cos(theta);
        var y = centerY + radiusMinor * Math.sin(theta);
        coordinates.push([x, y]);
      }
      console.log(coordinates);
      // 闭合多边形  
      coordinates.push(coordinates[0]);

      // 创建椭圆的多边形几何对象  
      var ellipsePolygon = new Polygon([coordinates]);

      // 创建要素并设置几何对象  
      this.ellipseFeature = new Feature({
        geometry: ellipsePolygon,
      });

      // 创建向量源并添加要素  
      var vectorSource = new VectorSource({
        features: [this.ellipseFeature],
      });

      // 创建向量图层并设置样式  
      var vectorLayer = new VectorLayer({
        source: vectorSource,
        name: '集群层',
        style: new Style({
          stroke: new Stroke({
            color: 'red',
            width: 2,
          }),
          fill: new Fill({
            color: 'rgba(0, 0, 255, 0.1)',
          }),
        }),
      });

      // 将向量图层添加到地图中  
      this.map.addLayer(vectorLayer);
    },

    /**
     * 根据最大最小经纬度边界值确定中心点，并绘制椭圆
     */
    drawEllipse3(cluster) {
      var nodeslist = cluster.nodesList;
      var pointfeatures = this.layervector.getSource().getFeatures();

      // 初始化边界值  
      let minX = Infinity, maxX = -Infinity, minY = Infinity, maxY = -Infinity;

      // 遍历所有的点并找到边界值 
      pointfeatures.forEach(function (feature) {
        if (feature.getGeometry().getType() === 'Point') {
          // 获取feature的属性
          var featureProperties = feature.getProperties();
          //如果featureProperties.vehicleId在nodeslist中,则计算最大最小经纬度边界值  
          if (nodeslist.includes(featureProperties.vehicleId)) {
            // console.log("featureProperties.vehicleId:" + featureProperties.vehicleId);
            minX = Math.min(minX, featureProperties.longitude); //  纬度 
            maxX = Math.max(maxX, featureProperties.longitude);
            minY = Math.min(minY, featureProperties.latitude); //   经度
            maxY = Math.max(maxY, featureProperties.latitude);
          }
        }
      });
      console.log("minX:" + minX + ",maxX:" + maxX);
      console.log("minY:" + minY + ",maxY:" + maxY);

      // 计算椭圆的中心  
      var centerX = (minX + maxX) / 2;
      var centerY = (minY + maxY) / 2;

      // 根据边界值计算椭圆的长轴和短轴  
      var radiusX = (maxX - minX) / 1.5; // 长半轴（经度方向）  
      var radiusY = (maxY - minY) / 1.5; // 短半轴（纬度方向）  
      var scaleFactor = 0.01; // 缩放因子  
      var ratio = 1.5; // 长半轴与短半轴的比例 
      
      // 椭圆上点的数量  
      var numPoints = 64;

      // 计算椭圆上点的坐标  
      var coordinates = [];
      for (let i = 0; i < numPoints; i++) {
        var theta = (i / (numPoints - 1)) * 2 * Math.PI;
        var x = centerX + radiusX * Math.cos(theta);
        var y = centerY + radiusY * Math.sin(theta);
        coordinates.push([x, y]);
      }

      // 闭合多边形  
      coordinates.push(coordinates[0]);

      // 创建椭圆的多边形几何对象  
      var ellipsePolygon = new Polygon([coordinates]);
      // 创建要素并设置几何对象  
      this.ellipseFeature = new Feature({
        geometry: ellipsePolygon,
      });

      // 创建向量源并添加要素  
      var vectorSource = new VectorSource({
        features: [this.ellipseFeature],
      });

      // 创建向量图层并设置样式  
      var vectorLayer = new VectorLayer({
        source: vectorSource,
        name: '集群层',
        style: new Style({
          stroke: new Stroke({
            // color: 'red',
            color: this.getRandomColor(), // 生成随机颜色
            width: 2,
          }),
          fill: new Fill({
            color: 'rgba(0, 0, 255, 0.1)',
          }),
        }),
      });

      // 将向量图层添加到地图中  
      this.map.addLayer(vectorLayer);
      // 调整图层的索引值，使得集群层显示在点图层下面
      // this.map.getLayers().setAt(1, vectorLayer);
    },

    drawEllipse4() {
      if(this.ellipseData.length<=0){
          return ;
      }
      //
      this.clusterData.forEach((item, index) => {
         var ellipse = this.ellipseData[index];
         var centerX = ellipse.centerX;
         var centerY = ellipse.centerY;
         var radiusX = ellipse.radiusLong; // 长半轴（经度方向）  
         var radiusY = ellipse.radiusShort; // 短半轴（纬度方向）  
         var rotation = ellipse.rotation;

         var sinR = Math.sin(rotation);
         var cosR = Math.cos(rotation);
         // 椭圆上点的数量  
         var numPoints = 64;

          // 计算椭圆上点的坐标  
          var coordinates = [];
          for (let i = 0; i < numPoints; i++) {
            var theta = (i / (numPoints - 1)) * 2 * Math.PI;
            //在零点时，旋转前的x、y
            var x =  radiusX * Math.cos(theta);
            var y =  radiusY * Math.sin(theta);

            // 旋转+平移后的x y
            var nx = centerX + x * cosR - y * sinR;
            var ny = centerY + x * sinR + y * cosR;

            coordinates.push([nx, ny]);
          }

        // 闭合多边形  
        coordinates.push(coordinates[0]);

        // 创建椭圆的多边形几何对象  
        var ellipsePolygon = new Polygon([coordinates]);
        // 创建要素并设置几何对象  
        this.ellipseFeature = new Feature({
          geometry: ellipsePolygon,
        });

        // 创建向量源并添加要素  
        var vectorSource = new VectorSource({
          features: [this.ellipseFeature],
        });

        // 创建向量图层并设置样式  
        var vectorLayer = new VectorLayer({
          source: vectorSource,
          name: '集群层',
          style: new Style({
            stroke: new Stroke({
              // color: 'red',
              color: this.getRandomColor(), // 生成随机颜色
              width: 2,
            }),
            fill: new Fill({
              color: 'rgba(0, 0, 255, 0.1)',
            }),
          }),
        });

        // 将向量图层添加到地图中  
        this.map.addLayer(vectorLayer);

      });
        
     
    },

    //随机生成颜色
    getRandomColor() {
      const letters = '0123456789ABCDEF';
      let color = '#';
      for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
      }
      return color;

    },


    /*
    *异步获取后端无人设备列表数据
    根据列表数据批量添加节点
    */
    async fetchVehicleData() {
      try {

        this.vehicleData = (await getVehicleData()).data;
        // this.clusterData = (await getClusterData()).data;
        console.log("后端拿到的节点数据：", this.vehicleData);
        // console.log("后端拿到的集群数据：", this.clusterData);
        this.handleAddBatchFeature();
        // this.clusterData.forEach((item, index) => {
        //   console.log("item:", item);
        //   // this.drawEllipse(item);
        //   this.drawEllipse3(item);
        // });

      } catch (error) {
        console.error('Error fetching data:', error);
      }
    },

    async fetchClusterData() {
      try {
        this.clusterData = (await getClusterData()).data;
        console.log("后端拿到的集群数据：", this.clusterData);
        this.clusterData.forEach((item, index) => {
          console.log("item:", item);
          // this.drawEllipse(item);
          this.drawEllipse3(item);

        });
        //所有的图层信息
        this.map.getLayers().forEach(item => {
          // console.log("itemlayer:", item);
          if (item.get('name') == '集群层') {
            console.log("集群层itemlayer:", item);
            console.log("集群层索引：", this.map.getLayers().getArray().indexOf(item));
          }
        });
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    },

    async fetchClusterData2() {
      try {
        this.clusterData = (await getClusterData()).data;
        console.log("后端拿到的集群数据：", this.clusterData);
        this.drawEllipse4();
        //所有的图层信息
        this.map.getLayers().forEach(item => {
          // console.log("itemlayer:", item);
          if (item.get('name') == '集群层') {
            console.log("集群层itemlayer:", item);
            console.log("集群层索引：", this.map.getLayers().getArray().indexOf(item));
          }
        });
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    },

    /**
     * 异步获取后端随机数据
     * 更新节点的经纬度
     */
    async intervalGetData() {

      try {
        this.randomData = (await getRandomData()).data;
        this.randomData.forEach((item, index) => {
          this.featureList[index].getGeometry().setCoordinates([item['longitude'], item['latitude']])
        });

        // for(let i=0;i<this.randomData.length;i++){
        //     this.featureList[i].getGeometry().setCoordinates([this.randomData[i][]])

        // }
        console.log("后端的随机数据：", this.randomData);
      } catch (error) {
        console.error('Error fetching data:', error);
      }


    },


  },
  mounted() {
    this.initMap();

    this.fetchVehicleData();

    this.addPop();

    this.initWebSocket();

    // this.intervalGetData();//获取随机经纬度的数据
    // const intervalId = setInterval(() => {
    //     // 从前端通过axios获取信息
    //     this.intervalGetData();  
    //   }, 3000);//轮询请求，每隔5秒获取一次数据

  },
  unmounted(){  //组件销毁时，清除定时器
    // clearInterval(intervalId);

    // 关闭 WebSocket 连接
    if (this.ws) {
      this.ws.close();
    }

  }

}

</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#map {
  width: 100%;
  height: 750px;
  margin: 0 auto;
  border: 1px solid #42B983;
}

.ol-popup {
  position: absolute;
  background-color: white;
  -webkit-filter: drop-shadow(0 1px 4px rgba(0, 0, 0, 0.2));
  filter: drop-shadow(0 1px 4px #FFC125);
  padding: 8px;
  border-radius: 10px;
  border: 1px solid #cccccc;
  bottom: 15px;
  left: -50px;
  min-width: 210px;
}

.ol-popup:after,
.ol-popup:before {
  top: 108%;
  border: solid transparent;
  content: "";
  height: 0;
  width: 0;
  position: absolute;
  pointer-events: none;
}


.ol-popup:after {
  border-top-color: white;
  border-width: 10px;
  left: 48px;
  margin-left: -10px;
}

.ol-popup:before {
  border-top-color: #cccccc;
  border-width: 11px;
  left: 48px;
  margin-left: -11px;
}

#popup-closer {
  text-decoration: none;
  position: absolute;
  top: 2px;
  right: 8px;
  color: red;
}

#popup-closer:after {
  content: "✖";
}

.row-container {
  display: flex;
  height: 40px;

  .row-container-item {
    font-size: 25px;
    font-weight: 600;
    /* color: #545252ed; */

  }
}

.row-1 {
  margin: 0;
  display: flex;
  align-items: center;
  justify-content: right;
  height: 7%;
  padding: 0;

  /* .tip-icon {
    // position: absolute;
    // top: 5px;
    // left: 35px; 
    margin-left: 5px;
    flex: 1;
    padding: 10px 0;
    background-color: rgb(152, 177, 252);
    border-start-end-radius: 20px;
    border-bottom-left-radius: 20px; 
    border-radius: 10px;
  } */

  .tip-blank {
    flex: 1;
    background-color: seagreen;
  }

  .tip-name {
    display: flex;
    justify-content: space-around;
    flex: 1.5;
    margin-right: 20px;
    padding: 3px 0;
    background-color: rgb(146 146 247 / 39%);
    border-radius: 10px;

    /**设备名称 */
    .tip-name-item {
      flex-basis: 80px;
      padding: 3px 0;
      text-align: center;
      /* font-weight: 550; */
    }

    /**分隔符 */
    .tip-split-item {
      display: inline-block;
      font-size: 22px;
      color: #0000ffb0;
    }

    /**数据源名称 */
    .tip-name-item-property {
      flex: 1;
      padding: 3px 0;
      text-align: left;
      padding-left: 5px;
    }
  }
}

.row-2 {
  /* height: 31%; */
  background-color: rgba(175, 224, 247, 0.29);
  border-top: 1px solid skyblue;
  border-bottom: 1px solid skyblue;

  .basic-item {
    padding-top: 5px;
    text-align: left;

    .basic-item-property {
      /* padding-right: 3px; */
      color: #060606;
      font-weight: 550;
    }
  }

  .basic-item:last-child {
    padding-bottom: 5px;
  }
}
</style>
