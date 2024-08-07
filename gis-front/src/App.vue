<template>  
    <div id="app">

      <div>
        <visual_backdata/>
      </div>


    </div>  
  </template>  
    
  <script>  
  import MapComponent from './components/MapComponent.vue';  
  import axiosTest  from './components/axiosTest.vue';  
  import socketTest from './components/socketTest.vue';
  import visual from './components/visual.vue';
  import visual_backdata from './components/visual_backdata_2430.vue'
  export default {  
    name: 'App',  
    components: {  
      visual_backdata
    },  
  };  
  </script>