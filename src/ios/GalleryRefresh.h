#import <Cordova/CDVPlugin.h>

@interface SaveImage : CDVPlugin {
	NSString* callbackId;
}

@property (nonatomic, copy) NSString* callbackId;

- (void)refresh:(CDVInvokedUrlCommand*)command;

@end
