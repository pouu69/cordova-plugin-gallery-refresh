var exec = require('cordova/exec');

function GalleryRefresh(){

}

GalleryRefresh.prototype.refresh = function(path, successCb, errorCb){
  exec(function(params){ successCb(params); }, function(error){ errorCb(error); }, "GalleryRefresh", "refresh", [path]);
}

window.galleryRefresh = new GalleryRefresh();
module.exports = galleryRefresh;
