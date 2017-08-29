# cordova-plugin-gallery-refresh
## Device
- android
- ios

## What is it?
> It is a plugin that allows you to do scanning after the download.

`inappbrowser` plugin을 사용하여 remote web을 사용할 때 web상에서 `FileTransfer` 를 이용하여 이미지를 다운로드 하면 디바이스 포토갤러리에서 자동으로 인식하여 리스팅 해줘야 하는데 현재 `cordova` 는 지원하지 않고 수동으로 스캐닝 해줘야 한다.  
그래서 강제로 다운로드 이후 스캐닝 작업을 해줘 포토갤러리에서 인식하게 해주는 plugin이다.

## install
Just add this line in config.xml
````
// config.xml
<plugin name="cordova-plugin-gallery-refresh" />

// or
$ cordova plugin add cordova-plugin-gallery-refresh
````

## Usage
```` javascript
// call refresh method after file transfer download success
window.galleryRefresh.refresh(
  entry.toURL(), // file local path
  function(success){ console.log(success); }, // success callback
  function(error){ console.log(error); } // error callback
);
````

```` javascript
// Exam
  var fileTransfer = new FileTransfer();
  var uri = encodeURI(this.args.url);
  var fileURL = fileEntry.toURL();

  console.log(fileEntry);
  fileTransfer.download(
      uri, // file's uri
      fileURL, // where will be saved
      function (entry) {
          console.log("Successful download..." , entry.toURL());
          window.galleryRefresh.refresh(
            entry.toURL(),
            function(success){ console.log(success); },
            function(error){ console.log(error); }
          );
      },
      function (error) {
          console.log("download error source " + error.source);
          console.log("download error target " + error.target);
          console.log("upload error code" + error.code);
      },
      null, // or, pass false
      {}
  );
````
