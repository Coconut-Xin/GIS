<template>  

    <div ref="mapContainer" class="map-container"></div>  
    <div><button @click="fetchData" style="size: 50px 50px;">

    </button></div>
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

  import {getDatam,getNode} from '@/api/node/node';
import { toHandlers } from 'vue';


  export default {  
    name: 'MapComponent',  
    data(){
      return {
        data :null,
        map : null,
        dynamic_layer:null,
        dynamic_source:null,
        dynamic_style:null,
        points : null,
      };
    },
    methods:{
      async fetchData(){
        console.log('sssss');
        // try{
        //   const response = await getData();
        //   this.data = response.data;
        //   console.log("12321312321312321312321312132");
        //   console.log(this.data)
        // }catch(error){
        //   console.error('Error fetching data:', error);  
        // }
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
        console.log(feature1);
        // if(feature){
        //   var geom = feature.getGeometry();

        //   if(geom instanceof Point){
        //     var coordinates = geom.getCoordinates();
        //     console.log("坐标是:",coordinates);
        //   }

        // }

      })
      

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


      this.dynamic_source.addFeature(this.points);
      this.dynamic_source.addFeature(point2);

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
      intervalId = setInterval(() => {
        
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
    height: 800px; /* 设置地图容器的高度 */  
  }  

  
</style>