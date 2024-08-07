<template>
  <div class="container">
    <!-- <div class="top" style="height: 30px;margin: 0px auto; padding-left: 30px">
      <el-button type="primary" plain @click="toggleClusterLayer" >集群层</el-button>
    </div> -->
    <div id="map">
      <div class="map-container">
        <!-- 项目名称 -->
        <h3 class="sysName">三层架构展示平台</h3>
        <el-button type="primary" plain @click="toggleClusterLayer" class="funcBtn">集群层</el-button>
        <el-button type="primary" plain @click="toggleTaskLayer" class="funcBtn-1">任务层</el-button>
        <el-button type="primary" plain @click="downloadWord" class="funcBtn-2">明细导出</el-button>
      </div>
    </div>
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

    <!-- 集群图层信息 -->
    <el-drawer v-model="clusterDrawerVisible" title="I am the title" :with-header="false" size="35%">
      <div class="row-container">
        <span class="row-container-item">集群属性信息</span>
      </div>
      <el-row class="row-1" :gutter="20">
        <div class="tip-name">
          <span class="tip-name-item">集群名称</span>
          <span class="tip-split-item">|</span>
          <span class="tip-name-item-property">{{ currentCluster.get('clusterName') }}</span>
        </div>
        <span class="tip-blank"></span>
      </el-row>
      <!-- 详细信息 -->
      <el-row class="row-2" :gutter="20">
        <el-col>
          <!-- //左边部分 -->
          <div class="basic-item">
            <span class="basic-item-property">集群编号：</span><span>{{ currentCluster.get('clusterId') }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">当前时间：</span><span>{{ currentCluster.get('createTime') }}s</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">集群状态：</span><span>{{ currentCluster.get('clusterStatus') }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">组成单元：</span><span>{{ currentCluster.get('nodesList') }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">失联节点数：</span><span>{{ currentCluster.get('lostNodesNumber') }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">损毁节点数：</span><span>{{ currentCluster.get('damagedNodesNumber') }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">外部通信情况：</span><span>{{ currentCluster.get('communicationStationStatus')
              }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">干扰情况：</span><span>{{ currentCluster.get('electronicInterferenceStatus')
              }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">任务编号：</span><span>{{ currentCluster.get('taskId') }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">任务完成情况：</span><span>{{ currentCluster.get('taskFinishFlag') }}</span>
          </div>

        </el-col>
      </el-row>

    </el-drawer>

    <!-- 任务图层信息 -->
    <el-drawer v-model="taskDrawerVisible" title="I am the title" :with-header="false" size="35%">
      <div class="row-container">
        <span class="row-container-item">任务属性信息</span>
      </div>
      <el-row class="row-1" :gutter="20">
        <div class="tip-name">
          <span class="tip-name-item">任务类型</span>
          <span class="tip-split-item">|</span>
          <span class="tip-name-item-property">{{ currentTask.get('taskType') }}</span>
        </div>
        <span class="tip-blank"></span>
      </el-row>
      <!-- 详细信息 -->
      <el-row class="row-2" :gutter="20">
        <el-col>
          <!-- //左边部分 -->
          <div class="basic-item">
            <span class="basic-item-property">任务编号：</span><span>{{ currentTask.get('taskId') }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">当前时间：</span><span>{{ currentTask.get('createTime') }}s</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">任务描述：</span><span>{{ currentTask.get('taskDescription') }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">设备单元：</span><span>{{ currentTask.get('nodesList') }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">单元类型：</span><span>{{ currentTask.get('nodesType') }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">集群数量：</span><span>{{ currentTask.get('clusterNumber') }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">集群标识：</span><span>{{ currentTask.get('clustersList') }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">目标经度：</span><span>{{ currentTask.get('taskLocationLongitude')
              }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">目标纬度：</span><span>{{ currentTask.get('taskLocationLatitude')
              }}</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">目标高度：</span><span>{{ currentTask.get('taskLocationAltitude') }}m</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">任务半径：</span><span>{{ currentTask.get('taskAreaRadius') }}m</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">最大完成时间：</span><span>{{ currentTask.get('completionTime') }}s</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">任务开始时间：</span><span>{{ currentTask.get('startTime') }}s</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">实际开始时间：</span><span>{{ currentTask.get('actualStartTime') }}s</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">实际结束时间：</span><span>{{ currentTask.get('actualCompletionTime') }}s</span>
          </div>
          <div class="basic-item">
            <span class="basic-item-property">任务完成情况：</span><span>{{ currentTask.get('taskFinishFlag') }}</span>
          </div>
        </el-col>
      </el-row>

    </el-drawer>
  </div>
</template>

<script>
import 'ol/ol.css'
import Map from 'ol/Map'
import View from 'ol/View'
import TileLayer from 'ol/layer/Tile'
import VectorLayer from 'ol/layer/Vector'
import XYZ from 'ol/source/XYZ'
import { Vector as VectorSource } from "ol/source";
import Feature from "ol/Feature"
import { Point, MultiLineString, Polygon } from 'ol/geom'
import { Style, Fill, Stroke } from 'ol/style'
import { Circle, Text } from 'ol/style';
import Icon from 'ol/style/Icon';
import ImageLayer from 'ol/layer/Image';
import ImageCanvas from 'ol/source/ImageCanvas';

import { ZoomSlider, FullScreen } from 'ol/control';
import Overlay from "ol/Overlay";
import { Collection } from 'ol'
import { fromLonLat } from 'ol/proj';
import { getRandomData } from '@/api/node/node';
import { getVehicleData } from '@/api/node/node';
import { getClusterData } from '@/api/cluster/cluster';
import { getTaskData } from '@/api/task/task';
import { exportDataWord } from '@/api/word/word';



export default {
  name: 'FirstMap',
  data() {
    return {
      randomData: [],
      featureList: [],//point的feature列表
      lineFeatureList: [],//line的feature列表
      lineFeatureListForDamage: [],//失联line的feature列表
      canvasLayers: [],//canvas的图层列表
      cxtList: [], //canvas的context的列表，用于快速重绘矩阵
      canvasList: [],
      currentFeature: [],//当前选中节点信息
      currentCluster: [],//当前集群信息
      currentTask: [],//当前任务信息
      map: null,
      overlay: null,
      drawerVisible: false,//节点信息抽屉显示
      clusterDrawerVisible: false,//集群信息抽屉显示
      clusterLayerVisible: false,
      taskDrawerVisible: false,//任务信息抽屉显示
      taskLayerVisible: false,
      vehicleData: [], //getVehicleData()方法获取vehicleData数据
      clusterData: [], //getClusterData()方法获取clusterData数据
      ellipseData: [], //获取的ellipseData数据
      taskData: [], //getTaskData()方法获取taskData数据
      taskEllipseData: [], //获取的taskEllipseData数据

      cachedColors: null, // 缓存颜色数组变量

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
          center: [119.29, 24.35],
          zoom: 8.5,
          projection: "EPSG:4326"
        })
      })

    },

    initWebSocket() {
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
        this.ellipseData = this.message.clusterEllipseList;
        this.taskData = this.message.tasklist;
        this.taskEllipseData = this.message.taskEllipseList;

        console.log('Message from server:', this.message.nodeList);
        console.log('Message from server_this.message.ellipseList:', this.message.ellipseList);
        console.log('Message from server_this.message.taskEllipseList:', this.message.taskEllipseList);

        // 更新点位置和图案
        // this.vehicleData.forEach((item, index) => {
        //   this.featureList[index].getGeometry().setCoordinates([item['longitude'], item['latitude']]);
        //   //if(this.featureList[index].)

        //   // this.featureList[index].getGeometry().setProperties("communicationSituation", item['communicationSituation']);
        // });

        // 更新点位置和图案
        this.vehicleData.forEach((item, index) => {

          this.featureList[index].getGeometry().setCoordinates([item['longitude'], item['latitude']]);
          this.featureList[index].set("communicationSituation", item['communicationSituation']);
          this.featureList[index].set("vehicleName", item['vehicleName']);
          this.featureList[index].set("vehicleId", item['vehicleId']);
          this.featureList[index].set("createTime", item['createTime']);
          this.featureList[index].set("speed", item['speed']);
          this.featureList[index].set("longitude", item['longitude']);
          this.featureList[index].set("latitude", item['latitude']);
          this.featureList[index].set("height", item['height']);
          this.featureList[index].set("pitchAngle", item['pitchAngle']);
          this.featureList[index].set("rollAngle", item['rollAngle']);
          this.featureList[index].set("surplusVoyage", item['surplusVoyage']);
          this.featureList[index].set("surplusTime", item['surplusTime']);
          this.featureList[index].set("surplusEnergy", item['surplusEnergy']);
          this.featureList[index].set("taskId", item['taskId']);
          this.featureList[index].set("clusterId", item['clusterId']);
          this.featureList[index].set("communicationsNumber", item['communicationsNumber']);
          this.featureList[index].set("communicationNodes", item['communicationNodes']);
          this.featureList[index].set("electronicInterferenceStatus", item['electronicInterferenceStatus']);
          if(this.featureList[index].get("directionAngle") != item['directionAngle'] || this.featureList[index].get("damageSituation") != item['damageSituation']){
            //console.log(this.featureList[index].get("damageSituation"))
            //console.log(item['damageSituation'])
            this.featureList[index].set("directionAngle", item['directionAngle']);
            this.featureList[index].set("damageSituation", item['damageSituation']);
            this.pointSetIcon(index);
          }else{
            this.featureList[index].set("directionAngle", item['directionAngle']);
            this.featureList[index].set("damageSituation", item['damageSituation']);
          }

        });



        //更新图层---如果集群层是在展示的话，则更新，否则不重新绘制
        this.updateClusterLayer();

        //更新图层---如果任务层是在展示的话，则更新，否则不重新绘制
        this.updateTaskLayer();

        //更新线条图层的位置
        this.updateLineLayer();

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
    initClusetCanvas() {
      canvasS = document.createElement('canvas');
      const ctx = canvas.getContext('2d');
      canvas.width = 256;
      canvas.height = 256;
    },

    updateCanvas() {
      let i = 0;
      var resolution = this.map.getView().getResolution();
      console.log("resolution:" + resolution);
      for (; i < this.ellipseData.length; i++) {
        console.log("updateCanvasING1111111111111111:");
        if (i >= this.cxtList.length) {
          console.log("updateCanvasING222222222222222222:");
          var canvas = document.createElement('canvas');
          this.canvasList.push(canvas);
          this.cxtList.push(canvas.getContext('2d'));
          var imageSource = new ImageCanvas({
            canvasFunction: function (extent, resolution, pixelRatio, size, projection) {
              // 由于我们只是返回预先创建的Canvas，这里不需要重新绘制  
              return canvas;
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
        var ellipse = this.ellipseData[i];

        //坐标转换
        var centerCoord = this.map.getPixelFromCoordinate(fromLonLat([ellipse.centerX, ellipse.centerY]));
        var radiusX = ellipse.radiusLong / resolution;
        var radiusY = ellipse.radiusShort / resolution;

        //开始绘制
        var canvas = this.canvasList[i];
        canvas.width = 256;
        canvas.height = 256;
        var ctx = this.cxtList[i];
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
      var properData = this.vehicleData;
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
          communicationNodes: properData[i].communicationNodes,
          communicationSituation: properData[i].communicationSituation,
          damageSituation: properData[i].damageSituation,
          electronicInterferenceStatus: properData[i].electronicInterferenceStatus,
        });

        //判断损坏程度 --把静态放前面，防止NULL
        var damageStr ="";
        if("设备完好".includes(properData[i].damageSituation)){
          damageStr ="_0";
        }else if("轻度损伤".includes(properData[i].damageSituation)){
          damageStr ="_1";
        }else if("重度损伤".includes(properData[i].damageSituation)){
          damageStr ="_2";
        }else if("设备损毁".includes(properData[i].damageSituation)){
          damageStr ="_3";
        }else{
          damageStr =damageStr;
        }


        //ICON和颜色是否可以在初始化时就加载进来，后面就不用重复加载了
        var IconUrl = "/nodeImg/node1";
        if (properData[i].vehicleName.includes("机")) {
          IconUrl = '/nodeImg/plane1';
        } else if(properData[i].vehicleName.includes("车")){
          IconUrl = '/nodeImg/car1';
        }

        IconUrl = IconUrl+damageStr+".png";

        let iconStyle = new Icon({
          anchor: [0.5, 0.5], // 设置图标锚点位置（默认是图标的中心点）  
          anchorXUnits: 'fraction',
          anchorYUnits: 'fraction',
          src: IconUrl, // 设置图标URL  
          scale: 0.25,
          rotation: (properData[i].directionAngle * Math.PI)/180
          // 还可以设置其他属性，如：scale（缩放比例）、rotation（旋转角度）等  
        });


        // 设置层级样式
        let style = new Style({
          image: iconStyle,
          text: new Text({
            text: (i + 1).toString(),
            font: "bold 15px Arial",
            fill: new Fill({
              color: "#000",
              // font: "20px Arial"
            }),
          })
        });
        point.setStyle(style);

        //nodePoint用于后面判断鼠标移动变为手的形状
        point.set("nodePoint", true);
        _that.featureList.push(point);

      }

      // _that.layervector.setStyle(style);
      _that.layervector.getSource().addFeatures(_that.featureList);
    },

    //设置点的ICON
    pointSetIcon(index){
       let point = this.featureList[index];

        //判断损坏程度 --把静态放前面，防止NULL
        var damageStr ="";
        if("设备完好".includes(point.get("damageSituation"))){
          damageStr ="_0";
        }else if("轻度损伤".includes(point.get("damageSituation"))){
          damageStr ="_1";
        }else if("重度损伤".includes(point.get("damageSituation"))){
          damageStr ="_2";
        }else if("设备损毁".includes(point.get("damageSituation"))){
          damageStr ="_3";
        }else{
          damageStr =damageStr;
        }

        //ICON和颜色是否可以在初始化时就加载进来，后面就不用重复加载了
        var IconUrl = "/nodeImg/node1";
        if (point.get("vehicleName").includes("机")) {
          IconUrl = '/nodeImg/plane1';
        } else if(point.get("vehicleName").includes("车")){
          IconUrl = '/nodeImg/car1';
        }

        IconUrl = IconUrl+damageStr+".png";

        let iconStyle = new Icon({
          anchor: [0.5, 0.5], // 设置图标锚点位置（默认是图标的中心点）  
          anchorXUnits: 'fraction',
          anchorYUnits: 'fraction',
          src: IconUrl, // 设置图标URL  
          scale: 0.25,
          rotation: (point.get("directionAngle") * Math.PI)/180
          // 还可以设置其他属性，如：scale（缩放比例）、rotation（旋转角度）等  
        });

        let style = point.getStyle();
        // 检查style是否为数组，因为Feature可以有多个样式  
        if (Array.isArray(style)) {  
          // 如果style是数组，通常我们只修改第一个样式（这取决于你的具体需求）  
          style = style[0]; // 或者你可以遍历数组来修改所有样式  
        }  
        style.setImage(iconStyle);

    },
    /**
     * 获取各集群线条的position数据
     * @returns linePositions[0]正常通信，linePositions[1]失联通信
     */
    getLinePositions() {
      var linePositions = [];
      var clusterPositions = [];
      var clusterPositionsForDamage = [];
      //针对每个集群数据
      this.clusterData.forEach((item, index) => {
        var positions = [];
        var position = [];
        var nextposition = [];
        var positionsForDamage = [];

        //获取集群节点列表
        var clusterNodeList = item.nodesList;
        // 遍历clusterNodeList，根据每个节点名称找到对应的point，获取经纬度
        clusterNodeList.forEach((itemNode, index) => {
          //针对每个节点，遍历通信节点列表，在Point中找到各节点位置
          var pointfeatures = this.layervector.getSource().getFeatures();
          var srcpoint = pointfeatures.find(feature => feature.getProperties()["vehicleId"] === itemNode);
          position = srcpoint.getGeometry().getCoordinates();
          var srcConmmunicationNodesList = srcpoint.getProperties()["communicationNodes"];
          var srcCommunicationsNumber = srcpoint.getProperties()["communicationsNumber"];
          if (srcCommunicationsNumber > 0) {
            //找各个通讯节点位置
            srcConmmunicationNodesList.forEach(itemCommunicateNode => {
              var destpoint = pointfeatures.find(feature => feature.getProperties()["vehicleId"] === itemCommunicateNode);
              nextposition = destpoint.getGeometry().getCoordinates();
              //判断通信状态
              //有一方失联
              if ((srcpoint.getProperties()["communicationSituation"] === "通信断开") || (destpoint.getProperties()["communicationSituation"] === "通信断开")) {
                positionsForDamage.push([position, nextposition]);
              } else {//都正常通信
                positions.push([position, nextposition]);
              }
            });
          }
        });
        clusterPositions.push(positions);
        clusterPositionsForDamage.push(positionsForDamage);
      });
      linePositions.push(clusterPositions, clusterPositionsForDamage);
      return linePositions;
    },


    //先获取集群线条位置最新数据，再绘制
    drawLine() {
      var linePositions = this.getLinePositions();
      var clusterPositions = linePositions[0];
      var clusterPositionsForDamage = linePositions[1];
      //正常通信线条绘制
      clusterPositions.forEach((itemPositons, index) => {
        var line = new Feature({
          geometry: new MultiLineString(itemPositons),
        });
        var vectorSource = new VectorSource({
          features: [line],
        });
        this.lineFeatureList.push(line);
        this.linevector = new VectorLayer({
          source: vectorSource,
          name: "line",
          style: new Style({
            stroke: new Stroke({
              color: this.getRandomColor()[index],
              width: 1.5
            })
          }),
        });
        this.map.addLayer(this.linevector);
        // this.map.getLayers().setAt(1, this.linevector);
      });
      //失联通信线条绘制
      if (clusterPositionsForDamage.length > 0) {
        clusterPositionsForDamage.forEach((itemPositons, index) => {
          var line = new Feature({
            geometry: new MultiLineString(itemPositons),
          });
          var vectorSource = new VectorSource({
            features: [line],
          });
          this.lineFeatureListForDamage.push(line);
          this.linevector = new VectorLayer({
            source: vectorSource,
            name: "line",
            style: new Style({
              stroke: new Stroke({
                lineDash: [6, 12],
                color: 'red',
                width: 1.5
              })
            }),
          });
          this.map.addLayer(this.linevector);
        });
      }
    },


    /**
     * 添加弹出框
     */
    addPop() {
      const _that = this;

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
            img.setAttribute('title', '详细信息');
            img.addEventListener('mouseenter', () => {
              document.body.style.cursor = 'pointer';
            });
            img.addEventListener('mouseleave', () => {
              document.body.style.cursor = 'auto';
            });
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
        this.drawClusterData();
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

    /**
     * 更新线条图层位置
     */
    updateLineLayer() {
      var linePositions = this.getLinePositions();
      var clusterPositions = linePositions[0];
      var clusterPosititionsForDamage = linePositions[1];

      clusterPositions.forEach((itemPositons, index) => {
        this.lineFeatureList[index].getGeometry().setCoordinates(itemPositons);
        // this.lineFeatureList[index].setStyle(new Style({
        //   stroke: new Stroke({
        //     color: this.getRandomColor()[index],
        //     width: 1.5
        //   })
        // }));
      });
      clusterPosititionsForDamage.forEach((itemPositons, index) => {
        this.lineFeatureListForDamage[index].getGeometry().setCoordinates(itemPositons);
        // this.lineFeatureListForDamage[index].setStyle(new Style({
        //   stroke: new Stroke({
        //     lineDash: [6, 12],
        //     color: 'red',
        //     width: 1.5
        //   })
        // }));
      });

    },


    /**
    * 集群层的更新
    */
    updateClusterLayer() {

      var _that = this;

      if (this.clusterLayerVisible) {
        //先移除图层
        var layersArray = _that.map.getLayers().getArray();//获取所有图层的数组
        var layersToRemove = layersArray.filter(function (layer) {//过滤出需要移除的集群图层
          return layer.get('name') === "集群层";
        });
        layersToRemove.forEach(function (layer) {
          _that.map.removeLayer(layer);
        });

        //再绘制图层
        this.drawClusterData();

      }
    },


    /**
     * 任务层的显示与隐藏
     */
    toggleTaskLayer() {
      var _that = this;
      //切换状态
      this.taskLayerVisible = !this.taskLayerVisible;
      //如果显示，则添加图层
      if (this.taskLayerVisible) {
        this.drawTaskData();
      } else {
        //如果隐藏，则移除图层
        var layersArray = _that.map.getLayers().getArray();//获取所有图层的数组
        var layersToRemove = layersArray.filter(function (layer) {//过滤出需要移除的集群图层
          return layer.get('name') === "任务层";
        });
        layersToRemove.forEach(function (layer) {
          _that.map.removeLayer(layer);
        });
      }
    },

    /**
     * 任务层的更新
     */
    updateTaskLayer() {

      var _that = this;

      if (this.taskLayerVisible) {
        //先移除图层
        var layersArray = _that.map.getLayers().getArray();//获取所有图层的数组
        var layersToRemove = layersArray.filter(function (layer) {//过滤出需要移除的集群图层
          return layer.get('name') === "任务层";
        });
        layersToRemove.forEach(function (layer) {
          _that.map.removeLayer(layer);
        });
        //再绘制图层
        this.drawTaskData();
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

    drawClusterEllipse() {
      if (this.ellipseData.length <= 0) {
        return;
      }
      const seedValue = 'mySeedValue'; // 设置任何字符串作为种子值
      //循环绘制各集群椭圆
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
          var x = radiusX * Math.cos(theta);
          var y = radiusY * Math.sin(theta);

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
          clusterId: item.clusterId,
          clusterName: item.clusterName,
          createTime: item.createTime,
          taskId: item.taskId,
          nodesList: item.nodesList,
          nodesNumber: item.nodesNumber,
          clusterStatus: item.clusterStatus,
          lostNodesNumber: item.lostNodesNumber,
          damagedNodesNumber: item.damagedNodesNumber,
          communicationStationStatus: item.communicationStationStatus,
          electronicInterferenceStatus: item.electronicInterferenceStatus,
          taskFinishFlag: item.taskFinishFlag,
        });
        this.ellipseFeature.set("ellipse", true);

        // 创建向量源并添加要素  
        var vectorSource = null;

        if(item.clusterStatus.includes("集群被攻击状态") ){
          //console.log("item.clusterStatus"+item.clusterStatus)
          var flagTempX = radiusX * Math.cos(0);
          var flagTempY = radiusY * Math.sin(0);
          var flagX = centerX + flagTempX * cosR - flagTempY * sinR;
          var flagY = centerY + flagTempX * sinR + flagTempY * cosR;
          var flagFeature = new Feature({
          geometry: new Point([flagX, flagY]),
          clusterId: item.clusterId,
          clusterName: item.clusterName,
          createTime: item.createTime,
          taskId: item.taskId,
          nodesList: item.nodesList,
          nodesNumber: item.nodesNumber,
          clusterStatus: item.clusterStatus,
          lostNodesNumber: item.lostNodesNumber,
          damagedNodesNumber: item.damagedNodesNumber,
          communicationStationStatus: item.communicationStationStatus,
          electronicInterferenceStatus: item.electronicInterferenceStatus,
          taskFinishFlag: item.taskFinishFlag,
          });

          let iconStyle = new Icon({
            anchor: [0.5, 0.5], // 设置图标锚点位置（默认是图标的中心点）  
            anchorXUnits: 'fraction',
            anchorYUnits: 'fraction',
            src: "/images/flag_red.png", // 设置图标URL  
            scale: 0.10,

          });
          
          // 设置层级样式
          let style = new Style({
            image: iconStyle
          });
          flagFeature.setStyle(style);
          flagFeature.set("ellipse", true);

          vectorSource = new VectorSource({
           features: [this.ellipseFeature,flagFeature],
          });

        }else{
          vectorSource = new VectorSource({
            features: [this.ellipseFeature],
          });
        }

        // 创建向量图层并设置样式  
        var vectorLayer = new VectorLayer({
          source: vectorSource,
          name: '集群层',
          style: new Style({
            stroke: new Stroke({
              lineDash: [6, 6],
              color: this.getRandomColor()[index], // 生成随机颜色
              width: 2,
            }),
            fill: new Fill({
              color: 'rgba(0, 0, 255, 0)',
            }),
          }),
        });

        // 将向量图层添加到地图中  
        this.map.addLayer(vectorLayer);

      });

      // 为图层添加hover事件，改变鼠标样式  
      var _that = this;
      _that.map.on('pointermove', function (evt) {
        // 获取鼠标的地图坐标  
        var feature = _that.map.forEachFeatureAtPixel(evt.pixel, function (feature) {
          return feature;
        })
        if (feature) {
          if (feature.get('ellipse') == true) {
            _that.map.getTargetElement().style.cursor = 'pointer';
          }
        } else {
          _that.map.getTargetElement().style.cursor = '';
        }
      });

      // 监听鼠标点击事件,点击后显示集群信息
      _that.map.on('click', function (evt) {
        var feature = _that.map.forEachFeatureAtPixel(evt.pixel, function (feature) {
          return feature;
        })
        if (feature) {
          if (feature.get('ellipse') == true) {
            _that.currentCluster = feature;//获取当前集群数据
            _that.clusterDrawerVisible = true;//显示抽屉信息
          }
        }
      });
    },

    drawTaskEllipse() {
      if (this.taskEllipseData.length <= 0) {
        return;
      }
      //
      this.taskData.forEach((item, index) => {
        //任务已完成的话则不再绘制相关椭圆
        if(item.taskFinishFlag.includes("未完成")){

          var ellipse = this.taskEllipseData[index];
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
            var x = radiusX * Math.cos(theta);
            var y = radiusY * Math.sin(theta);

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
            taskId: item.taskId,
            taskType: item.taskType,
            createTime: item.createTime,
            taskDescription: item.taskDescription,
            nodesList: item.nodesList,
            nodesType: item.nodesType,
            clustersList: item.clustersList,
            clusterNumber: item.clusterNumber,
            taskLocationLongitude: item.taskLocationLongitude,
            taskLocationLatitude: item.taskLocationLatitude,
            taskLocationAltitude: item.taskLocationAltitude,
            taskAreaRadius: item.taskAreaRadius,
            startTime: item.startTime,
            completionTime: item.completionTime,
            actualStartTime: item.actualStartTime,
            actualCompletionTime: item.actualCompletionTime,
            taskFinishFlag: item.taskFinishFlag,
          });
          this.ellipseFeature.set("taskEllipse", true);

          // 创建向量源并添加要素  
          var vectorSource = new VectorSource({
            features: [this.ellipseFeature],
          });

          // 创建向量图层并设置样式  
          var vectorLayer = new VectorLayer({
            source: vectorSource,
            name: '任务层',
            style: new Style({
              stroke: new Stroke({
                // color: 'red',
                color: this.getRandomColor()[index], // 生成随机颜色
                width: 2,
              }),
              fill: new Fill({
                color: 'rgba(0, 0, 255, 0.1)',
              }),
            }),
          });

          // 将向量图层添加到地图中  
          this.map.addLayer(vectorLayer);
      
        }
      });

      // 为图层添加hover事件，改变鼠标样式  
      var _that = this;
      _that.map.on('pointermove', function (evt) {
        // 获取鼠标的地图坐标  
        var feature = _that.map.forEachFeatureAtPixel(evt.pixel, function (feature) {
          return feature;
        })
        if (feature) {
          if (feature.get('taskEllipse') == true) {
            _that.map.getTargetElement().style.cursor = 'pointer';
          }
        } else {
          _that.map.getTargetElement().style.cursor = '';
        }
      });

      // 监听鼠标点击事件,点击后显示集群信息
      _that.map.on('click', function (evt) {
        var feature = _that.map.forEachFeatureAtPixel(evt.pixel, function (feature) {
          return feature;
        })
        if (feature) {
          if (feature.get('taskEllipse') == true) {
            _that.currentTask = feature;//获取当前任务数据
            _that.taskDrawerVisible = true;//显示抽屉信息
            _that.clusterDrawerVisible = false;
          }
        }
      });
    },

    //随机生成颜色
    getRandomColor() {
      if (this.cachedColors) {
        return this.cachedColors;
      }
      // 如果颜色数组不存在，则生成并缓存
      this.cachedColors = [];
      const letters = '0123456789ABCDEF';
      for (let i = 0; i < 50; i++) {
        let color = '#';
        for (let j = 0; j < 6; j++) {
          color += letters[Math.floor(Math.random() * 16)];
        }
        this.cachedColors.push(color);
      }
      return this.cachedColors;
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

    //仅获得集群数据
    async fetchClusterData() {
      try {
        this.clusterData = (await getClusterData()).data;
        console.log("后端拿到的集群数据：", this.clusterData);
        this.getLinePositions();
        this.drawLine();

      } catch (error) {
        console.error('Error fetching data:', error);
      }
    },

    //绘制集群椭圆
    async drawClusterData() {
      try {

        this.drawClusterEllipse();
        //所有的图层信息
        this.map.getLayers().forEach(item => {
          console.log("itemlayer:", item);
          if (item.get('name') == '集群层') {
            console.log("集群层itemlayer:", item);
            console.log("集群层索引：", this.map.getLayers().getArray().indexOf(item));
          }
        });

        //调整图层的层级，确保面积小的在最上面
        var layersArray = this.map.getLayers().getArray();
        layersArray.sort(function (a, b) {
          if (a.get('name') === "集群层" && b.get('name') === "集群层") {
            return b.get('area') - a.get('area'); // 按面积降序排序
          }
          return 0;// 如果不是"集群层"，或者其中一个不是，保持原始顺序
        });
        this.map.setLayers(new Collection(layersArray));

      } catch (error) {
        console.error('Error fetching data:', error);
      }
    },


    //仅获得任务数据
    async fetchTaskData() {
      try {
        this.taskData = (await getTaskData()).data;
        console.log("后端拿到的任务数据：", this.taskData);

      } catch (error) {
        console.error('Error fetching data:', error);
      }
    },

    //绘制任务椭圆
    async drawTaskData() {
      try {

        this.drawTaskEllipse();

        //调整图层的层级，确保面积小的在最上面
        var layersArray = this.map.getLayers().getArray();
        layersArray.sort(function (a, b) {
          if (a.get('name') === "任务层" && b.get('name') === "任务层") {
            return b.get('area') - a.get('area'); // 按面积降序排序
          }
          return 0;// 如果不是"任务层"，或者其中一个不是，保持原始顺序
        });
        this.map.setLayers(new Collection(layersArray));

      } catch (error) {
        console.error('Error fetching data:', error);
      }
    },

    /**
     * 异步下载Word文件
     */
    async downloadWord() {
      try {
        const response = await exportDataWord();
        const url = window.URL.createObjectURL(new Blob([response.data]));// 将二进制文件转化为可访问的url
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', 'dataInfo.docx'); // 设置下载的文件名  
        document.body.appendChild(link);
        link.click();

        // 清除url及a标签  
        window.URL.revokeObjectURL(url);
        document.body.removeChild(link);

      } catch (error) {
        console.error('下载失败:', error);
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
  async mounted() {
    this.initMap();

    //获取数据并直接绘制
    await this.fetchVehicleData();

    //获取任务数据，不绘制
    await this.fetchTaskData();

    //仅仅用来获取集群数据，不绘制，绘制的话通过点击触发事件
    await this.fetchClusterData();

    this.addPop();

    //连接过后，每次获得数据后更新地图
    this.initWebSocket();


    // this.intervalGetData();//获取随机经纬度的数据
    // const intervalId = setInterval(() => {
    //     // 从前端通过axios获取信息
    //     this.intervalGetData();  
    //   }, 3000);//轮询请求，每隔5秒获取一次数据

  },
  unmounted() {  //组件销毁时，清除定时器
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
  height: 760px;
  margin: 0 auto;
  border: 1px solid #42B983;
  position: relative;
  z-index: 1;
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


/* 系统名片 */
.map-container {
  display: inline-block;
  position: absolute;
  top: 7px;
  left: 2.5%;
  z-index: 2;
  width: 330px;
  height: 130px;
  /* 字体颜色 */
  color: #fff;
  background-color: rgba(10, 42, 47, 0.8);
  border: 1px solid skyblue;
  border-radius: 15px;

  /*  平台名称 */
  .sysName {
    margin: 5px auto;
    margin-bottom: 0px;
    height: 50px;
    font-size: 25px;
    text-align: center;
    line-height: 50px;
    /* border: 1px solid skyblue;
    border-radius: 5px;
    border-start-end-radius: 20px;
    border-bottom-left-radius: 20px; */
  }

  /* 集群按钮 */
  .funcBtn {
    display: inline-block;
    position: absolute;
    top: 65px;
    /* left: 15px; */
    left: 25px;
    height: 50px;
    /* background-color: transparent;透明背景 */
    background-color: #233b3a69;
    /* 字体颜色 */
    color: #fff;
    border: 1px solid skyblue;
  }

  /* 任务层按钮  */
  .funcBtn-1 {
    /* top: 150px;
    height: 30px;
    text-align: center; */
    display: inline-block;
    position: absolute;
    top: 65px;
    left: 105px;
    height: 50px;
    background-color: #233b3a69;
    color: #fff;
    border: 1px solid skyblue;
  }

  /* 导出按钮  */
  .funcBtn-2 {
    display: inline-block;
    position: absolute;
    top: 65px;
    left: 195px;
    height: 50px;
    background-color: #233b3a69;
    color: #fff;
    border: 1px solid skyblue;
  }
}

</style>
