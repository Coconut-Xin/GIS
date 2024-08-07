<template>
  <div class="container">
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
        <!-- <el-row class="address">
          <div class="basic-item">
            <span class="basic-item-property">住址：</span>
            <span>{{ inputValueObj.curAddress }}</span>
          </div>
        </el-row> -->
      </el-row>

    </el-drawer>
  </div>
</template>

<script>
import 'ol/ol.css'
import Map from 'ol/Map'
import View from 'ol/View'
import TileLayer from 'ol/layer/Tile'
import XYZ from 'ol/source/XYZ'
import VectorLayer from 'ol/layer/Vector'
import Feature from "ol/Feature"
import { Point } from 'ol/geom'
import { Style, Fill, Stroke } from 'ol/style'
import { Vector as VectorSource } from "ol/source";
import { Circle, Text } from 'ol/style';
import { ZoomSlider, FullScreen } from 'ol/control';
import Overlay from "ol/Overlay";

import { getRandomData } from '@/api/node/node';
import { getVehicleData } from '@/api/node/node';


export default {
  name: 'FirstMap',
  data() {
    return {
      randomData: [],
      featureList: [],
      currentFeature: [],
      map: null,
      overlay: null,
      drawerVisible: false,
      vehicleData: [],//从getRandomData()方法获取vehicleData数据的代码
      // properData: [{
      //   "name": "旋翼无人机",
      //   "SN": "S-24001",
      //   "speed": 30,
      //   "longitude": 120.12638163,
      //   "latitude": 24.1353712537,
      // }, {
      //   "name": "旋翼无人机",
      //   "SN": "S-24002",
      //   "speed": 28,
      //   "longitude": 120.56253054,
      //   "latitude": 24.6329956,
      // }, {
      //   "name": "扑翼无人机",
      //   "SN": "P-24013",
      //   "speed": 35,
      //   "longitude": 120.52801179,
      //   "latitude": 24.1595512,
      // }, {
      //   "name": "彩虹无人机",
      //   "SN": "P-24014",
      //   "speed": 29,
      //   "longitude": 120.6037498145,
      //   "latitude": 24.13,
      // }, {
      //   "name": "扑翼无人机",
      //   "SN": "P-24015",
      //   "speed": 33,
      //   "longitude": 120.34374981,
      //   "latitude": 24.85,
      // }, {
      //   "name": "旋翼无人机",
      //   "SN": "S-24003",
      //   "speed": 25,
      //   "longitude": 119.69234433025713,
      //   "latitude": 24.246740946087076,
      // }, {
      //   "name": "扑翼无人机",
      //   "SN": "P-24016",
      //   "speed": 28,
      //   "longitude": 120.051741725,
      //   "latitude": 24.48930760074888,
      // }, {
      //   "name": "扑翼无人机",
      //   "SN": "P-24017",
      //   "speed": 30,
      //   "longitude": 119.80645536017867,
      //   "latitude": 24.766067687763954,
      // }, {
      //   "name": "旋翼无人机",
      //   "SN": "S-24008",
      //   "speed": 32,
      //   "longitude": 120.68938808100708,
      //   "latitude": 24.440775109162328,
      // }, {
      //   "name": "扑翼无人机",
      //   "SN": "P-24018",
      //   "speed": 25,
      //   "longitude": 119.30303478675799,
      //   "latitude": 24.53296606981423,
      // }

      // ]
    }
  },
  methods: {
    initMap() {
      const gaodemap = new TileLayer({
        title: '高德地图',
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

    /**
    * 根据后端数据，批量添加坐标点
    */
    handleAddBatchFeature(properData) {

      console.log("根据传入的vehicleData批量添加点", properData);
      const _that = this;
      // _that.features = [];
      /*将矢量数据源添加到矢量图层*/
      _that.layervector = new VectorLayer({
        source: new VectorSource(),
        name: '系统层'
      })
      _that.map.addLayer(_that.layervector);
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
      })
      // 监听鼠标移动事件，鼠标移动到feature区域，变为手形
      _that.map.on('pointermove', function (evt) {
        var pixel = _that.map.getEventPixel(evt.originalEvent);
        var hit = _that.map.hasFeatureAtPixel(pixel);
        _that.map.getTargetElement().style.cursor = hit ? 'pointer' : '';
      })


    },

    /*
    信息明细
    */
    getDetail() {
      console.log("信息明细");
      this.drawerVisible = true;


    },

    /*
    *异步获取后端无人设备列表数据
    根据列表数据批量添加节点
    */
    async fetchVehicleData() {
      try {

        this.vehicleData = (await getVehicleData()).data;
        console.log("这是从后端拿到的数据：", this.vehicleData);
        this.handleAddBatchFeature(this.vehicleData);

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

    // this.intervalGetData();//获取随机经纬度的数据
    // const intervalId = setInterval(() => {
    //     // 从前端通过axios获取信息
    //     this.intervalGetData();  
    //   }, 3000);//轮询请求，每隔5秒获取一次数据

  },
  // unmounted(){  //组件销毁时，清除定时器
  //   clearInterval(intervalId);
  // }

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
