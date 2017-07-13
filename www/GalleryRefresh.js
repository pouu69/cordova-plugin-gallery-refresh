var exec = require('cordova/exec');

function GalleryRefresh(){

}

GalleryRefresh.prototype.refresh = function(path, successCb, errorCb){
  if (typeof successCb != 'function') {
      throw new Error('SaveImage Error: successCb is not a function');
  }

  if (typeof errorCb != 'function') {
      throw new Error('SaveImage Error: errorCb is not a function');
  }

  exec(function(params){ successCb(params); }, function(error){ errorCb(error); }, "GalleryRefresh", "refresh", [path]);
}

window.galleryRefresh = new GalleryRefresh();
module.exports = galleryRefresh;
