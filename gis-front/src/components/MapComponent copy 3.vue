<template>  

    <div ref="mapContainer" class="map-container"></div>  
    <!-- <div><button @click="fetchData" style="size: 50px 50px;">

    </button></div> -->

    <div id="popup" class="ol-popup">      
       <!-- <a href="#" id="popup-closer" class="ol-popup-closer"></a>   -->
       <!-- <div id="popup-content" style="" >{{ this.nowCoordinate }}</div>   -->
       <div id="popup-content" style="" v-if="this.nowCoordinate!=null" >经度：{{ this.nowCoordinate[0] }}</div> 
       <div id="popup-content" style="" v-if="this.nowCoordinate!=null">纬度：{{ this.nowCoordinate[1] }}</div> 
    </div>  

</template>  
    
 <script>  
  import 'ol/ol.css';  
  import Map from 'ol/Map';  
  import View from 'ol/View';  
  import TileLayer from 'ol/layer/Tile';  
  import OSM from 'ol/source/OSM';  
  import XYZ from 'ol/source/XYZ';
  import VectorLayer from 'ol/layer/Vector';
  import VectorSource from 'ol/source/Vector';
  import Feature from 'ol/Feature';
  import Point from 'ol/geom/Point';
  import { Circle, Fill, Stroke, Style } from 'ol/style';
  import { Overlay } from 'ol';

  import {getDatam,getNode} from '@/api/node/node';
  import { toHandlers } from 'vue';


  export default {  
    name: 'MapComponent',  
    data(){
      return {
        nowFeature:null,
        nowCoordinate:null,
        popup1:null, //popup1 layer 
        data :null,    
        map : {},       // 容器
        dynamic_layer:null, 
        dynamic_source:null,
        dynamic_style:null,
        points : null,
      };
    },
    methods:{
      async fetchData(){
        console.log('sssss');

        try{
          this.data = (await getNode()).data;
          console.log(this.data);
        }catch(error){
          console.error('Error fetching data:', error);  
        }
             
      }

    },

    mounted() {  

      // 地图容器  
      const mapContainer = this.$refs.mapContainer;  
      
      const gaode = new TileLayer({
               title: "高德地图",
               source: new XYZ({
                url: 'http://wprd0{1-4}.is.autonavi.com/appmaptile?lang=zh_cn&size=1&style=7&x={x}&y={y}&z={z}',
                wrapX: false
               })
       });

      const view = new View({
        center:[114.30,30.50],
        zoom : 16 ,
        projection : 'EPSG:4326'
      });

      this.map = new Map({  
        target: mapContainer,  
        layers: [gaode],  
        view: view,  
      });  

      this.popup1 = new Overlay({  
          element: document.getElementById('popup'), // 你需要在HTML中创建一个元素，比如一个div  
          autoPan: true, // 允许自动平移地图以使浮窗可见  
          autoPanAnimation: {  
          duration: 250  
            },  
          // positioning: 'top-center' // 设置浮窗的位置  
      });  
      
    //  this.map.addOverlay(this.popup1); // 将浮窗添加到地图上  
      
    this.map.on("click",(evt)=>{
        console.log(evt.pixel);
        var feature1 = this.map.getFeaturesAtPixel(evt.pixel,{
          hitTolerance : 10,
          layerFilter: function(layer) {  
            // 你可以在这里设置一个过滤器来决定哪些图层应该被查询  
            // 例如，你可能只想查询特定的图层  
            return true; // 返回 true 表示查询所有图层  
          },
        });
        console.log("123",feature1,typeof(feature1)); 
        // if(feature){
        //   var geom = feature.getGeometry();

        //   if(geom instanceof Point){
        //     var coordinates = geom.getCoordinates();
        //     console.log("坐标是:",coordinates);
        //   }

        // }

    });

        // 监听pointermove事件  
    this.map.on('pointermove', (evt)=>{  
          // 检查是否有特征在鼠标位置  
        var feature1 = this.map.getFeaturesAtPixel(evt.pixel,{
          hitTolerance : 10,
          layerFilter: function(layer) {  
            // 你可以在这里设置一个过滤器来决定哪些图层应该被查询  
            // 例如，你可能只想查询特定的图层  
            return true; // 返回 true 表示查询所有图层  
          },
        });
        console.log("鼠标在移动！");
        console.log(feature1);
        feature1 = feature1.shift();
        console.log(feature1);
        if (feature1 instanceof Feature) {  
              this.nowFeature = feature1;
              console.log("is FeatureFeatureFeatureFeatureFeatureFeatureFeatureFeature");
              // 获取特征的几何对象  
              var geometry = feature1.getGeometry();  
              // 假设几何对象是一个点，获取其坐标  
              if (geometry instanceof Point) {  
                  var coordinate = geometry.getCoordinates(); 
                  this.nowCoordinate = coordinate; 
                  console.log("geometry.getCoordinates() : ", coordinate)
                  // 设置浮窗的内容  
                  // var content = '坐标: ' + coordinate.join(', ');  
                  // this.popup1.setElement(document.getElementById('popup')); // 确保浮窗元素被设置  
                  // 设置浮窗的位置和偏移量  
                  this.popup1.setPosition(coordinate);  
                  // 显示浮窗  
                  this.map.addOverlay(this.popup1);  
              }  
          } else {  
            
            // 如果没有特征在鼠标位置，隐藏浮窗  
              this.map.removeOverlay(this.popup1);
               
          }  
      });
      

    this.dynamic_source = new VectorSource();

      this.dynamic_style = new Style({
            image : new Circle({
                radius : 18,
                fill : new Fill({
                    color : "#ff2d51"
                }),
                // 描边
                stroke : new Stroke({
                    color : "#333"
                })

            })
      });

      this.dynamic_layer=new VectorLayer({
        source: this.dynamic_source,
        style: this.dynamic_style,

      });
      
      this.map.addLayer(this.dynamic_layer);

  
      this.points = new Feature({
        geometry : new Point([114.30,30.50]),
      });

      var point2 = new Feature({
        geometry : new Point([114.30,30.50]),
       });
      
       var point3 = new Feature({
          geometry : new Point([112.30,32.50]),
       });

      this.dynamic_source.addFeature(this.points);
      this.dynamic_source.addFeature(point2);
      this.dynamic_source.addFeature(point3);
      // // 创建一个地图视图  
      // const view = new View({  
      //   center: [0, 0], // 地图中心坐标  
      //   zoom: 2, // 初始缩放级别  
      // });  
    
      // // 创建一个OSM图层  
      // const osmLayer = new TileLayer({  
      //   source: new OSM(),  
      // });  
    
      // // 创建一个地图实例，并添加到DOM容器中  
      // const map = new Map({  
      //   target: mapContainer,  
      //   layers: [osmLayer],  
      //   view: view,  
      // });  

      // 设置轮询，每秒更新一次时间
      const intervalId = setInterval(() => {
        
        // this.fetchData();
        const randomX = Math.random() * 360 - 180; // 在-180到180之间生成一个随机经度
        const randomY = Math.random() * 180 - 90; // 在-90到90之间生成一个随机纬度
        this.points.getGeometry().setCoordinates([randomX, randomY]);
        console.log(randomX);
        console.log(randomY);
      }, 5000);

      

    },
    unmounted(){
      clearInterval(intervalId);
    }


   };


  

</script>  
    
<style scoped>  
  .map-container {  
    width: 100%;  
    height: 1000px; /* 设置地图容器的高度 */  
  }  

  .ol-popup {  
    position: absolute;  
    background-color: white;  
    -webkit-filter: drop-shadow(0 1px 4px rgba(0,0,0,0.2));  
    filter: drop-shadow(0 1px 4px rgba(0,0,0,0.2));  
    padding: 15px;  
    border-radius: 10px;  
    border: 1px solid #cccccc;  
    bottom: 20px;  
    left: -64px;  
    min-width: 120px;  
  }  
 
  /* .ol-popup:after, .ol-popup:before {  
      content: '';  
      position: absolute;  
      bottom: 0;  
      right: 265px;  
      width: 0;  
      height: 0;  
      border: 20px solid transparent;  
      border-top-color: #ffffff;  
      border-bottom: 0;  
      border-right: 0;  
      margin-left: -10px;  
      margin-bottom: -20px;  
  }   */
  
  .ol-popup-closer {  
      text-decoration: none;  
      position: absolute;  
      top: 2px;  
      right: 8px;  
      font-size: 1.5em;  
  }

  
  
</style>