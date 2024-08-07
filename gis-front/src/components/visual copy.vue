<template>
  <div class="container">
    <div id="map"></div>
    <div id="popup" class="ol-popup">
      <a href="#" id="popup-closer" class="ol-popup-closer"></a>
      <div id="popup-content"></div>
    </div>
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




export default {
  name: 'FirstMap',
  data() {
    return {
      map: null,
      overlay: null,
      properData: [{
        "name": "旋翼无人机",
        "SN": "S-24001",
        "speed": 30,
        "longitude": 120.12638163,
        "latitude": 24.1353712537,
      }, {
        "name": "旋翼无人机",
        "SN": "S-24002",
        "speed": 28,
        "longitude": 120.56253054,
        "latitude": 24.6329956,
      }, {
        "name": "扑翼无人机",
        "SN": "P-24013",
        "speed": 35,
        "longitude": 120.52801179,
        "latitude": 24.1595512,
      }, {
        "name": "彩虹无人机",
        "SN": "P-24014",
        "speed": 29,
        "longitude": 120.6037498145,
        "latitude": 24.13,
      }, {
        "name": "扑翼无人机",
        "SN": "P-24015",
        "speed": 33,
        "longitude": 120.34374981,
        "latitude": 24.85,
      }, {
        "name": "旋翼无人机",
        "SN": "S-24003",
        "speed": 25,
        "longitude": 119.69234433025713,
        "latitude": 24.246740946087076,
      }, {
        "name": "扑翼无人机",
        "SN": "P-24016",
        "speed": 28,
        "longitude": 120.051741725,
        "latitude": 24.48930760074888,
      }, {
        "name": "扑翼无人机",
        "SN": "P-24017",
        "speed": 30,
        "longitude": 119.80645536017867,
        "latitude": 24.766067687763954,
      }, {
        "name": "旋翼无人机",
        "SN": "S-24008",
        "speed": 32,
        "longitude": 120.68938808100708,
        "latitude": 24.440775109162328,
      }, {
        "name": "扑翼无人机",
        "SN": "P-24018",
        "speed": 25,
        "longitude": 119.30303478675799,
        "latitude": 24.53296606981423,
      }

      ]
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
    * 批量添加坐标点
    */
    handleAddBatchFeature() {
      console.log(this.properData);
      const _that = this;
      _that.features = [];
      /*将矢量数据源添加到矢量图层*/
      _that.layervector = new VectorLayer({
        source: new VectorSource()
      })
      _that.map.addLayer(_that.layervector);
      // 循环添加point
      for (let i = 0; i < this.properData.length; i++) {
        // 创建point
        let point = new Feature({
          geometry: new Point([_that.properData[i].longitude, _that.properData[i].latitude])
        });
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
        _that.features.push(point);
      }

      _that.layervector.getSource().addFeatures(_that.features);
    },

    /**
     * 添加弹出框
     */
    addPop() {
      console.log(this.properData);
      const _that = this;
    
      var source = new VectorSource({
        wrapX: false
      });

      for (let i = 0; i < this.properData.length; i++) {
        var feature = new Feature({
          geometry: new Point([this.properData[i].longitude, this.properData[i].latitude]),
          name: this.properData[i].name,
          SN: this.properData[i].SN,
          speed: this.properData[i].speed,
          longitude: this.properData[i].longitude,
          latitude: this.properData[i].latitude,
        });
        source.addFeature(feature)
      }
      //创建图层
      var vectorLayer = new VectorLayer({
        source: source,
      });
      _that.map.addLayer(vectorLayer);

      // 获取pop的dom对象
      var container = document.getElementById('popup');// 弹出框的 HTML 元素
      var content = document.getElementById('popup-content');
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

          //无人机名称
          var name = document.createElement('h2');
          name.innerHTML = feature.get('name');
          content.appendChild(name);

          //无人机编号
          var SN = document.createElement('p');
          SN.innerHTML = '编号：' + feature.get('SN');
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


    }

  },
  mounted() {
    this.initMap();
    this.handleAddBatchFeature();
    this.addPop();



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
</style>
