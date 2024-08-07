<template>
  <div>
    <h1>WebSocket Example</h1>
    <p>{{ message }}</p>
    <input v-model="inputMessage" placeholder="Type a message">
    <button @click="sendMessage">Send</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      ws: null,
      message: '',
      inputMessage: ''
    };
  },
  mounted() {
    // 创建 WebSocket 连接
    this.ws = new WebSocket('ws://localhost:8080/websocket');

    // 连接打开
    this.ws.onopen = () => {
      console.log('WebSocket connection opened');
    };

    // 接收消息
    this.ws.onmessage = (event) => {
      this.message = JSON.parse(event.data);
      console.log('Message from server:', event.data);
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
  methods: {
    sendMessage() {
      if (this.ws && this.ws.readyState === WebSocket.OPEN) {
        this.ws.send(this.inputMessage);
        this.inputMessage = '';
      }
    }
  },
  beforeUnmount() {
    // 关闭 WebSocket 连接
    if (this.ws) {
      this.ws.close();
    }
  },
};
</script>

<style scoped>
h1 {
  color: #42b983;
}
</style>