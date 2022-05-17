import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import AntD from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
import axios from 'axios'

axios.defaults.baseURL = process.env.VUE_APP_SERVER

// axios interceptors
axios.interceptors.request.use(function (config) {
    console.log('request args: ', config);
    return config;
}, error => {
    return Promise.reject(error);
});
axios.interceptors.response.use(function (response) {
    console.log('return result: ', response);
    return response;
}, error => {
    console.log('return errors: ', error);
    return Promise.reject(error);
});

const app = createApp(App);
app.use(store).use(router).use(AntD).mount('#app')

console.log('env: ', process.env.NODE_ENV)
console.log('env: ', process.env.VUE_APP_SERVER)
