import Vue from 'vue'
import v from './v.vue'

Vue.config.productionTip = false;

new Vue({
    render: h => h(v),
}).$mount('#v');