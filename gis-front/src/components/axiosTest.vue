<template>  
    <div>  
      <!-- 显示从后端获取的数据 -->  
      <p>12321312</p>
      <p>{{ message }}</p>  
      <input v-model="message" placeholder="edit me">  
      <p>The message is: {{ message }}</p> 
    </div>  
</template>  
    
<script>  
  import axios from 'axios';  
  import {getData} from '@/api/node/node'; 
  
  export default {  
    data() {  
      return {  
        message: 'Loading...',  
      };  
    },  
    async created() {  
      try {  
       // const response = await axios.get('http://localhost:8080/GIS/node/list2'); // 假设你的 Spring Boot 应用运行在 localhost:8080
        const response = await getData();  
        this.message = response; // 假设后端返回了一个包含 message 字段的 JSON 对象  
        console.log(this.message);
      } catch (error) {  
        console.error('Error fetching data:', error);  
        this.message = 'An error occurred';  
      } ;
      



    },  
  };  

</script>

<style>

</style>