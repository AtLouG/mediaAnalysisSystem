(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-38903e44"],{"093a":function(e,t,i){"use strict";var o=i("b775");t["a"]={getVideoPageList:function(e){return Object(o["a"])("/api/admin/video/page/list",e)},createVideo:function(e){return Object(o["a"])("/api/admin/video/create",e)},editVideo:function(e){return Object(o["a"])("/api/admin/video/edit",e)},selectVideo:function(e){return Object(o["a"])("/api/admin/video/select/"+e)},deleteVideo:function(e){return Object(o["a"])("/api/admin/video/delete/"+e)},selectByVideoName:function(e){return Object(o["a"])("/api/admin/video/selectByVideoName",e)},getVideoDetailByVideoId:function(e){return Object(o["a"])("/api/admin/video/getVideoDetailByVideoId/"+e)},userAnalysis:function(e){return Object(o["a"])("/api/admin/video/userAnalysis/"+e)}}},"7bb9":function(e,t,i){"use strict";i.r(t);var o=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{staticClass:"app-container"},[i("el-form",{directives:[{name:"loading",rawName:"v-loading",value:e.formLoading,expression:"formLoading"}],ref:"form",attrs:{model:e.form,"label-width":"100px",rules:e.rules}},[i("el-form-item",{attrs:{label:"视频名字：",required:""}},[i("el-input",{model:{value:e.form.videoName,callback:function(t){e.$set(e.form,"videoName",t)},expression:"form.videoName"}})],1),i("el-form-item",{attrs:{label:"视频分类：",prop:"videoCategory",required:""}},[i("el-select",{attrs:{placeholder:"视频分类"},model:{value:e.form.videoCategory,callback:function(t){e.$set(e.form,"videoCategory",t)},expression:"form.videoCategory"}},e._l(e.categoryEnum,(function(e){return i("el-option",{key:e.key,attrs:{value:e.key,label:e.value}})})),1)],1),e._l(e.form.videoTagList,(function(t,o){return i("el-form-item",{key:o,attrs:{label:"视频标签"+(o+1)+"：",required:""}},[i("el-input",{staticStyle:{width:"80%"},model:{value:t.tagName,callback:function(i){e.$set(t,"tagName",i)},expression:"titleItem.tagName"}}),i("el-button",{staticClass:"link-left",attrs:{type:"text",size:"mini"},on:{click:function(t){return e.form.videoTagList.splice(o,1)}}},[e._v("删除")])],1)})),i("el-form-item",[i("el-button",{attrs:{type:"primary"},on:{click:e.submitForm}},[e._v("提交")]),i("el-button",{on:{click:e.resetForm}},[e._v("重置")]),i("el-button",{attrs:{type:"success"},on:{click:e.addTitle}},[e._v("添加标签")])],1)],2)],1)},r=[],a=i("5530"),n=i("2f62"),s=i("093a"),d={data:function(){return{form:{videoId:null,videoCategory:null,videoUrl:"",videoName:"",videoTagList:[]},formLoading:!1,rules:{videoCategory:[{required:!0,message:"请选择分类",trigger:"change"}]}}},created:function(){var e=this.$route.query.id,t=this;e&&0!==parseInt(e)&&(t.formLoading=!0,s["a"].getVideoDetailByVideoId(e).then((function(e){t.form=e.response,t.formLoading=!1})))},methods:Object(a["a"])({submitForm:function(){var e=this,t=this;this.$refs.form.validate((function(i){if(!i)return!1;e.formLoading=!0,s["a"].editVideo(e.form).then((function(e){1===e.code?(t.$message.success(e.message),t.delCurrentView(t).then((function(){t.$router.push("/video/list")}))):(t.$message.error(e.message),t.formLoading=!1)})).catch((function(e){t.formLoading=!1}))}))},addTitle:function(){this.form.videoTagList.push({userTag:""})},removeTitleItem:function(e){this.form.videoTagList.remove(e)},resetForm:function(){this.$refs["form"].resetFields()}},Object(n["b"])("tagsView",{delCurrentView:"delCurrentView"})),computed:Object(a["a"])(Object(a["a"])({},Object(n["c"])("enumItem",["enumFormat"])),Object(n["e"])("enumItem",{categoryEnum:function(e){return e.user.categoryEnum}}))},u=d,l=i("2877"),c=Object(l["a"])(u,o,r,!1,null,null,null);t["default"]=c.exports}}]);