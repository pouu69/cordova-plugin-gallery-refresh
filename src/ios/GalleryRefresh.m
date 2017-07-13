/********* GalleryRefresh.m Cordova Plugin Implementation *******/

#import "GalleryRefresh.h"
#import <Cordova/CDV.h>

@implementation GalleryRefresh
@synthesize callbackId;

- (void)refresh:(CDVInvokedUrlCommand*)command
{
  [self performSelectorInBackground:@selector(saveImage2Gallery) withObject:command];

  CDVPluginResult* pluginResult = nil;
  NSString* echo = [command.arguments objectAtIndex:0];

  if (echo != nil && [echo length] > 0) {
      pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:echo];
  } else {
      pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
  }

  [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)saveImage2Gallery:(CDVInvokedUrlCommand*)command
{
  NSString* imgPath = [command.arguments objectAtIndex:0];

  NSLog(@"Image absolute path: %@", imgPath);

  UIImage *image = [UIImage imageWithContentsOfFile:imgPath];
  UIImageWriteToSavedPhotosAlbum(image, self, @selector(image:didFinishSavingWithError:contextInfo:), nil);
}

- (void)image:(UIImage *)image didFinishSavingWithError:(NSError *)error contextInfo:(void *)contextInfo
{
  CDVPluginResult* pluginResult = nil;

  if (error != NULL) {
    NSLog(@"SaveImage, error: %@",error);
		pluginResult = [CDVPluginResult resultWithStatus: CDVCommandStatus_ERROR messageAsString:error.description];
  } else {
    NSLog(@"SaveImage, image saved");
		CDVPluginResult* result = [CDVPluginResult resultWithStatus: CDVCommandStatus_OK messageAsString:@"Image saved"];
  }
  [self.commandDelegate sendPluginResult:pluginResult callbackId:self.callbackId];
}

@end
