var exec = require('cordova/exec');

function GalleryRefresh(){

}

function _getLocalImagePathWithoutPrefix(url) {
    if (url.indexOf('file:///') === 0) {
        return url.substring(7);
    }
    return url;
}

GalleryRefresh.prototype.refresh = function(path, successCb, errorCb){
  if (typeof successCb != 'function') {
      throw new Error('SaveImage Error: successCb is not a function');
  }

  if (typeof errorCb != 'function') {
      throw new Error('SaveImage Error: errorCb is not a function');
  }

  var withoutPrefixPath = _getLocalImagePathWithoutPrefix(path);
  exec(function(params){ successCb(params); }, function(error){ errorCb(error); }, "GalleryRefresh", "refresh", [withoutPrefixPath]);


}

window.galleryRefresh = new GalleryRefresh();
module.exports = galleryRefresh;
