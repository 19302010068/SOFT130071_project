(function(t){function e(e){for(var r,a,l=e[0],s=e[1],u=e[2],h=0,f=[];h<l.length;h++)a=l[h],Object.prototype.hasOwnProperty.call(n,a)&&n[a]&&f.push(n[a][0]),n[a]=0;for(r in s)Object.prototype.hasOwnProperty.call(s,r)&&(t[r]=s[r]);c&&c(e);while(f.length)f.shift()();return i.push.apply(i,u||[]),o()}function o(){for(var t,e=0;e<i.length;e++){for(var o=i[e],r=!0,l=1;l<o.length;l++){var s=o[l];0!==n[s]&&(r=!1)}r&&(i.splice(e--,1),t=a(a.s=o[0]))}return t}var r={},n={browse:0},i=[];function a(e){if(r[e])return r[e].exports;var o=r[e]={i:e,l:!1,exports:{}};return t[e].call(o.exports,o,o.exports,a),o.l=!0,o.exports}a.m=t,a.c=r,a.d=function(t,e,o){a.o(t,e)||Object.defineProperty(t,e,{enumerable:!0,get:o})},a.r=function(t){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(t,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(t,"__esModule",{value:!0})},a.t=function(t,e){if(1&e&&(t=a(t)),8&e)return t;if(4&e&&"object"===typeof t&&t&&t.__esModule)return t;var o=Object.create(null);if(a.r(o),Object.defineProperty(o,"default",{enumerable:!0,value:t}),2&e&&"string"!=typeof t)for(var r in t)a.d(o,r,function(e){return t[e]}.bind(null,r));return o},a.n=function(t){var e=t&&t.__esModule?function(){return t["default"]}:function(){return t};return a.d(e,"a",e),e},a.o=function(t,e){return Object.prototype.hasOwnProperty.call(t,e)},a.p="/";var l=window["webpackJsonp"]=window["webpackJsonp"]||[],s=l.push.bind(l);l.push=e,l=l.slice();for(var u=0;u<l.length;u++)e(l[u]);var c=s;i.push([1,"chunk-vendors","chunk-common"]),o()})({1:function(t,e,o){t.exports=o("6f2e")},"6f2e":function(t,e,o){"use strict";o.r(e);var r=o("2b0e"),n=function(){var t=this,e=t.$createElement,o=t._self._c||e;return o("div",{attrs:{id:"v"}},[o("v-header",{attrs:{location:"/browse"}}),o("main",[o("h2",[t._v(t._s(t.title))]),void 0!==t.currentPhotos?o("v-result",{attrs:{photos:t.currentPhotos}},[t._v("No photos")]):t._e(),void 0!==t.allPhotos?o("v-pages",{attrs:{photos:t.allPhotos},on:{"fetch-photos":t.fetchPhotos}}):t._e()],1),o("v-footer")],1)},i=[],a=o("1157"),l=o.n(a),s=o("2ef0"),u=o.n(s),c=o("71c2"),h=o("f569"),f=o("5e23"),p=o("076e"),d={name:"v",components:{VPages:f["a"],VResult:h["a"],VFooter:p["a"],VHeader:c["a"]},data:function(){return{title:void 0,allPhotos:void 0,currentPhotos:void 0}},mounted:function(){let t,e=location.toString().match(/.*\?(author)=(\d+)#?$/),o=location.toString().match(/.*\?(content)=(.+)#?$/),r=location.toString().match(/.*\?(location)=(\d+)#?$/),n=location.toString().match(/.*\?(location)=([A-Z]{2})#?$/);if(null!==e)t=e;else if(null!==o)t=o;else if(null!==r)t=r;else{if(null===n)return void this.error();t=n}l.a.ajax({method:"GET",url:syt.backend+"/browse",xhrFields:{withCredentials:!0},data:{[t[1]]:t[2]},dataType:"json",success:e=>{404===e?this.error():(this.title=u.a.upperFirst(t[1])+": "+e[0],this.allPhotos=e[1])}})},methods:{error(){this.title="404 Not Found"},fetchPhotos(t){this.currentPhotos=t}},watch:{title(){document.title=this.title}}},v=d,b=o("2877"),m=Object(b["a"])(v,n,i,!1,null,"22a5b162",null),y=m.exports;r["a"].config.productionTip=!1,new r["a"]({render:t=>t(y)}).$mount("#v")}});
//# sourceMappingURL=browse.9d12fe59.js.map