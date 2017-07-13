#import "GalleryRefresh.h"
#import <Cordova/CDV.h>

@implementation GalleryRefresh
@synthesize callbackId;

- (void)refresh:(CDVInvokedUrlCommand*)command
{
  [self performSelectorInBackground:@selector(saveImage2Gallery:) withObject:command];
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
		pluginResult = [CDVPluginResult resultWithStatus: CDVCommandStatus_OK messageAsString:@"Image saved"];
  }
  [self.commandDelegate sendPluginResult:pluginResult callbackId:self.callbackId];
}

@end
