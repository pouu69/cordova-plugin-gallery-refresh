# cordova-plugin-gallery-refresh

## install
Just add this line in config.xml
````
// config.xml
<plugin name="cordova-plugin-gallery-refresh" spec="^1.0.2" />
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
